package com.example.dujic.usedcars;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dujic on 12/29/2017.
 */

public class NewCarAd extends AppCompatActivity {

    ImageView imgNew;
    EditText edtYear, edtPrice, edtConsummation, edtKm, edtPower, edtFuel,
            edtDescription, edtTelefon, edtLocation, edtMark, edtModel;
    Button btnAdd, btnRead;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    private ArrayList<Uri> uriImages;

    ProgressBar pbNew;

    StorageReference mStorage; //referenca za upload, download, delete
    DatabaseReference databasePitcures;
    DatabaseReference databaseCarAds;
    FirebaseAuth mAuth;

    private ArrayList<CarAd> carAds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_car);

        mStorage = FirebaseStorage.getInstance().getReference();
        //databasePitcures = FirebaseDatabase.getInstance().getReference("carpitcure"); //getRef: Gets a DatabaseReference for the database root node
        //getInstance: Gets the default FirebaseDatabase instance.
       databaseCarAds = FirebaseDatabase.getInstance().getReference("carad");
        mAuth = FirebaseAuth.getInstance();
        Log.i("Korisnik", mAuth.getCurrentUser().getEmail());//info o trenutnom korisniku
        pbNew = (ProgressBar) findViewById(R.id.pbNew);
        pbNew.setVisibility(View.GONE);

        carAds = new ArrayList<CarAd>();
        uriImages = new ArrayList<Uri>();

        imgNew = (ImageView) findViewById(R.id.imgNew);
        edtMark = (EditText) findViewById(R.id.edtMark);
        edtModel = (EditText) findViewById(R.id.edtModel);
        edtYear = (EditText) findViewById(R.id.edtYear);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtKm = (EditText) findViewById(R.id.edtKm);
        edtConsummation = (EditText) findViewById(R.id.edtConsummation);
        edtFuel = (EditText) findViewById(R.id.edtFuel);
        edtPower = (EditText) findViewById(R.id.edtPower);
        edtDescription = (EditText) findViewById(R.id.edtDescribe);
        edtTelefon = (EditText) findViewById(R.id.edtTelefon);
        edtLocation = (EditText) findViewById(R.id.edtLocation);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        //btnRead = (Button) findViewById(R.id.btnRead);

        Picasso.with(this).load("http://kalaharilifestyle.com/wp-content/uploads/2014/04/placeholder4.png").into(imgNew);

        //getCar();

        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarAd();
            }
        });


    }

    private void addCarAd() {

        if (edtMark.getText().toString().isEmpty()) {
            edtMark.setError("Niste unijeli marku vozila!");
            edtMark.requestFocus();
            return;
        }

        if (edtModel.getText().toString().isEmpty()) {
            edtModel.setError("Niste unijeli model vozila!");
            edtModel.requestFocus();
            return;
        }

        if (edtPrice.getText().toString().isEmpty()) {
            edtPrice.setError("Niste unijeli cijenu vozila!");
            edtPrice.requestFocus();
            return;
        }

        if (edtYear.getText().toString().isEmpty()) {
            edtYear.setError("Niste unijeli godinu vozila!");
            edtYear.requestFocus();
            return;
        }

        if (edtKm.getText().toString().isEmpty()) {
            edtKm.setError("Niste unijeli prijeđene kilometre vozila!");
            edtKm.requestFocus();
            return;
        }

        if (edtConsummation.getText().toString().isEmpty()) {
            edtConsummation.setError("Niste unijeli potrošnju vozila!");
            edtConsummation.requestFocus();
            return;
        }

        if (edtPower.getText().toString().isEmpty()) {
            edtPower.setError("Niste unijeli snagu vozila!");
            edtPower.requestFocus();
            return;
        }

        if (edtFuel.getText().toString().isEmpty()) {
            edtFuel.setError("Niste unijeli vrstu goriva vozila!");
            edtFuel.requestFocus();
            return;
        }

        if (edtTelefon.getText().toString().isEmpty()) {
            edtTelefon.setError("Niste unijeli broj telefona!");
            edtTelefon.requestFocus();
            return;
        }

        if (edtLocation.getText().toString().isEmpty()) {
            edtLocation.setError("Niste unijeli lokaciju oglasa!");
            edtLocation.requestFocus();
            return;
        }

        if (uriImages.size()==0){
            Helper.toastaj(getApplicationContext(), "Niste unijeli nijednu sliku!");
            return;
        }

        //try {

            String mark = String.valueOf(edtMark.getText().toString());
            String model = String.valueOf(edtModel.getText().toString());
            Integer year = Integer.valueOf(edtYear.getText().toString());
            Double price = Double.valueOf(edtPrice.getText().toString());
            Integer km = Integer.valueOf(edtKm.getText().toString());
            Double cons = Double.valueOf(edtConsummation.getText().toString());
            String fuel = String.valueOf(edtFuel.getText().toString());
            Integer power = Integer.valueOf(edtPower.getText().toString());
            String description = String.valueOf(edtDescription.getText().toString());
            String telefon = String.valueOf(edtTelefon.getText().toString());
            String location = String.valueOf(edtLocation.getText().toString());
            String userID = mAuth.getCurrentUser().getEmail();

            //final String id = databaseCarAds.push().getKey();

            pbNew.setVisibility(View.VISIBLE);
            CarAd ad = new CarAd("1", mark, model, price, year, power, cons, km, fuel, description, userID, telefon, location);
            //databaseCarAds.child(id).setValue(ad);


            btnAdd.setEnabled(false);

            DataStorage.addCarAdFirebase(ad, uriImages);




            //databaseCarAds.getDatabase();

            /*for (int i = 0; i < uriImages.size(); i++) {
                String uri = uriImages.get(i).toString();
                CarPitcure carPitcure = new CarPitcure("1", "aa", uri);
                DataStorage.addPitcureToDatabase(carPitcure);
            }*/


            Log.i("Novo", "1");
            //Helper.accessData();
            Handler handler = new Handler();
            //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
                    Helper.toastaj(getApplicationContext(), "Oglas uspješno dodan!");
                    Intent main = new Intent(NewCarAd.this, MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }, 4000);

            //Log.i("Novo", "2");

        //} catch (Exception e) {
          //  Helper.toastaj(getApplicationContext(), "Greška pri unosu");

