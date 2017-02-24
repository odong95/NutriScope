package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;
import edu.utdallas.csdesign.spring17.nutriscope.R;


public class LoginActivity extends AppCompatActivity {

    private MobileServiceClient mClient;
    public LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setClient();

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_login, fragment)
                    .commit();


        loginPresenter = new LoginPresenter(fragment, mClient);

    }

    public void setClient() {
        try {
            mClient = new MobileServiceClient("https://nutriscope.azurewebsites.net", this);
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
        } catch (Exception e) {
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }





}
