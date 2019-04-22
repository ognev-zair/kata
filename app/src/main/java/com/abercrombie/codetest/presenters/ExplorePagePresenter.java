package com.abercrombie.codetest.presenters;

import android.text.TextUtils;
import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.services.DataService;
import com.abercrombie.codetest.views.ExplorePageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.List;
import javax.inject.Inject;

/**
 *
 */
public class ExplorePagePresenter implements BasePresenter {

  @Inject DataService service;
  private ExplorePageView view;
  private CompositeDisposable compositeDisposable;

  @Inject
  public ExplorePagePresenter(ExplorePageView view) {
    this.view = view;
    this.compositeDisposable = new CompositeDisposable();
  }

  public void loadDataList() {
    view.showLoading();
    Disposable disposable = service.getDataList("codeTest_exploreData.json")
        .subscribeOn(Schedulers.io())
        .map(data -> {
          for(Data item: data) {
            item.setBottomDescription(
                TextUtils.isEmpty(item.getBottomDescription()) ?
                    null : item.getBottomDescription().replaceAll("\\\\",""));
          }
          return data;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSubscriber<List<Data>>() {
          @Override public void onNext(List<Data> data) {
            view.hideLoading();
            view.onDataListResponse(data);
          }

          @Override public void onError(Throwable t) {
            view.hideLoading();
            view.onDataListError(t);
          }

          @Override public void onComplete() {

          }
        });
    compositeDisposable.add(disposable);
  }

  @Override
  public void onDestroy() {
    // cancel requests
    if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
      compositeDisposable.clear();
    }
  }

  public CompositeDisposable getCompositeDisposable() {
    return compositeDisposable;
  }
}
