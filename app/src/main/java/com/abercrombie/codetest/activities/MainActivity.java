package com.abercrombie.codetest.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.abercrombie.codetest.R;
import com.abercrombie.codetest.fragments.BaseFragment;
import com.abercrombie.codetest.fragments.ExplorePageFragment;

public class MainActivity extends AppCompatActivity {
  private ExplorePageFragment pageFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    pageFragment = ExplorePageFragment.newInstance();
    displayFragment(pageFragment);
  }

  public void displayFragment(BaseFragment fragment) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    // TODO causes crash.. with simultaneous interactions on fragments
    //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
    ft.replace(R.id.fragment, fragment);
    if (!getSupportFragmentManager().getFragments().isEmpty())
      ft.addToBackStack(fragment.getClass().getName());
    ft.commit();
  }

  public ExplorePageFragment getPageFragment() {
    return pageFragment;
  }
}
