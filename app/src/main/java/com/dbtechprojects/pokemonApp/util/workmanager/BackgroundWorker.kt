package com.dbtechprojects.pokemonApp.util.workmanager

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.repository.DefaultRepository
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// class to create worker for work manager, this class with check and compare items in the DB with the API
// using @AssistedInject constructor as some parameters will be provided at run time i.e. when we define a job
// https://proandroiddev.com/whats-new-in-hilt-and-dagger-2-31-c46b7abbc64a

// coroutine worker - https://developer.android.com/topic/libraries/architecture/workmanager/advanced/coroutineworker

private const val TAG = "BackGroundWorker"

@HiltWorker
class BackgroundWorker @AssistedInject constructor(
    @Assisted var context: Context,
    @Assisted params: WorkerParameters,
    private var repository: DefaultRepository
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {

        Log.d(TAG, "Worker has run")
        return withContext(Dispatchers.IO) {

            // get id of last Pokemon in local DB
            val lastStoredPokemonObject = repository.getLastStoredPokemon()


            // Implement hard stop at 100 pokemon
            if (lastStoredPokemonObject.id!! >= 100) {
                val pref = context.getSharedPreferences("worker", AppCompatActivity.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("worker", "cancel")
                editor.commit()
                return@withContext Result.success()
            }

            // check API for details on next pokemon
            val nextPokemon = lastStoredPokemonObject.id + 1
            Log.d(TAG, "searched pokemon: $nextPokemon")
            Log.d(TAG, "lastPokemon: $nextPokemon")
            val apiResult = repository.getPokemonDetail(nextPokemon.toString())

            when (apiResult) {
                is Resource.Success -> {

                    if (apiResult.data != null) {
                        apiResult.data.let { newPokemon ->
                            // create custom pokemon object save in DB
                            val newPokemonObj = CustomPokemonListItem(
                                name = newPokemon.name,
                                Image = newPokemon.sprites.front_default,
                                type = newPokemon.types?.get(0)?.type?.name.toString(),
                                // set positions for map
                                positionLeft = (0..1500).random(),
                                positionTop = (0..1500).random(),
                                apiId = newPokemon.id
                            )
                            repository.savePokemon(newPokemonObj)

                            Log.d("worker", "new pokemon found ${newPokemonObj.name}")
                            return@withContext Result.success()

                        }
                    } else {
                        return@withContext Result.failure()
                    }


                }
                is Resource.Error -> {
                    return@withContext Result.failure()
                }
                else -> return@withContext Result.failure()
            }


        }

    }

}