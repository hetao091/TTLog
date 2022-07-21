package tt.reducto.log

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
                /* 格式化 json */
                mOkHttpBuffer.append(JsonTools.formatJson(JsonTools.decodeUnicode(message)))
                mOkHttpBuffer.append("\n")
            }
            if (message.startsWith("<-- END HTTP")) {
                d(mOkHttpBuffer.toString())
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