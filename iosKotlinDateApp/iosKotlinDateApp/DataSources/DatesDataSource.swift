//
//  DatesDataSource.swift
//  iosStratch1App
//
//  Created by Page Tyler on 2021-01-28.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared
/*  ************************************************************************************************
    *
    *  class:    DatesDataSource extends NSObject
    *  passing:     none
    *
    *  Param1:      none
    *  Properties:  tmpDates as a list of DateLines defined in the entity class file
    *
    *  return:
    *
    *  Description: This class fetches the data stored in the dataCasche opject. It then converts
    *               the Java List into a local swift List object to make it easier to debug in swift.
    *               the class also provides the methods process and store each list row into the
    *               Tavle View.
    *
    *               aND DateLinesRVAdaptor extends RecyclerView.Adapter
    *
    ************************************************************************************************
 */

class DatesDataSource: NSObject {
  // MARK: - Properties
  var tmpDates: [myDate]

  /*
        ************************************************************************************************
        *   function: generateDatesData
        *
        *   Param1:     none
        *
        *   return:     [myDate] A list of DateLines converted to a local swift List format.
        *
        *   Description: This routine is clled to retrieve the data from cache and return it in a local
        *                format. This will allow you to see the detailed information in the table while
        *                you are debuging youe application. This is not a requirement but it made it
        *                easier for someone who felt completely lost in the swift world.
        ************************************************************************************************
  */
  static func generateDatesData() -> [myDate] {
    let DS = CachedData()
    let DL:[DateLines] = DS.getStoredData()
    var myDates:[myDate] = [myDate]()
    
    //  Convert lines into a local format
    for dateLine  in DL {
        print (" Name is \(dateLine.dateName) Value is \(dateLine.dateValue) ")
        let mydate:myDate = myDate(dateName: dateLine.dateName, dateValue: dateLine.dateValue)
        myDates.append(mydate)
    }
    return   myDates
      
  }

  // MARK: - Initializers
  override init() {
    //  Get local data from data cache
    tmpDates = DatesDataSource.generateDatesData()
  }

  // MARK: - Datasource Methods
  func numberOfDates() -> Int {
    // Get local data count
    tmpDates.count
  }

  func append(tDate: myDate, to tableView: UITableView) {
    // Add a row to the table view
    tmpDates.append(tDate)
    tableView.insertRows(at: [IndexPath(row: tmpDates.count-1, section: 0)], with: .automatic)
  }

  func funDate(at indexPath: IndexPath) -> myDate {
    // Get a row of data from the list
    tmpDates[indexPath.row]
  }
}
