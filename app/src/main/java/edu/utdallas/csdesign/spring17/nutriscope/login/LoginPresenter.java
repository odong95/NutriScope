package edu.utdallas.csdesign.spring17.nutriscope.login;


import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginContract.Interactor interactor;


    @Inject
    public LoginPresenter(LoginContract.Interactor interactor, LoginContract.View view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
        interactor.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void login(String email, String password) {
        interactor.login(email, password);
    }

    @Override
    public void loginSuccessful() {
        view.loginSuccessful();
    }

/*    @Override
    public void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(a, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredential", task.getException());
                        }
                        else
                        {
                            Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());
                        }

                    }
                });
    }*/


    @Override
    public void onErrorResponse(String error) {
        view.onErrorResponse(error);
    }


}
