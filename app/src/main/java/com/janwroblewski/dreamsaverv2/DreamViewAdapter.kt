package com.janwroblewski.dreamsaverv2

import android.app.Service
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.dream_view.view.*

class DreamViewAdapter(val context: Context, val db: SQLiteDatabase, var dreams: ArrayList<DreamObject>): RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val eachDremView = layoutInflater.inflate(R.layout.dream_view, p0, false)
        return MyViewHolder(eachDremView)

    }
    override fun getItemCount(): Int {
        val cursor: Cursor = db.query(TableInfo.TABLE_NAME,
            null, //select all columns
            null, //filter by column ID
            null,null,null,null)
        val getNumberOfItems: Int = cursor.count
        cursor.close()
        return getNumberOfItems

    }
    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val eachView_dream = p0.view.each_dream_view
        val title: TextView = p0.view.title_text_view
        val desc: TextView = p0.view.desc_text_view

        title.setText(dreams[p0.adapterPosition].title)
        desc.setText(dreams[p0.adapterPosition].desc)

        val cursor: Cursor = db.query(TableInfo.TABLE_NAME,
            null, //select all columns
            BaseColumns._ID + "=?", //filter by column ID
            arrayOf(p0.adapterPosition.plus(1).//recycler view starts from 0 and table id starts at 1 so w need to add one
                toString()),null,null,null)

        p0.view.copy_btn.setOnClickListener {
                val cm = context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager // created service clipboard manager
                //copy and crated string of RecyclerView dream_view
                val clipdata = ClipData.newPlainText("Copy Text",
                    "Dream: " + title.text + "\n" + "Desc: " + desc.text)
                cm.primaryClip = clipdata
            Toast.makeText(context,"Copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        eachView_dream.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                    db.delete(TableInfo.TABLE_NAME,BaseColumns._ID + "=?",
                        arrayOf(dreams[p0.adapterPosition].id.toString())) //remove from sqlight table

                    dreams.removeAt(p0.adapterPosition)//remove from arraylist dreams

                    notifyItemRemoved(p0.adapterPosition)//notify recycler view that item was deleted so he can update the view.
                return true //return type boolean required for over right methods
            }

        })


        if(cursor.moveToFirst()) {
            if(!(cursor.getString(1).isNullOrEmpty() && cursor.getString(2).isNullOrEmpty())) {
                title.setText(cursor.getString(1))
                desc.setText(cursor.getString(2))

                eachView_dream.setOnClickListener {
                    //update when pressed on card view.
                    val intent = Intent(context, AddDreamActivity::class.java)
                    //create new intent
                    val titleEdit = dreams[p0.adapterPosition].title
                    val descEdit = dreams[p0.adapterPosition].desc
                    val idEdit = dreams[p0.adapterPosition].id.toString()
                    //grab all information from array  and sqlight


                    intent.putExtra("title", titleEdit)
                    intent.putExtra("desc", descEdit)
                    intent.putExtra("id",idEdit)

                    //sends info with puExtra to the intent stated above.

                    context.startActivity(intent)
                    //starts the activity using context that is stated above.
               }
            }
        }
    }
}


class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)