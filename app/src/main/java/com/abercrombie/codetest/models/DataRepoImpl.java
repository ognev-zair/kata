package com.abercrombie.codetest.models;

import android.text.TextUtils;
import com.abercrombie.codetest.services.DataService;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Repository.
 * We can add logic to save/retrieve in/from DB while handling
 * communication from the remote services
 */
public class DataRepoImpl implements IDataRepo {
  DataService service;

  public DataRepoImpl(DataService dataService) {
    this.service = dataService;
  }

  @Override public Flowable<List<Data>> retrieveDataList() {
    return service.getDataList("codeTest_exploreData.json")
        .subscribeOn(Schedulers.io())
        .map(data -> {
          for(Data item: data) {
            item.setBottomDescription(
                TextUtils.isEmpty(item.getBottomDescription()) ?
                    null : item.getBottomDescription().replaceAll("\\\\",""));
          }
          return data;
        });
  }
}
