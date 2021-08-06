package com.amit.care4u

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import java.util.*;
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "Care4UDB"
        private val TABLE_NAME = "medicines"
        private val KEY_ID = "med_id"
        private val KEY_NAME = "med_name"
        private val KEY_TIME = "med_time"
        private val KEY_DESC = "med_desc"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_MESSAGE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_DESC + " TEXT" + ")")
        db?.execSQL(CREATE_MESSAGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addMedicine(med: MedicineModel):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_ID, msg.message_id)
        contentValues.put(KEY_NAME, med.getMed_name())
        contentValues.put(KEY_TIME, med.getMed_timing())
        contentValues.put(KEY_DESC, med.getMed_desc())

        val success = db.insert(TABLE_NAME, null, contentValues)

        db.close()
        return success
    }

    fun viewMedicines():List<MedicineModel>{
        val msgList:ArrayList<MedicineModel> = ArrayList<MedicineModel>()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var medName: String
        var medTiming: String
        var medDesc: String
        if (cursor.moveToFirst()) {
            do {
                medName = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                medTiming = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                medDesc = cursor.getString(cursor.getColumnIndex(KEY_DESC))
                val med = MedicineModel(medName, medTiming, medDesc)
                msgList.add(med)
            } while (cursor.moveToNext())
        }
        return msgList
    }

    fun deleteAllMedicines():Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME,"*",null)
        db.close()
        return success
    }
}