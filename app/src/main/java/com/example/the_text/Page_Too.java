package com.example.the_text;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Page_Too extends AppCompatActivity {
EditText email;
EditText password;
Button login;
TextView create;
TextView forgetPassword;
FirebaseAuth auth =FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page__too);
        login=findViewById(R.id.login);
        create=findViewById(R.id.SignUp);
        email=findViewById(R.id.edittextemaillogin);
        password=findViewById(R.id.edittextpassword);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpgoto= new Intent(Page_Too.this,Page_SignUp.class);
                startActivity(SignUpgoto);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString().trim();
                String p =password.getText().toString().trim();
                if (e.isEmpty()&&p.isEmpty()){
                    Toast.makeText(Page_Too.this, "is empty", Toast.LENGTH_SHORT).show();
               return;
                }else {
                    auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                cheek();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Page_Too.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void cheek(){
        FirebaseUser user=  auth.getCurrentUser();
        if (user.isEmailVerified()){
            Intent intent= new Intent(Page_Too.this,Pagr_Three.class);
            startActivity(intent);
            finish();

        }else {
            Toast.makeText(this, "Go to cheek email", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");

            startActivity(intent);
            finish();
        }

    }
}
