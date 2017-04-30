package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.TaskStatus;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserStatus;
import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;

public class LoginCheckActivity extends Activity {

    final static private String TAG = "LoginCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "started");

        UserManager userManager = ((NutriscopeApplication) getApplication()).getUserManagerComponent().getUserManager();

        userManager.getUserStatus(new TaskStatus() {
            @Override
            public void success(UserStatus msg) {
                Log.d(TAG, "success");
                openOverview();
            }

            @Override
            public void failure(UserStatus msg) {
                Log.d(TAG, "failure");
                openLogin();
            }
        });

        finish();

    }

    private void openOverview() {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
        finish();

    }

    private void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
