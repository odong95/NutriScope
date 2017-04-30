package edu.utdallas.csdesign.spring17.nutriscope.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;
import edu.utdallas.csdesign.spring17.nutriscope.register.RegisterActivity;


public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {


    @BindView(R.id.email)
    EditText emailText;
    @BindView(R.id.password)
    EditText passwordText;
    @BindView(R.id.goToRegister)
    Button goToRegister;
    @BindView(R.id.login_button)
    Button login;
    @BindView(R.id.fb_login)
    LoginButton fbButton;

    private LoginContract.Presenter presenter;
    private ProgressDialog mProgressDialog;

    private CallbackManager callbackManager;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        fbButton.setReadPermissions("email", "public_profile");
        fbButton.setFragment(this);

        login.setOnClickListener(this);
        goToRegister.setOnClickListener(this);
        setupFBLogin();

    }

    @Override
    public void loginSuccessful() {
        hideProgressDialog();
        Intent intent = new Intent(getActivity(), OverviewActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (!checkEmpty()) {
                    showProgressDialog();
                    presenter.login(emailText.getText().toString().trim(), passwordText.getText().toString().trim());
                }
                break;
            case R.id.goToRegister:
                startActivity(new Intent(this.getActivity(), RegisterActivity.class));
                break;
        }
    }

    public void setupFBLogin() {
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showProgressDialog();
                presenter.registerLoginFB(loginResult.getAccessToken());
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
    public void onErrorResponse(String error) {
        hideProgressDialog();
        passwordText.setText("");
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    public boolean checkEmpty() {
        if (TextUtils.isEmpty(emailText.getText().toString().trim()) || TextUtils.isEmpty(passwordText.getText().toString().trim())) {
            onErrorResponse("Please fill out both fields");
            return true;
        }

        return false;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this.getContext());
            mProgressDialog.setMessage("Logging in...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}