//        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //Intent gallery = new Intent(Intent.EXTRA_ALLOW_MULTIPLE, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            uriImages.add(imageUri);
            //Helper.toastaj(getApplicationContext(), imageUri.toString());
            imgNew.setImageURI(imageUri);

            /*StorageReference filepath = mStorage.child("Photos").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.i("okej", "Upload image");

                }


            });


            /*filepath.getFile(imageUri).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.i("okej", "Download image");
                }
            });*/


        }
    }


    protected void getCar(){
        databaseCarAds.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                carAds.clear();

                for(DataSnapshot carAd : dataSnapshot.getChildren()){
                    CarAd ad = new CarAd();
                    ad = carAd.getValue(CarAd.class);
                    carAds.add(ad);

                }

                //Helper.toastaj(getApplicationContext(), String.valueOf(carAds.get(0).mark));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Helper.toastaj(getApplicationContext(), "Greška");
            }
        });

        /*databasePitcures.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //carAds.clear();

                for(DataSnapshot img : dataSnapshot.getChildren()){
                    CarPitcure im = new CarPitcure();
                    im = img.getValue(CarPitcure.class);
                    imgNew.setImageURI(Uri.parse(im.image));

                    Helper.toastaj(getApplicationContext(), im.image);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Helper.toastaj(getApplicationContext(), "Greška");
            }
        });*/


        databasePitcures.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //carAds.clear();

                for(DataSnapshot img : dataSnapshot.getChildren()){
                    CarPitcure im = new CarPitcure();
                    im = img.getValue(CarPitcure.class);
                    imgNew.setImageURI(Uri.parse(im.image));

                    Helper.toastaj(getApplicationContext(), im.image);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Helper.toastaj(getApplicationContext(), "Greška");
            }
        });
    }



}
