//
//  dataCache.swift
//  iosKotlinDateApp
//
//  Created by Page Tyler on 2021-02-10.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared
/*  ************************************************************************************************
    *
    *  class:    CachedData extends NSObject
    *  passing:     none
    *
    *  Param1:
    *  Properties:  dateLines as a list of DateLines defined in the entity class file
    *
    *  return:
    *
    *  Description: This class establishes the data connection between the data object passed in
    *               Created in the shared code modules of the KMM application
    *
    *               aND fun displayDates(needReload: Boolean):List<DateLines> of MainActivity
    *
    ************************************************************************************************
 */

class CachedData:NSObject {
    // MARK: - Properties
    
    // Local access to the Common Shared Data Source
    private let DS:DataSource = shared.DataSource()
    
    // Public access to the data passed to the iOS application from the common Kotlin modules
    var cachedData:[DateLines]?

    // MARK: - Initializers
    override init() {
        //  Get the data and load it into the public variable when this class is initilized
        cachedData = DS.dateLines
    }
    /*
        ************************************************************************************************
        *   function: getStoredData
        *
        *   Param1: none
        *
        *   return: [DateLines] A list of DateLines
        *
        *   Description: This is the function where the data is retrieved form the common source.
        *                The required UI handling is included here for No Data and Error Handling.
        *
        ************************************************************************************************
     */
    func getStoredData() -> [DateLines] {
        
        // Return the stored data lines previously retrived
        return cachedData ?? [DateLines(dateName: "NoReturn", dateValue: "No Data Found")]
    }
     
}
