package com.example.kratirastogi.garbagecollector;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

class ListViewItemViewHolder extends RecyclerView.ViewHolder{


    private TextView itemTextView;
    private TextView itemTextView1;
    private TextView itemTextView2;






    public ListViewItemViewHolder(View itemView) {
        super(itemView);
    }



    public TextView getItemTextView() {
        return itemTextView;
    }

    public void setItemTextView(TextView itemTextView) {
        this.itemTextView = itemTextView;
    }
    public TextView getItemTextView1() {
        return itemTextView1;
    }

    public void setItemTextView1(TextView itemTextView) {
        this.itemTextView1 = itemTextView;
    }

    public TextView getItemTextView2() {
        return itemTextView2;
    }

    public void setItemTextView2(TextView itemTextView2) {
        this.itemTextView2 = itemTextView2;
    }
}
