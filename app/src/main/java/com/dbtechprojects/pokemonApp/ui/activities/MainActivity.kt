package com.dbtechprojects.pokemonApp.ui.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.util.workmanager.BackgroundWorker
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.concurrent.TimeUnit


private const val TAG = "MainActivity"

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PokemonApp) // set theme of app once main activity has loaded
        setContentView(R.layout.activity_main)

        // internet check
        if (!isConnected()){
            Toast.makeText(this, "no internet access detected, please check internet connection", Toast.LENGTH_LONG).show()
        }

        workerCheck()

        Log.d(TAG, "onCreate Called")
    }


    // setup background worker to periodically check for new Pokemon and add it to our database

    @RequiresApi(Build.VERSION_CODES.M)
    private fun workerCheck() {
        val preferences = getSharedPreferences("worker", MODE_PRIVATE)

        // if worker value is not true then set the value and create the worker
        if (preferences.getString("worker", "") != "cancel") {
            val editPref = preferences.edit()
            editPref.putString("worker", "enabled")
            editPref.apply()
            Log.d("BackgroundWorker", "SharedPreferences Boolean Set")


            // create worker constraints

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            // run every 15 mins

            val backgroundWorker = PeriodicWorkRequest.Builder(BackgroundWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag("new_pokemon_checker") // optional
                .setConstraints(constraints) // optional
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS) // back off in the event of a worker job failing
                .build()



            WorkManager.getInstance(this.applicationContext).enqueue(backgroundWorker)
        } else {
            // cancelling worker
            cancelWorkers()
        }


    }

    // function to check if internet is available

    @Throws(InterruptedException::class, IOException::class)
    private fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }

    private fun cancelWorkers() {
        WorkManager.getInstance(this).cancelAllWorkByTag("new_pokemon_checker")
    }
}