package ru.chiya.clounada.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val DATABASE_NAME = "booking_db"
val TABLE_NAME = "available_seats"

val COL_ACT = "act"
val COL_LOCATION = "location"
val COL_ROW = "row"
val COL_SEATS = "seats"



class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " +  TABLE_NAME + " (" +
                COL_ACT + " VARCHAR(265) NOT NULL , " +
                COL_LOCATION + " VARCHAR(265) NOT NULL, " +
        COL_ROW + " INTEGER NOT NULL, " +
                COL_SEATS + " INTEGER NOT NULL) "

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(booking:Booking){

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_ACT, booking.act)
        values.put(COL_LOCATION, booking.location)
        values.put(COL_ROW, booking.row)
        values.put(COL_SEATS, booking.seats)


        var result = db.insert(TABLE_NAME, null, values)


        db.close()
    }
}