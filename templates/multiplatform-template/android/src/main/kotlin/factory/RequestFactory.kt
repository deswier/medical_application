package factory

import android.os.Build
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.myapplication.service.NoteService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.HttpURLConnection
import java.util.*

object RequestFactory {
    public var login = "user1";
    public var password = "user1Pass"

    private val objectMapper: ObjectMapper = ObjectMapper().let {
        it.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().let {
        val encoder: Base64.Encoder = Base64.getEncoder()
        it.addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("Authorization", "Basic " + encoder.encodeToString(("$login:$password").toByteArray()))
                    .method(chain.request().method(), chain.request().body())
                    .build()
            )
        }
    }.build()

    @RequiresApi(Build.VERSION_CODES.O)
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .baseUrl("http://10.0.2.2:8080/")
        .client(okHttpClient)
        .build()
        .also {
            HttpURLConnection.setFollowRedirects(true)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val noteService: NoteService = retrofit.create(NoteService::class.java)
}

class RequestFailedException(message: String?, cause: Throwable?) : RuntimeException(message, cause)

fun <T> Call<T>.call(
    onSuccess: (Call<T>, Response<T>) -> Unit = { _, _ -> },
    onError: (Call<T>, Response<T>) -> Unit = { _, r -> throw RuntimeException("${r.code()}: ${r.message()}"); }
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (!response.isSuccessful) {
                onError.invoke(call, response)
            }
            onSuccess.invoke(call, response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            throw RequestFailedException("Request failed", t)
        }
    })
}