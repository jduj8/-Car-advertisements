package com.example.dujic.usedcars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Dujic on 12/23/2017.
 */

public class MyAdapterTest extends BaseAdapter {

    private Context myContext;

    public MyAdapterTest(Context context) {
        this.myContext = context;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return DataStorageTest.listViewData.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = (LayoutInflater)
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = myInflater.inflate(R.layout.car_list_single_item_test, parent, false);
        return itemView;
    }
}
