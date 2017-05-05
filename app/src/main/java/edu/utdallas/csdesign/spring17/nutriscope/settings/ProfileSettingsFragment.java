package edu.utdallas.csdesign.spring17.nutriscope.settings;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.R2;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.NullUser;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;
import edu.utdallas.csdesign.spring17.nutriscope.settings.dialogs.SettingsDialogFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 4/29/17.
 */

public class ProfileSettingsFragment extends Fragment implements ProfileSettingsContract.View,
                                                SettingsDialogFragment.SettingsDialogListener {

    private static final String TAG = "settings fragment";

    private ProfileSettingsContract.Presenter presenter;

    @BindView(R2.id.toolbar) Toolbar toolbar;

    private ProgressDialog mProgressDialog;

    public ProfileSettingsFragment() {

    }

    public static ProfileSettingsFragment newInstance() {
        return new ProfileSettingsFragment();
    }

    @Override
    public void setPresenter(ProfileSettingsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings);

        return view;
    }




    @Override
    public void OnDialogSetBundle(Bundle bundle) {
        String type = bundle.getString(SettingsDialogFragment.SETTINGS_TYPE);

        try {
            switch (type) {
                case SettingsDialogFragment.NICKNAME:
                    final String msg = bundle.getString(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setNickname(msg);
                            presenter.modifyUser(user);

                        }
                    });

                    break;
                case SettingsDialogFragment.CALORIE_GOAL:
                    final int calorieGoal = bundle.getInt(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setCalorieGoal(calorieGoal);
                            presenter.modifyUser(user);

                        }
                    });
                    break;
                case SettingsDialogFragment.SEX:
                    final String sex_ = bundle.getString(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setSex(sex_);
                            presenter.modifyUser(user);

                        }
                    });
                    break;
                case SettingsDialogFragment.AGE:
                    final String age_ = bundle.getString(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setAge(age_);
                            presenter.modifyUser(user);

                        }
                    });
                    break;
                case SettingsDialogFragment.WEIGHT:
                    final String weight_ = bundle.getString(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setWeight(weight_);
                            presenter.modifyUser(user);

                        }
                    });
                    break;
                case SettingsDialogFragment.HEIGHT:
                    final String height_ = bundle.getString(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setHeight(height_);
                            String[] h = height_.split(" ");
                            user.setHft(h[0]);
                            user.setHin(h[1]);
                            presenter.modifyUser(user);

                        }
                    });
                    break;
                case SettingsDialogFragment.ACTIVITY_LEVEL:
                    final String activity_level_ = bundle.getString(SettingsDialogFragment.SETTINGS_MSG);
                    presenter.getUser(new ProfileSettingsContract.RetrieveUser() {
                        @Override
                        public void getUser(User user) {
                            user.setActivityLevel(activity_level_);
                            presenter.modifyUser(user);

                        }
                    });
                    break;
            }


        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    private User user = new NullUser();
    @Override
    public void populateUser(User user) {
        this.user = user;
        setNickname(user.getNickname());
        setCalorieGoal(user.getCalorieGoal());
        setSex(user.getSex());
        setAge(user.getAge());
        setWeight(user.getWeight());
        setHeight(user.getHft() + "ft " + user.getHin() + "in");
        setActivityLevel(User.parseLvl(user.getActivityLevel()));
    }

    @OnClick(R2.id.nickname)
    void changeNickname() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.NICKNAME);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_nickname) TextView nickName;
    void setNickname(String nickname) {
        this.nickName.setText(nickname);

    }

    @OnClick(R2.id.calorie_goal)
    void changeCalorieGoal() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.CALORIE_GOAL);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_calorie_goal) TextView calorieGoal;
    void setCalorieGoal(int calorieGoal) {
        this.calorieGoal.setText(String.valueOf(calorieGoal));

    }

    @OnClick(R2.id.sex)
    void changeSex() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.SEX);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_sex) TextView sex;
    void setSex(String sex) {
        Log.d(TAG, "set sex " + sex);
        this.sex.setText(sex);
    }

    @OnClick(R2.id.age)
    void changeAge() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.AGE);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_age) TextView age;
    void setAge(String age) {
        this.age.setText(age);

    }

    @OnClick(R2.id.weight)
    void changeWeight() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.WEIGHT);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_weight) TextView weight;
    void setWeight(String weight) {
        this.weight.setText(weight);

    }

    @OnClick(R2.id.height)
    void changeHeight() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.HEIGHT);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_height) TextView height;
    void setHeight(String height) {
        this.height.setText(height);

    }

    @OnClick(R2.id.activity_level)
    void changeActivityLevel() {
        Bundle bundle = new Bundle();
        bundle.putString(SettingsDialogFragment.SETTINGS_TYPE, SettingsDialogFragment.ACTIVITY_LEVEL);
        SettingsDialogFragment dialog = SettingsDialogFragment.newInstance(bundle);
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @BindView(R2.id.text_activity_level) TextView activityLevel;
    void setActivityLevel(String activityLevel) {
        this.activityLevel.setText(activityLevel);

    }

    @OnClick(R2.id.calculate_tdee)
    void calculateTdee(){

        if (user instanceof NullUser) {
            onErrorResponse("No User Data");
        }
        try {
            user.setCalorieGoal((int) (TdeeCalculator.getTdee(user.getHft(), user.getHin(), user.getAge(), user.getWeight(), user.getActivityLevel(), user.getSex())));
            presenter.modifyUser(user);
        } catch (IllegalArgumentException ex) {
            onErrorResponse("Height, Age, Weight, Activity Level and Sex must be entered.");
            Log.w(TAG, ex.toString());
        }

    }


    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseUser authuser = auth.getCurrentUser();

    @OnClick(R2.id.change_email_button)
    void handleChangeEmail() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    authuser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "reAuthentication:failed", task.getException());
                                onErrorResponse("Error, please check password and try again");
                            } else {
                                Log.w("AUTH", "reAuthentication:success - " + auth.getCurrentUser().getEmail());
                                showLoadingDialog();
                                authuser.updateEmail(p1.getText().toString().trim())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    db.child(user.getUid()).child("email").setValue(p1.getText().toString().trim());
                                                    Toast.makeText(getActivity(), "Email updated, please login again", Toast.LENGTH_LONG).show();
                                                    auth.signOut();
                                                    goToLogin();
                                                } else {
                                                    Log.w("AUTH", "emailUpdate:failed", task.getException());
                                                    Toast.makeText(getActivity(), "Failed to update email, please try again", Toast.LENGTH_LONG).show();
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

    @OnClick(R2.id.change_password_button)
    void handleChangePassword() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    authuser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.w("AUTH", "reAuthentication:failed", task.getException());
                                onErrorResponse("Error, please check password and try again");
                            } else {
                                Log.w("AUTH", "reAuthentication:success - " + auth.getCurrentUser().getEmail());
                                if (arePasswordsGood(p1.getText().toString().trim(), p2.getText().toString().trim())) {
                                    showLoadingDialog();
                                    authuser.updatePassword(p1.getText().toString().trim())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "Password updated, please login again", Toast.LENGTH_LONG).show();
                                                        auth.signOut();
                                                        goToLogin();
                                                    } else {
                                                        Log.w("AUTH", "passwordUpdate:failed", task.getException());
                                                        Toast.makeText(getActivity(), "Failed to update password, please try again", Toast.LENGTH_LONG).show();
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

    @OnClick(R2.id.delete_account_button)
    void handleDeleteAccount() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Account?");
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("WARNING. This cannot be undone.");
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
                        authuser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("AUTH", "reAuthentication:failed", task.getException());
                                    onErrorResponse("Error, please check password and try again");
                                } else {
                                    Log.w("AUTH", "reAuthentication:success - " + auth.getCurrentUser().getEmail());
                                    showLoadingDialog();
                                    final String uid = user.getUid();
                                    db.child(uid).removeValue();
                                    authuser.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "Account deleted.", Toast.LENGTH_LONG).show();
                                                        auth.signOut();
                                                        goToLogin();
                                                    } else {
                                                        Log.w("AUTH", "accountDelete:failed", task.getException());
                                                        Toast.makeText(getActivity(), "Failed to delete account, please try again", Toast.LENGTH_LONG).show();
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

    @OnClick(R2.id.logout_button)
    void logout(){
        auth.signOut();
        goToLogin();
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
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private void showLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredentialFailed", task.getException());
                        } else {
                            Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());

                            showLoadingDialog();
                            final String uid = user.getUid();
                            db.child(uid).removeValue();
                            authuser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Account deleted.", Toast.LENGTH_LONG).show();
                                                auth.signOut();
                                                LoginManager.getInstance().logOut();
                                                goToLogin();
                                            } else {
                                                Log.w("AUTH", "accountDelete:failed", task.getException());
                                                Toast.makeText(getActivity(), "Failed to delete account, please try again", Toast.LENGTH_LONG).show();
                                            }
                                            hideProgressDialog();

                                        }
                                    });
                            hideProgressDialog();
                        }
                    }
                });

    }

    private void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
