package com.haocxx.haocxxrecorder

import android.os.Bundle
import com.baidu.aip.asrwakeup3.core.mini.ActivityMiniRecog

/**
 * Created by Haocxx
 * on 2019/1/7
 */
class BaiduRecognizeDemoActivity : ActivityMiniRecog() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "百度语音识别"
    }
}
