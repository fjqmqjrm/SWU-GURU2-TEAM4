package com.example.guru2_team4

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class PostActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var postButton: Button

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_post)

        // Initialize UI components
        titleEditText = findViewById(R.id.editTextTitle)
        contentEditText = findViewById(R.id.editTextContent)
        postButton = findViewById(R.id.buttonPost)

        // Initialize DBHelper
        dbHelper = DBHelper(this)


        // Set click listener for the post button
        postButton.setOnClickListener {
            // Handle post button click
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            // TODO: Perform validation on title and content

            // TODO: Save the post data to the server or handle it as needed

            // Save post to local database
            savePostToDB(title, content)

            // For simplicity, finish the activity after posting
            finish()
        }
    }

    private fun savePostToDB(title: String, content: String) {
        // MainActivity에서 전달한 사용자 아이디 받아오기
        //val userId = intent.getStringExtra("USER_ID")
        // 다른 화면이나 액티비티에서 로그인 중인 사용자 아이디를 가져올 때
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""
        if (!userId.isNullOrBlank()) {
            // Insert post data into the local database
            val success = dbHelper.insertPostData(userId, title, content)

            if (success) {
                // 게시글 작성이 성공했음을 사용자에게 알리는 메시지 표시
                Toast.makeText(this, "게시글이 성공적으로 작성되었습니다!", Toast.LENGTH_SHORT).show()

                // 예를 들어, 게시글 목록 페이지로 이동하는 코드를 여기에 추가할 수 있습니다.
                // 예시: startActivity(Intent(this, PostListActivity::class.java))
            } else {
                // 게시글 작성이 실패했음을 사용자에게 알리는 메시지 표시
                Toast.makeText(this, "게시글 작성에 실패했습니다", Toast.LENGTH_SHORT).show()

                // 예를 들어, 실패 시 추가적인 조치를 사용자에게 안내하는 코드를 여기에 추가할 수 있습니다.
            }
        } else {
            // 사용자 ID가 없을 경우의 처리 (예: 로그인이 되어 있지 않은 상태)
            Toast.makeText(this, "사용자 ID가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the DBHelper to prevent memory leaks
        dbHelper.close()
    }


}
