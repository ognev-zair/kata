package com.abercrombie.codetest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.abercrombie.codetest.activities.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 */
@RunWith(AndroidJUnit4.class) @LargeTest public class OnlineScenariosUITests {

  private final int TIME_DELAY = 4000; // 4 seconds

  @Rule public ActivityTestRule<MainActivity> testRule =
      new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override protected void beforeActivityLaunched() {
          RobotHelper.enableWiFi(true);
          Intents.init();
        }
        @Override protected void afterActivityFinished() {
          super.afterActivityFinished();
          Intents.release();
        }
      };

  @Test public void useAppContext() {
    Context appContext = InstrumentationRegistry.getTargetContext();
    assertEquals("com.abercrombie.codetest", appContext.getPackageName());
  }

  @Test public void checkIsMainActivityDisplayed() {
    RobotHelper.checkIsActivityDisplayed(MainActivity.class.getName());
  }

  @Test public void testScroll() {
   RobotHelper.wait(TIME_DELAY);
   RobotHelper.scrollRecycler(R.id.recycler_view, 9);
   RobotHelper.scrollRecycler(R.id.recycler_view, 0);
   RobotHelper.scrollRecycler(R.id.recycler_view, 2);
   RobotHelper.scrollRecycler(R.id.recycler_view, 6);
   RobotHelper.scrollRecycler(R.id.recycler_view, 0);
  }

  /*
   Scenario "Open WebView":
   checking: is webview opened after "Shop Men" was clicked
   */
  @Test
  public void testShowMoreClicked() {
    RobotHelper.wait(TIME_DELAY);
    onView(withText("Shop Men"))
        .perform(click());
    onView(withId(R.id.web_view))
        .check(matches(isDisplayed()));
  }

  /*
  Scenario "Open-Close WebView multiple times":
  checking:
  */
  @Test
  public void testShowMoreMultipleClicked() {
    RobotHelper.wait(TIME_DELAY);

    RobotHelper.performClick("Shop Men");
    RobotHelper.checkIsViewDisplayed(R.id.web_view);
    Espresso.pressBack();
    RobotHelper.checkIsViewDisplayed(R.id.recycler_view);

    RobotHelper.performClick("Shop Women");
    RobotHelper.checkIsViewDisplayed(R.id.web_view);
    Espresso.pressBack();
    RobotHelper.checkIsViewDisplayed(R.id.recycler_view);

    RobotHelper.scrollRecycler(R.id.recycler_view, 2);
    RobotHelper.performClick("SHOP NOW");
    RobotHelper.checkIsViewDisplayed(R.id.web_view);
    Espresso.pressBack();
    RobotHelper.checkIsViewDisplayed(R.id.recycler_view);

    RobotHelper.scrollRecycler(R.id.recycler_view, 10);
    RobotHelper.performClick("SHOP NOW");
    RobotHelper.checkIsViewDisplayed(R.id.web_view);
    Espresso.pressBack();
    RobotHelper.checkIsViewDisplayed(R.id.recycler_view);
  }


  /*
  Test progress bar shown in webview
   */
  @Test
  public void testWebViewProgressBar() {
    RobotHelper.wait(TIME_DELAY);
    RobotHelper.performClick("Shop Men");
    RobotHelper.checkIsViewDisplayed(R.id.web_view);
    RobotHelper.checkIsViewDisplayed(R.id.progress_bar);
  }

  /*
  for the first item in the list Data has all properties,
  so that all view-elements should be visible in that product card
   */
  @Test
  public void testFirstElementAllPropertiesDisplayed() {
    RobotHelper.wait(TIME_DELAY);
    //RobotHelper.checkRecyclerViewElement()
    RobotHelper.checkRecyclerViewElementDisplayed(R.id.background_image, 0);
    RobotHelper.checkRecyclerViewElementDisplayed(R.id.top_description,0);
    RobotHelper.checkRecyclerViewElementDisplayed(R.id.title,0);
    RobotHelper.checkRecyclerViewElementDisplayed(R.id.promo_message,0);
    RobotHelper.checkRecyclerViewElementDisplayed(R.id.bottom_description,0);
    RobotHelper.checkRecyclerViewElementDisplayed(R.id.navigation_recycler, 0);
  }
}
