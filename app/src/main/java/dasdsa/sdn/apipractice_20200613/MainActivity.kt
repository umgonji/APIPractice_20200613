package dasdsa.sdn.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setValues()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        //서버에서 내 정보를 받아와서 화면에 출력

    }


}
