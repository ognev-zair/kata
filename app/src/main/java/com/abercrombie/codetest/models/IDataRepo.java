package com.abercrombie.codetest.models;

import io.reactivex.Flowable;
import java.util.List;

public interface IDataRepo {
  Flowable<List<Data>> retrieveDataList();
}
