package com.janwroblewski.dreamsaverv2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            val db:SQLiteDatabase = dbHelper.writableDatabase
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.adapter = DreamViewAdapter(applicationContext,db)

    }


}
