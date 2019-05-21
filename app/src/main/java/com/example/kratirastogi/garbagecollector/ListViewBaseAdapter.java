package com.example.kratirastogi.garbagecollector;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ListViewBaseAdapter extends BaseAdapter {
    private List<ListViewItemDTO> listViewItemDtoList = null;

    private Activity ctx = null;

    public ListViewBaseAdapter(Activity ctx, List<ListViewItemDTO> listViewItemDtoList) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
    }
    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null)
        {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        ListViewItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.custlist, null);



            TextView listItemText = (TextView) convertView.findViewById(R.id.name);
            TextView listItemText1 = (TextView) convertView.findViewById(R.id.add);
            TextView listItemText2 = (TextView) convertView.findViewById(R.id.pay);
            viewHolder = new ListViewItemViewHolder(convertView);

            viewHolder.setItemTextView(listItemText);

            viewHolder.setItemTextView1(listItemText1);
            viewHolder.setItemTextView2(listItemText2);




            convertView.setTag(viewHolder);
        }

        ListViewItemDTO listViewItemDto = listViewItemDtoList.get(itemIndex);



        viewHolder.getItemTextView().setText(listViewItemDto.getName());
        viewHolder.getItemTextView1().setText(listViewItemDto.getAddress());
        viewHolder.getItemTextView2().setText(listViewItemDto.getPay());



        return convertView;
    }
}
