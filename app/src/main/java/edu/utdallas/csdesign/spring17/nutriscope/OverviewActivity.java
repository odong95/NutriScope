package edu.utdallas.csdesign.spring17.nutriscope;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OverviewActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new OverviewFragment();
    }
}
