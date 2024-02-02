package com.example.guru2_team4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class RequestActivity : AppCompatActivity() {
    val dbHelper = DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_me_request)

        val input_me_request_finish = findViewById<ImageButton>(R.id.input_me_request_finish)

        // 뒤로가기
        input_me_request_finish.setOnClickListener {
            finish()
        }

        showQandA()
    }

    fun showQandA() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""
        val input_me_request_scrollview_layout = findViewById<LinearLayout>(R.id.input_me_request_scrollview_layout)

        // 스크롤뷰 화면 초기화
        input_me_request_scrollview_layout.removeAllViews()

        for (i in 0 until dbHelper.countQuestionsByRespondent(userId)) {
            val questionInfo = dbHelper.getQuestionInfoByRespondentAndIndex(userId, i)
            if (questionInfo != null) {
                val QandA_id = questionInfo.QandA_id
                val questioner_id = questionInfo.questioner_id

                if (dbHelper.isAnswered(QandA_id, userId, questioner_id) == false) {
                    // input_me_set 레이아웃 가져오기
                    val inflater = LayoutInflater.from(this)
                    val input_me_set = inflater.inflate(R.layout.input_me_set, null) as ConstraintLayout

                    // Layout에 동적으로 추가할 컴포넌트들을 찾기
                    val a_back = input_me_set.findViewById<ImageButton>(R.id.a_back)
                    val a_send = input_me_set.findViewById<ImageButton>(R.id.a_send)
                    val q_back = input_me_set.findViewById<ImageButton>(R.id.q_back)
                    val q_user = input_me_set.findViewById<ImageView>(R.id.q_user)
                    val q_nick = input_me_set.findViewById<TextView>(R.id.q_nick)
                    val q_title = input_me_set.findViewById<TextView>(R.id.q_title)
                    val a_content = input_me_set.findViewById<TextView>(R.id.a_content)
                    val q_content = input_me_set.findViewById<TextView>(R.id.q_content)
                    val q_content_scrollview = input_me_set.findViewById<ScrollView>(R.id.q_content_scrollview)
                    val a_content_scrollview = input_me_set.findViewById<ScrollView>(R.id.a_content_scrollview)

                    q_nick.text = dbHelper.getUserNickname(questioner_id)
                    q_title.text = dbHelper.getQuestionTitle(QandA_id, userId, questioner_id)
                    q_content.text = dbHelper.getQuestionContent(QandA_id, userId, questioner_id)

                    input_me_request_scrollview_layout.addView(input_me_set)

                    a_send.setOnClickListener {
                        val content = a_content.text.toString()
                        dbHelper.addAnswer(QandA_id, userId, questioner_id, content)
                        showQandA()
                    }
                }
            }
        }

    }

}