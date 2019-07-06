package com.andychylde.greencorner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailText, passwordText;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailTextView);
        passwordText = findViewById(R.id.passwordTextView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {
        //Process.killProcess(Process.myPid());
        this.finishAffinity();
    }

    public void login(String email, String password)
    {

        if(emailText.getText().toString().equals("") ||
                passwordText.getText().toString().equals(""))
        {
            Toast.makeText(SignInActivity.this, "Enter username and password.",
                    Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent myIntent = new Intent(SignInActivity.this, MainActivity.class);
                                SignInActivity.this.startActivity(myIntent);
                                progressDialog.dismiss();

                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.dismiss();
                            }

                            // ...
                        }
                    });
        }

    }

    public void onClickSignIn(View v)
    {
        switch(v.getId())
        {
            case R.id.signinButton:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing in");
                progressDialog.setCancelable(false);
                progressDialog.show();
                login(emailText.getText().toString(), passwordText.getText().toString());
                break;

        }

    }
}

