package com.abercrombie.codetest.injection.modules;

import com.abercrombie.codetest.views.ExplorePageView;
import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class AppModule {

  private ExplorePageView view;

  public AppModule(ExplorePageView view) {
    this.view = view;
  }

  @Provides ExplorePageView provideExplorePageView() {
    return view;
  }

}
