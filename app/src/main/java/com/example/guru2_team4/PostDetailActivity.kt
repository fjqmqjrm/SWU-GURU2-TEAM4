package com.example.guru2_team4

// PostDetailActivity.kt
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostDetailActivity : AppCompatActivity() {
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        dbHelper = DBHelper(this)
        // Post 데이터를 받아옴
        val postTitle = intent.getStringExtra("POST_TITLE") ?: ""
        val postContent = intent.getStringExtra("POST_CONTENT") ?: ""
        val postUser = intent.getStringExtra("POST_USER")  ?: ""
        // 다른 화면이나 액티비티에서 로그인 중인 사용자 아이디를 가져올 때
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val loginUser = sharedPreferences.getString("USER_ID", "") ?: ""
        val postTimestamp = intent.getLongExtra("POST_TIMESTAMP", 0L)
        // 상세 정보를 보여줄 TextView를 찾아서 텍스트 설정
        val postDetailTextView = findViewById<TextView>(R.id.postDetailTextView)
        val postUserTextView = findViewById<TextView>(R.id.postUserTextView)





        postUserTextView.text = dbHelper.getUserNickname(postUser)
        // SpannableString을 사용하여 일부 텍스트를 굵게 표시
        val formattedText = SpannableStringBuilder()

        // 제목 부분 크기 및 볼드 설정
        formattedText.append(postTitle)
        formattedText.setSpan(RelativeSizeSpan(0.7f), 0, postTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        formattedText.setSpan(StyleSpan(Typeface.BOLD), 0, postTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 내용 부분 크기 조절
        formattedText.append("\n\n")
        formattedText.append(postContent)
        formattedText.setSpan(RelativeSizeSpan(0.7f), formattedText.length - postContent.length, formattedText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 텍스트뷰에 적용
        postDetailTextView.text = formattedText

        postDetailTextView.gravity = Gravity.START

        // getFormattedTimestamp(postTimestamp) - 생성시간
        val postTimeView = findViewById<TextView>(R.id.postTimeView)
        postTimeView.text = getFormattedTimestamp(postTimestamp)

        // RecyclerView 초기화 및 댓글 목록 설정
        val commentRecyclerView = findViewById<RecyclerView>(R.id.commentRecyclerView)
        val selectedPostId = intent.getLongExtra("POST_ID", 0L).toString()
        // CommentAdapter 설정
        commentAdapter = CommentAdapter(this, dbHelper.getCommentsByPostId(selectedPostId),dbHelper)
        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.layoutManager = LinearLayoutManager(this)



        // 댓글 갯수 표시하는 TextView 업데이트
        val commentCountTextView = findViewById<TextView>(R.id.commentCountTextView)
        commentCountTextView.text =  commentAdapter.itemCount.toString()


        val postCommentButton = findViewById<ImageButton>(R.id.postCommentButton)
        val commentEditText = findViewById<EditText>(R.id.commentEditText)
        val dbHelper = DBHelper(this)


        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            // 뒤로가기 버튼이 클릭되었을 때 실행할 동작을 여기에 추가
            navigateToTargetActivity() // 새로운 액티비티로 이동하는 함수 호출
        }

        // 댓글 작성 버튼 클릭 시
        postCommentButton.setOnClickListener {
            val commentContent = commentEditText.text.toString()

            // 댓글 저장
            val success = dbHelper.insertCommentData(selectedPostId, loginUser, commentContent)

            if (success) {
                // 댓글 작성 성공
                Toast.makeText(this, "댓글이 성공적으로 작성되었습니다!", Toast.LENGTH_SHORT).show()

                // 필요하다면 commentEditText를 초기화하거나 특정 상태로 설정
                commentEditText.text.clear()

                // CommentAdapter 업데이트 및 RecyclerView 갱신
                commentAdapter.updateComments(dbHelper.getCommentsByPostId(selectedPostId))
                commentRecyclerView.adapter?.notifyDataSetChanged()
                // 댓글 갯수 표시하는 TextView 업데이트
                val commentCountTextView = findViewById<TextView>(R.id.commentCountTextView)
                commentCountTextView.text =  commentAdapter.itemCount.toString()
            } else {
                // 댓글 작성 실패
                Toast.makeText(this, "댓글 작성에 실패했습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getFormattedTimestamp(timestamp: Long): String {
        // 작성 시간 설정
        val formattedTimestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            .format(Date(timestamp))


        return formattedTimestamp
    }

    private fun navigateToTargetActivity() {
        // TargetActivity 로 이동하는 Intent 생성
        val intent = Intent(this, PostListActivity::class.java)

        // Intent를 사용하여 새로운 액티비티 시작
        startActivity(intent)
    }



}

