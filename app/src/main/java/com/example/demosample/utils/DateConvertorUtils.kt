package com.example.demosample.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateConvertorUtils {
    fun formatNewsDate(inputDate: String?): String {
        if (inputDate.isNullOrEmpty()) return ""

        return try {
            // 1. Input Format (ISO 8601 - Jo API se mil raha hai)
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            // 2. Output Format (Jo aapko chahiye)
            val outputFormat = SimpleDateFormat("dd/MMM/yyyy hh:mm a", Locale.getDefault())

            val date = inputFormat.parse(inputDate)
            if (date != null) {
                outputFormat.format(date)
            } else {
                inputDate // Agar parse nahi hua toh original bhej do
            }
        } catch (e: Exception) {
            e.printStackTrace()
            inputDate ?: ""
        }
    }
}