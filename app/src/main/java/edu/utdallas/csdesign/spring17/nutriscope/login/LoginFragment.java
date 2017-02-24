package edu.utdallas.csdesign.spring17.nutriscope.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.utdallas.csdesign.spring17.nutriscope.R;


public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {

    private EditText userText;
    private EditText passwordText;
    private Button register, login;
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

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        LoginActivity act = (LoginActivity) getActivity();
        presenter = act.loginPresenter;

    }

    @Override
    public void onLoginResponse(boolean isLoggedIn) {
        if (isLoggedIn) {
            createDialog("Login Successful");
        } else {
            createDialog("Login Failed");
        }
    }

    @Override
    public void onRegisterResponse(boolean isRegistered) {
        if (isRegistered) {
            createDialog("Registration Successful");
        } else {
            createDialog("Registration Failed");
        }
    }

    @Override
    public void onClick(View view) {
        if (checkEmpty())
            return;
        switch (view.getId()) {
            case R.id.bLogin:
                presenter.login(userText.getText().toString(), passwordText.getText().toString());
                break;
            case R.id.bRegister:
                presenter.register(userText.getText().toString(), passwordText.getText().toString());
                break;
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

    public void createDialog(String message) {
        userText.setText("");
        passwordText.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setNegativeButton("Continue", null)
                .create().show();
    }
}
