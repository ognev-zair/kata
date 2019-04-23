package com.abercrombie.codetest.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.abercrombie.codetest.R;
import com.abercrombie.codetest.adapters.holders.NavDataHolder;
import com.abercrombie.codetest.models.Navigation;
import com.abercrombie.codetest.views.ExplorePageView;
import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavDataHolder> {
  private List<Navigation> navigations;
  private ExplorePageView view;

  public NavAdapter(List<Navigation> navigations, ExplorePageView view) {
    this.navigations = navigations;
    this.view = view;
  }

  @NonNull @Override public NavDataHolder onCreateViewHolder
      (@NonNull ViewGroup viewGroup, int i) {
    return new NavDataHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_navigation, viewGroup, false));
  }

  @Override public void onBindViewHolder(@NonNull NavDataHolder holder, int i) {
    holder.bind(navigations.get(i), view);
  }

  @Override public int getItemCount() {
    return navigations.size();
  }
}
