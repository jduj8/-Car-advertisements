package com.example.dujic.usedcars;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Dujic on 12/27/2017.
 */

public class ListAdsAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap bmp;
    private LayoutInflater mInflater;
    private StorageReference mStorageRef;
    private File localFile;
    public ListAdsAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { /*vraća ukupni broj itema na listi*/
        return DataStorage.filteredAds.size();
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

        final CarAd car = DataStorage.filteredAds.get(position);

        txtMarkModel.setText(car.mark + " " + car.model);
        txtPrice.setText(String.valueOf(car.price) + " kn");
        txtYear.setText(String.valueOf(car.year) + ". godina");

        String image = DataStorage.pitcureOfCar(car.carAdID);
        if (image!=null){
            Log.i("Slika", image);
        }
        else
        {
            Log.i("Slika", "-");
        }
        Picasso.with(mContext).load(image).into(imgCar);

            //CarPitcure carPitcure = DataStorage.returnPitcureOfCarFirebase(car.carAdID);
            //StorageReference riversRef = mStorageRef.child(path);
            //Uri uri = Uri.parse("content://media/external/images/media/7509");
            //String[] slike = carPitcure.image.split("/");
            //Log.i("okej", slike[slike.length-1]);
            //String path = "Photos/" + slike[slike.length-1];
            //mStorageRef = FirebaseStorage.getInstance().getReference();
            /*
            try {
                localFile = File.createTempFile("Photos", "jpg");
                riversRef.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Successfully downloaded data to local file
                                // ...
                                bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                        Log.i("GetPitcure", "Error");
                    }

                });
                imgCar.setImageBitmap(bmp);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.with(mContext).load(uri).into(imgCar);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });*/

                //download file as a byte array
                /*storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imgCar.setImageBitmap(bitmap);
                        Log.i("okej", "jejejeje");
                    }
                });*/

            /*} catch (IOException e) {
                e.printStackTrace();
            }*/


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
