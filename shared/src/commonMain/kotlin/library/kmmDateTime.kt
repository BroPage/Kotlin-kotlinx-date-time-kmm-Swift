package library

import kotlinx.datetime.*
import kotlinx.datetime.LocalDateTime

/*  ************************************************************************************************
    *  Library:    com.pagetyler.kmmDateTime
    *
    * Description:  These functions in this library are designed to be operating system independent and
    *               are developed for the MPP and KMM system environments provided by the Kotlin
    *               development environments. The main objective is to provide access to the new
    *               Kotlin DateTime libraries while encapsulating the new functions and data types in
    *               this module. To that end all input and output data types are ISO String date
    *               time formats.
    *
    *               They will also support local data time durations that account for Leap years and
    *               daylight savings time laps periods. At the time of this wighting the module only
    *               considers the local time zone for duration calculations.
    *
    * Requires:     Android Studio version 4.1.1 or better
    *
    * Configurations:
    *               IN settings.gradle.kts
    *                   repositories {
    *                        jcenter()
    *                        maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
    *                   }
    *               IN build.gradle.kts
    *                   sourceSets {
    *                       commonMain  {
    *                           dependencies {               // need current version 4.1.1 or better
    *                               implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
    *                           }
    *                       }
    *
    ************************************************************************************************
    *  */

/*
*  Function:    getNow()
*  Param1:      none
*
*  return:      Instant     // Current system date and time stamp
* Description:  This function will return the current local system date and time stamp.
*
* */
private fun getNow(): Instant = Clock.System.now()

/*
*  Function:    hereAndNow()
*  Param1:      none
*
*  return:      LocalDateTime     // Current system date and time stamp in local time format with time zone
* Description:  This function will return the current local system date and time stamp and time zone.
*
* */
private fun hereAndNow(): kotlinx.datetime.LocalDateTime = getNow().toLocalDateTime(TimeZone.currentSystemDefault())
private fun getToday(): kotlinx.datetime.LocalDateTime = hereAndNow()
fun getShortToday(): String = getShortDate(getToday())

/*
  *  Function:    getShortDate()
  *  Param1:      LocalDateTime
  *
  *  return:      String     // The Short Date sort format from the input local date Param1 "yyyyMMdd"
  * Description:  Return the Short date of the input Local Date Time Field.
  *
  * */
private fun getShortDate(dateIn: LocalDateTime): String =
    dateIn.year.toString().padStart(4, '0') +
            dateIn.monthNumber.toString().padStart(2, '0') +
            dateIn.dayOfMonth.toString().padStart(2, '0')
fun getDateShort(longDTStamp:String) = getShortDate(parseDTStamp(longDTStamp))
fun getDateFormatted(longDTStamp:String):String = getFormattedField(getDateShort(longDTStamp))

/*
*  Function:    getShortTime()
*  Param1:      LocalDateTime
*
*  return:      String     // The Short Time sort format from the input local date Param1 "HHmmSS"
* Description:  Return the Short Time of the input Local date time field.
*
* */
private fun getShortTime(dateIn: LocalDateTime): String =
    dateIn.hour.toString().padStart(2, '0') +
            dateIn.minute.toString().padStart(2, '0') +
            dateIn.second.toString().padStart(2, '0')

fun getTimeShort(): String = getShortTime(getToday())
fun getTimeShort(longDTStamp:String) = getShortTime(parseDTStamp(longDTStamp))
fun getTimeFormatted(longDTStamp:String):String= getFormattedField(getTimeShort(longDTStamp),":","TIME")

/*
*  Function:    getLongDT()
*  Param1:      LocalDateTime
*
*  return:      String     // The Long DateTime format returned in a string form
* Description:  Return the string value of the input Local Date time value in Param1.
*
* */
private fun getStrDT(dateIn: LocalDateTime): String = dateIn.toString()
fun getDTStamp(): String = getStrDT(hereAndNow())
fun getDTStamp(shortDate: String):String = getStrDT(getLocalDateTime(shortDate))


/*
*  Function:    getLocalDateTime()
*  Param1:      shortDate
*
*  return:      LocalDateTime     // The LocalDateTime format returned for the short date input
* Description:  Return the LocalDateTime value of the input shortDate value in Param1.
*               Hours, Minutes, and Seconds are all defaulted to 0 in this function
*
* */
private fun getLocalDateTime(shortDate: String): LocalDateTime {
    val y = shortDate.substring(0, 4).toInt()
    val m = shortDate.substring(4, 6).toInt()
    val d = shortDate.substring(6, 8).toInt()
    return LocalDateTime( y, m, d, 0, 0, 0 )
}
private fun parseDTStamp(longDTStamp:String):LocalDateTime = LocalDateTime.parse(longDTStamp)
fun getLongDate (shortDate: String):String  {
    val ld = shortDate + "t05:00:00.001"
    return getStrDT(getLocalDateTime(ld))
}


//
fun getFormattedField(shortField:String, delimiter:String="-", type:String="date"):String {
    var ff = "2021-01-01"
    when (type.trim().toLowerCase()) {
        "date" -> ff ="${shortField.substring(0,4)}$delimiter${shortField.substring(4,6)}$delimiter${shortField.substring(6,8)}"
        "time" -> ff ="${shortField.substring(0,2)}$delimiter${shortField.substring(2,4)}$delimiter${shortField.substring(4,6)}"
    }
    return ff
}


