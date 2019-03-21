package com.pujara.dhaval.spendsmart.dashboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pujara.dhaval.spendsmart.R;

import java.util.ArrayList;

public class MonthAdapterJava extends RecyclerView.Adapter<MonthAdapterJava.ViewHolder> {
    private OnMonthSelectedListerner onMonthSelectedListerner;
    private Context context;
    private ArrayList<String> monthSelected,selected;
    @NonNull
    @Override
    public MonthAdapterJava.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_months, parent, false);
        return new ViewHolder(view, onMonthSelectedListerner);
    }

    public MonthAdapterJava(ArrayList<String> mData, Context context, OnMonthSelectedListerner onMonthSelectedListerner) {
        this.onMonthSelectedListerner = onMonthSelectedListerner;
        this.monthSelected = mData;
        this.context = context;
        this.selected = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull MonthAdapterJava.ViewHolder viewHolder, int i) {
        String month = monthSelected.get(i);
        viewHolder.month.setText(monthSelected.get(i));

        viewHolder.itemView.setOnClickListener(v -> {
            if(selected.contains(month)){
                selected.remove(month);
                unhighlightView(viewHolder);
            }else {
                selected.add(month);
                highlightView(viewHolder);
            }
            onMonthSelectedListerner.onMonthClick(month);
        });
        if (selected.contains(month))
            highlightView(viewHolder);
        else
            unhighlightView(viewHolder);
    }

    private void highlightView(ViewHolder holder) {
        holder.itemView.setBackground(context.getDrawable(R.drawable.linear_layout_pressed));
        holder.month.setTextColor(context.getResources().getColor(R.color.white));
    }

    private void unhighlightView(ViewHolder holder) {
        holder.itemView.setBackground(context.getDrawable(R.drawable.linear_layout_border));
        holder.month.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return monthSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView month;
        LinearLayout linearLayout;
        OnMonthSelectedListerner onMonthSelectedListerner;

        ViewHolder(@NonNull View itemView, OnMonthSelectedListerner onMonthSelectedListerner) {
            super(itemView);
            month = itemView.findViewById(R.id.month);
            linearLayout = itemView.findViewById(R.id.linear_layout_month);
            this.onMonthSelectedListerner = onMonthSelectedListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnMonthSelectedListerner {
        void onMonthClick(String position);
    }
}
