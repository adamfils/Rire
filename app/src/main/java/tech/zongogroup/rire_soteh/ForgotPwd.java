package tech.zongogroup.rire_soteh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by user on 23-Jul-17.
 */

public class ForgotPwd extends AppCompatActivity {
    EditText emailText;
    FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);

        auth = FirebaseAuth.getInstance();
        emailText = (EditText) findViewById(R.id.email_forgotpwd);

    }

    public void RESET(View v){
        String emailForgot = emailText.getText().toString().trim();
        if(TextUtils.isEmpty(emailForgot)){
            Toast.makeText(this, "Input Email To Proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.sendPasswordResetEmail(emailForgot).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                startActivity(new Intent(ForgotPwd.this,Login.class));
                for(int i=0;i<=2;i++)
                Toast.makeText(ForgotPwd.this, "Check Your Email For Password Reset Link", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPwd.this, "Could Not Send Email Check Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
