package com.haocxx.haocxxrecorder.listener

import android.os.Bundle
import android.util.Log
import com.iflytek.cloud.RecognizerListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechError

/**
 * Created by Haocxx
 * on 2019/1/7
 */
class IFlyRecognizerListener : RecognizerListener {
    private val TAG = "IFlyRecognizerListener"
    private var mCallback : Callback? = null

    override fun onVolumeChanged(p0: Int, p1: ByteArray?) {
        Log.d(TAG, "onVolumeChanged")
    }

    override fun onResult(recognizerResult: RecognizerResult?, p1: Boolean) {
        mCallback?.onCallback(recognizerResult?.resultString)
    }

    override fun onBeginOfSpeech() {
        Log.d(TAG, "onBeginOfSpeech")
    }

    override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
        Log.d(TAG, "onEvent")
    }

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech")
    }

    override fun onError(error: SpeechError?) {
        mCallback?.onErrorCalback()
    }

    fun setCallback(callback : Callback) {
        mCallback = callback
    }

    interface Callback {
        fun onCallback(s : String?)

        fun onErrorCalback()
    }
}