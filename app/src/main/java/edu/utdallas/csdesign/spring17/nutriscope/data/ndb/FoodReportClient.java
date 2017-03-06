package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by john on 3/3/17.
 */

public class FoodReportClient {

    final private static String TAG = "NDBClient";
    private static FoodReportClient INSTANCE = null;

    private FoodReportService service;




    private FoodReportClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                Log.d(TAG, originalHttpUrl.encodedPath());

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("format", "JSON")
                        .addQueryParameter("api_key", "14Ahlq8z1eROqsbWEI86M4klUkzsFh3du4j3FvuV")
                        .build();

                Log.d(TAG, url.toString());

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1").build();
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
        OkHttpClient httpClient = client.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nal.usda.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        this.service = retrofit.create(FoodReportService.class);




    }

    public static FoodReportClient getInstance() {
        if (INSTANCE == null) {
            return new FoodReportClient();
        }
        else {
            return null;
        }

    }


    public FoodReportService getFoodReportService() {
        return service;
    }


}
