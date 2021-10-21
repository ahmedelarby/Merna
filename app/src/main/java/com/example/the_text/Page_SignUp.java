package com.example.the_text;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Page_SignUp extends AppCompatActivity {
EditText firstname;
EditText lastname;
EditText email;
EditText password;
EditText gender;
Button Signup;
FirebaseAuth auth =FirebaseAuth.getInstance();
FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page__sign_up);
        firstname=findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.passwordSignUp);
        gender=findViewById(R.id.gender);
        Signup=findViewById(R.id.newemail);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn=firstname.getText().toString().trim();
                String ln=lastname.getText().toString().trim();
                String e=email.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String g=gender.getText().toString().trim();
                if (fn.isEmpty()){firstname.setError("is empty");return;}
                if (ln.isEmpty()){lastname.setError("is empty");return;}
                if (e.isEmpty()){email.setError("is empty");return;}
                if (pass.isEmpty()){password.setError("is empty");return;}
                if (g.isEmpty()){gender.setError("is empty");return;}
                else {
                    auth.createUserWithEmailAndPassword(e,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Map<String, Object> user = new HashMap<>();
                                user.put("firstName", fn);
                                user.put("lastName", ln);
                                user.put("email", e);
                                user.put("password", pass);
                                user.put("gender", g);
// Add a new document with a generated ID
                                firestore.collection("users").document(auth.getUid())
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            cheek();
                                            Intent gotologin=new Intent(Page_SignUp.this,Page_Too.class);
                                            startActivity(gotologin);
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Page_SignUp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Page_SignUp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });








    }
    private void cheek(){
        FirebaseUser user=  auth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Page_SignUp.this, "cheek your email ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Page_SignUp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
