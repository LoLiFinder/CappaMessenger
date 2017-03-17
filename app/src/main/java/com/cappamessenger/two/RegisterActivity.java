package com.cappamessenger.two;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends Activity implements View.OnClickListener{


    private Button button;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText editMail;
    private EditText editPassword;
    private EditText editPasswordCheck;
    private EditText editFirstName;
    private EditText editLastName;
    private Spinner spinner;


    private DatabaseReference mReference;
    private FirebaseUser firebaseUser;

    public final String USER_CHILD = "User";
    public final String USER_INFO = "UserInfo";
    public final String USER_INFO_FIRSNAME = "first_Name";
    public final String USER_INFO_LASTNAME = "last_Name";
    public final String USER_INFO_GROUP = "group";
    public final String USER_INFO_STATUS = "status";
    public final String USER_INFO_DSTATUS = "Учащийся";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = (Button) findViewById(R.id.reg_button_start);
        button.setOnClickListener(this);



        editMail = (EditText) findViewById(R.id.reg_mail);
        editPassword = (EditText) findViewById(R.id.reg_password);
        editPasswordCheck = (EditText) findViewById(R.id.reg_password_check);
        editFirstName = (EditText) findViewById(R.id.reg_first_name);
        editLastName = (EditText) findViewById(R.id.reg_last_name);
        spinner = (Spinner) findViewById(R.id.reg_spiner);
        mAuth = FirebaseAuth.getInstance();

        mReference = FirebaseDatabase.getInstance().getReference();



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    // User is signed out

                }
                // ...
            }
        };


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.reg_button_start:
                if (checkEditText()) {
                    registerFirebase(editMail.getText().toString(), editPassword.getText().toString());
                }
                break;

        }
    }


    public void registerFirebase (final String email, final String password) {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("Успешная регистрация");
                                mAuth.getCurrentUser()
                                        .sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            mReference.child(USER_CHILD).child(USER_INFO).child(user.getUid()).setValue(createBaseInfoUser(), new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                    mAuth.signOut();
                                                    System.out.println("Отправка сообщения");
                                                    Intent intent = new Intent(getApplicationContext(), SuccessfulRegister.class);
                                                    intent.putExtra("email", email);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }
                                            });




                                        }
                                    }
                                });
                            }
                        }
                    });

                    System.out.println("Проверка");

                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkEditText(){
        if (editPassword.getText().toString().equals(editPasswordCheck.getText().toString())) {
            if (editPassword.getText().toString().length() >= 8){
                if (editPassword.getText().toString().length() <= 32){
                    if (editFirstName.getText().toString().length()!=0 ){
                        if (editLastName.getText().toString().length() !=0){
                            return true;
                        } else {
                            Toast.makeText(this,"Введите фамилию",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this,"Введите Имя",Toast.LENGTH_LONG).show();

                    }

                }else {
                    Toast.makeText(this,"Пароль должен быть не больше 32 символов",Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(this,"Пороль должен быть не меньше 8 символов",Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(this,"Пароли не совпадают",Toast.LENGTH_LONG).show();
        }

        return false;
    }

    private Object createBaseInfoUser(){

        BaseUserInfo s = new BaseUserInfo(editFirstName.getText().toString()
                                         ,editLastName.getText().toString()
                                         ,spinner.getSelectedItem().toString()
                                         ,USER_INFO_DSTATUS
                                         ,false);
        System.out.println(s.getFirst_Name());
        System.out.println(s.getLast_Name());
        System.out.println(s.getGroup());
        System.out.println(s.getStatus());
        System.out.println(s.getUserLine());

        return s;
    }
}
