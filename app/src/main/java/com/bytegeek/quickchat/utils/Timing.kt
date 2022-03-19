package com.bytegeek.quickchat.utils

import android.text.format.Time
import com.bytegeek.quickchat.utils.Timing.TimeFormats
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Timing {
    fun getHour(date: Long): Int {
        val instance = Calendar.getInstance()
        instance.timeInMillis = date
        return instance[Calendar.HOUR_OF_DAY]
    }

    fun getMinute(date: Long): Int {
        val instance = Calendar.getInstance()
        instance.timeInMillis = date
        return instance[Calendar.MINUTE]
    }

    fun getLocalTimeFromUTC(timeInUnixStamp: Long): Long {
        val timeFormat = Time()
        timeFormat.set(timeInUnixStamp + TimeZone.getDefault().getOffset(timeInUnixStamp))
        return timeFormat.toMillis(true)
    }

    val initialDelay: Long
        get() {
            val hour = getHour(System.currentTimeMillis())
            return (24 - hour).toLong()
        }
    val currentTimeEpoch: Long
        get() = System.currentTimeMillis() / 1000
    val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    fun getTimeInString(timeStamp: Long, timeFormat: TimeFormats): String {
        val date = Date(timeStamp * 1000L)
        val sdf = SimpleDateFormat(timeFormat.timeFormat, Locale.getDefault())
        return sdf.format(date)
    }

    fun getTimeInStringWithoutStamp(timeStamp: Long, timeFormat: TimeFormats): String {
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat(timeFormat.timeFormat, Locale.getDefault())
        return sdf.format(date)
    }

    fun getTimeInUnixStamp(time: String?, timeFormat: TimeFormats): Long {
        return try {
            val sdf = SimpleDateFormat(timeFormat.timeFormat, Locale.getDefault())
            val date = sdf.parse(time)
            date.time / 1000
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    fun getTimeInMillis(time: String?, timeFormat: TimeFormats): Long {
        return try {
            val sdf = SimpleDateFormat(timeFormat.timeFormat, Locale.getDefault())
            val date = sdf.parse(time)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    fun getDayFromTimeStamp(timeStamp: Long): Int {
        val instance = Calendar.getInstance()
        instance.timeInMillis = timeStamp * 1000
        return instance[Calendar.DAY_OF_MONTH]
    }

    fun getDayNameFromTimeStamp(timeStamp: Long): Int {
        val instance = Calendar.getInstance()
        instance.timeInMillis = timeStamp * 1000
        return instance[Calendar.DAY_OF_WEEK]
    }

    fun getMonthFromTimeStamp(timeStamp: Long): Int {
        val instance = Calendar.getInstance()
        instance.timeInMillis = timeStamp * 1000
        return instance[Calendar.MONTH]
    }

    fun getTimeStampFromMonthAndYear(
        month: Int, year: Int
    ): Long {
        val instance = Calendar.getInstance()
        instance[Calendar.MONTH] = month
        instance[Calendar.YEAR] = year
        return instance.timeInMillis
    }

    fun getTimeStampFromDay(day: Int): Long {
        val instance = Calendar.getInstance()
        instance[Calendar.DAY_OF_MONTH] = day
        return instance.timeInMillis
    }

    fun weekFirstDay(): Long {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_WEEK] = calendar.firstDayOfWeek
        return calendar.timeInMillis / 1000
    }

    fun weekLastDay(): Long {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar[Calendar.DAY_OF_WEEK] = 7
        return calendar.timeInMillis / 1000
    }

    fun getStartOfTheDay(timeStamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        cal[Calendar.HOUR_OF_DAY] = 0 //set hours to zero
        cal[Calendar.MINUTE] = 0 // set minutes to zero
        cal[Calendar.SECOND] = 0 //set seconds to zero
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    fun getMonth(timeStamp: Long): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        return cal[Calendar.MONTH]
    }

    fun getStartOfTheMonth(timeStamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        cal[Calendar.DAY_OF_MONTH] = 1
        cal[Calendar.HOUR_OF_DAY] = 0 //set hours to zero
        cal[Calendar.MINUTE] = 0 // set minutes to zero
        cal[Calendar.SECOND] = 0 //set seconds to zero
        cal[Calendar.MILLISECOND] = 0
        cal.timeZone = TimeZone.getTimeZone("GMT")
        return cal.timeInMillis
    }

    fun getEndOfTheMonth(timeStamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        cal[Calendar.DAY_OF_MONTH] = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        cal[Calendar.HOUR_OF_DAY] = 0 //set hours to zero
        cal[Calendar.MINUTE] = 0 // set minutes to zero
        cal[Calendar.SECOND] = 0 //set seconds to zero
        cal[Calendar.MILLISECOND] = 0
        cal.timeZone = TimeZone.getTimeZone("GMT")
        return cal.timeInMillis
    }

    fun getEndOfTheDay(timeStamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        cal[Calendar.HOUR_OF_DAY] = 23 //set hours to zero
        cal[Calendar.MINUTE] = 59 // set minutes to zero
        cal[Calendar.SECOND] = 59 //set seconds to zero
        cal[Calendar.MILLISECOND] = 999
        return cal.timeInMillis
    }

    fun getTime(hourOfDay: Int, minute: Int): String {
        val AM_PM: String
        AM_PM = if (hourOfDay < 12) {
            "AM"
        } else {
            "PM"
        }
        return "$hourOfDay : $minute $AM_PM"
    }

    val currentMonth: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar[Calendar.MONTH]
        }
    val currentYear: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar[Calendar.YEAR]
        }
    val validDateOfBirth: Long
        get() {
            val instance = Calendar.getInstance()
            instance[Calendar.YEAR] = 1970
            return instance.timeInMillis
        }

    enum class TimeFormats(val timeFormat: String) {
        DD_MM_YYYY("dd/MM/yyyy"), DD__MM__YYYY("dd-MM-yyyy"), DD_MM_YYYY_HH_MM_A("dd/MM/yyyy hh:mm a"), DD_MMM_YYYY(
            "dd MMM yyyy"
        ),
        MMM_YYYY("MMM, yyyy"), MMM("MMM"), MM("MM"), EEEE("EEE"), DD("dd"), YYYY("yyyy"), DD_MMMM_YYYY(
            "dd MMMM yyyy"
        ),
        MM_DD_YYYY("MM/dd/yyyy"), YYYY_MM_DD("yyyy/MM/dd"), CUSTOM_YYYY_MM_DD("yyyy-MM-dd"), YYYY_MM_DD_HH_MM_A(
            "yyyy/MM/dd, hh:mm a"
        ),
        CUSTOM_YYYY_MM_DD_HH_MM_A("yyyy/MM/dd hh:mm a"), YYYY_MM_DD_HH_MM_S("yyyy-MM-dd hh:mm:ss"), MONTH_NUMBER(
            "MM"
        ),
        DD_MMM("dd MMM"), EEE_MM_DD_YYYY_HH_MM("E MMM dd yyyy dd HH:mm"), YYYY_MM_DD_T_00("yyyy-MM-dd'T'HH:mm:ss.sssZ"), E_MMM_DD_HH_SS_Z_YYYY(
            "E MMM dd HH:mm:ss Z yyyy"
        ),
        MONTH_FULL_NAME("MMMM"), MONTH_SHORT_NAME("MMM"), MMM_dd_hh_mm_a("MMM dd, hh:mm a"), YEAR("yyyy"), DATE(
            "dd"
        ),
        HH_24("HH:mm"), HH_12("hh:mm a"), HOUR_24("HH"), HOUR_12("hh"), MINUTE("mm"), AM_PM("a"), DAY_NAME(
            "EEEE"
        ),
        MONTH_DD_YEAR("MMMM dd yyyy"), CUSTOM_DAY("EEEE-dd-MMM"), CUSTOM_DATE_TIME("dd-MM-yyyy hh:mm");

    }
}