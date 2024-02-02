package com.example.guru2_team4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.SharedPreferences

class HomeActivity : AppCompatActivity() {
    lateinit var button_qanda: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


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
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""



        val home_tap_all = findViewById<Button>(R.id.home_tap_all)
        val home_tap_part1 = findViewById<Button>(R.id.home_tap_part1)
        val home_tap_part2 = findViewById<Button>(R.id.home_tap_part2)
        val home_tap_part3 = findViewById<Button>(R.id.home_tap_part3)
        val home_tap_part4 = findViewById<Button>(R.id.home_tap_part4)

        val home_scrollview_layout = findViewById<LinearLayout>(R.id.home_scrollview_layout)

        val dbHelper = DBHelper(this)

        // 초기화면, 모든 user 목록 출력
        home_scrollview_layout.removeAllViews()
        val userCount = dbHelper.getUserCount()
        for (i in 0 until userCount) {
            // home_user_set 레이아웃 가져오기
            val inflater = LayoutInflater.from(this)
            val home_user_set = inflater.inflate(R.layout.home_user_set, null) as ConstraintLayout

            // Layout에 동적으로 추가할 컴포넌트들을 찾기
            val home_user_back = home_user_set.findViewById<ImageButton>(R.id.home_user_back)
            val home_user_picture = home_user_set.findViewById<ImageView>(R.id.home_user_picture)
            val home_user_nick = home_user_set.findViewById<TextView>(R.id.home_user_nick)
            val home_user_part = home_user_set.findViewById<ImageView>(R.id.home_user_part)

            // 컴포넌트에 데이터 설정
            val nick = dbHelper.getUserNickByIndex(i)
            home_user_nick.text = nick
            val id = dbHelper.getUserIdByIndex(i)
            val part = dbHelper.getUserPart(id)
            if (part == 1) {
                home_user_part.setImageResource(R.drawable.part1)
            } else if (part == 2) {
                home_user_part.setImageResource(R.drawable.part2)
            } else if (part == 3) {
                home_user_part.setImageResource(R.drawable.part3)
            } else if (part == 4) {
                home_user_part.setImageResource(R.drawable.part4)
            }

            // 유저프로필로 이동
            home_user_back.setOnClickListener {
                // 유저 nick을 넘겨서 해당 유저의 마이페이지로 들어감
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("nick", nick)
                    startActivity(intent)
            }

            home_scrollview_layout.addView(home_user_set)
        }

