package tech.zongogroup.rire_soteh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import butterknife.ButterKnife;

/**
 * Created by user on 23-Jul-17.
 */

public class SignUp extends AppCompatActivity {
    //@BindView(R.id.ueername_signup)
    EditText nameText;
    //@BindView(R.id.useremail_signup)
    EditText emailText;
//    @BindView(R.id.userpwd_signup)
    EditText passText;
    //    @BindView(R.id.userphone_signup)
    EditText phoneText;
    //    @BindView(R.id.signupp_btn)
    Button signBtn;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        nameText = (EditText) findViewById(R.id.ueername_signup);
        emailText = (EditText) findViewById(R.id.useremail_signup);
        passText = (EditText) findViewById(R.id.userpwd_signup);
        phoneText = (EditText) findViewById(R.id.userphone_signup);
        signBtn = (Button) findViewById(R.id.signupp_btn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


    }

    public void SignUpBtn(View v) {
        final String name, email, pass, phone;
        name = nameText.getText().toString();
        email = emailText.getText().toString();
        pass = passText.getText().toString();
        phone = phoneText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).duration(500).playOn(signBtn);
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUp.this, "Welcome New User", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(SignUp.this, HomeActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                auth.fetchProvidersForEmail(email).addOnSuccessListener(new OnSuccessListener<ProviderQueryResult>() {
                    @Override
                    public void onSuccess(ProviderQueryResult providerQueryResult) {
                        Toast.makeText(SignUp.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
                Toast.makeText(SignUp.this, "Check Network Connectivity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SendLogin(View v){
        startActivity(new Intent(this,Login.class));
    }
}
