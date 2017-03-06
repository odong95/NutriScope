package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by john on 3/5/17.
 */

public interface AutoSuggestService {

    // https://api.nal.usda.gov/ndb/search/?format=json&q=butter salted&max=5&offset=0&api_key=14Ahlq8z1eROqsbWEI86M4klUkzsFh3du4j3FvuV&sort=r
    @GET("/ndb/search")
    Call<Search> autoComplete(@Query("q") String term);

}
