package com.abercrombie.codetest;

import android.view.View;
import android.widget.TextView;
import com.abercrombie.codetest.activities.MainActivity;
import com.abercrombie.codetest.fragments.ExplorePageFragment;
import com.abercrombie.codetest.fragments.WebViewFragment;
import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.presenters.ExplorePagePresenter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) @RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, application = App.class) public class UnitTest {
  private MainActivity activity;
  private ExplorePageFragment pageFragment;
  private ExplorePagePresenter presenter;
  private WebViewFragment webViewFragment;

  @Before public void setup() {
    activity =
        Mockito.spy(Robolectric.buildActivity(MainActivity.class).create().visible().start().get());
    MockitoAnnotations.initMocks(this);
    pageFragment = Mockito.spy(activity.getPageFragment());
    presenter =  Mockito.spy(pageFragment.getPagePresenter());
    //view = Mockito.spy(presenter.getView());
    //presenter.setView(view);
    WebViewFragment fragment = WebViewFragment.newInstance(
        "https://www.abercrombie.com/shop/us/womens-dresses-and-rompers");
    startFragment(fragment);
    webViewFragment = Mockito.spy(fragment);
    sleep(2000);
  }

  @After public void after() {
    activity.finish();
    pageFragment.onDestroy();
    activity = null;
    pageFragment = null;
    presenter = null;
    webViewFragment = null;
  }

  @Test public void test1ComponentsNotNull() {
    Robolectric.getForegroundThreadScheduler().pause();
    assertNotNull(activity);
    assertNotNull(pageFragment);
    assertNotNull(presenter);
    Robolectric.getForegroundThreadScheduler().reset();
  }

  @Test public void test2VerifyInitView() {
    Robolectric.getForegroundThreadScheduler().pause();
    pageFragment.initView();
    verify(pageFragment).initView();
    Robolectric.getForegroundThreadScheduler().reset();
  }

  @Test
  public void test3Widgets() {
    assertNotNull(pageFragment.getAdapter());
    assertNotNull(pageFragment.getRecyclerView());
    assertNotNull(pageFragment.getProgressBar());
  }

  /*
    check is all properties are not null for the adapter element
   */
  @Test public void test4AdapterItem() {
    List<Data> dataList = new ArrayList<>();
    dataList.add(new Data());
    pageFragment.getAdapter().setDataList(dataList);
    pageFragment.getAdapter().notifyDataSetChanged();
    View view = pageFragment.getRecyclerView().findViewHolderForAdapterPosition(0).itemView;
    assertNotNull(view.findViewById(R.id.title));
    assertNotNull(view.findViewById(R.id.top_description));
    assertNotNull(view.findViewById(R.id.promo_message));
    assertNotNull(view.findViewById(R.id.bottom_description));
  }
  /*
    Checking is data is correct for the first element in adapter
   */
  @Test public void test5AdapterItemData() {
    initData();
    View view = pageFragment.getRecyclerView().findViewHolderForAdapterPosition(0).itemView;
    TextView title = view.findViewById(R.id.title);
    TextView topDescription = view.findViewById(R.id.top_description);
    TextView promoMessage = view.findViewById(R.id.promo_message);
    TextView bottomDescription = view.findViewById(R.id.bottom_description);
    assertEquals(title.getText().toString(), "TOPS STARTING AT $12");
    assertEquals(topDescription.getText().toString(), "A&F ESSENTIALS");
    assertEquals(promoMessage.getText().toString(), "USE CODE: 12345");
    assertEquals(bottomDescription.getText().toString(), "Exclusive offer");
  }

  @Test public void test6PresenterLoadMethod() {
    presenter.loadDataList();
    verify(presenter, times(1)).loadDataList();
  }

  @Test public void test7WebViewFragment() {
    assertNotNull(webViewFragment);
    assertNotNull(webViewFragment.getProgressBar());
  }
  /*
      Test cancel REST API retrofit request
   */
  @Test
  public void test8PresenterCancelDataRequest() {
    assertTrue(presenter.getCompositeDisposable().size() > 0);
    presenter.onDestroy();
    assertTrue(presenter.getCompositeDisposable().size() == 0);
    verify(presenter, times(1)).onDestroy();
  }

  @Test
  public void test9PresenterLoadAndCancel() {
    for(int i = 0; i < 20; i++)
      presenter.loadDataList();
    // compositeDisposable().size() == 21 because first request was added
    // to compositeDisposable list during "setup" with Robolectric
    assertTrue(presenter.getCompositeDisposable().size() == 21);
    verify(presenter, times(20)).loadDataList();
    presenter.onDestroy();
    assertTrue(presenter.getCompositeDisposable().size() == 0);
  }

  @Test
  public void test10PresenterMultipleDestroy() {
    assertTrue(presenter.getCompositeDisposable().size() > 0);
    for(int i = 0; i < 20; i++)
      presenter.onDestroy();
    verify(presenter, times(20)).onDestroy();
    assertTrue(presenter.getCompositeDisposable().size() == 0);
  }

  private void sleep(int mls) {
    try {
      Thread.sleep(mls);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void initData() {
    List<Data> dataList = new ArrayList<>();
    Data data = new Data();
    data.setTitle("TOPS STARTING AT $12");
    data.setTopDescription("A&F ESSENTIALS");
    data.setPromoMessage("USE CODE: 12345");
    data.setBottomDescription("Exclusive offer");
    dataList.add(data);
    pageFragment.getAdapter().setDataList(dataList);
    pageFragment.getAdapter().notifyDataSetChanged();
  }
}