package com.example.guru2_team4

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QViewDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdetails_q)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""

        val viewdetails_q_finish = findViewById<ImageButton>(R.id.viewdetails_q_finish)
        val viewdetails_q_content = findViewById<TextView>(R.id.viewdetails_q_content)
        val viewdetails_q_title = findViewById<TextView>(R.id.viewdetails_q_title)

        // 뒤로가기
        viewdetails_q_finish.setOnClickListener {
            finish()
        }

        val dbHelper = DBHelper(this)

        val QandA_id = intent.getIntExtra("QandA_id", -1)
        val respondent_id = intent.getStringExtra("respondent_id").toString()

        // Store respondent_id in a variable to avoid redundant calls
        val respondentIdString = intent.getStringExtra("respondent_id").toString()

        viewdetails_q_title.text = dbHelper.getQuestionTitle(QandA_id, respondentIdString, userId)
        viewdetails_q_content.text = dbHelper.getQuestionContent(QandA_id, respondentIdString, userId)
    }
}