package com.llyod.sample.utils

import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Util {
    fun convertDateToFormattedString(releaseDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", Locale("es", "ES"))

            val date = inputFormat.parse(releaseDate)
            val calendar = Calendar.getInstance()
            calendar.time = date!!

            return outputFormat.format(calendar.time).replaceFirstChar {
                it.uppercase()
            }
        } catch (e: Exception) {
            releaseDate
        }
    }

    fun removeHtmlTags(input: String): String {
        return HtmlCompat.fromHtml(input, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

}