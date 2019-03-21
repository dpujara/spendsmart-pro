package com.pujara.dhaval.spendsmart.dashboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pujara.dhaval.spendsmart.R;
import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance;

import java.util.ArrayList;

public class FriendListAdapterGroups extends RecyclerView.Adapter<FriendListAdapterGroups.ViewHolder> {
    private onFriendListerner monFriendListerner;
    private Context context;
    private static final String TAG = "RecyclerView";
    private ArrayList<FriendBalance> exampleList;
    private ArrayList<String> selected;
    @NonNull
    @Override
    public FriendListAdapterGroups.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_friend_list_group, parent, false);
        return new ViewHolder(view, monFriendListerner);
    }
    public FriendListAdapterGroups(ArrayList<FriendBalance> mData, onFriendListerner monFriendListerner, Context context) {
        this.exampleList = mData;
        this.monFriendListerner = monFriendListerner;
        this.context = context;
        this.selected = new ArrayList<>();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FriendListAdapterGroups.ViewHolder viewHolder, int i) {
        String email = exampleList.get(i).getEmail();
        viewHolder.friend_name.setText(exampleList.get(i).getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected.contains(email)){
                    selected.remove(email);
                    unhighlightView(viewHolder);
                }else {
                    selected.add(email);
                    highlightView(viewHolder);
                }
                monFriendListerner.onFriendClick(i);
            }
        });
        if (selected.contains(email))
            highlightView(viewHolder);
        else
            unhighlightView(viewHolder);
    }

    private void highlightView(FriendListAdapterGroups.ViewHolder holder) {
        holder.friend_settle_up.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.friend_settle_up.setBackground(context.getDrawable(R.drawable.linear_layout_pressed));
    }

    private void unhighlightView(FriendListAdapterGroups.ViewHolder holder) {
        holder.friend_settle_up.setBackground(context.getDrawable(R.drawable.linear_layout_drawable));
        holder.friend_settle_up.setTextColor(context.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        onFriendListerner onFriendListerner;
        TextView textView3,friend_name,friend_settle_up;
        ConstraintLayout recycler_view_friend_list_rootlayout;
        ViewHolder(@NonNull View itemView, onFriendListerner onFriendListerner) {
            super(itemView);
            recycler_view_friend_list_rootlayout = itemView.findViewById(R.id.recycler_view_friend_list_rootlayout);
            textView3 = itemView.findViewById(R.id.textView3);
            friend_name = itemView.findViewById(R.id.friend_name);
            friend_settle_up = itemView.findViewById(R.id.friend_settle_up);
            this.onFriendListerner = onFriendListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFriendListerner.onFriendClick(getAdapterPosition());
        }
    }

    public interface onFriendListerner {
        void onFriendClick(int position);
    }
}
