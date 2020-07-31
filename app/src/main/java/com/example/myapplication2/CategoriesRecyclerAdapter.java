package com.example.myapplication2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<String> list;

    public CategoriesRecyclerAdapter(Context context, List<String> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.categories_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewCategory.setText(list.get(position));
        holder.textViewCategory.setBackground(getCategoryBackground(String.valueOf(position), context));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewCategory;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewCategory = (TextView)itemView.findViewById(R.id.textViewCategory);
        }
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