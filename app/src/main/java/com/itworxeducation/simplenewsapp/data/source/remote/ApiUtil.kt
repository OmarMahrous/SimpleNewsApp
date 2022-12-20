package com.itworxeducation.simplenewsapp.data.source.remote

import java.util.Locale

/**
 * Responsible for providing the base parameters for the endpoints
 */
object ApiUtil {
    val API_KEY: String by lazy {
        "b939b2761cf748ea89ebe6ba1e37da0b"
    }

    val APP_LANGUAGE:String by lazy {
        Locale.getDefault().language
    }
}