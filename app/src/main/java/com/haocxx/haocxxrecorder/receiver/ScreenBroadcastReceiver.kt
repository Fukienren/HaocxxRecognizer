package com.haocxx.haocxxrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * Created by Haocxx
 * on 2019/1/11
 */
class ScreenBroadcastReceiver : BroadcastReceiver() {
    var mCallback : Callback? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        when (action) {
            Intent.ACTION_SCREEN_ON -> {
                // 开屏
                mCallback?.onScreenOn()
            }
            Intent.ACTION_SCREEN_OFF -> {
                // 锁屏
                mCallback?.onScreenOff()
            }
            Intent.ACTION_USER_PRESENT -> {
                // 解锁
            }
        }
    }

    fun startScreenBroadcastReceiver(context: Context?) {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        context?.registerReceiver(this, filter)
    }

    fun stopScreenBroadcastReceiver(context: Context?) {
        context?.unregisterReceiver(this)
    }

    fun setCallback(calllback : Callback) {
        mCallback = calllback
    }

    interface Callback {
        fun onScreenOn()
        fun onScreenOff()
    }
}