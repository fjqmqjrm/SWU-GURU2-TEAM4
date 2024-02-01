package com.example.guru2_team4
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintSet
import com.example.guru2_team4.DBHelper
import com.example.guru2_team4.Post
import com.example.guru2_team4.PostActivity
import com.example.guru2_team4.PostDetailActivity
import com.example.guru2_team4.R

class PostListActivity : AppCompatActivity() {

    private lateinit var postListView: ListView
    private lateinit var dbHelper: DBHelper
    private lateinit var postList: List<Post> // postList를 멤버 변수로 선언

    override fun onResume() {
        super.onResume()
        // onResume에서 게시물 목록을 갱신
        displayPostList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        // UI 컴포넌트 초기화
        postListView = findViewById(R.id.postListView)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val loginUser = sharedPreferences.getString("USER_ID", "") ?: ""
        val rightview = findViewById<View>(R.id.right)
        val leftview = findViewById<View>(R.id.left)

        // MyPost 클릭 이벤트 처리
        val myPostButton = findViewById<View>(R.id.myPostButton)
        myPostButton.setOnClickListener {
            rightview.setBackgroundColor(Color.BLACK)
            leftview.setBackgroundColor(Color.GRAY)
            // MyPost 클릭 시 해당 사용자가 작성한 글만 필터링
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val loginUser = sharedPreferences.getString("USER_ID", "") ?: ""
            val filteredPostList = dbHelper.getAllPosts().filter { it.userId == loginUser }

            // CustomListAdapter를 사용하여 ListView에 게시물 표시
            val customListAdapter = CustomListAdapter(this, dbHelper, filteredPostList)
            postListView.adapter = customListAdapter

        }
        //allPostButton

        // All 클릭 이벤트 처리
        val allPostButton = findViewById<View>(R.id.allPostButton)
        allPostButton.setOnClickListener {
            rightview.setBackgroundColor(Color.GRAY)
            leftview.setBackgroundColor(Color.BLACK)

            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val loginUser = sharedPreferences.getString("USER_ID", "") ?: ""
            val filteredPostList = dbHelper.getAllPosts()

            // CustomListAdapter를 사용하여 ListView에 게시물 표시
            val customListAdapter = CustomListAdapter(this, dbHelper, filteredPostList)
            postListView.adapter = customListAdapter

        }

        dbHelper = DBHelper(this)

        // 게시물 목록 표시
        displayPostList()

        // 아이템 클릭 리스너 설정
        postListView.setOnItemClickListener { _, _, position, _ ->
            if (position < postList.size) {
                val selectedPost = postList[position]


                val userId = intent.getStringExtra("USER_ID")
                // PostDetailActivity 시작 및 선택한 게시물 정보 전달
                val intent = Intent(this, PostDetailActivity::class.java)
                intent.putExtra("POST_TITLE", selectedPost.title)
                intent.putExtra("POST_CONTENT", selectedPost.content)
                intent.putExtra("POST_USER",selectedPost.userId)
                intent.putExtra("POST_ID",selectedPost.id)
                intent.putExtra("POST_TIMESTAMP",selectedPost.timestamp)
                startActivity(intent)

            }
        }
    }

    private fun displayPostList() {
        // 데이터베이스에서 게시물 목록 가져오기
        postList = dbHelper.getAllPosts()

        // CustomListAdapter를 사용하여 ListView에 게시물 표시
        val customListAdapter = CustomListAdapter(this,dbHelper, postList)
        postListView.adapter = customListAdapter
    }

    fun goToPost(view: View) {
        // 필요시 이 메서드 수정
        val userId = intent.getStringExtra("USER_ID")
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }

    private fun changeLeftHalfColor(view: View) {
        val originalColor = Color.parseColor("#9B9B9B") // 초기 색상
        val width = view.width
        val halfWidth = width / 2

        // 배경이 GradientDrawable일 때만 처리
        if (view.background is GradientDrawable) {
            val gradientDrawable = view.background.mutate() as GradientDrawable
            gradientDrawable.setColor(originalColor) // 초기 색상 설정

            // 왼쪽 절반의 색상 변경
            gradientDrawable.setBounds(0, 0, halfWidth, view.height)
            gradientDrawable.setColor(Color.parseColor("#000000")) // 왼쪽 절반의 색상 변경

            // 오른쪽 절반의 색상을 초기 색상으로 변경
            gradientDrawable.setBounds(halfWidth, 0, width, view.height)
            gradientDrawable.setColor(originalColor)

            // 변경된 배경을 다시 설정
            view.background = gradientDrawable
        } else {
            // 배경이 null인 경우, 새로운 GradientDrawable을 생성하여 설정
            val newDrawable = GradientDrawable()
            newDrawable.setColor(originalColor)
            view.background = newDrawable
        }
    }





}
