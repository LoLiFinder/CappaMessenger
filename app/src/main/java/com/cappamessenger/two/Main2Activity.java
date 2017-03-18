package com.cappamessenger.two;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView mChatRecyclerView;
    private FirebaseUser firebaseUser;
    private String mCurrentUserId;
    private MessageAdapter messageChatAdapter;
    private DatabaseReference messageChatDatabase;
    private ChildEventListener messageChatListener;
    private FirebaseAuth fireBaseAuth;
    private Button buttonSend;
    private EditText editText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        fireBaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ButterKnife.bind(this);
        editText = (EditText) findViewById(R.id.edit_text_message);
        mChatRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat);
        //buttonSend = (Button) findViewById(R.id.btn_send_message);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessagesGroup newMessage = new MessagesGroup(editText.getText().toString(),firebaseUser.getUid(),"123");
                messageChatDatabase.push().setValue(newMessage);
                editText.setText("");
            }
        });
        setDatabaseInstance();
        setUsersId();
        setChatRecyclerView();
        messageChatListener = messageChatDatabase.limitToFirst(5000).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {

                if(dataSnapshot.exists()){
                    MessagesGroup newMessage = dataSnapshot.getValue(MessagesGroup.class);
                    if(newMessage.getSender().equals(mCurrentUserId)){
                        newMessage.setRecipientOrSenderStatus(MessageAdapter.SENDER);
                    }else{
                        newMessage.setRecipientOrSenderStatus(MessageAdapter.RECIPIENT);
                    }
                    messageChatAdapter.refillAdapter(newMessage);
                    mChatRecyclerView.scrollToPosition(messageChatAdapter.getItemCount()-1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }







    private void setDatabaseInstance() {

        messageChatDatabase = FirebaseDatabase.getInstance().getReference().child("TestMes");
    }

    private void setUsersId() {
        mCurrentUserId = firebaseUser.getUid();
    }

    private void setChatRecyclerView() {
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setHasFixedSize(true);
        messageChatAdapter = new MessageAdapter(new ArrayList<MessagesGroup>());
        mChatRecyclerView.setAdapter(messageChatAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();



    }


    @Override
    protected void onStop() {
        super.onStop();

        if(messageChatListener != null) {
            messageChatDatabase.removeEventListener(messageChatListener);
        }
        messageChatAdapter.cleanUp();

    }



}
