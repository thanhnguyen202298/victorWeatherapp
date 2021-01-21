package com.victorthanh.utilslib.utils.helper

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.victorthanh.utilslib.utils.appendZero
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object DateFormatHelper {

    //region Constructor, Properties
    @SuppressLint("ConstantLocale")
    private val sdfGMT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    private val sdfCurrent = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private var calendar = Calendar.getInstance()

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private const val WEEK_MILLIS = 7 * DAY_MILLIS


    init {
        /* Default TimeZone is GMT00:00 */
        sdfGMT.timeZone = TimeZone.getTimeZone("GMT00:00")
        /* Current TimeZone Device */
        sdfCurrent.timeZone = TimeZone.getDefault()
    }
    //endregion

    enum class DateTimeType {
        TODAY, PAST, FUTURE, UNKNOWN
    }


    fun dateLong2Date(secc: Long): String {
        val date = Date(TimeUnit.SECONDS.toMillis(secc))
        val format = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        return format.format(date)
    }

    fun dateYYYY_MM_dd_HH_mm_ss2Long(datestr: String): Long {
        val format = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val date = format.parse(datestr)
        return date.time
    }

    /**
     * this function
     * parameter: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * convert 2020-06-16T03:58:24.985Z to EEE dd MMM yyyy
     */
    fun getDateEEEdMMMyyyy(dateTime: String?): String? {
        var mValue = ""
        var format =
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        try {
            val date = format.parse(dateTime)
            format = SimpleDateFormat("EEE dd MMM yyyy")
            mValue = format.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return mValue
    }

    fun getDateMMMddyyyy(dateTime: String?): String? {
        var mValue = ""
        var format =
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        try {
            val date = format.parse(dateTime)
            format = SimpleDateFormat("MMM dd, yyyy")
            mValue = format.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return mValue
    }

    @JvmStatic
    fun convert24hTo12h(time24h: String): String {
        try {
            val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
            val sdf12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val dateTime = sdf24.parse(time24h)
            return sdf12.format(dateTime)
        } catch (ex: Exception) {

            Log.e("<<error", "Error: $ex")
        }
        return ""
    }

    @JvmStatic
    fun convertCalendarToDeviceTimeStampString(calendarCurrent: Calendar): String? {
        try {
            val sdfParsed = SimpleDateFormat("yyyy:MM:dd:HH:mm:ss", Locale.getDefault())
            sdfParsed.timeZone = TimeZone.getTimeZone("GMT00:00")
            val date = Date(calendarCurrent.timeInMillis)
            return sdfParsed.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {

            Log.e("<<error", "Error: $e")
        }
        return null
    }

    @JvmStatic
    fun convertCalendarToTimeUnixSecond(calendarCurrent: Calendar): Long {
        try {
            return calendarCurrent.timeInMillis / 1000
        } catch (e: ParseException) {

            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {

            Log.e("<<error", "Error: $e")
        }
        return 0
    }

    fun convertCurrentHourMinuteToMinuteGmt0(hour: Int, minute: Int): Int {
        try {
            val sdfParsed = SimpleDateFormat("HH:mm", Locale.getDefault())
            sdfParsed.timeZone = TimeZone.getTimeZone("GMT00:00")
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            val date = Date(calendar.timeInMillis)

            val hourMinuteArray = sdfParsed.format(date).split(":")
            val hourGMT0 = hourMinuteArray[0].toInt()
            val minuteGMT0 = hourMinuteArray[1].toInt()
            return hourGMT0 * 60 + minuteGMT0
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return 0
    }

    fun convertWeekdaysGmt0(hour: Int, minute: Int, weekdays: StringBuilder): StringBuilder {
        try {
            val weekdaysGmt0 = StringBuilder("0000000")
            val sdfParsed = SimpleDateFormat("EEEE", Locale.US)
            sdfParsed.timeZone = TimeZone.getTimeZone("GMT00:00")
            val calendar = Calendar.getInstance()

            weekdays.forEachIndexed { weekDayIndex, isEnable ->
                if (isEnable == '1') {
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.DAY_OF_WEEK, weekDayIndex + 1)
                    val date = Date(calendar.timeInMillis)
                    val weekdaysParsed = sdfParsed.format(date)
                    when (weekdaysParsed.toLowerCase()) {
                        "sunday" -> weekdaysGmt0.setCharAt(0, '1')
                        "monday" -> weekdaysGmt0.setCharAt(1, '1')
                        "tuesday" -> weekdaysGmt0.setCharAt(2, '1')
                        "wednesday" -> weekdaysGmt0.setCharAt(3, '1')
                        "thursday" -> weekdaysGmt0.setCharAt(4, '1')
                        "friday" -> weekdaysGmt0.setCharAt(5, '1')
                        "saturday" -> weekdaysGmt0.setCharAt(6, '1')
                    }
                }
            }
            return weekdaysGmt0
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return StringBuilder("0000000")
    }

    fun convertWeekdaysCurrentGmt(hour: Int, minute: Int, weekdays: StringBuilder): StringBuilder {
        try {
            val weekdaysGmt0 = StringBuilder("0000000")
            val sdfParsed = SimpleDateFormat("EEEE", Locale.US)
            sdfParsed.timeZone = TimeZone.getDefault()
            val calendar = Calendar.getInstance()
            calendar.timeZone = TimeZone.getTimeZone("GMT00:00")

            weekdays.forEachIndexed { weekDayIndex, isEnable ->
                if (isEnable == '1') {
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.DAY_OF_WEEK, weekDayIndex + 1)
                    val date = Date(calendar.timeInMillis)
                    val weekdaysParsed = sdfParsed.format(date)
                    when (weekdaysParsed.toLowerCase()) {
                        "sunday" -> weekdaysGmt0.setCharAt(0, '1')
                        "monday" -> weekdaysGmt0.setCharAt(1, '1')
                        "tuesday" -> weekdaysGmt0.setCharAt(2, '1')
                        "wednesday" -> weekdaysGmt0.setCharAt(3, '1')
                        "thursday" -> weekdaysGmt0.setCharAt(4, '1')
                        "friday" -> weekdaysGmt0.setCharAt(5, '1')
                        "saturday" -> weekdaysGmt0.setCharAt(6, '1')
                    }
                }
            }
            return weekdaysGmt0
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return StringBuilder("0000000")
    }

    @JvmStatic
    fun getCurrentDateMonth(): String? {
        try {
            val sdfParsed = SimpleDateFormat("dd/MM", Locale.getDefault())
            val date = Date(Calendar.getInstance().timeInMillis)
            return sdfParsed.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert name string format from database to mobile name string to show on UI
     * @param dateString: "dd/MM/yyyy"
     * @return mobileDateString: Monday, 22 Jun 2018
     */
    @JvmStatic
    fun convertCloudDateStringToMobileDateString(dateString: String?, context: Context): String? {
        val dateArray = dateString?.split("/")
        if (dateArray == null || dateArray.size < 3) {
            return null
        }

        val year = dateArray[2].toIntOrNull() ?: 0
        val month = dateArray[1].toIntOrNull() ?: 0
        val dayOfMonth = dateArray[0].toIntOrNull() ?: 0

        val calendarTemp = Calendar.getInstance()
        calendarTemp.set(Calendar.YEAR, year)
        calendarTemp.set(Calendar.MONTH, month)
        calendarTemp.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return "${
            getDayOfWeek(
                calendarTemp,
                context
            )
        }, $dayOfMonth ${
            getNameOfMonth(
                calendarTemp,
                context
            )
        } $year"
    }

    /***
     * Convert time stamp string to Date Current
     * @param stringTimeStamp : String?
     * @throws ParseException
     */
    @JvmStatic
    fun convertTimeStampStringToDate(stringTimeStamp: String?): Date? {
        try {
            return getDateCurrentDevice(
                stringTimeStamp
            )
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    fun convertTimeStampToTimeUnixSecond(stringTimeStamp: String?): Long = try {
        sdfGMT.parse(stringTimeStamp).time / 1000
    } catch (ex: Exception) {
        0
    }

    fun convertTimeStampMediaToTimeUnixSecond(stringTimeStamp: String?): Long = try {
        val sdfGMT = SimpleDateFormat("YYYY:MM:DD HH:MM:SS")
        val date = sdfGMT.parse(stringTimeStamp)
        date.time / 1000
    } catch (ex: Exception) {
        0
    }

    /***
     * Check timestamp string is whether today or not
     * @return true or false
     * @throws ParseException
     */
    @JvmStatic
    fun isToday(stringTimeStamp: String?): Boolean {
        try {
            val dateCurrent =
                getDateCurrentDevice(
                    stringTimeStamp
                )
            val calendarTemp = Calendar.getInstance()
            calendarTemp.timeInMillis = dateCurrent.time
            calendar = Calendar.getInstance()
            return (calendarTemp.get(Calendar.ERA) == calendar.get(Calendar.ERA) &&
                    calendarTemp.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    calendarTemp.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR))
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return false
    }

    /***
     * Check stringTimeStamp is: Today, Past or Future
     * @param stringTimeStamp
     * @throws ParseException
     */
    @JvmStatic
    fun checkDateTimeType(stringTimeStamp: String?): DateTimeType {
        try {
            return if (!isToday(
                    stringTimeStamp
                )
            ) {
                val dateParam =
                    getDateCurrentDevice(
                        stringTimeStamp
                    )
                val dateNow = Date(Calendar.getInstance().timeInMillis)
                when (dateParam < dateNow) {
                    true -> DateTimeType.PAST
                    else -> DateTimeType.FUTURE
                }
            } else DateTimeType.TODAY
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return DateTimeType.UNKNOWN

    }

    /***
     * Convert time in milliseconds to timestamp string "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @param timeMillisecond: Double
     * @throws ParseException
     * @return timeStampString: String
     */
    @JvmStatic
    fun convertTimeMillisecondToTimeStamp(timeMillisecond: Long): String? {
        try {
            val date = Date(timeMillisecond)
            val sdfGMT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            sdfGMT.timeZone = TimeZone.getTimeZone("GMT00:00")
            return sdfGMT.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert time in milliseconds to timestamp string "yyyy-MM-dd'T'HH:mm:ss"
     * @param timeMillisecond: Double
     * @throws ParseException
     * @return timeStampString: String
     */
    @JvmStatic
    fun convertTimeUnixToBackupTimeFileName(timeMillisecond: Long): String? {
        try {
            val date = Date(timeMillisecond)
            val sdfCurrent = SimpleDateFormat("(yyyyMMdd_HHmmss)", Locale.getDefault())
            return sdfCurrent.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    @JvmStatic
    fun convertTimeUnixToDateTime(timeMillisecond: Long): String? {
        try {
            val date = Date(timeMillisecond*1000)
            val sdfCurrent = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            return sdfCurrent.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert calendar to timestamp string "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @param calendarCurrent: Calendar
     * @throws ParseException
     * @return timeStampString: String
     */
    @JvmStatic
    fun convertCalendarToTimeStampString(calendarCurrent: Calendar): String? {
        try {
            val sdfParsed = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdfParsed.timeZone = TimeZone.getTimeZone("GMT00:00")
            val date = Date(calendarCurrent.timeInMillis)
            return sdfParsed.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    @JvmStatic
    fun getCurrentTimeZone(): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss ZZZZZ", Locale.getDefault())
        val result = sdf.format(Date())
        return result.substring(result.length - 6, result.length)
    }

    @JvmStatic
    fun getCurrentTime(): String? {
        try {
            val sdfParsed = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date(Calendar.getInstance().timeInMillis)
            return sdfParsed.format(date)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert name current to timestamp string "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @param dateCurrent: Date
     * @throws ParseException
     * @return timeStampString: String
     */
    @JvmStatic
    fun convertDateToTimeStampString(dateCurrent: Date): String? {
        try {
            val sdfParsed = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdfParsed.timeZone = TimeZone.getTimeZone("GMT00:00")
            return sdfParsed.format(dateCurrent)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert timestamp string to general format: 'MM-dd-yyyy'
     * @param stringTimeStamp : String?
     * @return name string with format: 'MM-dd-yyyy'
     * @throws ParseException
     */
    @JvmStatic
    fun convertGeneralFormatDate(stringTimeStamp: String?): String? {
        try {
            val dateCurrent =
                getDateCurrentDevice(
                    stringTimeStamp
                )
            val sdfFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
            return sdfFormat.format(dateCurrent)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert timestamp string to general format: 'MM-dd-yyyy'
     * @param stringTimeStamp : String?
     * @return name string with format: 'day month, year'
     * @throws ParseException
     */
    @JvmStatic
    fun convertToDayMonth(timeMillisecond: Long, context: Context): String? {
        try {
            val timeStampString =
                convertTimeMillisecondToTimeStamp(
                    timeMillisecond
                )
            val monthOfYear =
                getNameOfMonth(
                    context,
                    timeStampString
                )
            val dayOfMonth =
                getDayOfMonth(
                    timeStampString
                )
            val year =
                getYear(
                    timeStampString
                )
            return "${appendZero(dayOfMonth.toString(), 2)} ${
                appendZero(
                    monthOfYear.toString(),
                    2
                )
            }, $year"
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert timestamp string to general format: 'MM-dd-yyyy'
     * @param stringTimeStamp : String?
     * @return name string with format: 'day month, year'
     * @throws ParseException
     */
    @JvmStatic
    fun convertToMonth(timeMillisecond: Long, context: Context): String? {
        try {
            val timeStampString =
                convertTimeMillisecondToTimeStamp(
                    timeMillisecond
                )
            val monthOfYear =
                getNameOfMonth(
                    context,
                    timeStampString
                )
            val dayOfMonth =
                getDayOfMonth(
                    timeStampString
                )
            val year =
                getYear(
                    timeStampString
                )
            return "${appendZero(monthOfYear.toString(), 2)}, $year"
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert timestamp string to general format: 'MM-dd-yyyy'
     * @param stringTimeStamp : String?
     * @return name string with format: 'day month, year'
     * @throws ParseException
     */
    @JvmStatic
    fun convertToYear(timeMillisecond: Long, context: Context): String? {
        try {
            val timeStampString =
                convertTimeMillisecondToTimeStamp(
                    timeMillisecond
                )
            val monthOfYear =
                getNameOfMonth(
                    context,
                    timeStampString
                )
            val dayOfMonth =
                getDayOfMonth(
                    timeStampString
                )
            val year =
                getYear(
                    timeStampString
                )
            return "Year $year"
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * @return name string with format: 'day month, year at hh:mm'
     * @throws ParseException
     */
    @JvmStatic
    fun convertToDayMonthHour(timeMillisecond: Long, context: Context): String? {
        try {
            val timeStampString =
                convertTimeMillisecondToTimeStamp(
                    timeMillisecond
                )
            val monthOfYear =
                getNameOfMonth(
                    context,
                    timeStampString
                )
            val dayOfMonth =
                getDayOfMonth(
                    timeStampString
                )
            val year =
                getYear(
                    timeStampString
                )
            val hourMinute =
                getHourMinute(
                    timeMillisecond,
                    true
                )
            return "$dayOfMonth $monthOfYear, $year at $hourMinute"
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert timestamp string to general format: 'Month Name, day of year, yyyy'
     * @param timeStamp : String?
     * @return name string with format: 'MM-dd-yyyy'
     * @throws ParseException
     */
    @JvmStatic
    private fun convertGeneralMonthNameFormatDate(context: Context, timeStamp: String?): String? {
        try {
            return "${
                getNameOfMonth(
                    context,
                    timeStamp
                )
            }, ${
                getDayOfMonth(
                    timeStamp
                )
            }, ${
                getYear(
                    timeStamp
                )
            }"
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Convert calendar string to general format: 'Month Name, dd, yyyy'
     * @param calendarCurrent : Calendar
     * @return name string with format: 'MM-dd-yyyy'
     * @throws ParseException
     */
    @JvmStatic
    fun convertGeneralMonthNameFormatDate(calendarCurrent: Calendar, context: Context): String? {
        try {
            return "${
                getNameOfMonth(
                    calendarCurrent,
                    context
                )
            }, ${
                getDayOfWeek(
                    calendarCurrent,
                    context
                )
            }, ${
                getYear(
                    calendarCurrent
                )
            }"
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        } catch (e: NullPointerException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get day of daysOfWeek from timestamp string
     * @param stringTimeStamp : String?
     * @return day of daysOfWeek string
     * @throws ParseException
     */
    @JvmStatic
    fun getDayOfWeek(stringTimeStamp: String?, context: Context?): String? {
        try {
            val dateCurrent =
                getDateCurrentDevice(
                    stringTimeStamp
                )
            calendar.timeInMillis = dateCurrent.time
            val week = calendar.get(Calendar.DAY_OF_WEEK)
//            when (week) {
//                Calendar.SUNDAY -> return context?.getString(R.string.general_title_calendar_sunday)
//                Calendar.MONDAY -> return context?.getString(R.string.general_title_calendar_monday)
//                Calendar.TUESDAY -> return context?.getString(R.string.general_title_calendar_tuesday)
//                Calendar.WEDNESDAY -> return context?.getString(R.string.general_title_calendar_wednesday)
//                Calendar.THURSDAY -> return context?.getString(R.string.general_title_calendar_thursday)
//                Calendar.FRIDAY -> return context?.getString(R.string.general_title_calendar_friday)
//                Calendar.SATURDAY -> return context?.getString(R.string.general_title_calendar_saturday)
//            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get day of daysOfWeek from calendar
     * @param calendarCurrent : String?
     * @return day of daysOfWeek string
     * @throws ParseException
     */
    @JvmStatic
    fun getDayOfWeek(calendarCurrent: Calendar, context: Context): String? {
        try {
            val week = calendarCurrent.get(Calendar.DAY_OF_WEEK)
//            when (week) {
//                Calendar.SUNDAY -> return context.getString(R.string.general_title_calendar_sunday)
//                Calendar.MONDAY -> return context.getString(R.string.general_title_calendar_monday)
//                Calendar.TUESDAY -> return context.getString(R.string.general_title_calendar_tuesday)
//                Calendar.WEDNESDAY -> return context.getString(R.string.general_title_calendar_wednesday)
//                Calendar.THURSDAY -> return context.getString(R.string.general_title_calendar_thursday)
//                Calendar.FRIDAY -> return context.getString(R.string.general_title_calendar_friday)
//                Calendar.SATURDAY -> return context.getString(R.string.general_title_calendar_saturday)
//            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get code of month from timestamp string
     * @param stringTimeStamp : String?
     * @return code of month string
     * @throws ParseException
     */
    @JvmStatic
    fun getNameOfMonth(context: Context, stringTimeStamp: String?): String? {
        try {
            val dateCurrent =
                getDateCurrentDevice(
                    stringTimeStamp
                )
            calendar.timeInMillis = dateCurrent.time
            val week = calendar.get(Calendar.MONTH)
            when (week) {
//                Calendar.JANUARY -> return context.getString(R.string.general_message_date_january)
//                Calendar.FEBRUARY -> return context.getString(R.string.general_message_date_february)
//                Calendar.MARCH -> return context.getString(R.string.general_message_date_march)
//                Calendar.APRIL -> return context.getString(R.string.general_message_date_april)
//                Calendar.MAY -> return context.getString(R.string.general_message_date_may)
//                Calendar.JUNE -> return context.getString(R.string.general_message_date_june)
//                Calendar.JULY -> return context.getString(R.string.general_message_date_july)
//                Calendar.AUGUST -> return context.getString(R.string.general_message_date_august)
//                Calendar.SEPTEMBER -> return context.getString(R.string.general_message_date_september)
//                Calendar.OCTOBER -> return context.getString(R.string.general_message_date_october)
//                Calendar.NOVEMBER -> return context.getString(R.string.general_message_date_november)
//                Calendar.DECEMBER -> return context.getString(R.string.general_message_date_december)
            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get code of month from calendar instance
     * @param calendarCurrent : Calendar
     * @return code of month string
     * @throws ParseException
     */
    @JvmStatic
    fun getNameOfMonth(calendarCurrent: Calendar, context: Context): String? {
        try {
            val week = calendarCurrent.get(Calendar.MONTH)
//            when (week) {
//                Calendar.JANUARY -> return context.getString(R.string.general_message_date_january)
//                Calendar.FEBRUARY -> return context.getString(R.string.general_message_date_february)
//                Calendar.MARCH -> return context.getString(R.string.general_message_date_march)
//                Calendar.APRIL -> return context.getString(R.string.general_message_date_april)
//                Calendar.MAY -> return context.getString(R.string.general_message_date_may)
//                Calendar.JUNE -> return context.getString(R.string.general_message_date_june)
//                Calendar.JULY -> return context.getString(R.string.general_message_date_july)
//                Calendar.AUGUST -> return context.getString(R.string.general_message_date_august)
//                Calendar.SEPTEMBER -> return context.getString(R.string.general_message_date_september)
//                Calendar.OCTOBER -> return context.getString(R.string.general_message_date_october)
//                Calendar.NOVEMBER -> return context.getString(R.string.general_message_date_november)
//                Calendar.DECEMBER -> return context.getString(R.string.general_message_date_december)
//            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get code of month from calendar instance
     * @param calendarCurrent : Calendar
     * @return code of month string
     * @throws ParseException
     */
    @JvmStatic
    fun getNameOfMonth(context: Context, calendarCurrent: Calendar): String? {
        try {
            val week = calendarCurrent.get(Calendar.MONTH)
//            when (week) {
//                Calendar.JANUARY -> return context.getString(R.string.general_message_date_january)
//                Calendar.FEBRUARY -> return context.getString(R.string.general_message_date_february)
//                Calendar.MARCH -> return context.getString(R.string.general_message_date_march)
//                Calendar.APRIL -> return context.getString(R.string.general_message_date_april)
//                Calendar.MAY -> return context.getString(R.string.general_message_date_may)
//                Calendar.JUNE -> return context.getString(R.string.general_message_date_june)
//                Calendar.JULY -> return context.getString(R.string.general_message_date_july)
//                Calendar.AUGUST -> return context.getString(R.string.general_message_date_august)
//                Calendar.SEPTEMBER -> return context.getString(R.string.general_message_date_september)
//                Calendar.OCTOBER -> return context.getString(R.string.general_message_date_october)
//                Calendar.NOVEMBER -> return context.getString(R.string.general_message_date_november)
//                Calendar.DECEMBER -> return context.getString(R.string.general_message_date_december)
//            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get day of month from timestamp string
     * @param stringTimeStamp : String?
     * @return day of month string
     * @throws ParseException
     */
    @JvmStatic
    fun getDayOfMonth(stringTimeStamp: String?): String? {
        try {
            val dateGMT = sdfCurrent.parse(stringTimeStamp)
            calendar.timeInMillis = dateGMT.time
            return calendar.get(Calendar.DAY_OF_MONTH).toString()
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get day of month from calendar
     * @param calendar : Calendar
     * @return day of month string
     * @throws ParseException
     */
    @JvmStatic
    fun getDayOfMonth(calendar: Calendar): String? {
        try {
            return calendar.get(Calendar.DAY_OF_MONTH).toString()
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get year from timestamp string
     * @param stringTimeStamp : String?
     * @return year string
     * @throws ParseException
     */
    @JvmStatic
    fun getYear(stringTimeStamp: String?): String? {
        try {
            val dateCurrent =
                getDateCurrentDevice(
                    stringTimeStamp
                )
            calendar.timeInMillis = dateCurrent.time
            return calendar.get(Calendar.YEAR).toString()
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get year from calendar
     * @param calendarCurrent : String?
     * @return year string
     * @throws ParseException
     */
    @JvmStatic
    fun getYear(calendarCurrent: Calendar): String? {
        try {
            return calendarCurrent.get(Calendar.YEAR).toString()
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get hour minute from timestamp string with format: 'HH:mm'
     * @param stringTimeStamp : String?
     * @return hour minute with format: 'HH:mm'
     * @throws ParseException
     */
    @JvmStatic
    fun getHourMinute(stringTimeStamp: String?, is24Format: Boolean = false): String? {
        try {
            val dateCurrent = sdfCurrent.parse(stringTimeStamp)
            val sdfFormat: SimpleDateFormat? = if (is24Format) {
                SimpleDateFormat("hh:mm", Locale.getDefault())
            } else {
                SimpleDateFormat("hh:mm a", Locale.getDefault())
            }
            return sdfFormat?.format(dateCurrent)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get hour minute from timestamp string with format: 'HH:mm'
     * @param stringTimeStamp : String?
     * @return hour minute with format: 'HH:mm'
     * @throws ParseException
     */
    @JvmStatic
    fun getHourMinute(timeMillisecond: Long, is24Format: Boolean = false): String? {
        try {
            val dateTemp = Date()
            dateTemp.time = timeMillisecond
            val sdfFormat: SimpleDateFormat? = if (is24Format) {
                SimpleDateFormat("HH:mm", Locale.getDefault())
            } else {
                SimpleDateFormat("hh:mm a", Locale.getDefault())
            }
            return sdfFormat?.format(dateTemp)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /***
     * Get hour minute from calendar with format: 'HH:mm'
     * @param calendarCurrent : Calendar
     * @return hour minute with format: 'HH:mm'
     * @throws ParseException
     */
    @JvmStatic
    fun getHourMinute(calendarCurrent: Calendar): String? {
        try {
            val dateCurrent = Date(calendarCurrent.timeInMillis)
            val sdfFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return sdfFormat.format(dateCurrent)
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /**
     * Get time ago from timeStamp String
     * @param timeStamp: String
     * @return time ago string
     * @throws ParseException
     */
    @JvmStatic
    fun getTimeAgo(timeStamp: String?, context: Context): String? {
        try {
            val executedCalendar = Calendar.getInstance()
            val deviceCalendar = Calendar.getInstance()
            executedCalendar.timeInMillis = getDateCurrentDevice(
                timeStamp
            ).time

            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)
            val hours =
                TimeUnit.MILLISECONDS.toHours(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)
            val days =
                TimeUnit.MILLISECONDS.toDays(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)

//            return when {
//                seconds < 60 -> {
//                    if (seconds <= 1) return context.getString(R.string.general_message_date_just_now)
//                    "$seconds ${context.getString(R.string.general_message_date_seconds_ago)}"
//                }
//                minutes < 60 -> {
//                    if (minutes <= 1) return context.getString(R.string.general_message_date_minute_ago)
//                    "$minutes ${context.getString(R.string.general_message_date_minutes_ago)}"
//                }
//                hours < 24 -> {
//                    if (hours <= 1) return context.getString(R.string.general_message_date_hour_ago)
//                    "$hours ${context.getString(R.string.general_message_date_hours_ago)}"
//                }
//                days < 30 -> {
//                    if (days <= 1) return context.getString(R.string.general_message_date_day_ago)
//                    "$days ${context.getString(R.string.general_message_date_days_ago)}"
//                }
//                else -> convertGeneralMonthNameFormatDate(context, timeStamp)
//            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    /**
     * Get time ago from time in millisecond
     * @param timeMillisecond: Double
     * @return time ago string
     * @throws ParseException
     */
    @JvmStatic
    fun getTimeAgo(context: Context, timeMillisecond: Long): String? {
        try {
            val executedCalendar = Calendar.getInstance()
            val deviceCalendar = Calendar.getInstance()
            val timeStamp =
                convertTimeMillisecondToTimeStamp(
                    timeMillisecond
                )
            executedCalendar.timeInMillis = timeMillisecond.toLong()

            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)
            val hours =
                TimeUnit.MILLISECONDS.toHours(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)
            val days =
                TimeUnit.MILLISECONDS.toDays(deviceCalendar.timeInMillis - executedCalendar.timeInMillis)

//            return when {
//                seconds < 60 -> {
//                    if (seconds <= 1) return context.getString(R.string.general_message_date_just_now)
//                    "$seconds ${context.getString(R.string.general_message_date_seconds_ago)}"
//                }
//                minutes < 60 -> {
//                    if (minutes <= 1) return context.getString(R.string.general_message_date_minute_ago)
//                    "$minutes ${context.getString(R.string.general_message_date_minutes_ago)}"
//                }
//                hours < 24 -> {
//                    if (hours <= 1) return context.getString(R.string.general_message_date_hour_ago)
//                    "$hours ${context.getString(R.string.general_message_date_hours_ago)}"
//                }
//                days < 30 -> {
//                    if (days <= 1) return context.getString(R.string.general_message_date_day_ago)
//                    "$days ${context.getString(R.string.general_message_date_days_ago)}"
//                }
//                else -> convertGeneralMonthNameFormatDate(context, timeStamp)
//            }
        } catch (e: ParseException) {
            Log.e("<<error", "Error: $e")
        }
        return null
    }

    private fun getDateCurrentDevice(stringTimeStamp: String?): Date {
        try {
            val dateGMT = sdfGMT.parse(stringTimeStamp)
            return sdfCurrent.parse(
                sdfCurrent.format(dateGMT)
            )
        } catch (e: ParseException) {
            throw e
        }
    }

    @JvmStatic
    fun convertHourStringToMinute(hourString: String): Int {
        return try {
            hourString.substring(0, 2).toInt() * 60 + hourString.substring(3).toInt()
        } catch (ex: Exception) {
            0
        }
    }

    @JvmStatic
    fun convertMinuteToHourString(minute: Int?): String {
        if (minute == null || minute < 0) return ""
        val hourResult = minute / 60
        val minuteResult = minute % 60

        val sdfParsed = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT00:00")
        calendar.set(Calendar.HOUR_OF_DAY, hourResult)
        calendar.set(Calendar.MINUTE, minuteResult)
        return sdfParsed.format(calendar.time)
    }
}