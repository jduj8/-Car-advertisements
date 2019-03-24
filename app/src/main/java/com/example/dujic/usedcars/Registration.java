package com.example.dujic.usedcars;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import javax.net.ssl.HandshakeCompletedEvent;

/**
 * Created by Dujic on 12/24/2017.
 */

public class Registration extends AppCompatActivity {

    EditText edtMail, edtPassword, edtPassword2;
    Button btnRegistration;
    CheckBox cbRobot;

    private FirebaseAuth mAuth;

    ProgressBar pbRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        edtMail = (EditText) findViewById(R.id.edtMail);
        edtPassword = (EditText) findViewById(R.id.edtPasswordReg);
        edtPassword2 = (EditText) findViewById(R.id.edtPassword2Reg);
        cbRobot = (CheckBox) findViewById(R.id.cbRobot);

        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        pbRegistration = (ProgressBar) findViewById(R.id.pbRegistration);
        pbRegistration.setVisibility(View.GONE);


        mAuth = FirebaseAuth.getInstance();

        btnRegistration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Helper.toastaj(getApplicationContext(), "pozvan");
                registerUser();
            }
        });
    }

    private void registerUser(){

        String mail = edtMail.getText().toString();
        String password = edtPassword.getText().toString();
        String password2 = edtPassword2.getText().toString();

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


        if (!password.equals(password2)){
            Helper.toastaj(getApplication(), password + " " + password2);
            edtPassword2.setError("Pogrešno ponovljena lozinka!");
            edtPassword2.requestFocus();
            return;
        }

        if (!cbRobot.isChecked()){
            cbRobot.setError("Potvrdite da niste robot!");
            cbRobot.requestFocus();
            return;
        }


        pbRegistration.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pbRegistration.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Helper.toastaj(getApplicationContext(), "Uspješno ste se registrirali!");
                    Intent login = new Intent(getApplicationContext(), Login.class);
                    startActivity(login);
                }
                else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Helper.toastaj(getApplicationContext(), "Taj korisnik već postoji!");
                    }

                    else{
                        Helper.toastaj(getApplicationContext(), "Neuspješna registracija!");
                    }
                }
            }
        });
     }
}
