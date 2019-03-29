package com.janwroblewski.dreamsaverv2

import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dream_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddDreamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_add)

        dater()

        val dbHelper = DataBaseHelper(applicationContext)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        if(intent.hasExtra("title")) {title_text_input.setText(intent.getStringExtra("title"))}
        if(intent.hasExtra("desc")) {desc_text_input.setText(intent.getStringExtra("desc"))}



        add_dream_btn.setOnClickListener {

            val title: String = title_text_input.text.toString()
            val desc: String = desc_text_input.text.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "Please add Title", Toast.LENGTH_SHORT).show()
            } else {


                val value = ContentValues()
                value.put(TableInfo.TABLE_COLUMN_TITLE, title)
                value.put(TableInfo.TABLE_COLUMN_DESC, desc)





                if (intent.hasExtra("id")) {

                    db.update(
                        TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                        arrayOf(intent.getStringExtra("id"))
                    )
                    Toast.makeText(this, "Dream Updated", Toast.LENGTH_SHORT).show()
                    finish()

                } else {
                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                    Toast.makeText(this, "Dream saved", Toast.LENGTH_SHORT).show()
                    finish()

                }
            }
        }
    }


    private fun  dater() {
        val date = Calendar.getInstance().getTime()

        val sdf = SimpleDateFormat("dd/MM/YYYY")
        val dateString = sdf.format(date)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val day = cal.get(Calendar.DAY_OF_YEAR)
        val month = cal.get(Calendar.MONTH)


        date_pick.text = dateString

        date_pick.setOnClickListener {
            val dailogPicker =
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    date_pick.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year)
                }, year, month, day)
            dailogPicker.show()

        }
    }

}
