package com.abercrombie.codetest;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.abercrombie.codetest.activities.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing scenarios without internet connection. Mainly testing Alert Dialog behavior
 */
@RunWith(AndroidJUnit4.class) @LargeTest public class OfflineScenariosUITests {

  @Rule public ActivityTestRule<MainActivity> testRule =
      new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override protected void beforeActivityLaunched() {
          RobotHelper.enableWiFi(false);
          Intents.init();
        }

        @Override protected void afterActivityFinished() {
          super.afterActivityFinished();
          Intents.release();
        }
      };

  @Test public void checkIsMainActivityDisplayed() {
    RobotHelper.checkIsActivityDisplayed(MainActivity.class.getName());
  }

  /*
  Scenario "No internet connection":
  checking: is proper alert dialog displayed with all expected texts
  - CHECK is alert dialog displayed with R.string.error title
  - CHECK is message in dialog is R.string.something_went_wrong
  - CHECK is buttons yes and no displayed
   */
  @Test public void testNoInternetDialog() {
    int titleId =
        testRule.getActivity().getResources().getIdentifier("alertTitle", "id", "android");
    RobotHelper.checkDialog(titleId, R.string.error);
    RobotHelper.checkDialog(android.R.id.message, R.string.something_went_wrong);
    RobotHelper.checkDialog(android.R.id.button2, android.R.string.no);
    RobotHelper.checkDialog(android.R.id.button1, android.R.string.yes);
  }

  /*
  Scenario "No internet connection":
  checking: is "dialog" will be shown again after clicking on "yes" button
  - CHECK is error alert dialog displayed
  - CLICK button YES in alert dialog
  - CHECK again is alert dialog displayed
  */
  @Test public void testNoInternetDialogClickYesWithoutInternet() {
    RobotHelper.checkDialog(android.R.id.message, R.string.something_went_wrong);
    RobotHelper.performClick(android.R.id.button1);
    RobotHelper.checkDialog(android.R.id.message, R.string.something_went_wrong);
  }

  /*
   Scenario "No internet connection":
   checking: is loading from API starts if after clicking "yes" we enable internet
   - CHECK is error alert dialog displayed
   - click button yes in alert dialog
   - enable WIFI and wait until connection will be established 5 seconds
   - click button yes in alert dialog
   - check is progress_bar displayed
   */
  @Test public void testNoInternetDialogClickYesWithInternet() {
    RobotHelper.checkDialog(android.R.id.message, R.string.something_went_wrong);
    RobotHelper.performClick(android.R.id.button1);
    RobotHelper.enableWiFi(true);
    // waiting until items loaded and displayed
    RobotHelper.wait(6000);

    RobotHelper.performClick(android.R.id.button1);
    RobotHelper.checkIsViewDisplayed(R.id.progress);
  }

  /*
   Scenario "No internet connection":
   checking: is activity closes if "no" clicked
   */
  @Test public void testNoInternetDialogClickNO() {
    RobotHelper.checkDialog(android.R.id.message, R.string.something_went_wrong);
    RobotHelper.performClick(android.R.id.button2);
    RobotHelper.checkIsActivityDisplayed(MainActivity.class.getName());
  }
}
