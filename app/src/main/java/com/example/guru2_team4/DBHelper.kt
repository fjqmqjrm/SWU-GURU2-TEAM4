package com.example.guru2_team4

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import java.security.KeyStore.TrustedCertificateEntry




class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "Login.db", null, 1) {
    private lateinit var MyDB: SQLiteDatabase

    // users 테이블 생성
    // followers 테이블 생성 (사용자 간의 팔로우 관계를 저장)
    // questions, answers 테이블 생성 (Q&A 목록들을 저장)
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE users(id TEXT PRIMARY KEY, password TEXT, nick TEXT, phone TEXT, part INTEGER DEFAULT 1)")
        db.execSQL("create Table posts(id INTEGER primary key autoincrement, userId TEXT, title TEXT, content TEXT, timestamp INTEGER)")
        db.execSQL("create Table comments(commentId INTEGER primary key autoincrement, postId TEXT, userId TEXT, content TEXT, timestamp INTEGER)")
        MyDB = db
        MyDB.execSQL("create Table followers(user_id TEXT, follower_id TEXT, primary key(user_id, follower_id))")
        MyDB!!.execSQL("CREATE TABLE questions(QandA_id INTEGER, respondent_id TEXT, questioner_id TEXT, title TEXT, content TEXT, PRIMARY KEY (QandA_id, respondent_id, questioner_id))")
        MyDB!!.execSQL("CREATE TABLE answers(QandA_id INTEGER, respondent_id TEXT, questioner_id TEXT, content TEXT, PRIMARY KEY (QandA_id, respondent_id, questioner_id))")
        //kyueun추가
        MyDB!!.execSQL("create Table PartPage(partName TEXT primary key, url TEXT)")
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
    @SuppressLint("Range")
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
//    // userId, title, content, phone 삽입 (성공시 true, 실패시 false)
//    fun insertPostData(userId: String?, title: String?, content: String?): Boolean {
//        val MyDB = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put("userId", userId)
//        contentValues.put("title", title)
//        contentValues.put("content", content)
//        contentValues.put("timestamp", System.currentTimeMillis())
//        val result = MyDB.insert("posts", null, contentValues)
//        MyDB.close()
//        return result != -1L
//    }
//
//    @SuppressLint("Range")
//    fun getAllPosts(): List<Post> {
//        val postList = mutableListOf<Post>()
//        val db = this.readableDatabase
//
//        val cursor = db.rawQuery("SELECT * FROM posts ORDER BY timestamp DESC", null)
//
//        try {
//            // Cursor가 데이터를 포함하고 있는지 확인
//            if (cursor.moveToFirst()) {
//                do {
//                    val postId = cursor.getLong(cursor.getColumnIndex("id"))
//                    val userId = cursor.getString(cursor.getColumnIndex("userId"))
//                    val title = cursor.getString(cursor.getColumnIndex("title"))
//                    val content = cursor.getString(cursor.getColumnIndex("content"))
//                    val timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"))
//
//                    val post = Post(postId, userId, title, content, timestamp)
//                    postList.add(post)
//                } while (cursor.moveToNext())
//            }
//        } finally {
//            // Cursor를 항상 닫아야 함
//            cursor.close()
//        }
//
//        // 데이터베이스를 사용한 후에는 반드시 닫아야 함
//        db.close()
//
//        return postList
//    }

    @SuppressLint("Range")
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

//    fun insertCommentData(postId: String, userId: String, content: String): Boolean {
//        val db = this.writableDatabase
//
//        val values = ContentValues().apply {
//            put("postId", postId)
//            put("userId", userId)
//            put("content", content)
//            put("timestamp", System.currentTimeMillis())
//        }
//
//        val success = db.insert("comments", null, values) != -1L
//        db.close()
//
//        return success
//    }
//
//    @SuppressLint("Range")
//    fun getCommentsByPostId(postId: String): List<Comment> {
//        val commentList = mutableListOf<Comment>()
//        val db = this.readableDatabase
//
//        val cursor = db.rawQuery("SELECT * FROM comments WHERE postId = ?", arrayOf(postId))
//
//        try {
//            // Cursor가 데이터를 포함하고 있는지 확인
//            if (cursor.moveToFirst()) {
//                do {
//                    val commentId = cursor.getLong(cursor.getColumnIndex("commentId"))
//                    val userId = cursor.getString(cursor.getColumnIndex("userId"))
//                    val content = cursor.getString(cursor.getColumnIndex("content"))
//                    val timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"))
//
//                    val comment = Comment(commentId.toString(), postId, userId, content, timestamp)
//                    commentList.add(comment)
//                } while (cursor.moveToNext())
//            }
//        } finally {
//            cursor.close()
//        }
//
//        db.close()
//
//        return commentList
//    }















