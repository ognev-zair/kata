package com.abercrombie.codetest.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.abercrombie.codetest.R;
import com.abercrombie.codetest.adapters.holders.DataHolder;
import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.views.ExplorePageView;
import java.util.HashMap;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataHolder> {
  private List<Data> dataList;
  private HashMap<Data, NavAdapter> navAdapterList;
  private ExplorePageView view;
  public DataAdapter(List<Data> dataList, ExplorePageView view) {
    this.dataList = dataList;
    this.navAdapterList = new HashMap<>();
    this.view = view;
  }

  @NonNull @Override public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return new DataHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_data, viewGroup, false));
  }

  @Override public void onBindViewHolder(@NonNull DataHolder holder, int i) {
    holder.bind(dataList.get(i), view, navAdapterList);
  }

  @Override public int getItemCount() {
    return dataList.size();
  }

  public void setDataList(List<Data> dataList) {
    this.dataList = dataList;
  }
}
