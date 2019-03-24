package com.example.dujic.usedcars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Dujic on 12/26/2017.
 */

public class MyAdsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    public MyAdsAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { /*vraća ukupni broj itema na listi*/
        return DataStorage.usercarAdsList.size();
    }

    /*Također ćemo izmjeniti i getView() funkciju. Glavna promjena je u biti već u prvoj liniji. Ovaj
       put ne „inflate“amo svaki put nanovo novi layout, već provjeravamo da li on već postoji. Na
       ovaj način štedimo resurse, odnosno reusamo prethodno popunjeni view.*/

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        if (view == null) {
            view = mInflater.inflate(R.layout.my_car_list_single_item, parent, false);
        }

        final TextView txtMarkModel = (TextView) view.findViewById(R.id.txtMarkModel);
        final TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        final TextView txtYear = (TextView) view.findViewById(R.id.txtYear);
        final ImageView imgCar = (ImageView) view.findViewById(R.id.imgCar);
        final ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        final LinearLayout linear1 = (LinearLayout) view.findViewById(R.id.linear1);
        final LinearLayout linear2 = (LinearLayout) view.findViewById(R.id.linear2);

        Picasso.with(mContext).load("https://png.icons8.com/metro/540//delete.png").into(imgDelete);
        final CarAd car = DataStorage.usercarAdsList.get(position);
        Log.i("aaaaaa", String.valueOf(DataStorage.usercarAdsList.size()));
        txtMarkModel.setText(car.mark + " " + car.model);
        txtPrice.setText(String.valueOf(car.price) + " kn");
        txtYear.setText(String.valueOf(car.year) + ". godina");

        String image = DataStorage.pitcureOfCar(car.carAdID);
        Picasso.with(mContext).load(image).into(imgCar);

        imgDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Log.i("velicina", String.valueOf(DataStorage.ads.size()+ " " + String.valueOf(DataStorage.usercarAdsList.size())));
                //DataStorage.usercarAdsList.remove(position);
                //DataStorage.ads.clear();
                //parent.setBackgroundColor(Color.RED);
                DataStorage.deleteAd(car.carAdID);
                //DataStorage.userAds(Helper.trenutni);
                linear1.setBackgroundColor(Color.RED);
                linear2.setBackgroundColor(Color.RED);

                //Log.i("velicina", String.valueOf(DataStorage.ads.size()+ " " + String.valueOf(DataStorage.usercarAdsList.size())));
                //MyAdsAdapter.this.notifyDataSetChanged();

                //Intent main = new Intent(mContext.getApplicationContext(), MyAds.class);
                //mContext.startActivity(main);
            }
        });


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
