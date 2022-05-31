package factory

import androidx.constraintlayout.solver.widgets.Chain
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.myapplication.service.NoteService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.HttpURLConnection

object RequestFactory {
    private val objectMapper: ObjectMapper = ObjectMapper().let {
        it.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
    private val client: OkHttpClient = OkHttpClient().let {
        it.interceptors().add(Interceptor {
            override fun intercept(chain: Chain) {
                val original: Request = chain.request()
                // Настраиваем запросы
                Request request = original . newBuilder ()
                    .header("Accept", "application/json")
                    .header("Authorization", "auth-token")
                    .method(original.method(), original.body())
                    .build();
                Response response = chain . proceed (request);
                return response;
            }

        }
    }

//    client.interceptors().add(new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request original = chain.request();
//
//            // Настраиваем запросы
//            Request request = original.newBuilder()
//                .header("Accept", "application/json")
//                .header("Authorization", "auth-token")
//                .method(original.method(), original.body())
//                .build();
//
//            Response response = chain.proceed(request);
//
//            return response;
//        }


    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .baseUrl("http://10.0.2.2:8080/")
        .client(client)
        .build()
        .also {
            HttpURLConnection.setFollowRedirects(true)
        }

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