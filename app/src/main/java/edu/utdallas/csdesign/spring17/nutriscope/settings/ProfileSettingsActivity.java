package edu.utdallas.csdesign.spring17.nutriscope.settings;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;


public class ProfileSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button changeEmailB;
    private Button changePasswordB;
    private Button deleteAccountB;
    private Button backB;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        setTitle("Profile Settings");

        changeEmailB = (Button) findViewById(R.id.change_email_button);
        changePasswordB = (Button) findViewById(R.id.change_password_button);
        deleteAccountB = (Button) findViewById(R.id.delete_account_button);
        backB = (Button) findViewById(R.id.go_back_button);

        changeEmailB.setOnClickListener(this);
        changePasswordB.setOnClickListener(this);
        deleteAccountB.setOnClickListener(this);
        backB.setOnClickListener(this);

        if (isFBLoggedIn()) {
            changeEmailB.setVisibility(View.GONE);
            changePasswordB.setVisibility(View.GONE);
        }
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(ProfileSettingsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.change_email_button:
                handleChangeEmail();
                break;
            case R.id.change_password_button:
                handleChangePassword();
                break;
            case R.id.delete_account_button:
                handleDeleteAccount();
                break;
            case R.id.go_back_button:
                startActivity(new Intent(this, OverviewActivity.class));
                finish();
                break;
        }
    }

    private void handleChangeEmail() {
        LayoutInflater layoutInflater = LayoutInflater.from(ProfileSettingsActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingsActivity.this);
        builder.setTitle("Change Email");
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("Enter New Email Address:");
        final EditText p1 = (EditText) promptView.findViewById(R.id.edittext_input_dialog);
        final EditText p2 = (EditText) promptView.findViewById(R.id.edittext_input_dialog2);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(p2.getText().toString().trim())) {
                    showLoadingDialog();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), p2.getText().toString().trim());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "reAuthentication:failed", task.getException());
                                onErrorResponse("Error, please check password and try again");
                            } else {
                                Log.w("AUTH", "reAuthentication:success - " + auth.getCurrentUser().getEmail());
                                showLoadingDialog();
                                user.updateEmail(p1.getText().toString().trim())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ProfileSettingsActivity.this, "Email updated, please login again", Toast.LENGTH_LONG).show();
                                                    auth.signOut();
                                                } else {
                                                    Log.w("AUTH", "emailUpdate:failed", task.getException());
                                                    Toast.makeText(ProfileSettingsActivity.this, "Failed to update email, please try again", Toast.LENGTH_LONG).show();
                                                }
                                                hideProgressDialog();

                                            }
                                        });

                            }
                            hideProgressDialog();
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void handleChangePassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(ProfileSettingsActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingsActivity.this);
        builder.setTitle("Change Password");
        builder.setView(promptView);

        final EditText p0 = (EditText) promptView.findViewById(R.id.edittext_input_dialog1);
        final EditText p1 = (EditText) promptView.findViewById(R.id.edittext_input_dialog2);
        final EditText p2 = (EditText) promptView.findViewById(R.id.edittext_input_dialog3);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(p0.getText().toString().trim())) {
                    showLoadingDialog();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), p0.getText().toString().trim());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "reAuthentication:failed", task.getException());
                                onErrorResponse("Error, please check password and try again");
                            } else {
                                Log.w("AUTH", "reAuthentication:success - " + auth.getCurrentUser().getEmail());
                                if (arePasswordsGood(p1.getText().toString().trim(), p2.getText().toString().trim())) {
                                    showLoadingDialog();
                                    user.updatePassword(p1.getText().toString().trim())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ProfileSettingsActivity.this, "Password updated, please login again", Toast.LENGTH_LONG).show();
                                                        auth.signOut();
                                                    } else {
                                                        Log.w("AUTH", "passwordUpdate:failed", task.getException());
                                                        Toast.makeText(ProfileSettingsActivity.this, "Failed to update password, please try again", Toast.LENGTH_LONG).show();
                                                    }
                                                    hideProgressDialog();

                                                }
                                            });
                                }
                            }
                            hideProgressDialog();
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void handleDeleteAccount() {
        LayoutInflater layoutInflater = LayoutInflater.from(ProfileSettingsActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingsActivity.this);
        builder.setTitle("Delete Account?");
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("Warning this cannot be undone.");
        title.setPadding(0, 0, 0, 18);
        final EditText p1 = (EditText) promptView.findViewById(R.id.edittext_input_dialog);
        p1.setVisibility(promptView.GONE);
        final EditText p2 = (EditText) promptView.findViewById(R.id.edittext_input_dialog2);

        if (isFBLoggedIn()) {
            TextView t2 = (TextView) promptView.findViewById(R.id.input_dialog_text_msg2);
            t2.setVisibility(promptView.GONE);
            p2.setVisibility(promptView.GONE);
        }
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isFBLoggedIn()) {
                    if (!TextUtils.isEmpty(p2.getText().toString().trim())) {
                        showLoadingDialog();
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), p2.getText().toString().trim());
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("AUTH", "reAuthentication:failed", task.getException());
                                    onErrorResponse("Error, please check password and try again");
                                } else {
                                    Log.w("AUTH", "reAuthentication:success - " + auth.getCurrentUser().getEmail());
                                    showLoadingDialog();
                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ProfileSettingsActivity.this, "Account deleted.", Toast.LENGTH_LONG).show();
                                                        auth.signOut();
                                                    } else {
                                                        Log.w("AUTH", "accountDelete:failed", task.getException());
                                                        Toast.makeText(ProfileSettingsActivity.this, "Failed to delete account, please try again", Toast.LENGTH_LONG).show();
                                                    }
                                                    hideProgressDialog();

                                                }
                                            });

                                }
                                hideProgressDialog();
                            }
                        });
                    }
                } else {
                    showLoadingDialog();
                    handleFacebookAccessToken(AccessToken.getCurrentAccessToken());

                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    private boolean arePasswordsGood(final String p1, final String p2) {
        if (TextUtils.isEmpty(p1) && TextUtils.isEmpty(p2)) {
            return false;
        } else if (!p1.equals(p2)) {
            onErrorResponse("Passwords do not match, please try again");
            return false;
        } else if ((p1.length() < 8)) {
            onErrorResponse("Password must be at least 8 chars.");
            return false;
        }

        return true;
    }

    public void onErrorResponse(String error) {
        hideProgressDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void showLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
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

    public boolean isFBLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredentialFailed", task.getException());
                        } else {
                            Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());

                            showLoadingDialog();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProfileSettingsActivity.this, "Account deleted.", Toast.LENGTH_LONG).show();
                                                auth.signOut();
                                                LoginManager.getInstance().logOut();
                                            } else {
                                                Log.w("AUTH", "accountDelete:failed", task.getException());
                                                Toast.makeText(ProfileSettingsActivity.this, "Failed to delete account, please try again", Toast.LENGTH_LONG).show();
                                            }
                                            hideProgressDialog();

                                        }
                                    });
                            hideProgressDialog();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
