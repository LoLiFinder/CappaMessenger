package com.cappamessenger.two;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button button;
    private Button button2;
    private EditText editTextmail;
    private EditText password;
    private TextView textView1;
    private TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button2);
        button2 = (Button) findViewById(R.id.button4);
        editTextmail = (EditText) findViewById(R.id.auth_mainsign);
        password = (EditText) findViewById(R.id.password_mainsign);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        textView1 = (TextView) findViewById(R.id.textView6);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                        finish();
                    } else {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Подтвердите почту, повторно выслан запрос на подтверждение.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }


                } else {


                }
                // ...
            }
        };
    }





    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button2:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.button4:
                if (editTextmail.getText().length() > 0 && password.getText().length() > 0) {
                    signFirebase(editTextmail.getText().toString(), password.getText().toString());
                } else {
                    System.out.println("А_Дзе?");
                }
                break;
        }

    }

    public void signFirebase (final String email,final String password) {
        System.out.println(email);
        System.out.println(password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Ошибка: "+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
