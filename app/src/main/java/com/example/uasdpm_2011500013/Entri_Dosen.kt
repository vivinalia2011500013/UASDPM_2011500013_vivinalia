package com.example.uasdpm_2011500013

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Entri_Dosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_dosen)

        val modeEdit = intent.hasExtra("nidn") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golpat") &&
                intent.hasExtra("pendidikan") && intent.hasExtra("keahlian") &&
                intent.hasExtra("studi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etnidn = findViewById<EditText>(R.id.etnidn)
        val etnmDosen = findViewById<EditText>(R.id.etnmDosen)
        val spnjabatan = findViewById<Spinner>(R.id.spnjabatan)
        val spngolpangkat = findViewById<Spinner>(R.id.spngolpangkat)
        val rds2 = findViewById<RadioButton>(R.id.rds2)
        val rds3 = findViewById<RadioButton>(R.id.rds3)
        val etbdgAhli = findViewById<EditText>(R.id.etbdgAhli)
        val etstudi = findViewById<EditText>(R.id.etstudi)
        val btnsimpan = findViewById<Button>(R.id.btnsimpan)
        val etjabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val pangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolpat = ArrayAdapter(
            this@Entri_Dosen,
            android.R.layout.simple_spinner_dropdown_item,
            pangkat
        )
        spngolpangkat.adapter = adpGolpat

        val adpJabatan = ArrayAdapter(
            this@Entri_Dosen,
            android.R.layout.simple_spinner_dropdown_item,
            etjabatan
        )
        spnjabatan.adapter = adpJabatan

        if(modeEdit) {
            val kodeNidn = intent.getStringExtra("nidn")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val golpat = intent.getStringExtra("golpat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val studi = intent.getStringExtra("studi")

            etnidn.setText(kodeNidn)
            etnmDosen.setText(nama)
            spnjabatan.setSelection(etjabatan.indexOf(jabatan))
            spngolpangkat.setSelection(pangkat.indexOf(golpat))
            if(pendidikan == "S2") rds2.isChecked = true else rds3.isChecked = true
            etbdgAhli.setText(keahlian)
            etstudi.setText(studi)
        }
        etnidn.isEnabled = !modeEdit

        btnsimpan.setOnClickListener {
            if("${etnidn.text}".isNotEmpty() && "${etnmDosen.text}".isNotEmpty()
                && "${etbdgAhli.text}".isNotEmpty() && "${etstudi.text}".isNotEmpty() &&
                (rds2.isChecked || rds3.isChecked)) {
                val db = kampus(this@Entri_Dosen)
                db.nidn = "${etnidn.text}"
                db.nmDosen = "${etnmDosen.text}"
                db.jabatan = spnjabatan.selectedItem as String
                db.golonganpangkat = spngolpangkat.selectedItem as String
                db.pendidikan = if(rds2.isChecked) "S2" else "S3"
                db.keahlian = "${etbdgAhli.text}"
                db.programstudi = "${etstudi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etnidn.text}")) {
                    Toast.makeText(
                        this@Entri_Dosen,
                        "Data Dosen pengampu berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@Entri_Dosen,
                        "Data Dosen Pengampu kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@Entri_Dosen,
                    "Data Dosen Pengampu belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}