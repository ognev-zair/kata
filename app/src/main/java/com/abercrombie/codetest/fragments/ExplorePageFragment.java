package com.abercrombie.codetest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.abercrombie.codetest.R;
import com.abercrombie.codetest.activities.MainActivity;
import com.abercrombie.codetest.adapters.DataAdapter;
import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.models.Navigation;
import com.abercrombie.codetest.presenters.ExplorePagePresenter;
import com.abercrombie.codetest.views.ExplorePageView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 */
public class ExplorePageFragment extends BaseFragment implements ExplorePageView {
  @Inject ExplorePagePresenter pagePresenter;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.progress) ProgressBar progressBar;

  private List<Data> dataList;
  private DataAdapter adapter;

  public static ExplorePageFragment newInstance() {
    ExplorePageFragment fragment = new ExplorePageFragment();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createComponent(this).inject(this);
    dataList = new ArrayList<>();
    adapter = new DataAdapter(dataList, this);
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_page_explore;
  }

  @Override public void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(true);
    pagePresenter.loadDataList();
  }

  @Override public void onDataListResponse(List<Data> dataList) {
    if(isAdded()) {
      this.dataList.addAll(dataList);
      adapter.notifyDataSetChanged();
    }
  }

  @Override public void onDataListError(Throwable t) {
    if(isAdded()) {
      new android.app.AlertDialog.Builder(getContext()).setTitle(R.string.error)
          .setMessage(getString(R.string.something_went_wrong))
          .setCancelable(true)
          .setNegativeButton(android.R.string.no, (dialog, which) -> getActivity().finish())
          .setPositiveButton(android.R.string.yes, (dialog, which) -> {
            pagePresenter.loadDataList();
          })
          .show();
    }
  }

  @Override public void onNavClicked(Navigation navigation) {
    ((MainActivity)getActivity())
        .displayFragment(WebViewFragment.newInstance(navigation.getTarget()));
  }

  @Override public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (pagePresenter != null) {
      pagePresenter.onDestroy();
    }
  }

  public ExplorePagePresenter getPagePresenter() {
    return pagePresenter;
  }

  public DataAdapter getAdapter() {
    return adapter;
  }

  public RecyclerView getRecyclerView() {
    return recyclerView;
  }

  public ProgressBar getProgressBar() {
    return progressBar;
  }
}
