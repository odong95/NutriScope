package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by john on 3/4/17.
 */

public interface NDBService {
    // https://api.nal.usda.gov
    @GET("/ndb/V2/reports")
    Call<FoodReport> listReport(@Query("type") String type, @Query("format") String format,
                                @Query("api_key") String apiKey, @Query("ndbno") String ndbNo);

}