    // getUserIDByNick : 유저의 nick을 입력하면 id를 반환
    @SuppressLint("Range")
    fun getUserIDByNick(nick: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("Select id from users where nick =?", arrayOf(nick))

        return if (cursor.moveToFirst()) {
            val userID = cursor.getString(cursor.getColumnIndex("id"))
            cursor.close()
            userID
        } else {
            cursor.close()
            null
        }
    }

    // getUserNickByIndex : i를 입력하면 users 테이블에서 i번째 행의 nick을 반환
    fun getUserNickByIndex(i: Int): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT nick FROM users LIMIT 1 OFFSET ?", arrayOf(i.toString()))

        return try {
            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow("nick"))
            } else {
                null
            }
        } catch (e: Exception) {
            // Log an error and return null in case of any exception
            Log.e("getUserNickByIndex", "Error retrieving user id at index $i", e)
            null
        } finally {
            cursor?.close()
            MyDB.close()
        }
    }
    // getUserIdByIndex : i를 입력하면 users 테이블에서 i번째 행의 id를 반환
    fun getUserIdByIndex(i: Int): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT id FROM users LIMIT 1 OFFSET ?", arrayOf(i.toString()))

        return try {
            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow("id"))
            } else {
                null
            }
        } catch (e: Exception) {
            // Log an error and return null in case of any exception
            Log.e("getUserIdByIndex", "Error retrieving user id at index $i", e)
            null
        } finally {
            cursor?.close()
            MyDB.close()
        }
    }

    // getUserNickByIndexFromPart : 특정 파트를 공부하는 users 테이블에서 i번째 행의 nick을 반환
    fun getUserNickByIndexFromPart(part: Int, i: Int): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT nick FROM users WHERE part = ? LIMIT 1 OFFSET ?", arrayOf(part.toString(), i.toString()))

        return try {
            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow("nick"))
            } else {
                null
            }
        } catch (e: Exception) {
            // Log an error and return null in case of any exception
            Log.e("getUserIdByIndexFromPart", "Error retrieving user id at index $i in part $part", e)
            null
        } finally {
            cursor?.close()
            db.close()
        }
    }

    // addFollower : followers 테이블에서 사용자 간의 팔로우 관계를 추가하는 함수
    fun addFollower(user_id: String?, follower_id: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("user_id", user_id)           // user_id : 팔로우를 받는 사용자
        contentValues.put("follower_id", follower_id)   // follower_id : 팔로우를 하는 사용자
        val result = MyDB.insert("followers", null, contentValues)
        MyDB.close()
        return result != -1L
    }

    // isFollowing : followers 테이블에서 사용자가 다른 사용자를 팔로우하고 있는지 확인하는 함수
    fun isFollowing(user_id: String?, follower_id: String?): Boolean {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("Select * from followers where user_id =? and follower_id =?", arrayOf(user_id, follower_id))
        val result = cursor.count > 0
        cursor.close()
        return result
    }

    // delFollower : followers 테이블에서 사용자 간의 팔로우 관계를 삭제하는 함수
    fun delFollower(user_id: String?, follower_id: String?): Boolean {
        val MyDB = this.writableDatabase
        val result = MyDB.delete("followers", "user_id = ? AND follower_id = ?", arrayOf(user_id, follower_id))
        MyDB.close()
        return result > 0
    }

    // getUserCount : users 테이블에서 모든 유저 수를 반환하는 함수
    fun getUserCount(): Int {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT COUNT(*) FROM users", null)
        var userCount = 0
        if (cursor.moveToFirst()) {
            userCount = cursor.getInt(0)
        }
        cursor.close()
        MyDB.close()
        return userCount
    }

    // getUserCountFromPart : i를 입력하면 i번째 파트를 공부 중인 유저 수를 반환하는 함수
    fun getUserCountFromPart(i: Int): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM users WHERE part = ?", arrayOf(i.toString()))
        var count = 0

        try {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
        } finally {
            cursor.close()
            db.close()
        }

        return count
    }

    // getUserPart : 유저의 Part을 조회하는 함수
    @SuppressLint("Range")
    fun getUserPart(user_id: String?): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT part FROM users WHERE id = ?", arrayOf(user_id))
        var part = 0

        if (cursor.moveToFirst()) {
            part = cursor.getInt(cursor.getColumnIndex("part"))
        }

        cursor.close()
        db.close()
        return part
    }

    // setUserPart : 유저의 Part를 설정
    fun setUserPart(user_id: String?, newPart: Int) {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("part", newPart)

        val whereClause = "id = ?"
        val whereArgs = arrayOf(user_id)

        MyDB.update("users", contentValues, whereClause, whereArgs)
        MyDB.close()
    }

    // addQuestion : questions 테이블에 질문을 추가하는 함수
    fun addQuestion(QandA_id: Int?, respondent_id: String?, questioner_id: String?, title: String?, content: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("QandA_id", QandA_id ?: 0)
        contentValues.put("respondent_id", respondent_id)
        contentValues.put("questioner_id", questioner_id)
        contentValues.put("title", title)
        contentValues.put("content", content)

        val result = MyDB.insert("questions", null, contentValues)
        MyDB.close()
        return result != -1L
    }

    // addAnswer : answers 테이블에 답변을 추가하는 함수
    fun addAnswer(QandA_id: Int?, respondent_id: String?, questioner_id: String?, content: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("QandA_id", QandA_id)
        contentValues.put("respondent_id", respondent_id)
        contentValues.put("questioner_id", questioner_id)
        contentValues.put("content", content)

        val result = MyDB.insert("answers", null, contentValues)
        MyDB.close()
        return result != -1L
    }

    // getLatestQandAId : 가장 최근 QandA_id를 반환하는 함수
    fun getLatestQandAId(respondent_id: String, questioner_id: String): Int {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT MAX(QandA_id) FROM questions WHERE respondent_id = ? AND questioner_id = ?", arrayOf(respondent_id.toString(), questioner_id.toString()))

        var maxQandAId = 0
        if (cursor.moveToFirst()) {
            maxQandAId = cursor.getInt(0)
        }

        cursor.close()
        MyDB.close()
        return maxQandAId
    }
    // getQuestionCount : 질문자-답변자 간의 question 개수를 반환
    fun getQuestionCount(respondent_id: String, questioner_id: String): Int {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT COUNT(*) FROM questions WHERE respondent_id = ? AND questioner_id = ?",
            arrayOf(respondent_id, questioner_id))

        var questionCount = 0
        if (cursor.moveToFirst()) {
            questionCount = cursor.getInt(0)
        }

        cursor.close()
        MyDB.close()
        return questionCount
    }

    // getAnswerCount : 질문자-답변자 간의 answer 개수를 반환
    fun getAnswerCount(respondent_id: String, questioner_id: String): Int {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT COUNT(*) FROM answers WHERE respondent_id = ? AND questioner_id = ?",
            arrayOf(respondent_id, questioner_id))

        var answerCount = 0
        if (cursor.moveToFirst()) {
            answerCount = cursor.getInt(0)
        }

        cursor.close()
        MyDB.close()
        return answerCount
    }

    // getRespondentListFromQuestions : questions 테이블에서 특정 questioner_id에 해당하는 중복되지 않는 respondent_id 목록을 출력하는 함수
    fun getRespondentListFromQuestions(questioner_id: String): List<String> {
        val MyDB = this.readableDatabase
        val respondentList = mutableListOf<String>()

        val cursor = MyDB.rawQuery("SELECT DISTINCT respondent_id FROM questions WHERE questioner_id = ?", arrayOf(questioner_id))

        try {
            if (cursor.moveToFirst()) {
                do {
                    val respondent_id = cursor.getString(cursor.getColumnIndexOrThrow("respondent_id"))
                    respondentList.add(respondent_id)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            // Log an error and return an empty list in case of any exception
            Log.e("getRespondentListForQuestioner", "Error retrieving respondent list for questioner", e)
        } finally {
            cursor?.close()
            MyDB.close()
        }

        return respondentList
    }

    // getRespondentListFromAnswers : answer 테이블에서 특정 questioner_id에 해당하는 중복되지 않는 respondent_id 목록을 출력하는 함수
    fun getRespondentListFromAnswers(questioner_id: String): List<String> {
        val MyDB = this.readableDatabase
        val respondentList = mutableListOf<String>()

        val cursor = MyDB.rawQuery("SELECT DISTINCT respondent_id FROM answers WHERE questioner_id = ?", arrayOf(questioner_id))

        try {
            if (cursor.moveToFirst()) {
                do {
                    val respondent_id = cursor.getString(cursor.getColumnIndexOrThrow("respondent_id"))
                    respondentList.add(respondent_id)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            // Log an error and return an empty list in case of any exception
            Log.e("getRespondentListForQuestioner", "Error retrieving respondent list for questioner", e)
        } finally {
            cursor?.close()
            MyDB.close()
        }

        return respondentList
    }

    // getQuestionTitle : questions 테이블에서 특정 question의 title을 반환하는 함수
    fun getQuestionTitle(QandA_id: Int, respondent_id: String, questioner_id: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT title FROM questions WHERE QandA_id = ? AND respondent_id = ? AND questioner_id = ?",
            arrayOf(QandA_id.toString(), respondent_id, questioner_id))

        return try {
            if (cursor.moveToFirst()) {
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                title
            } else {
                null
            }
        } catch (e: Exception) {
            // Log an error and return null in case of any exception
            Log.e("getQuestionTitle", "Error retrieving question title", e)
            null
        } finally {
            cursor?.close()
            MyDB.close()
        }
    }

    // getQuestionContent : questions 테이블에서 특정 question의 content를 반환하는 함수
    fun getQuestionContent(QandA_id: Int, respondent_id: String, questioner_id: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT content FROM questions WHERE QandA_id = ? AND respondent_id = ? AND questioner_id = ?",
            arrayOf(QandA_id.toString(), respondent_id, questioner_id))

        return try {
            if (cursor.moveToFirst()) {
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                content
            } else {
                null
            }
        } catch (e: Exception) {
            // Log an error and return null in case of any exception
            Log.e("getQuestionContent", "Error retrieving question content", e)
            null
        } finally {
            cursor?.close()
            MyDB.close()
        }
    }

    // getAnswerContent : answers 테이블에서 특정 answer의 content를 반환하는 함수
    fun getAnswerContent(QandA_id: Int, respondent_id: String, questioner_id: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT content FROM answers WHERE QandA_id = ? AND respondent_id = ? AND questioner_id = ?",
            arrayOf(QandA_id.toString(), respondent_id, questioner_id))

        return try {
            if (cursor.moveToFirst()) {
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                content
            } else {
                null
            }
        } catch (e: Exception) {
            // Log an error and return null in case of any exception
            Log.e("getQuestionContent", "Error retrieving question content", e)
            null
        } finally {
            cursor?.close()
            MyDB.close()
        }
    }

    // isAnswered : answers 테이블에서 question이 답변되었는지 확인하는 함수
    fun isAnswered(QandA_id: Int, respondent_id: String, questioner_id: String): Boolean {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery(
            "SELECT 1 FROM answers WHERE QandA_id = ? AND respondent_id = ? AND questioner_id = ?",
            arrayOf(QandA_id.toString(), respondent_id, questioner_id)
        )

        val result = cursor.moveToFirst()
        cursor.close()
        MyDB.close()
        return result
    }
    // countQuestionsByRespondent : 입력한 respondent_id를 포함하는 행의 개수를 반환하는 함수
    fun countQuestionsByRespondent(respondentId: String): Int {
        val db = this.readableDatabase
        val selection = "respondent_id = ?"
        val selectionArgs = arrayOf(respondentId)

        val cursor = db.query("questions", null, selection, selectionArgs, null, null, null)

        val rowCount = cursor.count
        cursor.close()
        db.close()

        return rowCount
    }
    // 입력한 respondent_id를 포함하는 데이터의 i번째 행을 조회 -> QandA_id, questioner_id를 반환하는 함수
    data class QuestionInfo(val QandA_id: Int, val questioner_id: String)
    fun getQuestionInfoByRespondentAndIndex(respondentId: String, index: Int): QuestionInfo? {
        val db = this.readableDatabase
        val selection = "respondent_id = ?"
        val selectionArgs = arrayOf(respondentId)
        val limitOffset = "$index, 1" // Fetch only 1 row starting from the specified index

        val cursor = db.query("questions", arrayOf("QandA_id", "questioner_id"), selection, selectionArgs, null, null, null, limitOffset)

        return try {
            if (cursor.moveToFirst()) {
                val qAndAId = cursor.getInt(cursor.getColumnIndexOrThrow("QandA_id"))
                val questionerId = cursor.getString(cursor.getColumnIndexOrThrow("questioner_id"))
                QuestionInfo(qAndAId, questionerId)
            } else {
                null
            }
        } finally {
            cursor.close()
            db.close()
        }
    }


    // 여기서부터 규은님 DB
    // PartPage 테이블에 URL 추가
    fun insertPartUrl(partName: String?, url: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("partName", partName)
        contentValues.put("url", url)
        val result = MyDB.insert("PartPage", null, contentValues)
        MyDB.close()
        return if (result == -1L) false else true
    }

    // PartPage 테이블에서 URL 가져오기
    @SuppressLint("Range")
    fun getPartUrl(partName: String): String? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("Select url from PartPage where partName =?", arrayOf(partName))

        return if (cursor.moveToFirst()) {
            val url = cursor.getString(cursor.getColumnIndex("url"))
            cursor.close()
            url
        } else {
            cursor.close()
            null
        }
    }

    // DBHelper 클래스 내의 특정 메서드에서 사용하는 예제 코드
    fun getUrlFromCursor(cursor: Cursor): String? {
        val urlColumnIndex = cursor.getColumnIndex("url")

        return if (urlColumnIndex >= 0) {
            // 컬럼이 존재하는 경우에만 값을 가져옴
            cursor.getString(urlColumnIndex)
        } else {
            // 컬럼이 존재하지 않는 경우 처리
            // 예를 들어, 기본값을 설정하거나 에러를 처리할 수 있음
            null
        }
    }


    // DB name을 Login.db로 설정
    companion object {
        const val DBNAME = "Login.db"
    }

}