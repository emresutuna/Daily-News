package com.example.gunlukhaberapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gunlukhaberapp.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String email,emailReg;
    String password,passwordReg;
    Button button,registerClick,loginClick,register,login;
    EditText mail,pass,mailReg,passReg;
    DatabaseReference myRef;
    FirebaseDatabase database;
    RelativeLayout loading;
    RelativeLayout mainScroll,rgstrScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
getSupportActionBar().hide();
        mail=(EditText)findViewById(R.id.etMail);
        mailReg=(EditText)findViewById(R.id.rgstrEmail);
        passReg=(EditText)findViewById(R.id.rgstPassword);
        pass=(EditText)findViewById(R.id.etPassword);
        button=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        registerClick=(Button)findViewById(R.id.chooseRegister);
        rgstrScroll=(RelativeLayout)findViewById(R.id.registerScroll);
        loginClick=(Button)findViewById(R.id.choosingButton);
        loading=(RelativeLayout)findViewById(R.id.loading);
        mainScroll=(RelativeLayout)findViewById(R.id.mainScroll);
        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
registerClick.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mainScroll.setVisibility(View.INVISIBLE);
        rgstrScroll.setVisibility(View.VISIBLE);


    }
});
loginClick.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("Invısıvle","ınsvble");
        mainScroll.setVisibility(View.VISIBLE);
        rgstrScroll.setVisibility(View.INVISIBLE);


    }
});
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        email=mail.getText().toString();
        password=pass.getText().toString();
        mainScroll.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    mainScroll.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Giriş Başarılı", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(LoginActivity.this,NewsActivity.class);
                    startActivity(i);

                } else {
                    mainScroll.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Log.e("Giriş Hatası",task.getException().getMessage());
                }

            }
        });
    }
});

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailReg=mailReg.getText().toString();
                passwordReg=passReg.getText().toString();
                myRef = database.getReference();
                String id=mAuth.getUid();
                Users users= new Users(id,email);
                myRef.child("users").child(mAuth.getUid()).setValue(users);

                rgstrScroll.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        register.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Firebase Error", "Failed to read value.");

                    }
                });
                mAuth.createUserWithEmailAndPassword(emailReg,passwordReg).addOnSuccessListener(LoginActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(),"Kullanıcı Başarıyla Oluşturuldu.",Toast.LENGTH_SHORT).show();
                        mainScroll.setVisibility(View.VISIBLE);
                        rgstrScroll.setVisibility(View.GONE);


                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                rgstrScroll.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Kullanıcı Oluşturma Hatası!",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

}
