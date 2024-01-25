package com.example.guru2_team4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // MainActivity에서 전달한 사용자 아이디 받아오기
        val userId = intent.getStringExtra("USER_ID")

        // DBHelper를 통해 사용자 닉네임 가져오기
        val dbHelper = DBHelper(this)
        val userNickname = userId?.let { dbHelper.getUserNickname(it) }

        // 로그인 성공 메시지와 사용자 닉네임을 표시
        val textViewTest = findViewById<TextView>(R.id.textViewTest)
        textViewTest.text = "로그인 성공 페이지\n닉네임: $userNickname"
    }
}
