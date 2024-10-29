package ru.lonelywh1te.justweather.data.network

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

private const val LOG_TAG = "WeatherInterceptor"

class WeatherInterceptor(private val apiKey: String, private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlBuilder = chain.request().url().newBuilder()
            .addQueryParameter("key", apiKey)

        val locale = context.resources.configuration.locales[0]

        if (locale.language == Locale("ru").language) {
            urlBuilder.addQueryParameter("lang", "ru")
        } else {
            urlBuilder.addQueryParameter("lang", "en")
        }

        val url = urlBuilder.build()
        val request = chain.request().newBuilder().url(url).build()

        return chain.proceed(request)
    }
}