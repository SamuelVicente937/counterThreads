package com.example.counterwthreads

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var count1 = 0;
    private var count2 = 0;
    private lateinit var tvCounter1 : TextView;
    private lateinit var tvCounter2 : TextView;
    private var job1: Job?= null
    private var job2: Job?=null
    private val scope1 = CoroutineScope (Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvCounter1 = findViewById<TextView>(R.id.tvCounter1)
        tvCounter2 = findViewById<TextView>(R.id.tvCounter2)
        findViewById<Button>(R.id.iniCount1).setOnClickListener {
            startCounter()
        }
        findViewById<Button>(R.id.pauseCount1).setOnClickListener {
            pauseCounter()
        }
        findViewById<Button>(R.id.resetCount1).setOnClickListener {
            resetCounter()
        }
        findViewById<Button>(R.id.iniCount2).setOnClickListener {
            startCounter2()
        }
        findViewById<Button>(R.id.pauseCount2).setOnClickListener {
            pauseCounter2()
        }
        findViewById<Button>(R.id.resetCount2).setOnClickListener {
            resetCounter2()
        }


    }
    private fun startCounter() {
        if (job1?.isActive != true ){
            job1 = scope1.launch {
                while (isActive){
                    delay(1000)
                    count1++
                    tvCounter1.text = count1.toString()
                }
            }
        }
    }
    private fun pauseCounter(){
        job1?.cancel()
    }
    private fun resetCounter() {
        pauseCounter()
        count1 = 0
        tvCounter1.text = count1.toString()
    }
    private fun startCounter2(){
        if(job2?.isActive != true){
            job2 = scope1.launch {
                delay(1000)
                count2++
                tvCounter2.text = count2.toString()
            }
        }
    }
    private fun pauseCounter2(){
        job2?.cancel()
    }
    private fun resetCounter2(){
        pauseCounter2()
        count2 = 0
        tvCounter2.text = count2.toString()
    }
    override fun onDestroy() {
        super.onDestroy()
        scope1.cancel()
    }
}


