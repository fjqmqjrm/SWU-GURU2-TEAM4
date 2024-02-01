package com.example.guru2_team4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class MyPageActivity : AppCompatActivity() {
    private var checkedPart: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val dbHelper = DBHelper(this)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""

        val receivedIntent: Intent = intent
        val owner_nick: String? = receivedIntent.getStringExtra("nick")     // 페이지 주인장 유저 닉네임
        val user_nick = dbHelper.getUserNickname(userId)                          // 접속한 유저 닉네임

        if (user_nick == owner_nick) {
            // 접속한 페이지의 주인장 유저와 접속한 유저가 같은 경우 : 마이페이지
            showPage(true)
        } else {
            // 접속한 페이지의 주인장 유저와 접속한 유저가 다른 경우 : 유저페이지
            showPage(false)
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showPage(isOwner: Boolean) {
        val backButton: ImageButton = findViewById(R.id.backButton)
        val pagename: TextView = findViewById(R.id.pagename)
        val usernameText: TextView = findViewById(R.id.usernameText)
        var medal: ImageView = findViewById(R.id.medal)
        val profile_isfollowing: ImageButton = findViewById(R.id.profile_isfollowing)
        val profile_isfollowing_text: TextView = findViewById(R.id.profile_isfollowing_text)
        val profile_request: ImageButton = findViewById(R.id.profile_request)
        var isfollowing: Boolean = false
        val part1MenuButton: ImageButton = findViewById(R.id.part1MenuButton)
        val part2MenuButton: ImageButton = findViewById(R.id.part2MenuButton)
        val part3MenuButton: ImageButton = findViewById(R.id.part3MenuButton)
        val part4MenuButton: ImageButton = findViewById(R.id.part4MenuButton)

        val dbHelper = DBHelper(this)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""
        val receivedIntent: Intent = intent
        val owner_nick: String? = receivedIntent.getStringExtra("nick")     // 페이지 주인장 유저 닉네임
        val user_nick = dbHelper.getUserNickname(userId)                          // 접속한 유저 닉네임
        val owner_id = dbHelper.getUserIDByNick(owner_nick.toString())

        // 뒤로가기
        backButton.setOnClickListener {
            finish()
        }

        // 메달 이미지 업데이트
        updateMedalImage(dbHelper.getUserPart(owner_id))

        // 체크박스 업데이트
        val part1Checkbox: CheckBox = findViewById(R.id.part1Checkbox)
        val part2Checkbox: CheckBox = findViewById(R.id.part2Checkbox)
        val part3Checkbox: CheckBox = findViewById(R.id.part3Checkbox)
        val part4Checkbox: CheckBox = findViewById(R.id.part4Checkbox)
        if (dbHelper.getUserPart(owner_id) == 1) {
            part1Checkbox.isChecked = true
        } else if (dbHelper.getUserPart(owner_id) == 2) {
            part2Checkbox.isChecked = true
        } else if (dbHelper.getUserPart(owner_id) == 3) {
            part3Checkbox.isChecked = true
        } else if (dbHelper.getUserPart(owner_id) == 4) {
            part4Checkbox.isChecked = true
        }


        // 각 파트 클릭 시, url 등록 페이지로
        part1MenuButton.setOnClickListener {
            val intent = Intent(this, PartPageActivity::class.java)
            intent.putExtra("owner_id", owner_id)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        part2MenuButton.setOnClickListener {
            val intent = Intent(this, PartPageActivity2::class.java)
            intent.putExtra("owner_id", owner_id)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        part3MenuButton.setOnClickListener {
            val intent = Intent(this, PartPageActivity3::class.java)
            intent.putExtra("owner_id", owner_id)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        part4MenuButton.setOnClickListener {
            val intent = Intent(this, PartPageActivity4::class.java)
            intent.putExtra("owner_id", owner_id)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // 마이페이지인 경우, 팔로우/요청 버튼을 "gone", usernameText 변경, 체크박스 체크 가능
        // 유저페이지인 경우, 팔로우/요청 버튼을 "visible", pagename/usernameText 변경
        if (isOwner) {  // 마이페이지
            profile_isfollowing.visibility = View.GONE
            profile_request.visibility = View.GONE
            usernameText.text = user_nick

            // Part1 CheckBox 및 체크 이벤트 처리
            val part1Checkbox: CheckBox = findViewById(R.id.part1Checkbox)
            part1Checkbox.setOnCheckedChangeListener { _, isChecked ->
                //final 추가 , 체크가 하나만 되게 하는 거
                handleCheckboxChange(part1Checkbox, isChecked, R.id.part1TabLayout)

                if (isChecked) {
                    // Part1 체크 시 처리
                    showToast("Part1이 체크되었습니다.")
                    findViewById<RelativeLayout>(R.id.part1TabLayout).setBackgroundResource(R.drawable.tab_background_checked)
                    dbHelper.setUserPart(owner_id, 1)
                }
            }

            // Part2 CheckBox 및 체크 이벤트 처리
            val part2Checkbox: CheckBox = findViewById(R.id.part2Checkbox)
            part2Checkbox.setOnCheckedChangeListener { _, isChecked ->
                handleCheckboxChange(part2Checkbox, isChecked, R.id.part2TabLayout)

                if (isChecked) {
                    // Part2 체크 시 처리
                    showToast("Part2가 체크되었습니다.")
                    findViewById<RelativeLayout>(R.id.part2TabLayout).setBackgroundResource(R.drawable.tab_background_checked)
                    dbHelper.setUserPart(owner_id, 2)
                }
            }

            // Part3 CheckBox 및 체크 이벤트 처리
            val part3Checkbox: CheckBox = findViewById(R.id.part3Checkbox)
            part3Checkbox.setOnCheckedChangeListener { _, isChecked ->
                handleCheckboxChange(part3Checkbox, isChecked, R.id.part3TabLayout)
                if (isChecked) {
                    // Part3 체크 시 처리
                    showToast("Part3이 체크되었습니다.")
                    findViewById<RelativeLayout>(R.id.part3TabLayout).setBackgroundResource(R.drawable.tab_background_checked)
                    dbHelper.setUserPart(owner_id, 3)
                }
            }

            // Part4 CheckBox 및 체크 이벤트 처리
            val part4Checkbox: CheckBox = findViewById(R.id.part4Checkbox)
            part4Checkbox.setOnCheckedChangeListener { _, isChecked ->
                handleCheckboxChange(part4Checkbox, isChecked, R.id.part4TabLayout)
                if (isChecked) {
                    // Part4 체크 시 처리
                    showToast("Part4가 체크되었습니다.")
                    findViewById<RelativeLayout>(R.id.part4TabLayout).setBackgroundResource(R.drawable.tab_background_checked)
                    dbHelper.setUserPart(owner_id, 4)
                }
            }
        } else {    // 유저페이지
            profile_isfollowing.visibility = View.VISIBLE
            profile_request.visibility = View.VISIBLE
            pagename.text = owner_nick
            usernameText.text = owner_nick

            val part1Checkbox: CheckBox = findViewById(R.id.part1Checkbox)
            part1Checkbox.isEnabled = false
            val part2Checkbox: CheckBox = findViewById(R.id.part2Checkbox)
            part2Checkbox.isEnabled = false
            val part3Checkbox: CheckBox = findViewById(R.id.part3Checkbox)
            part3Checkbox.isEnabled = false
            val part4Checkbox: CheckBox = findViewById(R.id.part4Checkbox)
            part4Checkbox.isEnabled = false
        }

        // followers 테이블에서 데이터를 가져와서 isfollowing 초기화
        isfollowing = dbHelper.isFollowing(owner_id, userId)

        // isfollowing에 따라 UI 업데이트
        if (isfollowing) {
            profile_isfollowing.setImageResource(R.drawable.userprofile_following)
            profile_isfollowing_text.text = "following"
            profile_isfollowing_text.setTextColor(Color.parseColor("#6C63FE"))
        } else {
            profile_isfollowing.setImageResource(R.drawable.userprofile_follow)
            profile_isfollowing_text.text = "follow"
            profile_isfollowing_text.setTextColor(Color.parseColor("#FFFFFF"))
        }

        // isfollowing 버튼을 눌렀을 때
        profile_isfollowing.setOnClickListener {
            // 팔로우 여부에 따라 followers 테이블에 추가/삭제
            if (isfollowing) {
                dbHelper.delFollower(owner_id, userId)
            } else {
                dbHelper.addFollower(owner_id, userId)
            }

            // followers 테이블에서 데이터를 가져와서 isfollowing 초기화
            isfollowing = dbHelper.isFollowing(owner_id, userId)

            // isfollowing에 따라 UI 업데이트
            if (isfollowing) {
                profile_isfollowing.setImageResource(R.drawable.userprofile_following)
                profile_isfollowing_text.text = "following"
                profile_isfollowing_text.setTextColor(Color.parseColor("#6C63FE"))
            } else {
                profile_isfollowing.setImageResource(R.drawable.userprofile_follow)
                profile_isfollowing_text.text = "follow"
                profile_isfollowing_text.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }

        // request 버튼을 눌렀을 때
        profile_request.setOnClickListener {
            showRequestDialog()
        }
    }


    private fun handleCheckboxChange(checkbox: CheckBox, isChecked: Boolean, layoutId: Int) {
        if (isChecked) {
            // 다른 체크박스 해제
            resetCheckboxes(checkbox)
            // 현재 체크된 탭에 배경 설정
            findViewById<RelativeLayout>(layoutId).setBackgroundResource(R.drawable.tab_background_checked)
            showToast("${checkbox.text}가 체크되었습니다.")
            // 체크된 파트 업데이트
            checkedPart = layoutId
            // 메달 이미지 업데이트
            updateMedalImage(layoutId)
        } else {
            // 체크 해제 시 처리
            checkbox.setBackgroundColor(Color.TRANSPARENT)
            findViewById<RelativeLayout>(layoutId).setBackgroundResource(R.drawable.tab_background_normal)
        }
    }

    private fun updateMedalImage(layoutId: Int) {
        val medal: ImageView = findViewById(R.id.medal)

        when (layoutId) {
            R.id.part1TabLayout -> medal.setImageResource(R.drawable.part1)
            R.id.part2TabLayout -> medal.setImageResource(R.drawable.part2)
            R.id.part3TabLayout -> medal.setImageResource(R.drawable.part3)
            R.id.part4TabLayout -> medal.setImageResource(R.drawable.part4)
            // Add more cases if needed
        }
    }

    private fun resetCheckboxes(checkedCheckbox: CheckBox) {
        val checkboxes = listOf(
            findViewById<CheckBox>(R.id.part1Checkbox),
            findViewById<CheckBox>(R.id.part2Checkbox),
            findViewById<CheckBox>(R.id.part3Checkbox),
            findViewById<CheckBox>(R.id.part4Checkbox)
        )

        checkboxes.filter { it != checkedCheckbox }.forEach {
            it.isChecked = false
            it.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun showRequestDialog() {
        val dbHelper = DBHelper(this)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val follower_id = sharedPreferences.getString("USER_ID", "") ?: ""

        val receivedIntent: Intent = intent
        val owner_nick: String? = receivedIntent.getStringExtra("nick")
        val owner_id = dbHelper.getUserIDByNick(owner_nick.toString())

        val dialogView = layoutInflater.inflate(R.layout.request_popup, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = dialogBuilder.create()

        val request_user_nick = dialogView.findViewById<TextView>(R.id.request_user_nick)
        val request_title = dialogView.findViewById<EditText>(R.id.request_title)
        val request_content = dialogView.findViewById<EditText>(R.id.request_content)
        val request_send = dialogView.findViewById<ImageButton>(R.id.request_send)

        // 유저 nick 표시
        request_user_nick.text = owner_nick

        // Send 버튼 클릭 시 데이터베이스에 추가 및 토스트 메시지 출력
        request_send.setOnClickListener {
            val title = request_title.text.toString()
            val content = request_content.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val dbHelper = DBHelper(this)
                val latestQandAId = dbHelper.getLatestQandAId(owner_id.toString(), follower_id)

                val isSuccess = dbHelper.addQuestion(latestQandAId + 1, owner_id, follower_id, title, content)

                if (isSuccess) {
                    Toast.makeText(this, "질문 요청이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(this, "질문 요청을 추가하는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "제목과 내용을 모두 기입해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.show()
    }
}