package dasdsa.sdn.apipractice_20200613.datas

import org.json.JSONObject

class TopicReply {

    companion object {
        fun getTopicReplyFromJson(json: JSONObject) : TopicReply {
            val tr = TopicReply()
            
            tr.id = json.getInt("id")
            tr.content = json.getString("content")
            tr.topicId = json.getInt("topic_id")
            tr.sideId = json.getInt("side_id")
            tr.userId = json.getInt("user_id")
            
            
            // tr.user = ?? //의견 JSON의 항목중 user JSONObject를 => User클래스에 전달
            //User클래스가 Json을 받아서 => uSR로 변환해서 리턴.
            //tr.user 에 대입

            return  tr
        }
    }

    var id = 0
    var content = ""
    var topicId = 0
    var sideId = 0
    var userId = 0

    lateinit var user : User


}