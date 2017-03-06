package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by john on 3/5/17.
 */

public interface AutoSuggestService {

    // https://ndb.nal.usda.gov/ndb/search/autosuggest?manu=&amp;ingred=&amp;ds=&amp;fgcd=&term=big+mac&api_key=
    @GET("/ndb/search/autosuggest")
    Call<List<ACResult>> autoComplete(@Query("term") String term);

}
