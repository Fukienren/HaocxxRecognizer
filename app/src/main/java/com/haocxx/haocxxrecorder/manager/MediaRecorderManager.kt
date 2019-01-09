package com.haocxx.haocxxrecorder.manager

import android.media.MediaRecorder
import java.io.File

/**
 * Created by Haocxx
 * on 2019/1/2
 */
class MediaRecorderManager {

    var isRecording : Boolean = false

    private lateinit var mMediaRecorder : MediaRecorder
    private var mOnErrorListener : OnErrorListener? = null

    fun init() {
        mMediaRecorder = MediaRecorder()

        // 设置音频录入源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        mMediaRecorder.setOnErrorListener { _: MediaRecorder, _: Int, _: Int ->
            destroy()
            mOnErrorListener?.onMediaRecorderError()
        }
    }

    fun destroy() {
        if (isRecording) {
            mMediaRecorder.stop()
            mMediaRecorder.release()
            isRecording = false
        }
    }

    fun start(path : String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        mMediaRecorder.setOutputFile(file.absolutePath)
        mMediaRecorder.prepare()
        mMediaRecorder.start()
        isRecording = true
    }

    fun stop() {
        if (isRecording) {
            mMediaRecorder.stop()
            mMediaRecorder.release()
            isRecording = false
        }
    }

    fun setOnErrorListener(onErrorListener: OnErrorListener) {
        mOnErrorListener = onErrorListener
    }

    interface OnErrorListener {
        fun onMediaRecorderError()
    }
}