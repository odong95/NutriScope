package edu.utdallas.csdesign.spring17.nutriscope.register;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.user.TaskStatus;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserStatus;

final public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;

    private final UserManager userManager;

    @Inject
    public RegisterPresenter(RegisterContract.View view, UserManager userManager) {
        this.view = view;
        this.userManager = userManager;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String email, String password) {
        userManager.registerUserEmail(email, password, new TaskStatus() {
            @Override
            public void success(UserStatus userStatus) {
                onRegisterComplete();
            }

            @Override
            public void failure(UserStatus userStatus) {
                onErrorResponse("Could not register");
            }
        });
    }

    @Override
    public void onRegisterComplete() {
        view.onRegisterComplete();
    }


    @Override
    public void onErrorResponse(String error) {
        view.onErrorResponse(error);
    }

}
