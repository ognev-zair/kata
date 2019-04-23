package com.abercrombie.codetest.presenters;

import com.abercrombie.codetest.MockExplorePagePresenter;
import com.abercrombie.codetest.models.DataRepoImpl;
import com.abercrombie.codetest.models.IDataRepo;
import com.abercrombie.codetest.views.ExplorePageView;
import io.reactivex.disposables.CompositeDisposable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class ExplorePagePresenterTest {

  @Mock private MockExplorePagePresenter presenter;
  @Mock private ExplorePageView view;

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before public void setUp() throws Exception {
  }

  @After public void tearDown() throws Exception {
  }

  @Test public void loadDataList() {
    presenter.loadDataList();
    presenter.loadDataList();
    presenter.loadDataList();
    verify(presenter, times(3)).loadDataList();
  }

  @Test public void onDestroy() {
    presenter.onDestroy();
    presenter.onDestroy();
    verify(presenter, times(2)).onDestroy();
  }

  @Test
  public void testPresenterDisposable() {
    when(presenter.getCompositeDisposable()).thenReturn(new CompositeDisposable());
    assertNotNull(presenter.getCompositeDisposable());

    CompositeDisposable d = new CompositeDisposable();
    presenter.getCompositeDisposable().add(d);
    assertTrue(presenter.getCompositeDisposable().size() == 1);
  }

  @Test
  public void testPresenterView() {
    when(presenter.getView()).thenReturn(view);
    assertNotNull(presenter.getView());
    presenter.getView().showLoading();
    presenter.getView().showLoading();
    presenter.getView().hideLoading();

    verify(presenter.getView(), times(2)).showLoading();
    verify(presenter.getView()).hideLoading();
  }

  @Test
  public void testPresenterViewCallbacks() {
    when(presenter.getView()).thenReturn(view);
    assertNotNull(presenter.getView());
    presenter.getView().onDataListError(null);
    presenter.getView().onDataListResponse(null);
    presenter.getView().onNavClicked(null);

    verify(presenter.getView()).onDataListError(null);
    verify(presenter.getView()).onDataListResponse(null);
    verify(presenter.getView()).onNavClicked(null);
  }
}