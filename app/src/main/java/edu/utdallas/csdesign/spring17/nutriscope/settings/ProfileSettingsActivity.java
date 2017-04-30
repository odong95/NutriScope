package edu.utdallas.csdesign.spring17.nutriscope.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication;
import edu.utdallas.csdesign.spring17.nutriscope.R;


public class ProfileSettingsActivity extends AppCompatActivity {

    public static final String TAG = "ProSetAct";

    @Inject ProfileSettingsPresenter profileSettingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_fragment);

        ProfileSettingsFragment fragment = (ProfileSettingsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ProfileSettingsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        DaggerProfileSettingsComponent.builder()
                .userManagerComponent(((NutriscopeApplication) getApplication()).getUserManagerComponent())
                .profileSettingsModule(new ProfileSettingsModule(fragment))
                .build();
    }

}
