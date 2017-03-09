package edu.utdallas.csdesign.spring17.nutriscope.login;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import edu.utdallas.csdesign.spring17.nutriscope.OverviewActivity;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.UserManager;
import edu.utdallas.csdesign.spring17.nutriscope.auth.facebook.FacebookAuth;
import edu.utdallas.csdesign.spring17.nutriscope.auth.google.GoogleAuth;
import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication.AUTH_URL;
import static edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity.ACTION_IGNORE_CURRENT_USER;


public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener, SyncUser.Callback {

    private EditText userText;
    private EditText passwordText;
    private View progressView;
    private View loginFormView;
    private Button register, login;
    private FacebookAuth facebookAuth;
    private GoogleAuth googleAuth;
    private LoginContract.Presenter presenter;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        userText = (EditText) view.findViewById(R.id.userText);
        passwordText = (EditText) view.findViewById(R.id.passwordText);
        login = (Button) view.findViewById(R.id.bLogin);
        register = (Button) view.findViewById(R.id.bRegister);
        LoginActivity act = (LoginActivity) getActivity();
        presenter = act.loginPresenter;

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        loginFormView = view.findViewById(R.id.sign_in_form);
        progressView = view.findViewById(R.id.sign_in_progress);

        // Check if we already got a user, if yes, just continue automatically
        if (savedInstanceState == null) {
            if (!ACTION_IGNORE_CURRENT_USER.equals(getActivity().getIntent().getAction())) {
                final SyncUser user = SyncUser.currentUser();
                if (user != null) {
                    onSuccess(user);
                }
            }
        }

        facebookAuth = new FacebookAuth((LoginButton) view.findViewById(R.id.login_button)) {
            @Override
            public void onRegistrationComplete(final LoginResult loginResult) {
                UserManager.setAuthMode(UserManager.AUTH_MODE.FACEBOOK);
                SyncCredentials credentials = SyncCredentials.facebook(loginResult.getAccessToken().getToken());
                SyncUser.loginAsync(credentials, AUTH_URL, LoginFragment.this);
            }
        };

        googleAuth = new GoogleAuth((SignInButton) view.findViewById(R.id.google_sign_in_button), this.getActivity()) {
            @Override
            public void onRegistrationComplete(GoogleSignInResult result) {
                UserManager.setAuthMode(UserManager.AUTH_MODE.GOOGLE);
                GoogleSignInAccount acct = result.getSignInAccount();
                SyncCredentials credentials = SyncCredentials.google(acct.getIdToken());
                SyncUser.loginAsync(credentials, AUTH_URL, LoginFragment.this);
            }

        };


    }

    @Override
    public void onClick(View view) {
        if (checkEmpty())
            return;
        switch (view.getId()) {
            case R.id.bLogin:
                showProgress(true);
                presenter.login(userText.getText().toString(), passwordText.getText().toString());
                break;
            case R.id.bRegister:
                showProgress(true);
                presenter.register(userText.getText().toString(), passwordText.getText().toString());
                break;
        }
    }


    @Override
    public void onSuccess(SyncUser user) {
        showProgress(false);
        UserManager.setActiveUser(user);
        Intent intent = new Intent(getActivity(), OverviewActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuth.onActivityResult(requestCode, resultCode, data);
        facebookAuth.onActivityResult(requestCode, resultCode, data);
    }
    private void showProgress(final boolean show) {
        final int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        loginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
    @Override
    public void onError(ObjectServerError error) {
        showProgress(false);
        String errorMsg;
        switch (error.getErrorCode()) {
            case EXISTING_ACCOUNT:
                errorMsg = "Account already exists";
                break;
            case UNKNOWN_ACCOUNT:
                errorMsg = "Account does not exists";
                break;
            case INVALID_CREDENTIALS:
                errorMsg = "The provided credentials are invalid or a user already exists";
                break;
            default:
                errorMsg = error.toString();
        }
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorInputResponse(Boolean isNotValid) {
        showProgress(false);
        if (isNotValid) {
            String errorMsg = "Login failed, invalid input";
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegisterResponse(Boolean isRegistered) {
        if (isRegistered) {
            showProgress(false);
            String errorMsg = "Registration Successful";
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
        }
    }


    public boolean checkEmpty() {
        if (userText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
            userText.setText("");
            passwordText.setText("");
            return true;
        }
        return false;
    }

}