package tt.reducto.log

import android.R.attr.tag
import android.util.Log


/**
 * 提供给OkHttp 专用。
 *
 * <p>
 *     ......。
 * </p>
 *
 * <ul>
 *     <li></li>
 * </ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/31/20 10:50<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
class TTHttpLog private constructor() {

    companion object {
        private val mOkHttpBuffer = StringBuffer()
        private var printer: Operator = TTLogOperator()
        private var segmentSize = 3 * 1024
        init {
            /* 配置 */
            val s = TTFormatStrategy.newBuilder()
            s.tag = "okHttp"
            s.showThreadInfo = false
            s.methodCount = 0
            printer.addAdapter(AndroidLogAdapter(s.build()))
        }

        private fun d(any: Any?) {
            any?.let { printer.d(it) }
        }

        /**
         * okHttp 打印.
         */
        @JvmStatic
        @Synchronized
        fun okHttp(message: String, isLoggable: Boolean = true) {
            if (!isLoggable)
                return
            // 请求或者响应开始
            if (message.startsWith("--> POST") || message.startsWith("--> GET")
                || message.startsWith("--> PUT") || message.startsWith("--> DELETE")
            ) {
                try {
                    mOkHttpBuffer.setLength(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }
            }
            mOkHttpBuffer.append(message).append("\n")
            // 响应结束，打印整条日志
            if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))
            ) {
                /* 格式化 json  */
                mOkHttpBuffer.append(JsonTools.formatJson(JsonTools.decodeUnicode(message)))
                mOkHttpBuffer.append("\n")
            }
            if (message.startsWith("<-- END HTTP")) {
                if (mOkHttpBuffer.toString().length <= segmentSize) { // 长度小于等于限制直接打印
                    d(mOkHttpBuffer.toString())
                } else {
                    try {
                        var msg = mOkHttpBuffer.toString()
                        while (msg.length > segmentSize) { // 循环分段打印日志
                            val logContent: String = msg.substring(0, segmentSize)
                            msg = msg.replace(logContent, "")
                            d(logContent)
                        }
                        // 打印剩余日志
                        d(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                try {
                    mOkHttpBuffer.setLength(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }
            }
        }
    }
}