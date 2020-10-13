package com.elacqua.albedo.util

import android.widget.TextView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.quote_api.Quote
import java.security.MessageDigest
import java.sql.Timestamp

object Utility {

    val homeCategoryTitles = listOf(
        R.string.title_airings,
        R.string.title_upcomings,
        R.string.title_movies,
        R.string.title_top_manga,
        R.string.title_novels
    )

    fun <T>setTextViewText(textView: TextView, elements: List<T>) {
        var str = ""
        for (element in elements){
            str += "$element\n"
        }
        textView.text = str
    }

    fun getQuoteMd5Hash(quote: Quote): String {
        val value = quote.anime + quote.character + quote.quote
        return value.md5
    }

    val String.md5: String
        get() {
            val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }

    fun getTimestamp(): String{
        val timestamp = Timestamp(System.currentTimeMillis()).toString()
        return timestamp.replace(" ", "T")
    }
}