package edu.utdallas.csdesign.spring17.nutriscope.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.R2;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;


public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {

    @BindView(R2.id.email) EditText emailText;
    @BindView(R2.id.password) EditText passwordText;
    @BindView(R2.id.passwordconfirm) EditText confirmPasswordText;
    @BindView(R2.id.goToLogin) Button goToLogin;
    @BindView(R2.id.register_button) Button register;

    private RegisterContract.Presenter presenter;

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

        register.setOnClickListener(this);
        goToLogin.setOnClickListener(this);

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                if (!checkEmpty() && checkPasswords()) {
                    presenter.register(emailText.getText().toString().trim(), passwordText.getText().toString().trim());
                }
                break;
            case R.id.goToLogin:
                startActivity(new Intent(this.getActivity(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRegisterComplete() {
        Intent intent = new Intent(getActivity(), OverviewActivity.class);
        startActivity(intent);
    }


    @Override
    public void onErrorResponse(String error) {
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

    private boolean checkPasswords() {
        if (!passwordText.getText().toString().trim().equals(confirmPasswordText.getText().toString().trim())) {
            onErrorResponse("Passwords do not match");
            return false;
        } else if (passwordText.getText().toString().trim().length() < 8) {
            onErrorResponse("Password must be at least 8 chars.");
            return false;
        }

        return true;
    }

}
