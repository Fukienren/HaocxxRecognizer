package com.haocxx.haocxxrecorder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.baidu.aip.asrwakeup3.core.mini.ActivityMiniRecog
import com.haocxx.haocxxrecorder.receiver.ScreenBroadcastReceiver

class LockScreenRecognizeActivity2 : ActivityMiniRecog(), ScreenBroadcastReceiver.Callback {
    private val TOAST_STRING = "点击开始启动息屏下语音识别\n" + "该模式下只有关闭屏幕下识别才会工作\n" + "默认使用百度SDK\n" +"请息屏之后开始说话"

    private lateinit var mScreenBroadcastReceiver : ScreenBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "息屏下静默识别"
        Toast.makeText(this, TOAST_STRING, Toast.LENGTH_LONG).show()
        btn.visibility = View.INVISIBLE
        stopBtn.visibility = View.INVISIBLE
        mScreenBroadcastReceiver = ScreenBroadcastReceiver()
        mScreenBroadcastReceiver.setCallback(this)
        mScreenBroadcastReceiver.startScreenBroadcastReceiver(this)
    }

    override fun onScreenOn() {
        stop()
    }

    override fun onScreenOff() {
        start()
    }
}
