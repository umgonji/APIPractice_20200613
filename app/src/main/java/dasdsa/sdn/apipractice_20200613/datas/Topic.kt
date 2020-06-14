package dasdsa.sdn.apipractice_20200613.datas

import org.json.JSONObject

class Topic {
    //Json 을 넣으면 Topic 객체로 변환해주는 기능

    companion object {
        fun getTopicFormJson(json : JSONObject) : Topic {
            val topic = Topic()

            //빈 내용으로 만든 topic의 내용을
            //json에 담겨있는 내용을 가지고 채워준다.

            topic.id = json.getInt("id")
            topic.title = json.getString("title")
            topic.imageUrl = json.getString("img_url")

            //주제 파싱중 => 선택진영들 JSONArray 를 가져오기
            val sides = json.getJSONArray("sides")

            //JSONArray 내부를 돌면서 파싱
            for (i in 0..sides.length()-1 ){
                //선택진영 하나의 정보가 담긴 JSONObject 추출
                val sideJson = sides.getJSONObject(i)

                //이 Json 을 선택진영으로 변환해주는 기능 사용
                val side = TopicSide.getTopicSideFromJson(sideJson)

                //이 주제의 진영 목록으로 추가
                topic.sides.add(side)

            }

            //댓글목록도 같이 파싱
            var replies = json.getJSONArray("replies")
            //댓글 JSONArray 돌면서 => 파싱한 내용을 =>topic.replies 에 추가
            for(i in 0..replies.length()-1) {
                val replyJson = replies.getJSONObject(i)
                val reply = TopicReply.getTopicReplyFromJson(replyJson)
                topic.replies.add(reply)
            }

            return topic
        }
    }


    var id = 0
    var title = ""
    var imageUrl = ""
    //선택 가능 진영 목록을 담는 배열
    val sides= ArrayList<TopicSide>()
    //의견 목록을 담는 배역
    val replies = ArrayList<TopicReply>()



}