package com.abercrombie.codetest.adapters.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abercrombie.codetest.R;
import com.abercrombie.codetest.adapters.NavAdapter;
import com.abercrombie.codetest.models.Data;
import com.abercrombie.codetest.views.ExplorePageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.HashMap;

public class DataHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.background_image) ImageView image;
    @BindView(R.id.top_description) TextView topDescription;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.promo_message) TextView promoMessage;
    @BindView(R.id.bottom_description) TextView bottomDescription;
    @BindView(R.id.navigation_recycler) RecyclerView navigationRecyclerView;

    public DataHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

  public void bind(Data item, ExplorePageView view, HashMap<Data, NavAdapter> navAdapterList) {
    if(!TextUtils.isEmpty(item.getTopDescription())) {
      topDescription.setVisibility(View.VISIBLE);
      topDescription.setText(item.getTopDescription());
    } else
      topDescription.setVisibility(View.GONE);

    if(!TextUtils.isEmpty(item.getTitle())) {
      title.setVisibility(View.VISIBLE);
      title.setText(item.getTitle());
    } else
      title.setVisibility(View.GONE);

    if(!TextUtils.isEmpty(item.getPromoMessage())) {
      promoMessage.setVisibility(View.VISIBLE);
      promoMessage.setText(item.getPromoMessage());
    } else
      promoMessage.setVisibility(View.GONE);

    if(!TextUtils.isEmpty(item.getBottomDescription())) {
      bottomDescription.setVisibility(View.VISIBLE);
      Spanned result;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        result = Html.fromHtml(item.getBottomDescription(), Html.FROM_HTML_MODE_LEGACY);
      } else {
        result = Html.fromHtml(item.getBottomDescription());
      }
      bottomDescription.setText(result);
      bottomDescription.setMovementMethod(LinkMovementMethod.getInstance());
    } else
      bottomDescription.setVisibility(View.GONE);

    Glide.with(itemView.getContext())
        //.asBitmap()
        //.transition(BitmapTransitionOptions.withCrossFade())
        .load(item.getBackgroundImage())
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(image);


    if(item.getNavigations() != null && item.getNavigations().size() > 0) {
      navigationRecyclerView.setVisibility(View.VISIBLE);
      navigationRecyclerView.setLayoutManager(
          new LinearLayoutManager(itemView.getContext()));
      navigationRecyclerView.setHasFixedSize(true);
      if(!navAdapterList.containsKey(item))
        navAdapterList.put(item , new NavAdapter(item.getNavigations(), view));
      navigationRecyclerView.setAdapter(navAdapterList.get(item));
    } else
      navigationRecyclerView.setVisibility(View.GONE);
  }
}