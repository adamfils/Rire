package tech.zongogroup.rire_soteh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ldoublem.loadingviewlib.view.LVBlazeWood;

import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    //@BindView(R.id.email_login)
    EditText emailText;
    //@BindView(R.id.pwd_login)
    EditText passText;
    //@BindView(R.id.signin_btn)
    Button signin;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    FirebaseUser user;
    LVBlazeWood wood;
    LinearLayout disable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        emailText = (EditText) findViewById(R.id.email_login);
        passText = (EditText) findViewById(R.id.pwd_login);
        signin = (Button) findViewById(R.id.signin_btn);

        wood = (LVBlazeWood) findViewById(R.id.wood_anim);
        disable = (LinearLayout) findViewById(R.id.disable_ui);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }

    public void SignIn(View v){
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email Is Empty", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).duration(500).playOn(signin);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Password Is Empty", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).duration(500).playOn(signin);
            return;
        }
        YoYo.with(Techniques.RubberBand).duration(500).playOn(signin);
        emailText.setEnabled(false);
        passText.setEnabled(false);
        signin.setEnabled(false);
        wood.setVisibility(View.VISIBLE);
        wood.startAnim(2000);

        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Login.this, "Welcome To Rire-Soteh", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(Login.this,HomeActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Could Not Sign In", Toast.LENGTH_SHORT).show();
                disable.setEnabled(true);
                wood.stopAnim();
                wood.setVisibility(View.INVISIBLE);
                emailText.setEnabled(true);
                passText.setEnabled(true);
                signin.setEnabled(true);
            }
        });
    }

    public void ForgotPwd(View v){
        startActivity(new Intent(this,ForgotPwd.class));
    }

}
