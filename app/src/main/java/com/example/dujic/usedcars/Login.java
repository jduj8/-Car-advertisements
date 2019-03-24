package com.example.dujic.usedcars;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * Created by Dujic on 12/24/2017.
 */

public class Login extends AppCompatActivity {

    ImageView imgRegister;
    Button btnLogin;
    EditText edtMail, edtPassword;

    FirebaseAuth mAuth;
    ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.login);

        imgRegister = (ImageView) findViewById(R.id.imgRegister);
        Picasso.with(this).load("https://static1.squarespace.com/static/561bbe91e4b0beb41675a492/t/5867de023e00be540c4109d8/1483202087637/register+here_button+blue.png").into(imgRegister);

        //Helper.accessData();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtMail = (EditText) findViewById(R.id.edtMailLog);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        pbLogin = (ProgressBar) findViewById(R.id.pbLogin);
        pbLogin.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Helper.toastaj(getApplication(), "pozvan");
                userLogin();
            }
        });

        imgRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registration = new Intent(Login.this, Registration.class);
                startActivity(registration);
            }
        });


    }

    private void userLogin(){

        String mail = edtMail.getText().toString();
        String password = edtPassword.getText().toString();

        if (mail.isEmpty()){
            edtMail.setError("Niste unijeli e-mail!");
            edtMail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            edtMail.setError("Pogrešno unesen e-mail!");
            edtMail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPassword.setError("Niste unijeli lozinku!");
            edtPassword.requestFocus();
            return;
        }

        pbLogin.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pbLogin.setVisibility(View.GONE);
                            Helper.toastaj(getApplicationContext(), "Uspješna prijava!");
                            Intent myAds = new Intent(Login.this, MyAds.class);
                            Log.i("Korisnik", mAuth.getCurrentUser().getEmail());
                            myAds.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//If set, and the activity being launched is already running in the current task, then instead of launching a new instance of that activity, all of the other activities on top of it will be closed and this Intent will be delivered to the (now on top) old activity as a new Intent.
                            startActivity(myAds);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Helper.toastaj(getApplicationContext(), "Neuspješna prijava!");
                            pbLogin.setVisibility(View.GONE);

                        }

                        // ...
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Helper.accessData();
        //DataStorage.ads.clear();
        //DataStorage.filteredAds.clear();
    }

    @Override
    protected void onPause(){
        super.onPause();

    }
}
