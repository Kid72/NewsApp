package com.example.myapplication2;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private List<NewsEnt> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final RealmChangeListener listener;

    private Context context;

    protected LayoutInflater inflater;
    protected RealmResults<NewsEnt> realmResults;

    public HomeRecyclerViewAdapter(Context context, RealmResults<NewsEnt> realmResults, boolean automaticUpdate) {
        if(context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
        this.realmResults = realmResults;
        this.mInflater = LayoutInflater.from(context);
        this.listener = (!automaticUpdate) ? null : new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                notifyDataSetChanged();

            }

        };

        if(listener != null && realmResults != null) {
            realmResults.getRealm()
                    .addChangeListener(listener);
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.homerecyclerview_row2, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {



        holder.textViewDescr.setText(getItem(position).getDescription());
        holder.textViewCategory.setText(getCategoryName(getItem(position).getCategory(), context));
        holder.textViewCategory.setBackground(getCategoryBackground(getItem(position).getCategory(), context));
        holder.textViewCategory.setPadding(5, 5, 5, 5);


        holder.imageView7.setImageURI(getItem(position).getImage_link());

        String s = getItem(position).getImage_link();
    }

    @Override
    public int getItemCount() {
        if(realmResults == null) {
            return 0;
        }
        return realmResults.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView textViewDescr;
        TextView textViewCategory;
        SimpleDraweeView imageView7;

        ViewHolder(View itemView) {
            super(itemView);
            textViewDescr = itemView.findViewById(R.id.textViewDescr);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            imageView7 = (SimpleDraweeView) itemView.findViewById(R.id.imageView7);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webActivity = new Intent(context, WebActivity.class);
                    webActivity.putExtra("link", realmResults.get(getAdapterPosition()).getLink());
                    context.startActivity(webActivity);
                }
            });
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public long getItemId(int i) {
        // TODO: find better solution once we have unique IDs
        return i;
    }

    public NewsEnt getItem(int i) {
        if(realmResults == null) {
            return null;
        }
        return (NewsEnt) realmResults.get(i);
    }

    public void updateRealmResults(RealmResults<NewsEnt> queryResults) {
        if(listener != null) {
            // Making sure that Adapter is refreshed correctly if new RealmResults come from another Realm
            if(this.realmResults != null) {
                realmResults.getRealm().removeChangeListener(listener);
            }
            if(queryResults != null) {
                queryResults.getRealm().addChangeListener(listener);
            }
        }

        this.realmResults = queryResults;
        notifyDataSetChanged();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private static String getCategoryName(String categoryNumber, Context context){
        List<String> arrayList = Arrays.asList(context.getResources().getStringArray(R.array.categories));
        return arrayList.get(Integer.parseInt(categoryNumber));
    }

    private static Drawable getCategoryBackground(String categoryNumber, Context context){
        if(categoryNumber.equals("0")){
            return context.getResources().getDrawable(R.drawable.gradient_1_rose);
        }else if (categoryNumber.equals("1")){
            return context.getResources().getDrawable(R.drawable.gradient_2_blue);
        }else if (categoryNumber.equals("2")){
            return context.getResources().getDrawable(R.drawable.gradient_3_purple);
        }else if (categoryNumber.equals("3")){
            return context.getResources().getDrawable(R.drawable.gradient_4_piglet);
        }else if (categoryNumber.equals("4")){
            return context.getResources().getDrawable(R.drawable.gradient_5_mauve);
        }else if (categoryNumber.equals("5")){
            return context.getResources().getDrawable(R.drawable.gradient_6_grey);
        }else if (categoryNumber.equals("6")){
            return context.getResources().getDrawable(R.drawable.gradient_7_memory);
        }else if (categoryNumber.equals("7")){
            return context.getResources().getDrawable(R.drawable.gradient_8_social);
        }else if (categoryNumber.equals("8")){
            return context.getResources().getDrawable(R.drawable.gradient_9_cherry);
        }else if (categoryNumber.equals("9")){
            return context.getResources().getDrawable(R.drawable.gradient_10_pinky);
        }else if (categoryNumber.equals("10")){
            return context.getResources().getDrawable(R.drawable.gradient_11_lush);
        }else if (categoryNumber.equals("11")){
            return context.getResources().getDrawable(R.drawable.gradient_12_kashmir);
        }else if (categoryNumber.equals("12")){
            return context.getResources().getDrawable(R.drawable.gradient_13_green);
        }else if (categoryNumber.equals("13")){
            return context.getResources().getDrawable(R.drawable.gradient_14_blue);
        }else if (categoryNumber.equals("14")){
            return context.getResources().getDrawable(R.drawable.gradient_15_cyan);
        }else{
            return context.getResources().getDrawable(R.drawable.gradient_15_cyan);
        }

    }
}