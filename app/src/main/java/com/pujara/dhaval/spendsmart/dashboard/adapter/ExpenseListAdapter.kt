package com.pujara.dhaval.spendsmart.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.model.PersonalExpenseData
import kotlinx.android.synthetic.main.recycler_view_personal_expense.view.*

class ExpenseListAdapter(
    private var expenseList: MutableList<PersonalExpenseData>,
    val context: Context
) : RecyclerView.Adapter<ExpenseListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.recycler_view_personal_expense, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.description_personal_expense.text = expenseList[position].description
        holder.view.amount_personal_expense.text = expenseList[position].amount
        if(expenseList[position].expense == "Expense"){
            holder.view.amount_personal_expense.setTextColor(ContextCompat.getColor(context, R.color.red))
        }else{
            holder.view.amount_personal_expense.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        }
        holder.view.date_personal_expense.text = expenseList[position].month + "/" + expenseList[position].day + "/" + expenseList[position].year


    }
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }

}