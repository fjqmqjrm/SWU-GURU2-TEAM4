package com.example.guru2_team4
/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PartPageActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page3)
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

class PartPageActivity3 : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView
    private lateinit var tabUrlStringTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlLoopTextView: TextView // @@@@@@@추가
    private lateinit var tabUrlComplexityTextView: TextView // @@@@@@@추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page3)
        /*
                findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
                    showAddUrlDialog("Part3")
                }
        */
        tabUrlTextView = findViewById(R.id.tabUrl)
        tabUrlStringTextView = findViewById(R.id.tabStringUrl) // 추가@@@@@@@@
        tabUrlLoopTextView = findViewById(R.id.tabLoopUrl) // 추가@@@@@@@@
        tabUrlComplexityTextView = findViewById(R.id.tabComplexityUrl) // 추가@@@@@@@@

        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "그래프(vertex, edge, node, arc)") //Part3
            addUrlDialog.show()
        }

        findViewById<RelativeLayout>(R.id.tabString).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "BFS")//Part3
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabLoop).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "DFS")//Part3
            addUrlDialog.show()
        }
        findViewById<RelativeLayout>(R.id.tabComplexity).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this, "위상정렬")//Part3
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

        when (topic) {
            "그래프(vertex, edge, node, arc)" -> {
                tabUrlTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlTextView, enteredUrl)
            }
            "BFS" -> {
                tabUrlStringTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlStringTextView, enteredUrl)
            }
            // 다른 탭에 대한 처리 추가
            "DFS" -> {
                tabUrlLoopTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlLoopTextView, enteredUrl)
            }
            "위상정렬" -> {
                tabUrlComplexityTextView.text = "URL         $enteredUrl"
                setClickableUrl(tabUrlComplexityTextView, enteredUrl)
            }
        }
        /*
        tabUrlTextView.text = "URL         $enteredUrl"
        tabUrlStringTextView.text = "URL         $enteredUrl"  // 문자열

         */

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
        return "배열" // 예시로 "동적 프로그래밍"으로 초기화
    }
}

/*
package com.example.codemate3

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView

class PartPageActivity3 : AppCompatActivity(), OnUrlEnteredListener {

    private lateinit var tabUrlTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_page3)

        findViewById<RelativeLayout>(R.id.addButton).setOnClickListener {
            val addUrlDialog = AddUrlDialog(this, this)
            addUrlDialog.show()
        }

        tabUrlTextView = findViewById(R.id.tabUrl)

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