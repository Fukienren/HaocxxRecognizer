package com.haocxx.haocxxrecorder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.haocxx.haocxxrecorder.listener.IFlyRecognizerListener
import com.haocxx.haocxxrecorder.receiver.ScreenBroadcastReceiver
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechRecognizer
import kotlinx.android.synthetic.main.activity_lock_screen_recognize.*

/**
 * 科大讯飞的SDK有使用次数限制,超出次数之后将无法识别
 */
class LockScreenRecognizeActivity : AppCompatActivity(), InitListener, IFlyRecognizerListener.Callback, ScreenBroadcastReceiver.Callback {

    private val START_BTN_TEXT_STRING = "点击启动息屏下语音识别\n" + "该模式下只有关闭屏幕下识别才会工作\n" + "默认使用科大讯飞SDK"

    private lateinit var mSpeechRecognizer : SpeechRecognizer
    private lateinit var mIFlyRecognizerListener : IFlyRecognizerListener
    private lateinit var mScreenBroadcastReceiver : ScreenBroadcastReceiver
    private var inited = false
    private var hasStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen_recognize)
        title = "息屏下静默识别"
        mScreenBroadcastReceiver = ScreenBroadcastReceiver()
        mScreenBroadcastReceiver.setCallback(this)
        mScreenBroadcastReceiver.startScreenBroadcastReceiver(this)
        start_btn.text = START_BTN_TEXT_STRING
        start_btn.setOnClickListener {
            hasStart = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mScreenBroadcastReceiver.stopScreenBroadcastReceiver(this)
        if (inited) {
            releaseRecognizer()
        }
    }

    private fun init() {
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(this, this)
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mSpeechRecognizer.setParameter( SpeechConstant.CLOUD_GRAMMAR, null )
        mSpeechRecognizer.setParameter( SpeechConstant.SUBJECT, null )
        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "plain")
        //此处engineType为“cloud”
        mSpeechRecognizer.setParameter( SpeechConstant.ENGINE_TYPE, "cloud")
        //设置语音输入语言，zh_cn为简体中文
        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
        //设置结果返回语言
        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin")
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000")
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, "1000")
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT,"1")

        //开始识别，并设置监听器
        mIFlyRecognizerListener = IFlyRecognizerListener()
        mIFlyRecognizerListener.setCallback(this)
        mSpeechRecognizer.startListening(mIFlyRecognizerListener)
        inited = true
    }

    private fun releaseRecognizer() {
        mSpeechRecognizer.stopListening()
        mSpeechRecognizer.cancel()
        inited = false
    }

    private fun printLog(s : String?) {
        log_content_view.append("$s。")
    }

    override fun onInit(p0: Int) {

    }

    override fun onCallback(s : String?) {
        printLog(s)
        init()
    }

    override fun onErrorCalback() {
        init()
    }

    override fun onScreenOn() {
        if (hasStart) {
            releaseRecognizer()
        }
    }

    override fun onScreenOff() {
        if (hasStart) {
            init()
        }
    }
}
