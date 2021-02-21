package com.example.kotlindate.shared

import cache.DataSource
import entity.DateLines

/*
    ************************************************************************************************
    *   class:      DataStore (actual Android)
    *   inherits:   none
    *
    *   Description: This is the common point where the data acquired in the common routine is passed
    *                to the iOS application. There is a matching implementation in the commonMain
    *                and androidMain that does exactly the same thing as this module. This would be the
    *                same as the DatabaseDriverFactory class that you would find in a database
    *                accessing application.
    *
    *               There is a reason why this object exist. I tried to address DataStore directly
    *               the way Greeting is called by both iOS and Android. I found it difficult to
    *               debug the application that way. from the iOS side. Therefore I have used the
    *               expect and actual pairing provided by KMM.
    *
    ************************************************************************************************
 */
actual class DataStore {
        actual val dataStore: List<DateLines> = DataSource().dateLines
        fun getStoredData() = dataStore
}
