package com.example.guru2_team4

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AViewDetailsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdetails_a)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""

        val viewdetails_a_finish = findViewById<ImageButton>(R.id.viewdetails_a_finish)
        val viewdetails_a_content = findViewById<TextView>(R.id.viewdetails_a_content)
        val viewdetails_a_title = findViewById<TextView>(R.id.viewdetails_a_title)

        // 뒤로가기
        viewdetails_a_finish.setOnClickListener {
            finish()
        }

        val dbHelper = DBHelper(this)

        val QandA_id = intent.getIntExtra("QandA_id", -1)
        val respondentIdString = intent.getStringExtra("respondent_id").toString()

        viewdetails_a_title.text = dbHelper.getQuestionTitle(QandA_id, respondentIdString, userId)
        viewdetails_a_content.text = dbHelper.getAnswerContent(QandA_id, respondentIdString, userId)
    }
}