package dasdsa.sdn.apipractice_20200613

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import dasdsa.sdn.apipractice_20200613.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        singUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SingUpActivity::class.java)
            startActivity(myIntent)
        }

        loginBtn.setOnClickListener {
            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            //실제로 서버에 두개의 변수를 전달해서 로그인 시도
            //별개의 클래스 (ServerUtil) 에 서버 요청 기능을 만들고, 화면에서는 이를 사용.
            ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    Log.d("화면에서 보는 응답", json.toString())

                    //응답 내용 분석 => 화면에 반영.
                    //제일 큰 중괄호에 달린 code라는 이름이 붙은 Int를 받아서 codeNum 에 저장
                    // (https://jsonformatter.curiousconcept.com/ 여기에서 응답온 json.toString() 를 이쁘게 정렬해보면 보임
                    //code 에 적힌 값이 뭔지? codeNum 에 저장
                    val codeNum = json.getInt("code")

                    if(codeNum == 200) {

                        //서버에서 내려주는 토큰값을 SharedPrefence에 저장


                        //로그인 성공 => 메일엑티비티로 이동
                        val myIntent  = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
/*
                        //로그인 성공 => 로그인 한 사람의 이메일을 그대로 토스트로 출력
                        val data = json.getJSONObject("data")
                        val user = data.getJSONObject("user")
                        val loginUserEmail = user.getString("email")

                        runOnUiThread {

                            Toast.makeText(mContext, "${loginUserEmail}님 환영합니다.", Toast.LENGTH_SHORT).show()
                        }

*/

                       /*
                        //로그인 성공

                        val data = json.getJSONObject("data")
                        val user = data.getJSONObject("user")
                        val loginUserNickName = user.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${loginUserNickName}님 환영합니다.", Toast.LENGTH_SHORT).show()
                        }
                        */

                    }
                    else {
                        //그외의 숫자 :로그인 실패
                        //실패 사유 : message에 적히 String 을 확인하자. => Toast로 출력
                        val message  = json.getString("message")

                        //인터넷 연결 쓰레드가 아닌, UI 담당 쓰레드가 토스트를 띄우도록 처리
                        runOnUiThread {
                            //서버가 알려준 실패사유를 그대로 토스트로 띄우기
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }


                }

            })

        }
    }

    override fun setValues() {
    }


}
