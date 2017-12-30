package com.ike.sq.alliance.chat.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;


import com.ike.sq.alliance.App;
import com.ike.sq.alliance.bean.Msg;
import com.ike.sq.alliance.chat.adapter.ChatRecyclerAdapter;
import com.ike.sq.alliance.chat.animator.SlideInOutBottomItemAnimator;
import com.ike.sq.alliance.chat.common.ChatConst;
import com.ike.sq.alliance.chat.utils.KeyBoardUtils;
import com.ike.sq.alliance.chat.widget.AudioRecordButton;
import com.ike.sq.alliance.chat.widget.pulltorefresh.PullToRefreshRecyclerView;
import com.ike.sq.alliance.chat.widget.pulltorefresh.PullToRefreshView;
import com.ike.sq.alliance.chat.widget.pulltorefresh.WrapContentLinearLayoutManager;
import com.ike.sq.alliance.listener.WebSocketConnectionListener;
import com.ike.sq.alliance.mvp.contract.IRecyclerViewChatView;
import com.ike.sq.alliance.mvp.presenter.RecyclerViewChatPresenter;
import com.ike.sq.alliance.ui.activity.GroupDetailActivity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RecyclerViewChatActivity extends BaseChatActivity implements
        WebSocketConnectionListener.ChatDataObserver
        , IRecyclerViewChatView {
    private PullToRefreshRecyclerView myList;
    private ChatRecyclerAdapter tbAdapter;
    private SendMessageHandler sendMessageHandler;
    private WrapContentLinearLayoutManager wcLinearLayoutManger;
    //private Friend friend;
    private RecyclerViewChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewChatActivity.this.finish();
            }
        });
        presenter = new RecyclerViewChatPresenter(this, this);
    }

    @Override
    protected void findView() {
        super.findView();
        //friend = getIntent().getParcelableExtra("friend");
        pullList.setSlideView(new PullToRefreshView(this).getSlideView(PullToRefreshView.RECYCLERVIEW));
        myList = (PullToRefreshRecyclerView) pullList.returnMylist();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tblist.clear();
        tbAdapter.notifyDataSetChanged();
        myList.setAdapter(null);
        sendMessageHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected void init() {
        tbAdapter = new ChatRecyclerAdapter(this, tblist);
        wcLinearLayoutManger = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myList.setLayoutManager(wcLinearLayoutManger);
        myList.setItemAnimator(new SlideInOutBottomItemAnimator(myList));
        myList.setAdapter(tbAdapter);
        sendMessageHandler = new SendMessageHandler(this);
        tbAdapter.isPicRefresh = true;
        tbAdapter.notifyDataSetChanged();
        tbAdapter.setSendErrorListener(new ChatRecyclerAdapter.SendErrorListener() {

            @Override
            public void onClick(int position) {
                Msg tbub = tblist.get(position);
                if (tbub.getType() == ChatRecyclerAdapter.TO_USER_VOICE) {
                    sendVoice(tbub.getUserVoiceTime(), tbub.getContent());//播放语音消息
                    tblist.remove(position);
                } else if (tbub.getType() == ChatRecyclerAdapter.TO_USER_IMG) {
                    sendImage(tbub.getContent());
                    tblist.remove(position);
                }
            }

        });
        tbAdapter.setVoiceIsReadListener(new ChatRecyclerAdapter.VoiceIsRead() {

            @Override
            public void voiceOnClick(int position) {

                for (int i = 0; i < tbAdapter.unReadPosition.size(); i++) {
                    if (tbAdapter.unReadPosition.get(i).equals(position + "")) {
                        tbAdapter.unReadPosition.remove(i);
                        break;
                    }
                }
            }

        });
        voiceBtn.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {

            @Override
            public void onFinished(float seconds, String filePath) {//录音结束，发送

                sendVoice(seconds, filePath);
            }

            @Override
            public void onStart() {//开始录音

                tbAdapter.stopPlayVoice();
            }
        });
        myList.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

                switch (scrollState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        tbAdapter.handler.removeCallbacksAndMessages(null);
                        tbAdapter.setIsGif(true);
                        tbAdapter.isPicRefresh = false;
                        tbAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        tbAdapter.handler.removeCallbacksAndMessages(null);
                        tbAdapter.setIsGif(false);
                        tbAdapter.isPicRefresh = true;
                        reset();
                        KeyBoardUtils.hideKeyBoard(RecyclerViewChatActivity.this,
                                mEditTextContent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        iv_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewChatActivity.this, GroupDetailActivity.class);
                intent.putExtra("groupBean", groupBean);
                startActivity(intent);
            }
        });
        controlKeyboardLayout(activityRootView, pullList);
        super.init();
    }

    /**
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final View needToScrollView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                RecyclerViewChatActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = RecyclerViewChatActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                int recyclerHeight = 0;
                if (wcLinearLayoutManger != null) {
                    recyclerHeight = wcLinearLayoutManger.getRecyclerHeight();
                }
                if (heightDifference == bottomStatusHeight) {
                    needToScrollView.scrollTo(0, 0);
                } else {
                    if (heightDifference < recyclerHeight) {
                        int contentHeight = wcLinearLayoutManger == null ? 0 : wcLinearLayoutManger.getHeight();
                        if (recyclerHeight < contentHeight) {
                            listSlideHeight = heightDifference - (contentHeight - recyclerHeight);
                            needToScrollView.scrollTo(0, listSlideHeight);
                        } else {
                            listSlideHeight = heightDifference;
                            needToScrollView.scrollTo(0, listSlideHeight);
                        }
                    } else {
                        listSlideHeight = 0;
                    }
                }
            }
        });
    }

    @Override
    protected void loadRecords() {
        isDown = true;
        if (pagelist != null) {
            pagelist.clear();
        }

        pagelist = mChatDbManager.loadPages(condition, page, number);
        position = pagelist.size();
        if (pagelist.size() != 0) {
            pagelist.addAll(tblist);
            tblist.clear();
            tblist.addAll(pagelist);
            if (imageList != null) {
                imageList.clear();
            }
            if (imagePosition != null) {
                imagePosition.clear();
            }
            int key = 0;
            int position = 0;
            for (Msg cmb : tblist) {
                if (cmb.getType() == ChatRecyclerAdapter.FROM_USER_IMG || cmb.getType() == ChatRecyclerAdapter.TO_USER_IMG) {
                    imageList.add(cmb.getImageLocal());
                    imagePosition.put(key, position);
                    position++;
                }
                key++;
            }
            tbAdapter.setImageList(imageList);
            tbAdapter.setImagePosition(imagePosition);
            sendMessageHandler.sendEmptyMessage(PULL_TO_REFRESH_DOWN);
            if (page == 0) {
                pullList.refreshComplete();
                pullList.setPullGone();
            } else {
                page--;
            }
        } else {
            if (page == 0) {
                pullList.refreshComplete();
                pullList.setPullGone();
            }
        }
    }

    @Override
    public void OnMessage(Msg msg) {
        tblist.add(msg);
        switch (msg.getMsgCategory()) {
            case "TEXT":
                receriveMsgText(msg);
                break;
            case "IMG":
                receriveImageText(msg);
                break;
            case "VOICE":
                receriveVoiceText(msg);
                break;
            default:
                break;
        }
        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
    }

    @Override
    public void onNetClose() {

    }

    /**
     * 上传文件回调
     *
     * @param msg
     */
    @Override
    public void upload(List<String> msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                tblist.add(getTbub(userName, null, null, content, null, null,
                        null, null, false, ChatRecyclerAdapter.TO_USER_IMG,
                        null, 0f, null, null, ChatConst.COMPLETED, null, null, filePath));


                imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                sendMessageHandler.sendEmptyMessage(SEND_OK);
                RecyclerViewChatActivity.this.filePath = filePath;
                //receriveHandler.sendEmptyMessageDelayed(1, 3000);

            }
        }).start();
    }

    /**
     * 错误消息回调
     *
     * @param errorString
     */
    @Override
    public void showError(String errorString) {
        Toast.makeText(RecyclerViewChatActivity.this,errorString,Toast.LENGTH_LONG).show();
    }

    static class SendMessageHandler extends Handler {
        WeakReference<RecyclerViewChatActivity> mActivity;

        SendMessageHandler(RecyclerViewChatActivity activity) {
            mActivity = new WeakReference<RecyclerViewChatActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RecyclerViewChatActivity theActivity = mActivity.get();
            if (theActivity != null) {
                switch (msg.what) {
                    case REFRESH:
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyDataSetChanged();
                        int position = theActivity.tbAdapter.getItemCount() - 1 < 0 ? 0 : theActivity.tbAdapter.getItemCount() - 1;
                        theActivity.myList.smoothScrollToPosition(position);
                        break;
                    case SEND_OK:
                        theActivity.mEditTextContent.setText("");
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyItemInserted(theActivity.tblist
                                .size() - 1);
                        theActivity.myList.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                        break;
                    case RECERIVE_OK:
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyItemInserted(theActivity.tblist
                                .size() - 1);
                        theActivity.myList.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                        break;
                    case PULL_TO_REFRESH_DOWN:
                        theActivity.pullList.refreshComplete();
                        theActivity.tbAdapter.notifyDataSetChanged();
                        theActivity.myList.smoothScrollToPosition(theActivity.position - 1);
                        theActivity.isDown = false;
                        break;
                    default:
                        break;
                }
            }
        }

    }

    /**
     * 发送文字
     */
    @Override
    protected void sendMessage() {
        final String content = mEditTextContent.getText().toString();
        if (null == content || content.equals("")) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                tblist.add(getTbub(App.userBean.getNickname(), App.userBean.getId(), App.userBean.getHeadPath(), content, "TEXT", null,
                        id, null, false, ChatRecyclerAdapter.TO_USER_MSG,
                        null, 0f, null, null, ChatConst.COMPLETED, null, null, null));
                sendMessageHandler.sendEmptyMessage(SEND_OK);
                RecyclerViewChatActivity.this.content = content;
                //receriveHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }).start();
    }

    /**
     * 接收文字
     */
    String content = "";

    private void receriveMsgText(Msg msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "回复：" + content;
                Msg tbub = new Msg();
                tbub.setSenderName(userName);
                String time = returnTime();
                tbub.setContent(message);
                tbub.setTime(time);
                tbub.setType(ChatRecyclerAdapter.FROM_USER_MSG);
                tblist.add(tbub);
                sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                mChatDbManager.insert(tbub);
            }
        }).start();
    }

    /**
     * 发送图片
     */
    int i = 0;

    @Override
    protected void sendImage(final String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        presenter.upload(App.token, requestBody);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                if (i == 0) {
                    tblist.add(getTbub(userName, null, null, content, "IMG", null,
                            null, null, false, ChatRecyclerAdapter.TO_USER_IMG,
                            null, 0f, null, null, ChatConst.SENDING, null, null, filePath));
                } else if (i == 1) {
                    tblist.add(getTbub(userName, null, null, content, null, null,
                            null, null, false, ChatRecyclerAdapter.TO_USER_IMG,
                            null, 0f, null, null, ChatConst.SENDERROR, null, null, filePath));
                } else if (i == 2) {
                    tblist.add(getTbub(userName, null, null, content, null, null,
                            null, null, false, ChatRecyclerAdapter.TO_USER_IMG,
                            null, 0f, null, null, ChatConst.COMPLETED, null, null, filePath));
                    i = -1;
                }
                imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                sendMessageHandler.sendEmptyMessage(SEND_OK);
                RecyclerViewChatActivity.this.filePath = filePath;
                //receriveHandler.sendEmptyMessageDelayed(1, 3000);
                i++;
            }
        }).start();*/
    }

    /**
     * 接收图片
     */
    String filePath = "";

    private void receriveImageText(final Msg msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Msg tbub = new Msg();
                tbub.setSenderName(userName);
                String time = returnTime();
                tbub.setTime(time);
                filePath = msg.getImageUrl();
                if (filePath == null) {
                    {
                        filePath = msg.getContent();
                    }
                }
                tbub.setImageLocal(filePath);
                tbub.setType(ChatRecyclerAdapter.FROM_USER_IMG);
                tblist.add(tbub);
                imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                mChatDbManager.insert(tbub);
            }
        }).start();
    }

    /**
     * 发送语音
     */
    @Override
    protected void sendVoice(final float seconds, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tblist.add(getTbub(userName, null, null, content, "VOICE", null,
                        null, null, false, ChatRecyclerAdapter.TO_USER_VOICE,
                        null, seconds, filePath, null, ChatConst.SENDING, null, null, null));
                sendMessageHandler.sendEmptyMessage(SEND_OK);
                RecyclerViewChatActivity.this.seconds = seconds;
                voiceFilePath = filePath;
                //receriveHandler.sendEmptyMessageDelayed(2, 3000);
            }
        }).start();
    }

    /**
     * 接收语音
     */
    float seconds = 0.0f;
    String voiceFilePath = "";
String is="";
    private void receriveVoiceText(final Msg msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Msg tbub = new Msg();
                tbub.setSenderName(userName);
                String time = returnTime();
                tbub.setTime(time);
                seconds = msg.getUserVoiceTime();
                filePath = msg.getUserVoicePath();
                tbub.setUserVoiceTime(seconds);
                tbub.setUserVoicePath(filePath);
                tbAdapter.unReadPosition.add(tblist.size() + "");
                tbub.setType(ChatRecyclerAdapter.FROM_USER_VOICE);
                tblist.add(tbub);
                sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                mChatDbManager.insert(tbub);
            }
        }).start();
    }

    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


    /**
     * 为了模拟接收延迟
     */
   /* private Handler receriveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    receriveMsgText(content);
                    break;
                case 1:
                    receriveImageText(filePath);
                    break;
                case 2:
                    receriveVoiceText(seconds, voiceFilePath);
                    break;
                default:
                    break;
            }
        }
    };*/

}
