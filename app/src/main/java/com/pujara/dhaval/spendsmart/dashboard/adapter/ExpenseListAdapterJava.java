package com.pujara.dhaval.spendsmart.dashboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pujara.dhaval.spendsmart.R;
import com.pujara.dhaval.spendsmart.dashboard.model.PersonalExpenseData;

import java.util.ArrayList;

public class ExpenseListAdapterJava extends RecyclerView.Adapter<ExpenseListAdapterJava.ViewHolder> {
    private onNoteListerner monNoteListener;
    private Context context;

    @NonNull
    @Override
    public ExpenseListAdapterJava.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_personal_expense, parent, false);
        return new ViewHolder(view, monNoteListener);
    }

    private static final String TAG = "RecyclerView";
    private ArrayList<PersonalExpenseData> exampleList;

    public ExpenseListAdapterJava(ArrayList<PersonalExpenseData> mData, onNoteListerner monNoteListerner, Context context) {
        this.exampleList = mData;
        this.monNoteListener = monNoteListerner;
        this.context = context;
        Log.d(TAG, "ExpenseListAdapterJava: " + exampleList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapterJava.ViewHolder viewHolder, int i) {
        viewHolder.descr.setText(exampleList.get(i).getDescription());
        viewHolder.amount.setText("$" + exampleList.get(i).getAmount());
        if (exampleList.get(i).getExpense().equals("Expense")) {
            viewHolder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            viewHolder.amount.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryLight));
        }
        viewHolder.date.setText(exampleList.get(i).getMonth() + "/" + exampleList.get(i).getDay() + "/" + exampleList.get(i).getYear());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, descr, amount;

        onNoteListerner onNoteListerner;

        ViewHolder(@NonNull View itemView, onNoteListerner onNoteListerner) {
            super(itemView);
            date = itemView.findViewById(R.id.date_personal_expense);
            descr = itemView.findViewById(R.id.description_personal_expense);
            amount = itemView.findViewById(R.id.amount_personal_expense);
            this.onNoteListerner = onNoteListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListerner.onNoteClick(getAdapterPosition());
        }
    }

    public interface onNoteListerner {
        void onNoteClick(int position);
    }
}
