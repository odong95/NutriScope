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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.register.RegisterActivity;


public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener{

    private EditText emailText;
    private EditText passwordText;
    private Button goToRegister, login;
    private LoginButton fbButton;
    private LoginContract.Presenter presenter;
    private ProgressDialog mProgressDialog;

    private CallbackManager callbackManager;
    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        emailText = (EditText) view.findViewById(R.id.email);
        passwordText = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.login_button);
        goToRegister = (Button) view.findViewById(R.id.goToRegister);
        fbButton = (LoginButton) view.findViewById(R.id.fb_login);
        callbackManager = CallbackManager.Factory.create();
        fbButton.setReadPermissions("email", "public_profile");
        fbButton.setFragment(this);

        login.setOnClickListener(this);
        goToRegister.setOnClickListener(this);
        setupFBLogin();

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

        this.presenter = presenter;
    }

    public void setDialog(ProgressDialog d)
    {
        this.mProgressDialog = d;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if(!checkEmpty()) {
                    showProgressDialog();
                    presenter.login(emailText.getText().toString().trim(), passwordText.getText().toString().trim());
                }
                break;
            case R.id.goToRegister:
                startActivity(new Intent(this.getActivity(), RegisterActivity.class));
                break;
        }
    }

    public void setupFBLogin(){
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showProgressDialog();
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