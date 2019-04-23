package com.abercrombie.codetest.injection.modules;

import com.abercrombie.codetest.models.DataRepoImpl;
import com.abercrombie.codetest.models.IDataRepo;
import com.abercrombie.codetest.services.DataService;
import com.abercrombie.codetest.views.ExplorePageView;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private ExplorePageView view;

  public AppModule(ExplorePageView view) {
    this.view = view;
  }

  @Provides ExplorePageView provideExplorePageView() {
    return view;
  }

  @Provides IDataRepo provideDataRepo(DataService dataService) {
    return new DataRepoImpl(dataService);
  }
}
