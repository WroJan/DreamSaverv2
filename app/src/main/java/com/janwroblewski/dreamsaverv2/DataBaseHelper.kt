package com.janwroblewski.dreamsaverv2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
object TableInfo: BaseColumns {
    //table description
    const val TABLE_NAME = "Dream"
    const val TABLE_COLUMN_TITLE = "Title"
    const val TABLE_COLUMN_DESC = "Description"


}
object Commands {
    //basic commands of sql light database
    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_TITLE} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_DESC} TEXT NOT NULL)"


    //if table exists than sql_delete_table will be used to delete tables permanently or delete and than updated
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
}
class DataBaseHelper(context: Context): SQLiteOpenHelper(context,TableInfo.TABLE_NAME, null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        //creates new table in database Dreams
        db?.execSQL(Commands.SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //deletes the table that exists
        db?.execSQL(Commands.SQL_DELETE_TABLE)
        //creates the table again with updated parameters
        onCreate(db)
    }
}