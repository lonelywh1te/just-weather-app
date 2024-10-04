package ru.lonelywh1te.justweather.data.network

import okhttp3.Interceptor
import okhttp3.Response

class WeatherApiKeyInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("key", apiKey)
            .addQueryParameter("lang", "ru") // TODO: change to user language
            .build()
        val request = chain.request().newBuilder().url(url).build()

        return chain.proceed(request)
    }
}