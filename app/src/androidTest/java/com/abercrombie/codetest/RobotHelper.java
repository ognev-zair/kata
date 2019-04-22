package com.abercrombie.codetest;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 *
 */
public class RobotHelper {

  public static void checkDialog(@StringRes int titleId, @StringRes int message) {
    onView(withId(titleId))
        .inRoot(isDialog())
        .check(matches(withText(message)))
        .check(matches(isDisplayed()));
  }

  public static void performClick(@IdRes int id) {
    onView(withId(id)).perform(click());
  }

  public static void performClick(String id) {
    onView(withText(id)).perform(click());
  }

  public static void checkIsViewDisplayed(@IdRes int id) {
    onView(withId(id)).check(matches(isDisplayed()));
  }


  public static void enableWiFi(boolean enable) {
    WifiManager wifi = (WifiManager)
        InstrumentationRegistry.getTargetContext().getSystemService(Context.WIFI_SERVICE);
    wifi.setWifiEnabled(enable);
  }

  public static void checkIsActivityDisplayed(String activityName) {
    intended(hasComponent(activityName));
  }

  public static void wait(int mls) {
    try { // waiting until items loaded and displayed
      Thread.sleep(mls);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void scrollRecycler(@IdRes int viewId, int scrollToIndex) {
    onView(withId(viewId)).perform(RecyclerViewActions.
        scrollToPosition(scrollToIndex))
        .check(matches(isDisplayed()));
  }

  public static void checkRecyclerViewElementEquals(@IdRes int resId, int position,
      String text) {
    onData(withId(R.id.recycler_view)).atPosition(position)
        .check(matches(hasDescendant(withText(position))));
  }

  public static void checkRecyclerViewElementDisplayed(@IdRes int resId, int position) {
    onView(withId(R.id.recycler_view))
        .check(matches(withViewAtPosition(position, hasDescendant(allOf(withId(resId), isDisplayed())))));
  }

  public static Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
    return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
      @Override
      public void describeTo(Description description) {
        itemMatcher.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(RecyclerView recyclerView) {
        final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
      }
    };
  }
}
