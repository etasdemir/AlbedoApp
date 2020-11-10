package com.elacqua.albedo.util

import android.widget.TextView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.data.remote.quote_api.QuoteList
import com.google.gson.Gson
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
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

    fun jsonToQuoteList(inputStream: InputStream): QuoteList {
        val json = inputStreamToString(inputStream)
        return Gson().fromJson(json, QuoteList::class.java) ?: QuoteList()
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            Timber.e(e)
            ""
        }
    }

    fun <T> setTextViewText(textView: TextView, elements: List<T>) {
        var str = ""
        for (element in elements) {
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

    fun getTimestamp(): String {
        val timestamp = Timestamp(System.currentTimeMillis()).toString()
        return timestamp.replace(" ", "T")
    }
}