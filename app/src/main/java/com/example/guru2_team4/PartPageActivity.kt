package com.example.guru2_team4
/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PartPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page)
    }
}
package com.example.codemate3
*/
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView

class PartPageActivity : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView
    private lateinit var tabUrlStringTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlLoopTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlComplexityTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlSortingTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlBruteforceTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlNumberTheoryTextView: TextView // @@@@@@@추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page)

        /*
                //무조건있어야함 바로아래줄은, 있어야 배열탭이 열림
                findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
                    showAddUrlDialog("Part1")
                }*/
        /*
                findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
                    showAddUrlDialog("배열") // 주제를 해당 탭의 이름으로 전달
                }

                findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
                    showAddUrlDialog("문자열") // 주제를 해당 탭의 이름으로 전달
                }*/

        tabUrlTextView = findViewById(R.id.tabUrl)
        tabUrlStringTextView = findViewById(R.id.tabStringUrl) // 추가@@@@@@@@

        tabUrlLoopTextView = findViewById(R.id.tabLoopUrl) // 추가@@@@@@@@
        tabUrlComplexityTextView = findViewById(R.id.tabComplexityUrl) // 추가@@@@@@@@
        tabUrlSortingTextView = findViewById(R.id.tabSortingUrl) // 추가@@@@@@@@
        tabUrlBruteforceTextView = findViewById(R.id.tabBruteforceUrl) // 추가@@@@@@@@
        tabUrlNumberTheoryTextView = findViewById(R.id.tabNumberTheoryUrl) // 추가@@@@@@@@


        // DB에서 가져온 URL을 EditText에 적용
        val dbHelper = DBHelper(this)

        // String 리스트 선언
        val topicList = listOf("배열", "문자열", "반복문과 재귀함수", "계산복잡도", "정렬", "완전탐색", "정수론")


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
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "") ?: ""

        if (ownerId != userId){
            hideAllTabs()
        }




                findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "배열")//"Part1"
            addUrlDialog.show()
        }

        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "문자열")//"Part1"
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabLoop).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "반복문과 재귀함수")//"Part1"
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabComplexity).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "계산복잡도")//"Part1"
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabSorting).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "정렬")//"Part1"
            addUrlDialog.show()
        }

        findViewById<RelativeLayout>(R.id.tabBruteforce).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "완전탐색")//"Part1"
            addUrlDialog.show()
        }

        findViewById<RelativeLayout>(R.id.tabNumberTheory).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "정수론")//"Part1"
            addUrlDialog.show()
        }

        // 이전 버튼 설정
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    //어드바이저님이 추가하신거
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
            "배열" -> {
                tabUrlTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlTextView, enteredUrl)
            }
            "문자열" -> {
                tabUrlStringTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlStringTextView, enteredUrl)
            }
            "반복문과 재귀함수" -> {
                tabUrlLoopTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlLoopTextView, enteredUrl)
            }
            "계산복잡도" -> {
                tabUrlComplexityTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlComplexityTextView, enteredUrl)
            }
            "정렬" -> {
                tabUrlSortingTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlSortingTextView, enteredUrl)
            }
            "완전탐색" -> {
                tabUrlBruteforceTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlBruteforceTextView, enteredUrl)
            }
            "정수론" -> {
                tabUrlNumberTheoryTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlNumberTheoryTextView, enteredUrl)
            }
            // 다른 탭에 대한 처리 추가
        }

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

    private fun getCurrentTab(): String {
        // 현재 선택된 탭을 반환하는 로직 추가

        return "문자열" // 예시로 "동적 프로그래밍"으로 초기화
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 눌렀을 때의 동작 정의
        super.onBackPressed()
        finish() // 현재 액티비티 종료
    }

    // 모든 탭 버튼을 숨기는 함수
    private fun hideAllTabs() {
        findViewById<ImageView>(R.id.btn1).visibility = View.GONE
        findViewById<ImageView>(R.id.btn2).visibility = View.GONE
        findViewById<ImageView>(R.id.btn3).visibility = View.GONE
        findViewById<ImageView>(R.id.btn4).visibility = View.GONE
        findViewById<ImageView>(R.id.btn5).visibility = View.GONE
        findViewById<ImageView>(R.id.btn6).visibility = View.GONE
        findViewById<ImageView>(R.id.btn7).visibility = View.GONE
    }
    /*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // 여기에 현재 상태를 저장하는 코드 추가
        outState.putString("tabUrlText", tabUrlTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 여기에 저장된 상태를 복원하는 코드 추가
        val savedTabUrlText = savedInstanceState.getString("tabUrlText")
        tabUrlTextView.text = savedTabUrlText
    }*/


}


/*package com.example.codemate3

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView

class PartPageActivity : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView

    //추가1400
    //private lateinit var currentTabTextView: TextView

    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page)

        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()

            //openAddUrlDialog("배열")
        }

        tabUrlTextView = findViewById(R.id.tabUrl)

        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
            //openAddUrlDialog("문자열")
        }

        findViewById<RelativeLayout>(R.id.tabLoop).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
            //openAddUrlDialog("반복문과 재귀함수")
        }
        findViewById<RelativeLayout>(R.id.tabComplexity).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()

            //openAddUrlDialog("계산복잡도")
        }
        findViewById<RelativeLayout>(R.id.tabSorting).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()

            //openAddUrlDialog("정렬")
        }
        findViewById<RelativeLayout>(R.id.tabBruteforce).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()

            //openAddUrlDialog("완전탐색")
        }
        findViewById<RelativeLayout>(R.id.tabNumberTheory).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
            //openAddUrlDialog("정수론")
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
    /*
    // 탭 버튼을 클릭했을 때 새로운 다이얼로그 열기
    private fun openAddUrlDialog(tabName: String) {

        //1412

        val addUrlDialog = AddUrlDialog(this, this)
        addUrlDialog.setTitle(tabName)
        addUrlDialog.show()
    }*/



}
*/