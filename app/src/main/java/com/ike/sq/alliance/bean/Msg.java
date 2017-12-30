package com.ike.sq.alliance.bean;


import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @Author: [liguiqin]
 * @Date: [2017-07-19 14:31]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
@Entity
public class Msg implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    private Long id;
    /**
     * 发送人
     */
    @Property(nameInDb = "senderName")
    private String senderName;
    /**
     * 发送人id
     */
    @Property(nameInDb = "senderId")
    private String senderId;
    /**
     * 发送人头像
     */
    @Property(nameInDb = "senderHeadPath")
    private String senderHeadPath;
    /**
     * 发送内容
     */
    @Property(nameInDb = "content")
    private String content;
    /**
     * 发送的内容类型
     * TEXT 文本
     * VOICE 声音
     * VIDEO 视频
     * FILE 文件
     * IMG 图片
     */
    @Property(nameInDb = "msgCategory")
    private String msgCategory;
    /**
     * 消息类型
     */
    @Property(nameInDb = "msgType")
    private String msgType;//消息类型
    @Property(nameInDb = "type")
    private int type;
    @Property(nameInDb = "time")
    private String time;

    /**
     * 接收组
     */
    @Property(nameInDb = "group")
    private String group;

    /**
     * 接收人
     */
    @Property(nameInDb = "receiver")
    private String receiver;

    /**
     * 上下线用户
     */
    @Property(nameInDb = "userId")
    private String userId;

    /**
     * 是否为已读消息
     */
    @Property(nameInDb = "read")
    private boolean read;

    @Property(nameInDb = "UserVoiceTime")
    private float userVoiceTime;
    @Property(nameInDb = "UserVoicePath")
    private String userVoicePath;
    @Property(nameInDb = "UserVoiceUrl")
    private String userVoiceUrl;
    @Property(nameInDb = "sendState")
    private int sendState;
    @Property(nameInDb = "imageUrl")
    private String imageUrl;
    @Property(nameInDb = "imageIconUrl")
    private String imageIconUrl;
    @Property(nameInDb = "imageLocal")
    private String imageLocal;
    @Property(nameInDb = "friendId")
    private String friendId;

    @Keep
    public Msg(Long id, String senderName, String senderId, String senderHeadPath, String content,
               String msgCategory,String msgType, String group, String receiver, String userId, boolean read,
               int type, String time, float userVoiceTime, String userVoicePath, String userVoiceUrl,
               int sendState, String imageUrl, String imageIconUrl, String imageLocal, String friendId) {
        this.id = id;
        this.senderName = senderName;
        this.senderId = senderId;
        this.senderHeadPath = senderHeadPath;
        this.content = content;
        this.msgCategory=msgCategory;
        this.msgType = msgType;
        this.group = group;
        this.receiver = receiver;
        this.userId = userId;
        this.read = read;
        this.time = time;
        this.type = type;
        this.userVoiceTime = userVoiceTime;
        this.userVoicePath = userVoicePath;
        this.userVoiceUrl = userVoiceUrl;
        this.sendState = sendState;
        this.imageUrl = imageUrl;
        this.imageIconUrl = imageIconUrl;
        this.imageLocal = imageLocal;
        this.friendId = friendId;
    }


    @Generated(hash = 23037457)
    public Msg() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderHeadPath() {
        return senderHeadPath;
    }

    public void setSenderHeadPath(String senderHeadPath) {
        this.senderHeadPath = senderHeadPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgCategory() {
        return msgCategory;
    }

    public void setMsgCategory(String msgCategory) {
        this.msgCategory = msgCategory;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public float getUserVoiceTime() {
        return userVoiceTime;
    }

    public void setUserVoiceTime(float userVoiceTime) {
        this.userVoiceTime = userVoiceTime;
    }

    public String getUserVoicePath() {
        return userVoicePath;
    }

    public void setUserVoicePath(String userVoicePath) {
        this.userVoicePath = userVoicePath;
    }

    public String getUserVoiceUrl() {
        return userVoiceUrl;
    }

    public void setUserVoiceUrl(String userVoiceUrl) {
        this.userVoiceUrl = userVoiceUrl;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageIconUrl() {
        return imageIconUrl;
    }

    public void setImageIconUrl(String imageIconUrl) {
        this.imageIconUrl = imageIconUrl;
    }

    public String getImageLocal() {
        return imageLocal;
    }

    public void setImageLocal(String imageLocal) {
        this.imageLocal = imageLocal;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "{" +
                "senderName:'" + senderName + '\'' +
                ", senderId:'" + senderId + '\'' +
                //", senderHeadPath:'" + senderHeadPath + '\'' +
                ", content:'" + content + '\'' +
                ", msgCategory:'" + msgCategory + '\'' +
                ", messageType:'" + msgType + '\'' +
                // ", type:" + type +
                ", receiver:'" + receiver + '\'' +
                '}';
    }
}
