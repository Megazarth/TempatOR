package com.yafetsutanto.tempator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText textNama, textEmail, textPassword, textRePass, textTelp;
    private Button btnRegister;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btnRegister.setOnClickListener((v) -> {
            boolean check = true;

            //Pengecekan daftar register tidak bisa kosong
            if (TextUtils.isEmpty(textNama.getText().toString())) {
                textNama.setError("Nama tidak bisa kosong");
                check = false;
            }
            if (TextUtils.isEmpty(textEmail.getText().toString())) {
                textEmail.setError("Email tidak bisa kosong");
                check = false;
            }
            if (TextUtils.isEmpty(textTelp.getText().toString())) {
                textTelp.setError("Nomor HP tidak bisa kosong");
                check = false;
            }

            // Pengecekan password
            if (TextUtils.isEmpty(textPassword.getText().toString())) {
                textPassword.setError("Password tidak bisa kosong");
                check = false;
            }
            if (textPassword.getText().toString().length() < 6) {
                textPassword.setError("Password min. 6 karakter");
            }
            if (TextUtils.isEmpty(textRePass.getText().toString())) {
                textRePass.setError("Isi kembali password anda");
                check = false;
            }
            if (!textRePass.getText().toString().equals(textPassword.getText().toString())) {
                textRePass.setError("Password tidak sama");
                check = false;
            }

            if (check) {
                String email = textEmail.getText().toString();
                String telp = textTelp.getText().toString();
                String nama = textNama.getText().toString();
                String password = textPassword.getText().toString();

                //Memanggil method register
                register(email, telp, nama, password);
            }

        });
    }

    private void init() {
        textNama = findViewById(R.id.textNama);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textRePass = findViewById(R.id.textRePassword);
        btnRegister = findViewById(R.id.btnRegister);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void register(final String email, final String telp, final String nama, final String password) {
        //penggunaan API dari FirebaseAuth
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Jika register berhasil
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    String key = currentUser.getUid();

                    User user = new User(nama, email, password, telp, "pegguna");

                    databaseReference.child("user").child(key).setValue(user);
                    Toast.makeText(RegisterActivity.this, "Register berhasil!", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(login);
                }
                //Register gagal
                else {
                    Toast.makeText(RegisterActivity.this, "Register gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
