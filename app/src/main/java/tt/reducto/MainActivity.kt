package tt.reducto

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tt.reducto.log.TTLog

class MainActivity : AppCompatActivity() {
    private var  m :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var s= ""
//        for (i in 0..4000) {
//            s += "$i"
//            TTHttpLog.okHttp(s)
            TTLog.json("{\"msg\":\"\",\"error\":0,\"data\":{\"list\":[],\"page\":1,\"page_size\":10,\"by_ai\":1,\"district_code\":110000000000}}")
//        }

//        TTLog.d(m)
        MainTools()
        try {
            //  <data android:scheme= "artist"
            //                    android:host= "first"
            //                    android:path= "/enter" />
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("artist://first/enter")
            )
            startActivity(intent)
        } catch (e: Exception) {
        }

    }
}