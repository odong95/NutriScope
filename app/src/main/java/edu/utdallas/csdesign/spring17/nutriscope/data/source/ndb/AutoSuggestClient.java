package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by john on 3/3/17.
 */


public class AutoSuggestClient {

    final private static String TAG = "NDBClient";


    private AutoSuggestService autoSuggestService;


    public AutoSuggestClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                Log.d(TAG, originalHttpUrl.encodedPath());

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("max", "50")
                        .addQueryParameter("offset", "0")
                        .addQueryParameter("sort", "r")
                        .addQueryParameter("format", "JSON")
                        .addQueryParameter("ds", "Standard Reference")
                        .addQueryParameter("api_key", "14Ahlq8z1eROqsbWEI86M4klUkzsFh3du4j3FvuV")
                        .build();

                Log.d(TAG, url.toString());

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);

            }
        });

        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Log.d(TAG, original.url().toString());


                Response response = chain.proceed(original);
                //  Log.d(TAG, response. response.body().string());
                return response;
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.addNetworkInterceptor(interceptor);

        OkHttpClient httpClient = client.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nal.usda.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        this.autoSuggestService = retrofit.create(AutoSuggestService.class);


    }

    public AutoSuggestService getAutoSuggestService() {
        return autoSuggestService;
    }

}
