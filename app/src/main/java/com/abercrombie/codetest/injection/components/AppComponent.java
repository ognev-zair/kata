package com.abercrombie.codetest.injection.components;

import com.abercrombie.codetest.fragments.ExplorePageFragment;
import com.abercrombie.codetest.injection.modules.AppModule;
import com.abercrombie.codetest.injection.modules.NetworkModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
  void inject(ExplorePageFragment fragment);
}
