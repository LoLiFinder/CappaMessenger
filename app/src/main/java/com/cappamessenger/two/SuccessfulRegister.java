package com.cappamessenger.two;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class SuccessfulRegister extends AppCompatActivity {

    private TextView emailUserThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_register);
        System.out.println("Вход в успешную регистрацию");
        emailUserThis = (TextView) findViewById(R.id.textView4);
        Intent intent = getIntent();
        String s = intent.getStringExtra("email");
        emailUserThis.setText(emailUserThis.getText()+ " " + s);
        System.out.println("Установка текста");


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        return true;
    }
}
