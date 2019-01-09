package com.haocxx.haocxxrecorder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.ui.RecognizerDialog
import com.iflytek.cloud.ui.RecognizerDialogListener
import kotlinx.android.synthetic.main.activity_ifly_recognize_demo2.*

class IFlyRecognizeDemoActivity2 : AppCompatActivity() {
    private val TAG = "IFlyRecognizeActivity2"
    private var mIatDialog: RecognizerDialog? = null
    private var mToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifly_recognize_demo2)
        title = "科大讯飞语音识别"
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        mIatDialog = RecognizerDialog(this, mInitListener)

        start_btn.setOnClickListener {
            content_text_view.text = ""
            mIatDialog?.setListener(mRecognizerDialogListener)
            mIatDialog?.show()
        }
    }

    private val mInitListener = InitListener { code ->
        Log.d(TAG, "SpeechRecognizer init() code = $code")
        if (code != ErrorCode.SUCCESS) {
            showTip("初始化失败，错误码：$code")
        }
    }

    private fun showTip(str: String) {
        mToast?.setText(str)
        mToast?.show()
    }

    /**
     * 听写UI监听器
     */
    private val mRecognizerDialogListener = object : RecognizerDialogListener {
        override fun onResult(results: RecognizerResult, isLast: Boolean) {
//            val trans = JsonParser.parseTransResult(results.resultString, "dst")
//            val oris = JsonParser.parseTransResult(results.resultString, "src")
            content_text_view.append(results.resultString + "\n")
        }

        /**
         * 识别回调错误.
         */
        override fun onError(error: SpeechError) {
            if (error.errorCode == 14002) {
                showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能")
            } else {
                showTip(error.getPlainDescription(true))
            }
        }

    }
}
