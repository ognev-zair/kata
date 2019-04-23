package com.abercrombie.codetest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.abercrombie.codetest.injection.components.AppComponent;
import com.abercrombie.codetest.injection.components.DaggerAppComponent;
import com.abercrombie.codetest.injection.modules.AppModule;
import com.abercrombie.codetest.injection.modules.NetworkModule;
import com.abercrombie.codetest.views.ExplorePageView;

public abstract class BaseFragment extends Fragment {
  protected View mainView;
  private Unbinder unbinder;
  protected AppComponent component;

  protected abstract int getLayoutId();

  public abstract void initView();

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (mainView == null) {
      mainView = inflater.inflate(getLayoutId(), container, false);
      unbinder = ButterKnife.bind(this, mainView);
      initView();
    }
    return mainView;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  protected AppComponent createComponent(ExplorePageView mainView) {
    component = DaggerAppComponent.builder()
        .networkModule(new NetworkModule())
        .appModule(new AppModule(mainView))
        .build();
    return component;
  }
}
