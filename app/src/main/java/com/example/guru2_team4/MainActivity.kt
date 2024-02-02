package com.example.guru2_team4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnLogin: ImageButton
    lateinit var editTextId: EditText
    lateinit var editTextPassword: EditText
    lateinit var btnRegister: ImageButton
    var DB: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DB = DBHelper(this)

        btnLogin = findViewById(R.id.btnLogin)
        editTextId = findViewById(R.id.editTextId)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnRegister = findViewById(R.id.btnRegister)

        // 로그인 버튼 클릭
        btnLogin.setOnClickListener {
            val user = editTextId.text.toString()
            val pass = editTextPassword.text.toString()

            // 빈칸 제출시 Toast
            if (user.isBlank() || pass.isBlank()) {
                Toast.makeText(this@MainActivity, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val checkUserpass = DB!!.checkUserpass(user, pass)

                // id와 password 일치시
                if (checkUserpass) {
                    Toast.makeText(this@MainActivity, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()


                    // PostActivity 이동하면서 사용자 아이디를 전달
                    //val intent = Intent(this, TestActivity::class.java)

                    // PostActivity 이동하면서 사용자 아이디를 전달
                    //val intent = Intent(this, TestActivity::class.java)
                    //intent.putExtra("USER_ID", user)
                    //startActivity(intent)
                    // 로그인 성공 -> home
                    val intent = Intent(this, HomeActivity::class.java)

                    intent.putExtra("USER_ID", user)
                    startActivity(intent)
                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("USER_ID", user) // 여기서 userId는 로그인한 사용자의 아이디입니다.
                    editor.apply()





                } else {
                    Toast.makeText(this@MainActivity, "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 회원가입 버튼 클릭시
        btnRegister.setOnClickListener {
            val loginIntent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(loginIntent)
        }
    }
}
