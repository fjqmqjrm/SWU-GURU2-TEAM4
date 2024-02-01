package com.example.guru2_team4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CustomListAdapter(private val context: Context,private val dbHelper: DBHelper, private val dataList: List<Post>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false)

        // 데이터에서 현재 포지션의 아이템을 가져옴
        val currentItem = getItem(position) as Post


        // 닉네임 가져오기
        val nickname = dbHelper.getUserNickname(currentItem.userId)

        // 레이아웃에서 TextView들을 찾아와 데이터 설정
        val titleTextView = view.findViewById<TextView>(R.id.textViewTitle)
        val contentTextView = view.findViewById<TextView>(R.id.textViewContent)
        val nickTextView = view.findViewById<TextView>(R.id.textViewNick)
        val timestampTextView: TextView = view.findViewById(R.id.textViewTime)
        titleTextView.text = currentItem.title
        contentTextView.text = currentItem.content
        nickTextView.text = nickname
        // 작성 시간 설정
        val formattedTimestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            .format(Date(currentItem.timestamp))
        timestampTextView.text = formattedTimestamp

        return view
    }
}
