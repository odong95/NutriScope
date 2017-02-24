package edu.utdallas.csdesign.spring17.nutriscope.login;

public class LoginContract {
    public interface View {
        void onLoginResponse(boolean isLoggedIn);

        void onRegisterResponse(boolean isRegistered);
    }

    public interface Presenter {

        void login(String username, String password);

        void register(String username, String password);

        void onLoginResponse(boolean isLoggedIn);

        void onRegisterResponse(boolean isRegistered);
    }

    public interface Model {

        void login(String username, String password);

        void register(String username, String password);

    }


}
