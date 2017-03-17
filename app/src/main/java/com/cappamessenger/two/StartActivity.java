package com.cappamessenger.two;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Dkflbc on 14.03.17.
 */

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        } else {
            if (mFirebaseUser.isEmailVerified()) {
                startActivity(new Intent(this, NavigationDrawer.class));
                finish();
            } else {
                mFirebaseAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            return;
            }
        }

    }

