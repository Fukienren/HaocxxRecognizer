package com.haocxx.haocxxrecorder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.haocxx.haocxxrecorder.manager.MediaRecorderManager
import com.haocxx.haocxxrecorder.util.RecordPermissionUtil
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Haocxx
 * on 2019/1/2
 */
class MainActivity : AppCompatActivity(), MediaRecorderManager.OnErrorListener{

    private lateinit var mMediaRecorderManager : MediaRecorderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // iFly SDK init
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5c33455a")

        mMediaRecorderManager = MediaRecorderManager()
        mMediaRecorderManager.setOnErrorListener(this)

//        media_recorder_switch.setOnClickListener {
//            if (mMediaRecorderManager.isRecording) {
//                mMediaRecorderManager.stop()
//                media_recorder_switch.text = "start"
//            } else {
//                if (!RecordPermissionUtil.getRecordPermissionState(this@MainActivity)) {
//                    RecordPermissionUtil.requestRecordPermission(this)
//                } else {
//                    mMediaRecorderManager.init()
//                    media_recorder_switch.text = "stop"
//                    mMediaRecorderManager.start(Environment.getExternalStorageDirectory().path + "/mediarecorder.amr")
//                }
//            }
//        }

        baidu_recognize_btn.setOnClickListener {
            val intent = Intent(this@MainActivity, BaiduRecognizeDemoActivity::class.java)
            startActivity(intent)
        }

        ifly_recognize_btn.setOnClickListener {
            val intent = Intent(this@MainActivity, IFlyRecognizeDemoActivity::class.java)
            startActivity(intent)
        }

        lock_screen_recognize_btn.setOnClickListener {
            val intent = Intent(this@MainActivity, LockScreenRecognizeActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaRecorderManager.destroy()
    }

    override fun onMediaRecorderError() {
        //media_recorder_switch.text = "start"
    }
}
