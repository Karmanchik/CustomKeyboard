package house.with.swimmingpool.api.retrofit

import com.google.gson.GsonBuilder
import house.with.swimmingpool.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val mainRetrofit by lazy { getRetrofit() }

val APIKEY = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"

fun getRetrofit(): Retrofit {
        val baseUrl = "https://domsbasseinom.ru/app/"
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else
                        HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val gson = GsonBuilder()
                .setLenient()
                .create()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
}