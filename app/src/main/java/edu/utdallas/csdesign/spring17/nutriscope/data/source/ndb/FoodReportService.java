package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodReport;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by john on 3/5/17.
 */

public interface FoodReportService {

    // https://api.nal.usda.gov
    @GET("/ndb/V2/reports")
    Call<FoodReport> listReport(@Query("ndbno") String ndbNo);
}
