package com.janwroblewski.dreamsaverv2

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_dream.*

class AddDreamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dream)

        val dbHelper = DataBaseHelper(applicationContext)
        val db: SQLiteDatabase = dbHelper.writableDatabase


        add_dream_btn.setOnClickListener {
            val title: String = title_text_input.text.toString()
            val desc:String = desc_text_input.text.toString()

            val value = ContentValues()
            value.put("title", title)
            value.put("description", desc)

            db.insertOrThrow(TableInfo.TABLE_NAME, null, value)

            Toast.makeText(this, "Dream saved", Toast.LENGTH_SHORT).show()
            finish()




        }
    }
}
