package com.dbtechprojects.pokemonApp.ui.activities
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dbtechprojects.pokemonApp.R
import dagger.hilt.android.AndroidEntryPoint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PokemonApp) // set theme of app once main activity has loaded
        setContentView(R.layout.activity_main)

        // internet check
        if (!isNetworkConnected()){
            Toast.makeText(this, "no internet access detected, please check internet connection", Toast.LENGTH_LONG).show()
        }
    }


    // function to check if internet is available
    private fun isNetworkConnected() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =  connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities.also {
            if (it != null){
                if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    return true
                else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return true
                }
            }
        }
        return false
    }
}