package com.example.guru2_team4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class QandAActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qanda)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""

        val dbHelper = DBHelper(this)

        var qanda_back = findViewById<ImageView>(R.id.qanda_back)
        var qanda_tap_questions = findViewById<Button>(R.id.qanda_tap_questions)
        var qanda_tap_answers = findViewById<Button>(R.id.qanda_tap_answers)
        var qanda_scrollview_layout = findViewById<LinearLayout>(R.id.qanda_scrollview_layout)
        var input_me_question = findViewById<ImageButton>(R.id.input_me_question)


        // navi

        // HomeActivity로 이동
        findViewById<ImageView>(R.id.navi_home).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // PostListActivity로 이동
        findViewById<ImageView>(R.id.navi_community).setOnClickListener {
            startActivity(Intent(this, PostListActivity::class.java))
        }

        // QandAActivity로 이동
        findViewById<ImageView>(R.id.navi_qanda).setOnClickListener {
            startActivity(Intent(this, QandAActivity::class.java))
        }

        // MyPageActivity로 이동
        findViewById<ImageView>(R.id.navi_mypage).setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            val dbHelper = DBHelper(this)
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val user_id = sharedPreferences.getString("USER_ID", "") ?: ""
            intent.putExtra("nick", dbHelper.getUserNickname(user_id))
            startActivity(intent)

        }
        // 초기화면, question 목록 출력
        var respondentList = dbHelper.getRespondentListFromQuestions(userId)
        for (respondent_id in respondentList) {
            var questionCount = dbHelper.getQuestionCount(respondent_id, userId)
            for (j in 1..questionCount) {
                // qanda_q_set 레이아웃 가져오기
                val inflater = LayoutInflater.from(this)
                val qanda_q_set = inflater.inflate(R.layout.qanda_q_set, null) as ConstraintLayout

                // Layout에 동적으로 추가할 컴포넌트들을 찾기
                val qanda_q_box = qanda_q_set.findViewById<ImageButton>(R.id.qanda_q_box)
                val qanda_q_title = qanda_q_set.findViewById<TextView>(R.id.qanda_q_title)
                val qanda_isAnswered = qanda_q_set.findViewById<ImageView>(R.id.qanda_isAnswered)
                val qanda_q_content = qanda_q_set.findViewById<TextView>(R.id.qanda_q_content)

                // 컴포넌트에 데이터 설정
                qanda_q_box.setImageResource(R.drawable.qanda_q_button)
                qanda_q_title.text = dbHelper.getQuestionTitle(j, respondent_id, userId)
                if (dbHelper.isAnswered(j, respondent_id, userId) == true) {
                    qanda_isAnswered.setImageResource(R.drawable.qanda_answer_completed)
                }
                qanda_q_content.text = dbHelper.getQuestionContent(j, respondent_id, userId)



                qanda_scrollview_layout.addView(qanda_q_set)

                // 상세보기 이동
                qanda_q_box.setOnClickListener {
                    val intent = Intent(this, QViewDetailsActivity::class.java)

                    intent.putExtra("QandA_id", j)
                    intent.putExtra("respondent_id", respondent_id)

                    startActivity(intent)
                }
            }
        }

        // Answers 버튼 클릭 시
        qanda_tap_answers.setOnClickListener {
            qanda_back.setImageResource(R.drawable.qanda_a_back)
            qanda_scrollview_layout.removeAllViews()

            var respondentList = dbHelper.getRespondentListFromAnswers(userId)
            for (respondent_id in respondentList) {
                var answerCount = dbHelper.getAnswerCount(respondent_id, userId)
                for (j in 1..answerCount) {
                    // qanda_a_set 레이아웃 가져오기
                    val inflater = LayoutInflater.from(this)
                    val qanda_a_set = inflater.inflate(R.layout.qanda_a_set, null) as ConstraintLayout

                    // Layout에 동적으로 추가할 컴포넌트들을 찾기
                    val qanda_a_box = qanda_a_set.findViewById<ImageButton>(R.id.qanda_a_box)
                    val qanda_a_title = qanda_a_set.findViewById<TextView>(R.id.qanda_a_title)
                    val qanda_a_content = qanda_a_set.findViewById<TextView>(R.id.qanda_a_content)

                    // 컴포넌트에 데이터 설정
                    qanda_a_box.setImageResource(R.drawable.qanda_a_button)
                    qanda_a_title.text = dbHelper.getQuestionTitle(j, respondent_id, userId)
                    qanda_a_content.text = dbHelper.getAnswerContent(j, respondent_id, userId)

                    qanda_scrollview_layout.addView(qanda_a_set)

                    // 상세보기 이동
                    qanda_a_box.setOnClickListener {
                        val intent = Intent(this, AViewDetailsActivity::class.java)

                        intent.putExtra("QandA_id", j)
                        intent.putExtra("respondent_id", respondent_id)

                        startActivity(intent)
                    }
                }
            }
        }
        
        // Questions 버튼 클릭 시
        qanda_tap_questions.setOnClickListener {
            qanda_back.setImageResource(R.drawable.qanda_q_back)
            qanda_scrollview_layout.removeAllViews()

            var respondentList = dbHelper.getRespondentListFromQuestions(userId)
            for (respondent_id in respondentList) {
                var questionCount = dbHelper.getQuestionCount(respondent_id, userId)
                for (j in 1..questionCount) {
                    // qanda_q_set 레이아웃 가져오기
                    val inflater = LayoutInflater.from(this)
                    val qanda_q_set = inflater.inflate(R.layout.qanda_q_set, null) as ConstraintLayout

                    // Layout에 동적으로 추가할 컴포넌트들을 찾기
                    val qanda_q_box = qanda_q_set.findViewById<ImageButton>(R.id.qanda_q_box)
                    val qanda_q_title = qanda_q_set.findViewById<TextView>(R.id.qanda_q_title)
                    val qanda_isAnswered = qanda_q_set.findViewById<ImageView>(R.id.qanda_isAnswered)
                    val qanda_q_content = qanda_q_set.findViewById<TextView>(R.id.qanda_q_content)

                    // 컴포넌트에 데이터 설정
                    qanda_q_box.setImageResource(R.drawable.qanda_q_button)
                    qanda_q_title.text = dbHelper.getQuestionTitle(j, respondent_id, userId)
                    if (dbHelper.isAnswered(j, respondent_id, userId) == true) {
                        qanda_isAnswered.setImageResource(R.drawable.qanda_answer_completed)
                    }
                    qanda_q_content.text = dbHelper.getQuestionContent(j, respondent_id, userId)

                    qanda_scrollview_layout.addView(qanda_q_set)

                    // 상세보기 이동
                    qanda_q_box.setOnClickListener {
                        val intent = Intent(this, QViewDetailsActivity::class.java)

                        intent.putExtra("QandA_id", j)
                        intent.putExtra("respondent_id", respondent_id)

                        startActivity(intent)
                    }
                }
            }
        }

        // 받은 요청 조회 버튼 클릭 시
        input_me_question.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }
    }
}