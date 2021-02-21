package cache

import com.example.kotlindate.shared.Greeting

import entity.DateLines
import library.*

/*
    ************************************************************************************************
    *   class:      DataSource (actual Android)
    *   inherits:   none
    *
    *   Description: This is the common point where the data acquired in the common routine. It is
    *                distributed to the applications the DataStore functions in each OS's actual
    *                data retrieval function defined in their Main module.
    *
    ************************************************************************************************
 */

class DataSource {
    var dateLines:List<DateLines> = returnDateLines()


    private fun returnDateLines():List<DateLines>

    {
        val Greeting = Greeting().greeting()
        val dt:String = getDTStamp()            //  Get current date time stamp from system
        val sd:String = getDateShort(dt)        //  view txShortDate
        val st:String = getTimeShort(dt)        //  view txShortTime
        val ct:String = getTimeShort()          //  view txCurrTime
        val dts:String = getDTStamp()           //  view txDTStamp
        val sod:String = getDTStamp(sd)         //  view txStrOfDay
        val ldt:String = getLongDate(sd)        //  view txLongDate
        val nxd:String = getNextDay (dts)       //  view txNxtDate
        val prd: String = getPreviousDay(ldt)   //  view txPrevDate
        val dur: Array<Long> = getDuration(prd,nxd) // view txFullDuration
        val sDur:String = dur[0].toString() + "-" +dur[1].toString() + "-" + dur[2].toString() + "-" + "  " +
                dur[3].toString() + ":" + dur[4].toString() + ":" + dur[5].toString() + "." + dur[6].toString().substring(0,4) + " "
        val days:Int = getNumDays(prd,nxd)      //  view txDaysDiff
        val fdt = getDateFormatted(sod)         //  view txFmtDate
        val ftm = getTimeFormatted(dts)         //  view txFmtTime
        val zdt:String = getZonedDateTime(dts)  //  view txLocalTime

        var list:List<DateLines> = arrayListOf<DateLines>(
            DateLines("Greeting", Greeting),
            DateLines("Short Date", sd),
            DateLines("Short Time", st),
            DateLines("Curr Time", ct),
            DateLines("Local DT Stamp", dts),
            DateLines("Start of Day", sod),
            DateLines("Long Date Time", ldt),
            DateLines("Next Day", nxd),
            DateLines("Previous Day", prd),
            DateLines("Full Duration", sDur),
            DateLines("Days Duration", days.toString()),
            DateLines("Formatted Date", fdt),
            DateLines("Formatted Time", ftm),
            DateLines("GMT Time Stamp", zdt),
            DateLines("Future Use", ""),
        )
        return list.toList()
    }
}