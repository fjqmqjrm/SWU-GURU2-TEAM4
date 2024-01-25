package com.example.guru2_team4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.KeyStore.TrustedCertificateEntry


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "Login.db", null, 1) {
    // users 테이블 생성
    override fun onCreate(MyDB: SQLiteDatabase?) {
        MyDB!!.execSQL("create Table users(id TEXT primary key, password TEXT, nick TEXT, phone TEXT)")
    }

    // 정보 갱신
    override fun onUpgrade(MyDB: SQLiteDatabase?, i: Int, i1: Int) {
        MyDB!!.execSQL("drop Table if exists users")
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


    // DB name을 Login.db로 설정
    companion object {
        const val DBNAME = "Login.db"
    }





}