package dasdsa.sdn.apipractice_20200613.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {



    //어느 객체인지 관계없이 기능/값만 잘 사용하면 되는 것들을 모아두는 영역
    // JAVA => static 에 대응되는 개념
    companion object {

        //어느 서버로 가야하는지 (HOST 주소) 적어도는 변수
        val BASE_URL = "http://15.165.177.142"


        fun getRequestDuplicatedCheck(context: Context, checkType: String, inputVal: String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            //GET 방식은 어디로 갈지 주소 + 어떤데이터를 보낼지 같이 표시됨.
            // 주소를 만들때 데이터 첨부까지 같이 진행.

            //중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder로 만듦
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            //만든 주소 변수에 파라미터 첨부
            urlBuilder.addEncodedQueryParameter("type", checkType)
            urlBuilder.addEncodedQueryParameter("value", inputVal)

            //첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

            //Request를 만들어서 최종 전송 정보 마무리
            val request = Request.Builder()
                .url(urlString)
                .get()
//                .header() //헤더를 요구하면 추가
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })

        }

        fun getRequestMyInfo(context: Context, handler: JsonResponseHandler?) { //context 넣는건 mContext 가 없는 것들이 많아서 무조건 넣어두는거다.

            val client = OkHttpClient()

            //GET 방식은 어디로 갈지 주소 + 어떤데이터를 보낼지 같이 표시됨.
            // 주소를 만들때 데이터 첨부까지 같이 진행.

            //중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder로 만듦
            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
            //만든 주소 변수에 파라미터 첨부 //header 는 여기서 넣는것 아님.
         //   urlBuilder.addEncodedQueryParameter("type", checkType)
         //   urlBuilder.addEncodedQueryParameter("value", inputVal)

            //첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

            //Request를 만들어서 최종 전송 정보 마무리
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) //헤더를 요구하면 추가 //위의 context 임.mContext 없어서.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })

        }

        //원하는 주제의 상세정보 보기 => 몇번주제? 화면에서 받아와야함
        fun getRequestTopicDetail(context: Context, topicId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            //4번주제? /topic/4, 2번? /topic/2
            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()
            //   urlBuilder.addEncodedQueryParameter("type", checkType)
            //   urlBuilder.addEncodedQueryParameter("value", inputVal)

            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })

        }



        //원하는 의견의 상세정보 보기
        fun getRequestReplyDetail(context: Context, replyId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            //4번의견? /topic_reply/4, 2번? /topic_reply/2
            val urlBuilder = "${BASE_URL}/topic_reply/${replyId}".toHttpUrlOrNull()!!.newBuilder()
            //   urlBuilder.addEncodedQueryParameter("type", checkType)
            //   urlBuilder.addEncodedQueryParameter("value", inputVal)

            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })

        }

        //원하는 댓글 삭제 하기
        fun deleteRequestReply(context: Context, replyId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/topic_reply".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("reply_id", replyId.toString())
            //   urlBuilder.addEncodedQueryParameter("value", inputVal)

            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

            val request = Request.Builder()
                .url(urlString)
                .delete()
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })

        }

        fun getRequestV2MainInfo(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            //GET 방식은 어디로 갈지 주소 + 어떤데이터를 보낼지 같이 표시됨.
            // 주소를 만들때 데이터 첨부까지 같이 진행.

            //중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder로 만듦
            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
            //만든 주소 변수에 파라미터 첨부 //header 는 여기서 넣는것 아님.
            //   urlBuilder.addEncodedQueryParameter("type", checkType)
            //   urlBuilder.addEncodedQueryParameter("value", inputVal)

            //첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 주소", urlString)

            //Request를 만들어서 최종 전송 정보 마무리
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) //헤더를 요구하면 추가 //위의 context 임.mContext 없어서.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })

        }


        //서버에 로그인 요청 해주는 함수
        //context / handler 필수로 적어주자.
        //둘 사이에, 화면에서 넘겨워야하는 자료들을 추가로 적어줌. -> id, pw 를 받아오자.
        fun postRequestLogin(context: Context, id:String, pw:String, handler: JsonResponseHandler?) {

            //이 안드로이드 앱이 클라이언트로 동작하도록 도와주는 클래스 => 객체화
            val client = OkHttpClient()

            //어떤 기능을 수행하러 가는지 주소 완성 => 로그인: http://15.165.177.142/user
            val urlString = "${BASE_URL}/user"

            //서버에 들고갈 데이터를(파라미터) 첨부. => intent에 putExtra 하듯이. => POST : FormData에 담자.
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

            //Request 정보를 완성해주자. => 화면으로 따지면 Intent 객체를 만드는 행위.
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .head()   //API가 헤더를 요구하면 여기서 첨부하면 된다.
                .build()

            //startActivity 처럼 실제로 요청을 날리는 코드
            //요청에 대한 서버의 응답 처리도 같이 코딩
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //서버에 연결 자체를 실패했을 경우
                }
                override fun onResponse(call: Call, response: Response) {
                    //서버에서 응답을 잘 받아왔을 경우
                    //응답 중에서 body(내용물) 을 string으로 저장
                    val bodyString = response.body!!.string()
                    //저장한 String을 JSONObject 양식으로 가공
                    //서버의 응답이 JSON 형태이기 때문.
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    //화면 (액티비티)에 만ㄷ르어낸 json 변수를 전달
                    handler?.onResponse(json)
                }
            })
        }

        fun postRequestVote(context: Context, sideId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_vote"

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })
        }

        fun postRequestReply(context: Context, topicId: Int, content: String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply"

            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })
        }

        //의견에 대한 답글 남기기
        fun postRequestReReply(context: Context, parentReplyId: Int, content: String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply"

            val formData = FormBody.Builder()
                .add("parent_reply_id", parentReplyId.toString())
                .add("content", content.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })
        }

        fun postRequestReplyLikeOrDislike(context: Context, replyId:Int, isLike:Boolean, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply_like"

            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })
        }

        fun putRequestSingUp(context: Context, email:String, pw:String, nick:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nick)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
//                .head()   //API가 헤더를 요구하면 여기서 첨부하면 된다.
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)
                }
            })
        }

    }

    //서버통신의 응답 내용(json) 을 화면으로 전달해주기 위한 인터페이스
    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }



}