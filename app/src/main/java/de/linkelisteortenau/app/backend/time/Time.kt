package de.linkelisteortenau.app.backend.time

/**
 * @author Manuel Ziel
 * @since 0.1 Beta 2022.11
 *
 * Get and convert the Time
 **/

import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.debug.DEBUG_TIME
import de.linkelisteortenau.app.backend.preferences.Preferences
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * Enum class for time units
 **/
enum class EnumTime{
    YEAR, MONTH, DAY, WEEKDAY, HOUR, MINUTE
}

/**
 * Class to load Time and format Date and Time
 *
 * @param context as Context
 **/
class Time(
    private val context: Context
    ) {
    private val debug = Preferences(context).getSystemDebug()
    private val locale = Preferences(context).getLocale()

    /**
     * Get Unix system time as Long in ms "1672072346296"
     *
     * @return Long
     **/
    fun getUnixTime(): Long = System.currentTimeMillis()

    /**
     * Get date and time format as String "yyyy-MM-dd'T'HH:mm:ssXXX"
     *
     * @param unixTime as Time to format to String
     * @return String
     **/
    fun getDateFormat(
        unixTime: Long
    ): String {
        val patter = SimpleDateFormat(TIME_PATTER_GLOBAL, Locale.GERMAN)
        return patter.format(unixTime)
    }

    /**
     * Convert date format to Unix timestamp
     *
     * @param patternAsString as String "yyyy-MM-dd'T'HH:mm:ssXXX"
     * @param timeAsString as String 2019-05-04T12:00:00+02:00
     * @return Long?
     **/
    fun dateFormatToUnix(
        patternAsString: String,
        timeAsString: String
    ): Long? {
        return SimpleDateFormat(patternAsString, Locale.getDefault()).parse(timeAsString)?.time
    }

    /**
     * Convert Unix or other timestamp to human readable time with system default locale
     *
     * @param unixTime as Long
     * @return HashMap as EnumTime
     **/
    fun dateFormatToHumanTime(
        unixTime: Long
    ): HashMap<EnumTime, String> {
        val hashMap: HashMap<EnumTime, String> = HashMap<EnumTime, String>()

        if (locale == "DE") {
            val year = SimpleDateFormat(TIME_PATTER_YEAR, Locale.GERMAN)
            val month = SimpleDateFormat(TIME_PATTER_MONTH, Locale.GERMAN)
            val day = SimpleDateFormat(TIME_PATTER_DAY, Locale.GERMAN)
            val weekday = SimpleDateFormat(TIME_PATTER_WEEKDAY, Locale.GERMAN)
            val hour = SimpleDateFormat(TIME_PATTER_HOUR, Locale.GERMAN)
            val minute = SimpleDateFormat(TIME_PATTER_MINUTE, Locale.GERMAN)
            hashMap[EnumTime.YEAR] = year.format(unixTime).toString()
            hashMap[EnumTime.MONTH] = month.format(unixTime).toString()
            hashMap[EnumTime.DAY] = day.format(unixTime).toString()
            hashMap[EnumTime.WEEKDAY] = weekday.format(unixTime).toString()
            hashMap[EnumTime.HOUR] = hour.format(unixTime).toString()
            hashMap[EnumTime.MINUTE] = minute.format(unixTime).toString()
        } else {
            val year = SimpleDateFormat(TIME_PATTER_YEAR, Locale.ENGLISH)
            val month = SimpleDateFormat(TIME_PATTER_MONTH, Locale.ENGLISH)
            val day = SimpleDateFormat(TIME_PATTER_DAY, Locale.ENGLISH)
            val weekday = SimpleDateFormat(TIME_PATTER_WEEKDAY, Locale.ENGLISH)
            val hour = SimpleDateFormat(TIME_PATTER_HOUR, Locale.GERMAN)
            val minute = SimpleDateFormat(TIME_PATTER_MINUTE, Locale.GERMAN)
            hashMap[EnumTime.YEAR] = year.format(unixTime).toString()
            hashMap[EnumTime.MONTH] = month.format(unixTime).toString()
            hashMap[EnumTime.DAY] = day.format(unixTime).toString()
            hashMap[EnumTime.WEEKDAY] = weekday.format(unixTime).toString()
            hashMap[EnumTime.HOUR] = hour.format(unixTime).toString()
            hashMap[EnumTime.MINUTE] = minute.format(unixTime).toString()
        }

        if (debug) {
            Log.d(DEBUG_TIME, "Convert from \"$unixTime\" to \"${hashMap[EnumTime.HOUR]}:${hashMap[EnumTime.MINUTE]} at ${hashMap[EnumTime.WEEKDAY]}\" \n")
        }
        return hashMap
    }
}