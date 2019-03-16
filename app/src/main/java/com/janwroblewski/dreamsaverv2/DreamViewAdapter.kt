package com.janwroblewski.dreamsaverv2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.v4.content.ContextCompat.startActivities
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.dream_view.view.*


class DreamViewAdapter(val context: Context, val db: SQLiteDatabase): RecyclerView.Adapter<MyViewHolder>() {
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

        val cursor: Cursor = db.query(TableInfo.TABLE_NAME,
            null, //select all columns
            BaseColumns._ID + "=?", //filter by column ID
            arrayOf(p0.adapterPosition.plus(1).//recycler view starts from 0 and table id starts at 1 so w need to add one
                toString()),null,null,null)

        if(cursor.moveToFirst()) {
            if(!(cursor.getString(1).isNullOrEmpty() && cursor.getString(2).isNullOrEmpty())) {
                title.setText(cursor.getString(1))
                desc.setText(cursor.getString(2))


                eachView_dream.setOnClickListener {
                    val intent = Intent(context, AddDreamActivity::class.java)

                    val titleEdit = title.text
                    val descEdit = desc.text
                    val idEdit = p0.adapterPosition.plus(1) //recycler starts at 0 table starts from 1



                    intent.putExtra("title", titleEdit)
                    intent.putExtra("desc", descEdit)
                    intent.putExtra("id",idEdit)

                    context.startActivities(arrayOf(intent))


                }

            }
        }



    }

}


class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)
