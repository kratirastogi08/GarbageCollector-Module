package com.example.kratirastogi.garbagecollector;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
EditText email,pass;
Button login,forgotpass;
String e,p;
FirebaseAuth auth;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.mipmap.garbi).into(imageView);
        ImageView t = findViewById(R.id.imageView9);
        t.setBackgroundColor(Color.argb(50, 255, 140, 0));
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        login=findViewById(R.id.login);
        forgotpass=findViewById(R.id.forgotpass);
        auth=FirebaseAuth.getInstance();
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPasswordActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            e=email.getText().toString();
            p=pass.getText().toString();
            if(TextUtils.isEmpty(e))
            {
                email.setError("Enter email");
            }
            else
                if(TextUtils.isEmpty(p))
                {
                    pass.setError("Enter password");
                }
            else {

                    auth.signInWithEmailAndPassword(e, p)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