        // All 클릭 시
        home_tap_all.setOnClickListener {
            home_scrollview_layout.removeAllViews()
            val userCount = dbHelper.getUserCount()
            for (i in 0 until userCount) {
                // home_user_set 레이아웃 가져오기
                val inflater = LayoutInflater.from(this)
                val home_user_set = inflater.inflate(R.layout.home_user_set, null) as ConstraintLayout

                // Layout에 동적으로 추가할 컴포넌트들을 찾기
                val home_user_back = home_user_set.findViewById<ImageButton>(R.id.home_user_back)
                val home_user_picture = home_user_set.findViewById<ImageView>(R.id.home_user_picture)
                val home_user_nick = home_user_set.findViewById<TextView>(R.id.home_user_nick)
                val home_user_part = home_user_set.findViewById<ImageView>(R.id.home_user_part)

                // 컴포넌트에 데이터 설정
                val nick = dbHelper.getUserNickByIndex(i)
                home_user_nick.text = nick
                val id = dbHelper.getUserIdByIndex(i)
                val part = dbHelper.getUserPart(id)
                if (part == 1) {
                    home_user_part.setImageResource(R.drawable.part1)
                } else if (part == 2) {
                    home_user_part.setImageResource(R.drawable.part2)
                } else if (part == 3) {
                    home_user_part.setImageResource(R.drawable.part3)
                } else if (part == 4) {
                    home_user_part.setImageResource(R.drawable.part4)
                }

                // 유저프로필로 이동
                home_user_back.setOnClickListener {
                    // 유저 nick을 넘겨서 해당 유저의 마이페이지로 들어감
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("nick", nick)
                    startActivity(intent)
                }

                home_scrollview_layout.addView(home_user_set)
            }
        }
        // Part1 클릭 시 (레벨이 part1인 유저 수)
        home_tap_part1.setOnClickListener {
            home_scrollview_layout.removeAllViews()
            val userCount = dbHelper.getUserCountFromPart(1)
            for (i in 0 until userCount) {
                // home_user_set 레이아웃 가져오기
                val inflater = LayoutInflater.from(this)
                val home_user_set = inflater.inflate(R.layout.home_user_set, null) as ConstraintLayout

                // Layout에 동적으로 추가할 컴포넌트들을 찾기
                val home_user_back = home_user_set.findViewById<ImageButton>(R.id.home_user_back)
                val home_user_picture = home_user_set.findViewById<ImageView>(R.id.home_user_picture)
                val home_user_nick = home_user_set.findViewById<TextView>(R.id.home_user_nick)
                val home_user_part = home_user_set.findViewById<ImageView>(R.id.home_user_part)

                // 컴포넌트에 데이터 설정
                val nick = dbHelper.getUserNickByIndexFromPart(1, i)
                home_user_nick.text = nick
                home_user_part.setImageResource(R.drawable.part1)

                // 유저프로필로 이동
                home_user_back.setOnClickListener {
                    // 유저 nick을 넘겨서 해당 유저의 마이페이지로 들어감
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("nick", nick)
                    startActivity(intent)
                }

                home_scrollview_layout.addView(home_user_set)
            }
        }
        // Part2 클릭 시 (레벨이 part2인 유저 수)
        home_tap_part2.setOnClickListener {
            home_scrollview_layout.removeAllViews()
            val userCount = dbHelper.getUserCountFromPart(2)
            for (i in 0 until userCount) {
                // home_user_set 레이아웃 가져오기
                val inflater = LayoutInflater.from(this)
                val home_user_set = inflater.inflate(R.layout.home_user_set, null) as ConstraintLayout

                // Layout에 동적으로 추가할 컴포넌트들을 찾기
                val home_user_back = home_user_set.findViewById<ImageButton>(R.id.home_user_back)
                val home_user_picture = home_user_set.findViewById<ImageView>(R.id.home_user_picture)
                val home_user_nick = home_user_set.findViewById<TextView>(R.id.home_user_nick)
                val home_user_part = home_user_set.findViewById<ImageView>(R.id.home_user_part)

                // 컴포넌트에 데이터 설정
                val nick = dbHelper.getUserNickByIndexFromPart(2, i)
                home_user_nick.text = nick
                home_user_part.setImageResource(R.drawable.part2)

                // 유저프로필로 이동
                home_user_back.setOnClickListener {
                    // 유저 nick을 넘겨서 해당 유저의 마이페이지로 들어감
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("nick", nick)
                    startActivity(intent)
                }

                home_scrollview_layout.addView(home_user_set)
            }
        }
        // Part3 클릭 시 (레벨이 part3인 유저 수)
        home_tap_part3.setOnClickListener {
            home_scrollview_layout.removeAllViews()
            val userCount = dbHelper.getUserCountFromPart(3)
            for (i in 0 until userCount) {
                // home_user_set 레이아웃 가져오기
                val inflater = LayoutInflater.from(this)
                val home_user_set = inflater.inflate(R.layout.home_user_set, null) as ConstraintLayout

                // Layout에 동적으로 추가할 컴포넌트들을 찾기
                val home_user_back = home_user_set.findViewById<ImageButton>(R.id.home_user_back)
                val home_user_picture = home_user_set.findViewById<ImageView>(R.id.home_user_picture)
                val home_user_nick = home_user_set.findViewById<TextView>(R.id.home_user_nick)
                val home_user_part = home_user_set.findViewById<ImageView>(R.id.home_user_part)

                // 컴포넌트에 데이터 설정
                val nick = dbHelper.getUserNickByIndexFromPart(3, i)
                home_user_nick.text = nick
                home_user_part.setImageResource(R.drawable.part3)

                // 유저프로필로 이동
                home_user_back.setOnClickListener {
                    // 유저 nick을 넘겨서 해당 유저의 마이페이지로 들어감
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("nick", nick)
                    startActivity(intent)
                }

                home_scrollview_layout.addView(home_user_set)
            }
        }
        // Part4 클릭 시 (레벨이 part4인 유저 수)
        home_tap_part4.setOnClickListener {
            home_scrollview_layout.removeAllViews()
            val userCount = dbHelper.getUserCountFromPart(4)
            for (i in 0 until userCount) {
                // home_user_set 레이아웃 가져오기
                val inflater = LayoutInflater.from(this)
                val home_user_set = inflater.inflate(R.layout.home_user_set, null) as ConstraintLayout

                // Layout에 동적으로 추가할 컴포넌트들을 찾기
                val home_user_back = home_user_set.findViewById<ImageButton>(R.id.home_user_back)
                val home_user_picture = home_user_set.findViewById<ImageView>(R.id.home_user_picture)
                val home_user_nick = home_user_set.findViewById<TextView>(R.id.home_user_nick)
                val home_user_part = home_user_set.findViewById<ImageView>(R.id.home_user_part)

                // 컴포넌트에 데이터 설정
                val nick = dbHelper.getUserNickByIndexFromPart(4, i)
                home_user_nick.text = nick
                home_user_part.setImageResource(R.drawable.part4)

                // 유저프로필로 이동
                home_user_back.setOnClickListener {
                    // 유저 nick을 넘겨서 해당 유저의 마이페이지로 들어감
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("nick", nick)
                    startActivity(intent)
                }

                home_scrollview_layout.addView(home_user_set)
            }
        }
    }
}