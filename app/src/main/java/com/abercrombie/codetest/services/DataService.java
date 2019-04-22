package com.abercrombie.codetest.services;

import com.abercrombie.codetest.models.Data;
import io.reactivex.Flowable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Tricky User-Agent helps to retrieve data from REST-API
 */
public interface DataService {
  @Headers({
      "Content-type: application/json",
      "Accept: */*",
      "User-Agent: Mozilla/5.0 (compatible; Rigor/1.0.0; http://rigor.com)",
  })
  @GET("anf/nativeapp/qa/codetest/{fileName}") Flowable<List<Data>>
  getDataList(@Path("fileName") String fileName);

}
