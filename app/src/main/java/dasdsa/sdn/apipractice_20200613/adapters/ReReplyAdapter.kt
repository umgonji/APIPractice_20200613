package dasdsa.sdn.apipractice_20200613.adapters

import android.content.Context
import android.content.Intent
import android.media.JetPlayer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import dasdsa.sdn.apipractice_20200613.R
import dasdsa.sdn.apipractice_20200613.ViewReplyDetailActivity
import dasdsa.sdn.apipractice_20200613.datas.TopicReply
import dasdsa.sdn.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ReReplyAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:List<TopicReply>) : ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_re_reply_list_item, null)
        }
        val row = tempRow!!

        //xml에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

        //목록에서 뿌려줄 데이터 꺼내오기
        val data = mList[position]

        //데이터 / 뷰 연결 => 알고리즘 고민하는곳
        writerNickNameTxt.text = data.user.nickName
        selectedSideTitleTxt.text = "(${data.selectedSide.title})"
        contentTxt.text = data.content

        //좋아요 / 싫어요 버튼 관련 표시
        //몇명이 좋아요 ? 숫자 표시
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

        //내 좋아요 여부에 따라 모양 변경
        if(data.isMyLike) {
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }
        else if (data.isMyDislike) {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        }
        else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }

        //좋아요 / 싫어요 이벤트 처리
        likeBtn.setOnClickListener {
            //좋아요 API 호출 => 좋아요 누르기 / 취소 처리
            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, true, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    //화면에 변경된 종아요/싫어요 갯수 반영
                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    //목록에서 꺼낸data변수의 객체를 통째로 바꾸는건 불가능.
                    //var 로 바꿔서 통째로 바꿔도 => 목록에는 반영되지 않음.
                    //data = TopicReply.getTopicReplyFromJson(reply)

                    //목록에서 꺼낸data 변수의 좋아요 갯수/ 싫어요 갯수를 직접 변경

                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

                    //색도 반영해주기
                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

                    //목록의 내용을 일부 변경 => 변경하려면
                    //어댑터.notifyDataSetChanged() 실행 필요함.
                    //이미 어댑터 내부에 있는 상황 => 곧바로 notifyDataSetChanged() 실행 가능

                    //runOnUiThread로 처리 필요 => 어댑터내부에선 사용 불가.
                    // 대체재 : Handler(Looper.getMainLooper()).post (UI쓰레드 접근하는 다른 방법)
                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()  //어뎁터를 다시 실행하는것
                    }
                }
            })
        }

        dislikeBtn.setOnClickListener {
            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, false, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    val likeCount = reply.getInt("like_count")
                    val dislikeCount = reply.getInt("dislike_count")

                    data.likeCount = likeCount
                    data.dislikeCount = dislikeCount

                    //색도 반영해주기
                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }
                }
            })
        }

        return row
    }

}