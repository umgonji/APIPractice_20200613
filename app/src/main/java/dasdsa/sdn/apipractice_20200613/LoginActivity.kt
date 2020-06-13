package dasdsa.sdn.apipractice_20200613

import android.os.Bundle
import dasdsa.sdn.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            //실제로 서버에 두개의 변수를 전달해서 로그인 시도
            //별개의 클래스 (ServerUtil) 에 서버 요청 기능을 만들고, 화면에서는 이를 사용.


        }
    }

    override fun setValues() {
    }


}
