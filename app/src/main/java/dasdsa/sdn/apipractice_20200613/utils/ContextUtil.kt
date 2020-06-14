package dasdsa.sdn.apipractice_20200613.utils

import android.content.Context

class ContextUtil {

    companion object {
        //메모장의 파일 이름에 대응되는 개념으로 만든 변수
        val prefName = "APIPracticePreference"  //이름은 플젝마다 바꾸면 된다.

        //저장될 데이터 항목 이름들을 변수로 설정
        val USER_TOKEN = "USER_TOKEN"   //토큰 저장

        //토큰 저장 기능
        fun setUserToken(context: Context, token:String) {
            //저장할때 사용할 메모장 파일을 열자. (pref)
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            //열어준 메모장의 USER_TOKEN 항목에 받아온 token 에 든 값을 저장.
            pref.edit().putString(USER_TOKEN, token).apply() //.edit() 는 수정모드다(쓰는거라서)
        }

        //저장된 토큰 불러오기
        fun getUserToken(context: Context) : String {
            //저장할때 사용한 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            //열어준 메모장의 USER_TOKEN 항목에 저장된 token값을 꺼내서 리턴
            return pref.getString(USER_TOKEN, "")!! //getString 에 널이 나올수도 있어서 오류가 떠서 !! 로 넣어줬다. "" 나오게 해놨으니까 괜찮음.
        }
    }

}