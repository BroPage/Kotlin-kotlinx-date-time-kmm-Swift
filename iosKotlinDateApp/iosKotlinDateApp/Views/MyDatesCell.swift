//
//  MyDatesCell.swift
//  iosKotlinDateApp
//
//  Created by Page Tyler on 2021-02-07.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

/*  ************************************************************************************************
    *
    *  class:    MyDatesCell extends UITableViewCell
    *  passing:     none
    *
    *  Properties:  @IBOutlet nameLabel
    *               @IBOutlet valueLabel
    *
    *  return:
    *
    *  Description: This class establishes the data connection between the data List object and the
    *               actual UI Cell data objects
    *
    *               aND RecyclerView.ViewHolder Function
    *
    ************************************************************************************************
 */
class MyDatesCell: UITableViewCell {
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var valueLabel: UILabel!

    // 1:
    var  ldate: myDate? {
      didSet {guard let Tdate = ldate else { return }
        var sText = Tdate.dateName
        nameLabel.text = sText
        sText = Tdate.dateValue
        valueLabel.text = sText
        
      }
    }

    // 2:
    override func awakeFromNib() {
          super.awakeFromNib()
          // Initialization code
      }

      override func setSelected(_ selected: Bool, animated: Bool) {
          super.setSelected(selected, animated: animated)

          // Configure the view for the selected state
      }

  }
