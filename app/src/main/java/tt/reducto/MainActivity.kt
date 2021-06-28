package tt.reducto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tt.reducto.log.TTHttpLog
import tt.reducto.log.TTLog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var s= ""
//        for (i in 0..4000) {
//            s += "$i"
//            TTHttpLog.okHttp(s)
            TTLog.json("{\"msg\":\"\",\"error\":0,\"data\":{\"list\":[],\"page\":1,\"page_size\":10,\"by_ai\":1,\"district_code\":110000000000}}")
//        }

    }
}