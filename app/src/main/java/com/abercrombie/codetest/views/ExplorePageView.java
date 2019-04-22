package com.abercrombie.codetest.views;

import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.models.Navigation;
import java.util.List;

/**
 *
 */
public interface ExplorePageView extends BaseView {

  void onDataListResponse(List<Data> dataList);

  void onDataListError(Throwable t);

  void onNavClicked(Navigation navigation);
}
