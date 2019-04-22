package com.abercrombie.codetest.adapters.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abercrombie.codetest.R;
import com.abercrombie.codetest.models.Navigation;
import com.abercrombie.codetest.views.ExplorePageView;

public class NavDataHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.button) TextView button;

    public NavDataHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

  public void bind(Navigation item, ExplorePageView view) {
    button.setText(item.getTitle());
    button.setOnClickListener(v -> {
      view.onNavClicked(item);
    });
  }
}