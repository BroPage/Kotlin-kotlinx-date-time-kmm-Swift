//
//DatesViewControllers.swift
//  DatesViewControllers.swift
//  iosStratch1App
//
//  Created by Page Tyler on 2021-01-28.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

/*  ************************************************************************************************
    *
    *  class:    DatesViewController extends UITableViewController
    *  passing:     none
    *
    *  Properties:  datesDataSource
    *
    *
    *  return:
    *
    *  Description: This class establishes the data connection between the data List object and the
    *               actual UI Cell data objects
    *
    *               aND DateLinesRVAdaptor(var dateLines: List<DateLines>)
    *
    ************************************************************************************************
 */

class DatesViewController: UITableViewController {
    var datesDataSource = DatesDataSource()
    
}

extension UIViewController {
    /// Loads a `UIViewController` of type `T` with storyboard. Assumes that the storyboards Storyboard ID has the same name as the storyboard and that the storyboard has been marked as Is Initial View Controller.
    /// - Parameter storyboardName: Name of the storyboard without .xib/nib suffix.
    
    }

    //
    //  Retrieve the total number rows being displayed in the table view
    //
extension DatesViewController {
  override func tableView(
    _ tableView: UITableView,
    numberOfRowsInSection section: Int
  ) -> Int {
    datesDataSource.numberOfDates()
  }
  
    //
    //  Establish addressability to the current table view cell
    //
  override func tableView(
    _ tableView: UITableView,
    cellForRowAt indexPath: IndexPath
  ) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(
      withIdentifier: "MyDateCell",
      for: indexPath) as! MyDatesCell
    
    //  Pass the current row to the current cell field processor
    cell.ldate = datesDataSource.funDate(at: indexPath)
    
    return cell
  }
  
  
}

extension DatesViewController {
  @IBAction func cancelToDatesViewController(_ segue: UIStoryboardSegue) {
    
  }

  @IBAction func saveDateDetail(_ segue: UIStoryboardSegue) {

  }
}
