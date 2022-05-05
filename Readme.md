# RGB Colour Picker
An App which changes the colour of its background based on the postion of the 3 sliders.


## Screenshots

<p align="center">
  <img src="https://github.com/danielmbutler/RGBColourPicker/blob/master/resources/screenshot1.png" width="250" >
  <img src="https://github.com/danielmbutler/RGBColourPicker/blob/master/resources/screenshot2.png" width="250">
  <img src="https://github.com/danielmbutler/RGBColourPicker/blob/master/resources/screenshot3.png" width="250">
</p>


## DEMO

![alt text](https://github.com/danielmbutler/RGBColourPicker/blob/master/resources/mp4.gif)

## Design Decisions
Whilst designing this app I identified an initial issue that when the colour changes

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





