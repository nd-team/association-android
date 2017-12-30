package com.ike.sq.alliance.ui.widget

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ike.sq.alliance.R
import java.util.HashMap

/**
 * Created by T-BayMax on 2017/9/7.
 */
class SendDialog {

    open fun initDialog(view: View,styleId:Int):Dialog{
        var dialog = Dialog(view.context, styleId)

        dialog.setCancelable(false)
        dialog.setContentView(view)
        val dialogWindow = dialog.getWindow()
        dialogWindow.setGravity(Gravity.BOTTOM)
        dialogWindow.setWindowAnimations(R.style.dialogstyle) // 添加动画
        val lp = dialogWindow.getAttributes() // 获取对话框当前的参数值
        lp.x = 0 // 新位置X坐标
        lp.y = -20 // 新位置Y坐标
        lp.width = (view.context.getResources().getDisplayMetrics().widthPixels*0.95).toInt() // 宽度

        view.measure(0, 0)
        lp.height = view.measuredHeight+120
       // lp.alpha = 9f // 透明度
        dialogWindow.setAttributes(lp)
        return dialog
    }
}