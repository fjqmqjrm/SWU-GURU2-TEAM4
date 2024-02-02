package com.example.guru2_team4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommentAdapter(private val context: Context, private var commentList: List<Comment>, private val dbHelper: DBHelper) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {


    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView) // 추가된 부분
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    // 새로운 댓글 목록으로 업데이트하는 메서드 추가
    fun updateComments(newCommentList: List<Comment>) {
        commentList = newCommentList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        val userName = dbHelper.getUserNicknameById(comment.userId) ?: "Unknown User"
        holder.userNameTextView.text = userName
        holder.contentTextView.text = comment.content

        // 작성 시간 설정
        val formattedTimestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            .format(Date(comment.timestamp))
        holder.timestampTextView.text = formattedTimestamp
    }

    override fun getItemCount(): Int {
        return commentList.size
    }



}

