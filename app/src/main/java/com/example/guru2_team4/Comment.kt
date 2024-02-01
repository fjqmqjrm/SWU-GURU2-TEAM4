package com.example.guru2_team4

data class Comment(
    val commentId: String,
    val postId: String,  // 해당 댓글이 속한 게시물 ID
    val userId: String,
    val content: String,
    val timestamp: Long
)

