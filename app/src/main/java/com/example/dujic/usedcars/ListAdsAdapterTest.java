package com.example.dujic.usedcars;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Dujic on 12/23/2017.
 */

public class ListAdsAdapterTest extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    public ListAdsAdapterTest(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { /*vraća ukupni broj itema na listi*/
        return DataStorageTest.carTestList.size();
    }

    /*Također ćemo izmjeniti i getView() funkciju. Glavna promjena je u biti već u prvoj liniji. Ovaj
       put ne „inflate“amo svaki put nanovo novi layout, već provjeravamo da li on već postoji. Na
       ovaj način štedimo resurse, odnosno reusamo prethodno popunjeni view.*/

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = mInflater.inflate(R.layout.car_list_single_item_test, parent, false);
        }

        final TextView txtMarkModel = (TextView) view.findViewById(R.id.txtMarkModel);
        final TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        final TextView txtYear = (TextView) view.findViewById(R.id.txtYear);
        final ImageView imgCar = (ImageView) view.findViewById(R.id.imgCar);

        final CarTest car = DataStorageTest.carTestList.get(position);
        txtMarkModel.setText(car.mark + " " + car.model);
        txtPrice.setText(String.valueOf(car.price) + " kn");
        txtYear.setText(String.valueOf(car.year) + ". godina");
        Picasso.with(mContext).load(car.images[0].toString()).into(imgCar);


        return view;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

}
