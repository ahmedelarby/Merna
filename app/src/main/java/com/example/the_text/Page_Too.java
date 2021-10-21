package com.example.the_text;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Page_Too extends AppCompatActivity {
EditText email;
EditText password;
Button login;
TextView create;
TextView forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page__too);
        create=findViewById(R.id.SignUp);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpgoto= new Intent(Page_Too.this,Page_SignUp.class);
                startActivity(SignUpgoto);

            }
        });
    }
}
