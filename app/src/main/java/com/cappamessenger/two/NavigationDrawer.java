package com.cappamessenger.two;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public int numMess = -1;
    public final String START_COUNT = "startCount";
    public final String USER_CHILD = "User";
    public final String USER_INFO = "UserInfo";
    public final String USER_INFO_FIRSNAME = "first_Name";
    public final String USER_INFO_LASTNAME = "last_Name";
    public final String USER_INFO_GROUP = "group";
    public final String USER_INFO_STATUS = "status";

    private String userGroup;
    private MessagesGroup messagesGroup;
    private Button sendMessege;
    private EditText textMessege;
    private ImageView avatarUserDrawer;
    private TextView nameUserDrawer;
    private TextView mailUserDrawer;
    private TextView groupUserDrawer;
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser user;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        textMessege = (EditText)findViewById(R.id.msgEditText);
        messagesGroup = new MessagesGroup();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.nav_recycle_messages);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);


        avatarUserDrawer = (ImageView) headerView.findViewById(R.id.imageView);
        nameUserDrawer = (TextView) headerView.findViewById(R.id.nav_head_name_user);
        mailUserDrawer = (TextView) headerView.findViewById(R.id.nav_head_mail_user);





        navigationDrawerSetInfoUser();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        sendMessege = (Button)findViewById(R.id.sendButton);
        sendMessege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textMessege.getText().length() >0){
                    numMess = numMess++;
                    String numMessStr = Integer.toString(numMess);
                    System.out.println (numMessStr);
                    messagesGroup.setMessagesGroup(textMessege.getText().toString());
                    mRef.child("Groups").child(userGroup).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            System.out.println("Выход");
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void signOutUser(){

        System.out.println("Вход в метод выхода");
    }

    private  void navigationDrawerSetInfoUser(){

        mRef.child(USER_CHILD).child(USER_INFO).child(user.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameUserDrawer.setText(dataSnapshot.child(USER_INFO_FIRSNAME).getValue()+" "+dataSnapshot.child(USER_INFO_LASTNAME).getValue()
                + "    "+dataSnapshot.child(USER_INFO_GROUP).getValue());
                mailUserDrawer.setText(user.getEmail());
                messagesGroup.setName(nameUserDrawer.getText().toString());
                userGroup = dataSnapshot.child(USER_INFO_GROUP).getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
