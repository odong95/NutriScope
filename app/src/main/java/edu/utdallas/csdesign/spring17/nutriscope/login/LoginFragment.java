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



import edu.utdallas.csdesign.spring17.nutriscope.R;




public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener{

    private EditText userText;
    private EditText passwordText;
    private View progressView;
    private View loginFormView;
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
        LoginActivity act = (LoginActivity) getActivity();
        presenter = act.loginPresenter;

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        loginFormView = view.findViewById(R.id.sign_in_form);
        progressView = view.findViewById(R.id.sign_in_progress);

        // Check if we already got a user, if yes, just continue automatically
        if (savedInstanceState == null) {

        }




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