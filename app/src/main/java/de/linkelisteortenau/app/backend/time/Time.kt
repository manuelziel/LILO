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
 * Enum class for global
 **/
enum class EnumTime{YEAR, MONTH, DAY, WEEKDAY, HOUR, MINUTE}

/**
 * Class to load Time and format Date and Time
 *
 * @param context as Context
 **/
class Time(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val locale = Preferences(context).getLocale()

    /**
     * Get Unix System Time as Long in ms "1672072346296"
     *
     * @return long
     **/
    fun getUnixTime(): Long {
        return System.currentTimeMillis()
    }

    /**
     * Get Date and Time format as String "yyyy-MM-dd'T'HH:mm:ssXXX"
     *
     * @param unixTime as Time to format to String
     * @return long
     **/
    fun getDateFormat(
        unixTime: Long
    ): String {
        val patter = SimpleDateFormat(TIME_PATTER_GLOBAL, Locale.GERMAN)
        return patter.format(unixTime).toString()
    }

    /**
     * Convert date format to unix example use:
     * return is the unix timestamp
     *
     * @param patternAsString as String "yyyy-MM-dd'T'HH:mm:ssXXX"
     * @param timeAsString as String 2019-05-04T12:00:00+02:00
     * @return long
     **/
    fun dateFormatToUnix(
        patternAsString: String,
        timeAsString: String
    ): Long? {
        return SimpleDateFormat(patternAsString, Locale.getDefault()).parse(timeAsString)?.time
    }

    /**
     * Convert unix or other timestamp to human readable time with locale default from system
     *
     * @param unixTime as Long
     * @return HashMap as EnumTime
     **/
    fun dateFormatToHumanTime(
        unixTime: Long
    ): HashMap<EnumTime, String> {
        val hashMap: HashMap<EnumTime, String> = HashMap<EnumTime, String>()

        if (locale == "DE") {
            //val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN)
            //val timeZone = TimeZone.getTimeZone("Europe/Berlin")
            //simpleDateFormat.timeZone = timeZone

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
            //val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
            //val timeZone = TimeZone.getTimeZone("Europe/Berlin")
            //simpleDateFormat.timeZone = timeZone

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