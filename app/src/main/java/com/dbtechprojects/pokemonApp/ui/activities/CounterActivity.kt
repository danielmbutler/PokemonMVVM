package com.dbtechprojects.pokemonApp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.dbtechprojects.pokemonApp.R

class CounterActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var countBtn : Button
    private lateinit var closeBtn : Button
    private lateinit var countTxt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
        setup()
    }

    private fun setup() {
        countBtn = findViewById(R.id.increase_countBtn)
        countTxt = findViewById(R.id.countTv)
        closeBtn = findViewById(R.id.closeBtn)
        countBtn.setOnClickListener {
            count +=1
            countTxt.text = count.toString()
        }
        closeBtn.setOnClickListener {
            // create intent add count to intent , set result of activity to intent and then finish
            val intent = Intent()
            intent.putExtra("count", count)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}