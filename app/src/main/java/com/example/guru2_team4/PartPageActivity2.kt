package com.example.guru2_team4
/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PartPageActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page2)
    }
}
package com.example.codemate3
*/
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

class PartPageActivity2 : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView
    private lateinit var tabUrlStringTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlLoopTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlComplexityTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlSortingTextView: TextView // @@@@@@@추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page2)
        /*
                findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
                    showAddUrlDialog("Part2")
                }*/

        tabUrlTextView = findViewById(R.id.tabUrl) //첫번째탭
        tabUrlStringTextView = findViewById(R.id.tabStringUrl) // 추가@@@@@@@@
        tabUrlLoopTextView = findViewById(R.id.tabLoopUrl) // 추가@@@@@@@@
        tabUrlComplexityTextView = findViewById(R.id.tabComplexityUrl) // 추가@@@@@@@@
        tabUrlSortingTextView = findViewById(R.id.tabSortingUrl) // 추가@@@@@@@@
        /*
                //어드바이저님이 쓰신거 근데 이거 하면 url가 첫번째두번째탭에도 적용됨..
                tabUrlTextView.text =getPartUrl("part 이름")
                //어드바이저님이 쓰신거끝
        */


        // DB에서 가져온 URL을 EditText에 적용
        val dbHelper = DBHelper(this)

        // String 리스트 선언
        val topicList = listOf("분할정복", "이분탐색", "스택", "큐", "우선순위 큐")

        // DB 데이터 적용

        val ownerId = intent.getStringExtra("owner_id")
        // DB 데이터 적용

        if (ownerId != null) {
            // ownerId를 사용하는 코드
            // DB 데이터 적용
            for (topic in topicList) {
                dbHelper.getPartUrl(ownerId, topic)?.let { partPageUrl ->
                    onUrlEntered(partPageUrl, topic) // 링크로 변환
                }
            }
        } else {
            // ownerId가 null일 때의 처리
            // 예를 들어 기본값을 설정하거나 다른 처리를 수행할 수 있습니다.
        }


        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "분할정복")//Part2
            addUrlDialog.show()
        }

        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "이분탐색")//Part2
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabLoop).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "스택")//Part2
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabComplexity).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "큐")//Part2
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabSorting).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "우선순위 큐")//Part2
            addUrlDialog.show()
        }


        // 이전 버튼 설정
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    //어드바이저님이 추가하신거//
    private fun getPartUrl(part: String): String? {
        val DBHelper = DBHelper(this)
        val ownerId: String? = intent.getStringExtra("owner_id")

        if (ownerId != null) {
            return DBHelper.getPartUrl(ownerId, part)
        } else {
            // ownerId가 null일 때의 처리
            return null  // 또는 기본값 등을 반환하도록 처리할 수 있음
        }
    }
    //어드 추가끝
    override fun onUrlEntered(enteredUrl: String, topic: String) {
        when (topic) {
            "분할정복" -> {
                tabUrlTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlTextView, enteredUrl)
            }

            "이분탐색" -> {
                tabUrlStringTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlStringTextView, enteredUrl)
            }
            // 다른 탭에 대한 처리 추가
            "스택" -> {
                tabUrlLoopTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlLoopTextView, enteredUrl)
            }
            "큐" -> {
                tabUrlComplexityTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlComplexityTextView, enteredUrl)
            }
            "우선순위 큐" -> {
                tabUrlSortingTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlSortingTextView, enteredUrl)
            }
        }
        /*
            tabUrlTextView.text = "URL         $enteredUrl"
            tabUrlStringTextView.text = "URL         $enteredUrl"  // 문자열

         */

    }

    //url 클릭하면 인터넷 넘어가는 함수

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

    private fun getCurrentTab(): String {
        // 현재 선택된 탭을 반환하는 로직 추가
        return "문자열" // 예시로 "동적 프로그래밍"으로 초기화
    }
}

/*
package com.example.codemate3

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView

class PartPageActivity2 : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page2)

        tabUrlTextView = findViewById(R.id.tabUrl)

        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }


        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabLoop).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabComplexity).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabSorting).setOnClickListener {
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


}
*/