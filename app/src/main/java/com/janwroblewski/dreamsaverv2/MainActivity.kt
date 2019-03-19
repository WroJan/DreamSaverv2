package com.janwroblewski.dreamsaverv2

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_dream_btn.setOnClickListener {
            val intent = Intent(this, AddDreamActivity::class.java)
            startActivity(intent)

        }

    }
        override fun onResume() {
            super.onResume()

            val dbHelper = DataBaseHelper(applicationContext)
            val db: SQLiteDatabase = dbHelper.writableDatabase


            val cursor: Cursor = db.query(TableInfo.TABLE_NAME, null, null, null, null, null, null)
            val dreams = ArrayList<DreamObject>()

            if (cursor.count > 0) {
                cursor.moveToFirst()

                while (!cursor.isAfterLast) {
                    val dream = DreamObject()
                    dream.id = cursor.getInt(0)
                    dream.title = cursor.getString(1)
                    dream.desc = cursor.getString(2)
                    dreams.add(dream)
                    cursor.moveToNext()

                }

            }
            cursor.close()

            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.adapter = DreamViewAdapter(applicationContext, db, dreams)
        }
}