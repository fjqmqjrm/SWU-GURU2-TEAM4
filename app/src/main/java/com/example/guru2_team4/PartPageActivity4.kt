package com.example.guru2_team4
/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PartPageActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page4)
    }
}
package com.example.codemate3
*/
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject.NULL
import java.sql.Types.NULL

class PartPageActivity4 : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView
    private lateinit var tabUrlStringTextView: TextView // @@@@@@@추가
    //21000
    /* private lateinit var currentTabTitle: String // 추가: 현재 선택된 탭의 제목을 저장하는 변수
 */
    //@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page4)
        /*
                findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
                    showAddUrlDialog("Part4")
                }
        */
        tabUrlTextView = findViewById(R.id.tabUrl)

        tabUrlStringTextView = findViewById(R.id.tabStringUrl) // 추가@@@@@@@@

        /*
        //어드바이저님이 쓰신거 근데 이거 하면 url가 첫번째두번째탭에도 적용됨..
        tabUrlTextView.text =getPartUrl("part 이름")
        //어드바이저님이 쓰신거끝


*/

        // DB에서 가져온 URL을 EditText에 적용
        val dbHelper = DBHelper(this)

        // String 리스트 선언
        val topicList = listOf("동적 프로그래밍", "그리디 알고리즘")

// DB 데이터 적용
        for (topic in topicList) {
            dbHelper.getPartUrl(topic)?.let { partPageUrl ->
                onUrlEntered(partPageUrl, topic) // 링크로 변환
            }
        }


        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "동적 프로그래밍")//Part4
            addUrlDialog.show()
        }

        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "그리디 알고리즘")//Part4
            addUrlDialog.show()
        }
        // 이전 버튼 설정
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    //어드바이저님이 추가하신거//
    private fun getPartUrl(part:String) :String?{
        val DBHelper = DBHelper(this)
        return DBHelper.getPartUrl(part)
    }
    //어드 추가끝
    override fun onUrlEntered(enteredUrl: String, topic: String) {
        // 현재 선택된 탭에 따라서 각 탭의 TextView에 URL을 설정

        when (topic) {
            "동적 프로그래밍" -> {
                tabUrlTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlTextView, enteredUrl)
            }
            "그리디 알고리즘" -> {
                tabUrlStringTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlStringTextView, enteredUrl)
            }
            // 다른 탭에 대한 처리 추가
        }
        //2300
        /*
        when (getCurrentTab()) {
            "동적 프로그래밍" -> tabUrlTextView.text = "URL $enteredUrl"
            "그리디 알고리즘" -> tabUrlStringTextView.text = "URL $enteredUrl"
            // 다른 탭에 대한 처리 추가
        }
*/
        /*
        tabUrlTextView.text = "URL         $enteredUrl"  // Part4
        tabUrlStringTextView.text = "URL         $enteredUrl"  // 문자열
        */
        /*
        tabUrlTextView.text = "URL1 $enteredUrl"  // Part4
        tabUrlStringTextView.text = "URL2 $enteredUrl"  // 문자열*/
    }
    private fun setClickableUrl(textView: TextView, url: String) {
        textView.setOnClickListener {
            openUrlInBrowser(url)
        }
        textView.text = "URL         $url"
        textView.setTextColor(Color.BLUE)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun openUrlInBrowser(url: String) {
        if (url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            // 인텐트를 처리할 수 있는 앱이 있는지 확인
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                intent.data = Uri.parse("http://$url")
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "URL이 비어 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }

//0130 0720에 발견 뭐지 이거?

    private fun getCurrentTab(): String {
        // 현재 선택된 탭을 반환하는 로직 추가
        return "동적 프로그래밍" // 예시로 "동적 프로그래밍"으로 초기화
    }
    /*
        override fun onUrlEntered(enteredUrl: String) {
            tabUrlTextView.text = "URL $enteredUrl"
        }

        */

    /*
        private fun showAddUrlDialog(topic: String) {
            val addUrlDialog = AddUrlDialog(this, this, topic)
            addUrlDialog.show()
        }*/

    override fun onBackPressed() {
        // 뒤로가기 버튼 눌렀을 때의 동작 정의
        super.onBackPressed()
        finish() // 현재 액티비티 종료
    }
}


/*
package com.example.codemate3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView

class PartPageActivity4 : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page4)

        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }

        tabUrlTextView = findViewById(R.id.tabUrl)

        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }
        // 이전 버튼 설정
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onUrlEntered(enteredUrl: String) {
        //tabUrlTextView.text = enteredUrl
        // 'URL' 글자와 함께 입력한 URL을 표시
        tabUrlTextView.text = "URL $enteredUrl"
    }
    override fun onBackPressed() {
        // 뒤로가기 버튼 눌렀을 때의 동작 정의
        super.onBackPressed()
        finish() // 현재 액티비티 종료
    }



}*/