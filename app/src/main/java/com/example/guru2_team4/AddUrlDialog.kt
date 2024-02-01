package com.example.guru2_team4

//import androidx.appcompat.app.AppCompatActivity
/*
class AddUrlDialog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}*/



//import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
//import android.view.Window
import android.widget.EditText
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import android.widget.Toast
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class AddUrlDialog(
    context: Context,
    private val listener: OnUrlEnteredListener,
    private val topic: String, // 추가


) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_url_dialog)
        val closeButton = findViewById<ImageView>(R.id.closeButton)
        //2100
        //
        /*
        val sendButton = findViewById<RelativeLayout>(R.id.sendButton)
        val urlEditText = findViewById<EditText>(R.id.urlEditText)
*/
        // 주제 TextView 설정
        findViewById<TextView>(R.id.topicTextView).text = topic

        findViewById<TextView>(R.id.topic).text = topic
        // 하단주제 TextView 설정
        val topicTextView = findViewById<TextView>(R.id.topic)
        when (topic) {
            //Part1
            "배열" -> topicTextView.text = "Part1"
            "문자열" -> topicTextView.text = "Part1"
            "반복문과 재귀함수" -> topicTextView.text = "Part1"
            "계산복잡도" -> topicTextView.text = "Part1"
            "정렬" -> topicTextView.text = "Part1"
            "완전탐색" -> topicTextView.text = "Part1"
            "정수론" -> topicTextView.text = "Part1"

            //Part2
            "분할정복" -> topicTextView.text = "Part2"
            "이분탐색" -> topicTextView.text = "Part2"
            "스택" -> topicTextView.text = "Part2"
            "큐" -> topicTextView.text = "Part2"
            "우선순위 큐" -> topicTextView.text = "Part2"

            //Part3
            "그래프(vertex, edge, node, arc)" -> topicTextView.text = "Part3"
            "BFS" -> topicTextView.text = "Part3"
            "DFS" -> topicTextView.text = "Part3"
            "위상정렬" -> topicTextView.text = "Part3"

            //Part4
            "동적 프로그래밍" -> topicTextView.text = "Part4"
            "그리디 알고리즘" -> topicTextView.text = "Part4"



            else -> topicTextView.text = "주제"
        }

        /*
                when (topic) {
                    "Part1" -> topic.text = "배열"
                    "Part2" -> topic.text = "문자열"
                    "Part3" -> topic.text = "기타 주제1"
                    "Part4" -> topic.text = "기타 주제2"
                    else -> topic.text = "기본 주제"
                }*/
        /*
        // 하단주제 TextView 설정
        findViewById<TextView>(R.id.topicTextView).text = topic*/
        /*
        findViewById<TextView>(R.id.topicTextView).text = "주제(ex: $topic)" //위에 topic을 그냥ㄱ대로됨
*/
        // send 확인 버튼 클릭 시 동작
        findViewById<RelativeLayout>(R.id.sendButton).setOnClickListener {
            val enteredUrl = findViewById<EditText>(R.id.urlEditText).text.toString()
            listener.onUrlEntered(enteredUrl, topic)
            val DBHelper = DBHelper(context)
            DBHelper.insertPartUrl(topic, enteredUrl)
            dismiss() // 다이얼로그 닫기
        }

        // x 버튼 동작
        closeButton.setOnClickListener {
            dismiss()
        }
    }

}

/*
class AddUrlDialog(context: Context, private val urlEnteredListener: OnUrlEnteredListener) : Dialog(context) {

    //1415

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_add_url_dialog)

        val closeButton = findViewById<ImageView>(R.id.closeButton)
        val sendButton = findViewById<RelativeLayout>(R.id.sendButton)
        val urlEditText = findViewById<EditText>(R.id.urlEditText)

        closeButton.setOnClickListener {
            dismiss()
        }

        sendButton.setOnClickListener {
            val enteredUrl = urlEditText.text.toString()
            if (enteredUrl.isNotEmpty()) {
                urlEnteredListener.onUrlEntered(enteredUrl)
                dismiss()
            } else {
                Toast.makeText(context, "Please enter a valid URL", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
*/