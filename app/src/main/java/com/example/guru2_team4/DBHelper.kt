package com.example.guru2_team4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.KeyStore.TrustedCertificateEntry


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "Login.db", null, 1) {
    private lateinit var MyDB: SQLiteDatabase
    // users 테이블 생성
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create Table users(id TEXT primary key, password TEXT, nick TEXT, phone TEXT)")
        db.execSQL("create Table posts(id INTEGER primary key autoincrement, userId TEXT, title TEXT, content TEXT, timestamp INTEGER)")
        db.execSQL("create Table comments(commentId INTEGER primary key autoincrement, postId TEXT, userId TEXT, content TEXT, timestamp INTEGER)")
        MyDB = db
    }

    // 정보 갱신
    override fun onUpgrade(MyDB: SQLiteDatabase?, i: Int, i1: Int) {
        MyDB!!.execSQL("drop Table if exists users")
        MyDB.execSQL("drop Table if exists posts")
        MyDB.execSQL("drop Table if exists comments")

        onCreate(MyDB)
    }

    // id, password, nick, phone 삽입 (성공시 true, 실패시 false)
    fun insertData (id: String?, password: String?, nick: String?, phone: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("password", password)
        contentValues.put("nick", nick)
        contentValues.put("phone", phone)
        val result = MyDB.insert("users", null, contentValues)
        MyDB.close()
        return if (result == -1L) false else true
    }

    // 사용자 아이디가 없으면 false, 이미 존재하면 true
    fun checkUser(id: String?): Boolean {
        val MyDB = this.readableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where id =?", arrayOf(id))
        if (cursor.count <= 0) res = false
        return res
    }

    // 사용자 닉네임이 없으면 false, 이미 존재하면 true
    fun checkNick(nick: String?): Boolean {
        val MyDB = this.readableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where nick =?", arrayOf(nick))
        if (cursor.count <= 0) res = false
        return res
    }

    // 해당 id, password가 있는지 확인 (없다면 false)
    fun checkUserpass(id: String, password: String) : Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery(
            "Select * from users where id = ? and password = ?",
            arrayOf(id, password)
        )
        if (cursor.count <= 0) res = false
        return res
    }

    // DBHelper 클래스에 getUserNickname 함수 추가
    fun getUserNickname(id: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("Select nick from users where id =?", arrayOf(id))

        return if (cursor.moveToFirst()) {
            val nickname = cursor.getString(cursor.getColumnIndex("nick"))
            cursor.close()
            nickname
        } else {
            cursor.close()
            null
        }
    }

    // userId, title, content, phone 삽입 (성공시 true, 실패시 false)
    fun insertPostData(userId: String?, title: String?, content: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("userId", userId)
        contentValues.put("title", title)
        contentValues.put("content", content)
        contentValues.put("timestamp", System.currentTimeMillis())
        val result = MyDB.insert("posts", null, contentValues)
        MyDB.close()
        return result != -1L
    }

    fun getAllPosts(): List<Post> {
        val postList = mutableListOf<Post>()
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM posts ORDER BY timestamp DESC", null)

        try {
            // Cursor가 데이터를 포함하고 있는지 확인
            if (cursor.moveToFirst()) {
                do {
                    val postId = cursor.getLong(cursor.getColumnIndex("id"))
                    val userId = cursor.getString(cursor.getColumnIndex("userId"))
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val content = cursor.getString(cursor.getColumnIndex("content"))
                    val timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"))

                    val post = Post(postId, userId, title, content, timestamp)
                    postList.add(post)
                } while (cursor.moveToNext())
            }
        } finally {
            // Cursor를 항상 닫아야 함
            cursor.close()
        }

        // 데이터베이스를 사용한 후에는 반드시 닫아야 함
        db.close()

        return postList
    }



    fun getUserNicknameById(id: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT nick FROM users WHERE id =?", arrayOf(id))

        return if (cursor.moveToFirst()) {
            val nickname = cursor.getString(cursor.getColumnIndex("nick"))
            cursor.close()
            nickname
        } else {
            // 데이터가 없는 경우에 대한 처리
            cursor.close()
            null
        }
    }

    fun insertCommentData(postId: String, userId: String, content: String): Boolean {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put("postId", postId)
            put("userId", userId)
            put("content", content)
            put("timestamp", System.currentTimeMillis())
        }

        val success = db.insert("comments", null, values) != -1L
        db.close()

        return success
    }

    fun getCommentsByPostId(postId: String): List<Comment> {
        val commentList = mutableListOf<Comment>()
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM comments WHERE postId = ?", arrayOf(postId))

        try {
            // Cursor가 데이터를 포함하고 있는지 확인
            if (cursor.moveToFirst()) {
                do {
                    val commentId = cursor.getLong(cursor.getColumnIndex("commentId"))
                    val userId = cursor.getString(cursor.getColumnIndex("userId"))
                    val content = cursor.getString(cursor.getColumnIndex("content"))
                    val timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"))

                    val comment = Comment(commentId.toString(), postId, userId, content, timestamp)
                    commentList.add(comment)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
        }

        db.close()

        return commentList
    }




    // DB name을 Login.db로 설정
    companion object {
        const val DBNAME = "Login.db"
    }





}