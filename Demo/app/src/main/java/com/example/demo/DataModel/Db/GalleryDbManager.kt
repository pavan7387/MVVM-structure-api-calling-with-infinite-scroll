package com.example.demo.DataModel.Db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.demo.Model.Data
import com.example.demo.Model.ModelPage
import java.util.zip.ZipException

class GalleryDbManager {
    /**
     * add patient item in database table
     * @param modelPatient list of stickers
     * @return
     * @throws ZipException exception
     */
    @Throws(ZipException::class)
    fun addGalleryData(modelGallery: Data): Int {
        var sqLiteDatabase: SQLiteDatabase? = null
        var database: Database? = null
        var result: Int = 0

        try {
            database = Database.instance
            sqLiteDatabase = database!!.openDatabase()
                beginTransaction(sqLiteDatabase)
                val values = ContentValues()
                values.put(Database.COLUMN_IMAGE_ID, modelGallery.id)
                values.put(Database.COLUMN_IMAGE_URL, modelGallery.images?.get(0)?.link)
                values.put(Database.COLUMN_TITAL, modelGallery.title)


                if (doesPatientIdExist(modelGallery.id!!, sqLiteDatabase!!, Database.TABLE_GALLERY) ) {
                    val whereClause: String =Database.COLUMN_IMAGE_ID.toString() + " = ?"
                    val whereClauseArgs = arrayOf<String>(java.lang.String.valueOf(modelGallery.id))
                    result =   sqLiteDatabase!!.update(Database.TABLE_GALLERY, values,whereClause,whereClauseArgs )
                } else {
                    result =    sqLiteDatabase!!.insert(Database.TABLE_GALLERY, null, values).toInt()
                }
                setTransactionSuccessful(sqLiteDatabase)

        } catch (exception: Exception) {
            Log.d("databaseexception",exception.toString())
        } finally { /*End Transaction*/
            endTransaction(sqLiteDatabase)
            database!!.closeDatabase()
        }
        return result
    }


    /**
     * check the media already added or not
     * @param id
     * @param sqliteDatabase Sqlite instance
     * @param tableName database table name
     * @return boolean if sticker exist or not
     * @throws ZipException
     */
    @Throws(ZipException::class)
    private fun doesPatientIdExist( id: String,sqliteDatabase: SQLiteDatabase, tableName: String ): Boolean {
        var isMediaExist = false
        var count = 0
        var cursor: Cursor? = null
        try {
            cursor = sqliteDatabase.query(tableName,arrayOf(Database.COLUMN_IMAGE_ID), Database.COLUMN_IMAGE_ID.toString() + " = ?",
                arrayOf(id.toString()),null,null,null)
            if (cursor != null) {
                count = cursor.count
                if (count > 0) {
                    isMediaExist = true
                }
            }
        } catch (exception: java.lang.Exception) {
            Log.d("databaseexception",exception.toString())

        } finally {
            closeCursor(cursor)
        }
        return isMediaExist
    }

    /***
     * Close cursor
     * @param cursor
     */
    fun closeCursor(cursor: Cursor?) {
        if (cursor != null && !cursor.isClosed) {
            try {
                cursor.close()
            } catch (e: java.lang.Exception) { //Do Nothing
                e.printStackTrace()
            }
        }
    }


    fun endTransaction(sqliteDatabase: SQLiteDatabase?) {
        try {
            sqliteDatabase?.endTransaction()
        } catch (exception: java.lang.Exception) { //Consume
        }
    }

    /**
     * Sql Transaction Methods
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    fun beginTransaction(sqliteDatabase: SQLiteDatabase?) {
        try {
            sqliteDatabase?.beginTransactionNonExclusive()
        } catch (exception: java.lang.Exception) { //Consume
        }
    }

    fun setTransactionSuccessful(sqliteDatabase: SQLiteDatabase?) {
        try {
            sqliteDatabase?.setTransactionSuccessful()
        } catch (exception: java.lang.Exception) { //Consume
        }
    }

    fun getImageDetailById(imageid: String): Data {
        var sqLiteDatabase: SQLiteDatabase? = null
        var database: Database? = null
        var cursorMedia: Cursor? = null
        val gallery = Data()
        try {
            database = Database.instance
            sqLiteDatabase = database!!.openDatabase()
            val query = " SELECT *"  + " FROM " + Database.TABLE_GALLERY + " WHERE " + Database.COLUMN_IMAGE_ID +" = "+ "\'$imageid\'"
            cursorMedia = sqLiteDatabase?.rawQuery(query,null)
            if (cursorMedia != null && cursorMedia.moveToFirst()){
                do {
                    gallery.link =cursorMedia.getString(cursorMedia.getColumnIndex(Database.COLUMN_IMAGE_URL))
                    gallery.description =cursorMedia.getString(cursorMedia.getColumnIndex(Database.COLUMN_COMMENT))
                    gallery.id =cursorMedia.getString(cursorMedia.getColumnIndex(Database.COLUMN_IMAGE_ID))
                    gallery.title =cursorMedia.getString(cursorMedia.getColumnIndex(Database.COLUMN_TITAL))
                }while (cursorMedia.moveToNext())
                closeCursor(cursorMedia)
            }
        } catch (exception: Exception) {
            Log.d("databaseexception",exception.toString())
        } finally {
            endTransaction(sqLiteDatabase)
            database!!.closeDatabase()
        }
        return gallery

    }

    fun updateComment(imageId: String, data: String) {
        var sqLiteDatabase: SQLiteDatabase? = null
        var database: Database? = null

        try {
            database = Database.instance
            sqLiteDatabase = database!!.openDatabase()
            val sql = "UPDATE " + Database.TABLE_GALLERY + " SET " +
                    Database.COLUMN_COMMENT + " = " + "\'$data\'" +
                    " WHERE " + Database.COLUMN_IMAGE_ID + " = " +"\'$imageId\'"
            sqLiteDatabase!!.execSQL(sql)
        } catch (exception: java.lang.Exception) {
            Log.d("dberror",exception.toString())
        } finally { /*End Transaction*/
            endTransaction(sqLiteDatabase)
            database!!.closeDatabase()
        }
    }

}