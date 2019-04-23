package com.abercrombie.codetest.presenters;

import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.models.IDataRepo;
import com.abercrombie.codetest.views.ExplorePageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

public class ExplorePagePresenter implements BasePresenter {
  @Inject protected IDataRepo dataRepo;
  protected ExplorePageView view;
  protected CompositeDisposable compositeDisposable;

  @Inject
  public ExplorePagePresenter(ExplorePageView view) {
    this.view = view;
    this.compositeDisposable = new CompositeDisposable();
  }

  public void loadDataList() {
    view.showLoading();
    compositeDisposable.add(dataRepo.retrieveDataList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((List<Data> data) -> {
          view.onDataListResponse(data);
          view.hideLoading();
        }, (Throwable t) -> {
          view.onDataListError(t);
          view.hideLoading();
        }));
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
