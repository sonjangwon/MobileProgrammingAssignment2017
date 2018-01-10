package com.example.jaehyukshin.goolgemaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN=10;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private String name;
    EditText id;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Button loginButton = (Button)findViewById(R.id.loginButton);
        SignInButton googleLogin = (SignInButton)findViewById(R.id.googleLogin);

        loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (id.getText().toString() == "admin" && password.getText().toString() == "admin") {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "일치하지 않습니다.".toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        });

        googleLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                name = account.getDisplayName();
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this,"FireBase아이디 생성 실패",Toast.LENGTH_SHORT).show();
//                        }else{
                            //Toast.makeText(LoginActivity.this,"FireBase아이디 생성 완료",Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                            intent2.putExtra("name",name);
                        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                            startActivity(intent2);
//                        }
                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
