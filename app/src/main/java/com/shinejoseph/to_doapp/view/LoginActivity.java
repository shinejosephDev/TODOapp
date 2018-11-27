package com.shinejoseph.to_doapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.shinejoseph.to_doapp.R;
import com.shinejoseph.to_doapp.utils.Preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();
    private static final int RC_SIGN_IN = 11000;
    private GoogleSignInClient mGoogleSignInClient;

    private Preferences mPref;

    private ProgressBar mPbProgress;
    private LinearLayout mLlLogin;
    private Toolbar mToolbar;
    private SignInButton mSignInButton;
    private Button mBtnGuest;
    private EditText mEtGuestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        init();
    }

    private void bindViews() {
        mPbProgress = findViewById(R.id.progressBar);
        mLlLogin = findViewById(R.id.ll_login);
        mToolbar = findViewById(R.id.toolbar);
        mSignInButton = findViewById(R.id.sign_in_button);
        mBtnGuest = findViewById(R.id.btn_guest);
        mEtGuestName = findViewById(R.id.et_name);
    }

    private void init() {
        mPref = new Preferences(this);
        setSupportActionBar(mToolbar);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setOnClickListener(v -> signIn());

        mBtnGuest.setOnClickListener(v -> signInGuest());
    }

    private void signInGuest() {
        if (mEtGuestName.getText().toString().equals("")) {
            Snackbar.make(findViewById(R.id.base), getString(R.string.enter_name), Snackbar.LENGTH_LONG)
                    .show();
            return;
        }
        updateData(mEtGuestName.getText().toString());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPref.getName().equals("")) {
            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            updateUI(account);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            updateData(account.getDisplayName());
        } else {
            mLlLogin.setVisibility(View.VISIBLE);
            mPbProgress.setVisibility(View.GONE);
        }
    }

    private void updateData(String name) {
        mPref.setName(name);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

}
