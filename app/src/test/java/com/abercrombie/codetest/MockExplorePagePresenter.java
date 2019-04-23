package com.abercrombie.codetest;

import com.abercrombie.codetest.models.IDataRepo;
import com.abercrombie.codetest.presenters.ExplorePagePresenter;
import com.abercrombie.codetest.views.ExplorePageView;
import io.reactivex.disposables.CompositeDisposable;

public class MockExplorePagePresenter extends ExplorePagePresenter {
  public MockExplorePagePresenter(ExplorePageView view) {
    super(view);
  }

  public void setView(ExplorePageView view) {
      this.view = view;
  }

  public IDataRepo getDataRepo() {
    return dataRepo;
  }

  public void setDataRepo(IDataRepo repo) {
    this.dataRepo = repo;
  }

  public CompositeDisposable getCompositeDisposable() {
    return compositeDisposable;
  }

  public ExplorePageView getView() {
    return view;
  }
}