/*
*      Date math operation functions that will account for time laps periods in DST and leap year
*
*  Function:    dateAdd(dateIn:LocalDateTime, period:Int)
*  Param1:      dateIn      // Local date and time
*  Param2:      period      // Number of days
*
*  return:      LocalDateTime     // The LocalDateTime format returned for dateIn plus the period days
* Description:  Return the LocalDateTime value of the input Param1 plus the period days in Param2.
*
* */
private fun dateMath(dateIn:LocalDateTime, period:Int=1, oper:String="add"):LocalDateTime {
    var nd:LocalDate = LocalDate(2021,1,1)

    when (oper.trim().toLowerCase()) {
        "add" -> nd = dateIn.date.plus(period,DateTimeUnit.DAY)
        "sub" -> nd = dateIn.date.minus(period,DateTimeUnit.DAY)
    }
    return LocalDateTime(nd.year,nd.monthNumber,nd.dayOfMonth,dateIn.hour,dateIn.minute,dateIn.second,dateIn.nanosecond)
}
private fun dateAdd(dateIn:LocalDateTime, period:Int):LocalDateTime = dateMath(dateIn, period, "ADD")
private fun getNextDay(dateIn:LocalDateTime):LocalDateTime = dateAdd(dateIn, 1)
fun getNextDay(longDTStamp:String):String = getStrDT(getNextDay(parseDTStamp(longDTStamp)))
fun getDateAdd(longDTStamp:String,period:Int):String = getStrDT(dateAdd(parseDTStamp(longDTStamp),period))

/*
*
*  Function:    dateSub(dateIn:LocalDateTime, period:Int)
*  Param1:      dateIn      // Local date and time
*  Param2:      period      // Number of days
*
*  return:      LocalDateTime     // The LocalDateTime format returned for dateIn minus the period days
* Description:  Return the LocalDateTime value of the input Param1 minus the period days in Param2.
*
* */
private fun dateSub(dateIn:LocalDateTime, period:Int):LocalDateTime = dateMath(dateIn, period, "SUB")
private fun getPreviousDay(dateIn:LocalDateTime):LocalDateTime = dateSub(dateIn, 1)
fun getPreviousDay(longDTStamp:String):String = getStrDT(getPreviousDay(parseDTStamp(longDTStamp)))
fun getDateSub(longDTStamp:String,period:Int):String = getStrDT(dateSub(parseDTStamp(longDTStamp),period))


/*
*
*  Function:    getDuration(dateFrom:LocalDateTime, dateTo:LocalDateTime):DatePeriod
*  Param1:      dateFrom      // Start date and time
*  Param2:      dateTo        // To date and time
*
*  return:      DatePeriod    // The DatePeriod format is returned for the duration between the 2 dates
* Description:  Return the duration between the 2 dates of from date Param1 to the to date in Param2.
*
* */
private fun getDuration(dateFrom:LocalDateTime, dateTo:LocalDateTime):DateTimePeriod {
    val tz = TimeZone.currentSystemDefault()
    return dateFrom.toInstant(tz).periodUntil(dateTo.toInstant(tz),tz)
}
fun getDuration(fromDTStamp:String, toDTStamp:String):Array<Long>  {
    val f = parseDTStamp(fromDTStamp)
    val t = parseDTStamp(toDTStamp)
    val p = getDuration(f,t)
    return arrayOf(p.years.toLong(),p.months.toLong(),p.days.toLong(),p.hours.toLong(),
        p.minutes.toLong(),p.seconds,p.nanoseconds)
}
//
// for internal use using internal date formats
private fun getNumDays(dateFrom:LocalDateTime, dateTo:LocalDateTime):Int {
    return getDuration(dateFrom,dateTo).days
}
fun getNumDays(fromDTStamp:String, toDTStamp:String):Int {
    val pArray = getDuration(fromDTStamp, toDTStamp)
    return pArray[2].toInt()
}

/*
*    Zoned Date time operation functions that will convert LocalDateTime to a GMT time stamp
*
*  Function:    getZonedDateTime(dateIn:LocalDateTime, timeZoneIn= TimeZone.currentSystemDefault())
*  Param1:      dateIn      // Local date and time
*  Param2:      TimeZone    // Time Zone for this date
*  Param3:      period      // Number of days
*  Param4:      period      // Number of days
*
*  return:      DateTime     // The LocalDateTime format returned for dateIn plus the period days
* Description:  Return the LocalDateTime value of the input Param1 plus the period days in Param2.
*
* */
private fun getZonedDateTime(dateIn:LocalDateTime, timeZoneIn:TimeZone=TimeZone.currentSystemDefault()):Instant
        = dateIn.toInstant(timeZoneIn)
fun getZonedDateTime(longDTStamp:String, timeZone:String=TimeZone.currentSystemDefault().toString()):String {
    val ldt = parseDTStamp(longDTStamp)
    val tz:TimeZone = TimeZone.of(timeZone)
    return getZonedDateTime(ldt, tz).toString()
}