package dasdsa.sdn.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sing_up.*

class SingUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        emailCheckBtn.setOnClickListener {
            //입력한 이메일이 이미 회원으로 있는지 확인 => 서버에 요청
            val inputEmail = emailEdt.text.toString()



        }
    }

    override fun setValues() {
    }


}
