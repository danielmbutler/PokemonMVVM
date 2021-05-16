# PokeDex App
An App to search a list of pokemon from the pokemon api, view their abilities, stats and see their last known location in the pokemon world. The list view shows a list of pokemon which can be searched using the search bar or filters, from this view the map and saved screen can also be accessed, the map view shows the last known location of all pokemon that the app knows about, the saved view will contain any pokemon that you have saved to your deck. When clicking on the pokemon in the list view you will get taken to the detail screen, this shows the card of the pokemon with a list of its abilities, stats, type, last know location and a star rating which is calculated based on the average of all the stats. This app periodically searches the pokemon api in the background even whilst the app is not open to find new pokemon and add them to the internal database (this can be turned off from the settings icon on the list view).


## Screenshots

<p align="center">
  <img src="https://github.com/danielmbutler/pokemonresourceImages/blob/master/searchview.PNG" width="250" >
  <img src="https://github.com/danielmbutler/pokemonresourceImages/blob/master/detailview.PNG" width="250">
  <img src="https://github.com/danielmbutler/pokemonresourceImages/blob/master/detailviewScroll.PNG" width="250">
</p>

<p align="center">
  <img src="https://github.com/danielmbutler/pokemonresourceImages/blob/master/savedPokemon.PNG" width="250" >
  <img src="https://github.com/danielmbutler/pokemonresourceImages/blob/master/Filter.PNG" width="250">
  <img src="https://github.com/danielmbutler/pokemonresourceImages/blob/master/mapviewScreenshot.PNG" width="250">
</p>

## DEMO

![alt text](https://github.com/danielmbutler/pokemonresourceImages/blob/master/mp4%20test.gif)

## Technical Stuff
### Architecture

![alt text](https://github.com/danielmbutler/pokemonresourceImages/blob/master/MVVM%20Architecture.png)

This app is using the MVVM architecture pattern, this app retrieves data from two sources first the pokemon api and then the internal database using Room DB, the app intelligently decides based on a cache value whether to retrieve results from the api or to return the items that are already stored in the DB. The two data sources are queried using our repository which acts as a hub for data retrieval, once data is retrieved it is return to our viewmodel which posts the data to LiveData objects which are observed in UI, the data will be wrapped in a resource class to show whether the result is in a Successful, Loading, or Error state, the UI will then react accordingly. This app uses a single activity which hosts fragments to show the various views, the navigation is handled via navigation component. To retrieve new pokemon I decided to implement work manager to create a periodic task to run every 15 minutes to search for the next pokemon from the api, if one is found then it will create a custom object and store it in the room DB to then be shown in the list view.

### Cache Log Screenshots

![alt text](https://github.com/danielmbutler/pokemonresourceImages/blob/master/Caching.PNG)

### Work Manager Screenshots

![alt text](https://github.com/danielmbutler/pokemonresourceImages/blob/master/WorkManager.PNG)

## Libraries and Resources Used

* Work Manager - Used for background tasks to communicate with api.
* Dagger Hilt - Used for dependency injection to provide classes at run time (this means they are only instantiated once)
* Shared Preferences - Used as local storage for Key-Value pairs (small data), this is used to enable/disable work manager tasks.
* Room Database - Internal database to store pokemon objects.
* Retrofit - Api client used to retrieve data from pokemon api.
* Glide - Image loading library 
* Testing - two tests have been included for testing the Roomdb and api.
* MVVM - architecture pattern
* LiveData - Data that can be changed and then observed, this is used to handle data retrieval responses in the fragments.
* Single Live Event - Custom class provided by google to ensure that the same livedata object does not get re-emitted to observers, once the event has been handled it will not be returned (stops duplicate data appearing in views).
* Custom dialogs - used to give filtering options for users in the list view.
* Offline first - this app checks for internet connectivity and will warn the user if no connection is found, the app will continue to run without internet and will return results from the room DB but will warn the user periodically.
* Resource Wrapper class - wraps around the data response objects to show whether a result is in "Success", "Loading" or "Error" state
* Coroutines - used for async calls to the database and api in this app I have bounded these to the ViewModel scope to ensure they will be cancelled when the ViewModel is cleared (this ensures they survive lifecycle changes).
* Navigation Component - used to handle the navigation between fragments.
* Single Activity - with Splash Screen - only 1 activity class is used and the splash screen is handled by using a custom theme, the splash theme will be shown until main activity has reached "OnCreate" status, we then switch the theme back to normal.
* Circle Image View - external library used to implement an easy circle image view.

## Links

* Work Manager - https://developer.android.com/topic/libraries/architecture/workmanager
* Dagger Hilt - https://developer.android.com/training/dependency-injection/hilt-android
* Shared Preferences - https://developer.android.com/training/data-storage/shared-preferences
* Room DB - https://developer.android.com/training/data-storage/room
* RetroFit - https://square.github.io/retrofit/
* Glide - https://github.com/bumptech/glide
* LiveData - https://developer.android.com/topic/libraries/architecture/livedata
* Single Live Event Class - https://proandroiddev.com/singleliveevent-to-help-you-work-with-livedata-and-events-5ac519989c70
* Dialog Fragment - https://developer.android.com/guide/fragments/dialogs
* Navigation Component - https://developer.android.com/guide/navigation/navigation-getting-started
* Circle Image View - https://github.com/lopspower/CircularImageView





