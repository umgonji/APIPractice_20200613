package dasdsa.sdn.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dasdsa.sdn.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sing_up.*
import org.json.JSONObject

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

            ServerUtil.getRequestDuplicatedCheck(mContext, "EMAIL", inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")
                    if( code == 200 ){
                        runOnUiThread {
                            emailCheckResultTxt.text = "사용해도 좋습니다."
                        }

                    }
                    else {
                        runOnUiThread {
                            emailCheckResultTxt.text = "이미 사용중입니다. 다른 이메일로 다시 체크해주세요."
                        }
                    }
                }
            })


        }
    }

    override fun setValues() {
    }


}
