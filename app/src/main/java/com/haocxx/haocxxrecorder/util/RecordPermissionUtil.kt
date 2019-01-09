package com.haocxx.haocxxrecorder.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.media.AudioRecord
import android.media.MediaRecorder



/**
 * Created by Haocxx
 * on 2019/1/2
 */
object RecordPermissionUtil {
    const val PERMISSION_REQUEST_CODE = 10086

    fun getRecordPermissionState(activity : Activity) : Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun requestRecordPermission(activity : Activity) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    }

    private fun validateMicAvailability(): Boolean {
        var available: Boolean? = true
        var recorder: AudioRecord? = AudioRecord(
            MediaRecorder.AudioSource.MIC, 44100,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_DEFAULT, 44100
        )
        try {
            if (recorder!!.recordingState != AudioRecord.RECORDSTATE_STOPPED) {
                available = false

            }

            recorder.startRecording()
            if (recorder.recordingState != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop()
                available = false

            }
            recorder.stop()
        } finally {
            recorder!!.release()
        }

        return available!!
    }
}