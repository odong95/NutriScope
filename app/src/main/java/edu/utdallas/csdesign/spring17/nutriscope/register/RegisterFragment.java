package edu.utdallas.csdesign.spring17.nutriscope.register;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;


public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button goToLogin, register;
    private LoginButton fbButton;
    private CallbackManager callbackManager;
    private RegisterContract.Presenter presenter;
    public ProgressDialog mProgressDialog;

    public RegisterFragment() {

    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        emailText = (EditText) view.findViewById(R.id.email);
        passwordText = (EditText) view.findViewById(R.id.password);
        confirmPasswordText = (EditText) view.findViewById(R.id.passwordconfirm);
        register = (Button) view.findViewById(R.id.register_button);
        goToLogin = (Button) view.findViewById(R.id.goToLogin);
        fbButton = (LoginButton) view.findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        fbButton.setReadPermissions("email", "public_profile");
        fbButton.setFragment(this);

        register.setOnClickListener(this);
        goToLogin.setOnClickListener(this);

        setupFBLogin();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setDialog(ProgressDialog d)
    {
        this.mProgressDialog = d;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                if(!checkEmpty() && checkPasswords()) {
                    showProgressDialog();
                    presenter.register(emailText.getText().toString().trim(), passwordText.getText().toString().trim());
                }
                break;
            case R.id.goToLogin:
                startActivity(new Intent(this.getActivity(), LoginActivity.class));
                break;
        }
    }

    public void setupFBLogin(){
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showLoadingDialog();
                presenter.handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TAG", "facebook:onError", exception);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        hideProgressDialog();
    }


    @Override
    public void onErrorResponse(String error) {
            hideProgressDialog();
            passwordText.setText("");
            confirmPasswordText.setText("");
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }


    private boolean checkEmpty() {
        if (TextUtils.isEmpty(emailText.getText().toString().trim()) || TextUtils.isEmpty(passwordText.getText().toString().trim())
                || TextUtils.isEmpty(confirmPasswordText.getText().toString().trim())) {
            onErrorResponse("Please fill out all fields");
            return true;
        }

        return false;
    }

    private boolean checkPasswords()
    {
        if(!passwordText.getText().toString().trim().equals(confirmPasswordText.getText().toString().trim()))
        {
            onErrorResponse("Passwords do not match");
            return false;
        }
        else if(passwordText.getText().toString().trim().length() < 8)
        {
            onErrorResponse("Password must be at least 8 chars.");
            return false;
        }

        return true;
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this.getActivity());
            mProgressDialog.setMessage("Registering...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void showLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this.getActivity());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
