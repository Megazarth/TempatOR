package com.yafetsutanto.tempator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText textEmail, textPassword;
    private Button btnLogin, btnRegister;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener((v) ->{
            boolean check = true;

            if (TextUtils.isEmpty(textEmail.getText().toString())) {
                textEmail.setError("Email tidak bisa kosong!");
                check = false;
            }
            if (TextUtils.isEmpty(textPassword.getText().toString())) {
                textPassword.setError("Password tidak bisa kosong!");
                check = false;
            }
            if (check) {
                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();
            }
        });
    }

    private void init() {
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void log(final String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //LOGIN BERHASIL
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User currentUser = dataSnapshot.getValue(User.class);

                            if (currentUser != null) {
                                Toast.makeText(getBaseContext(), currentUser.getRole(), Toast.LENGTH_LONG).show();
                                if (currentUser.getRole().equalsIgnoreCase("admin")) {
                                    Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Welcome User", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                //LOGIN GAGAL
                else Toast.makeText(LoginActivity.this, "Email atau Password Anda salah!", Toast.LENGTH_SHORT);
            }
        });
    }
}
