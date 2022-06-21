package com.example.uasdpm_2011500013

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpDosen: Adapterdosen
    private lateinit var data_Kampus: ArrayList<data_kampus>
    private lateinit var lvkampus: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvkampus = findViewById(R.id.lvKampus)
        linTidakAda = findViewById(R.id.linTidakAda)

        data_Kampus = ArrayList()
        adpDosen = Adapterdosen(this@MainActivity, data_Kampus)

        lvkampus.adapter =adpDosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, Entri_Dosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = kampus(this@MainActivity)
        val data = db.tampil()
        repeat(data_Kampus.size) { data_Kampus.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val matkul = data_kampus(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDosen.add(matkul)
                adpDosen.notifyDataSetChanged()
            }
            lvkampus.visibility = View.VISIBLE
            linTidakAda.visibility  = View.GONE
        } else {
            lvkampus.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}

