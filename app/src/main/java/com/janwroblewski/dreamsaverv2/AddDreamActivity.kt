package com.janwroblewski.dreamsaverv2

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dream_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddDreamActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_add)

        val actionbarBack = supportActionBar
        actionbarBack!!.title = "Dream Save" // sets title on menu with in the addDreamActivity

        actionbarBack.setDisplayHomeAsUpEnabled(true)

        dater()

        if(intent.hasExtra("title")) {title_text_input.setText(intent.getStringExtra("title"))}
        if(intent.hasExtra("desc")) {desc_text_input.setText(intent.getStringExtra("desc"))}

        share_btn.setOnClickListener {
            shareTheDream()
        }
    }




    override fun onSupportNavigateUp(): Boolean {//back arrow button in menu at the top
        alertOnBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu) //save button inflater
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean { // method to handle save button
        if(item?.itemId == R.id.save_btn) {

            dreamHandler()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() { //what is happening when back button is pressed
        alertOnBackPressed()
    }

    private fun dreamHandler() {

        val dbHelper = DataBaseHelper(applicationContext)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val title: String = title_text_input.text.toString()
        val desc: String = desc_text_input.text.toString()

        if (title.isEmpty()) {
            title_text_input.setError("Please enter Title")
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

    private fun alertOnBackPressed() { // function is reused in two buttons, top arrow and back pressed.

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Discard the Dream?")

        builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
            finish()
        })

        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int ->

        })

        builder.setNeutralButton("Cancel", { dialogInterface: DialogInterface, i: Int ->

        })
        builder.show()
    }

    private fun shareTheDream() {
        val titleFromScreen = title_text_input.text.toString()
        val descFromScreen = desc_text_input.text.toString()

        if(titleFromScreen.isEmpty() || descFromScreen.isEmpty()) {
            Toast.makeText(this,"Enter Title & Description before sharing", Toast.LENGTH_SHORT).show()
        }
        else {

            val shareIntent = Intent()//create new intent as the other one above will open same reopen activity
            shareIntent.action = Intent.ACTION_SEND //set intent to send data
            shareIntent.putExtra(Intent.EXTRA_TEXT, titleFromScreen) //send data to the intent
            shareIntent.putExtra(Intent.EXTRA_TEXT, descFromScreen)
            shareIntent.type = "text/plain" //set it as plain text

            startActivity(Intent.createChooser(shareIntent, "Share Dream: ")) //open share activity build in the device.
        }
    }



}
