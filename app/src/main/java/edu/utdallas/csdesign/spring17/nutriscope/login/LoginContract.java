package edu.utdallas.csdesign.spring17.nutriscope.login;



public class LoginContract {
    public interface View {

        void errorInputResponse(Boolean isValid);

        void onRegisterResponse(Boolean isRegistered);
    }

    public interface Presenter {

        void login(String username, String password);

        void register(String username, String password);

        void errorInputResponse(Boolean isValid);

        void onRegisterResponse(Boolean isRegistered);

    }

    public interface Model {

        void login(String username, String password);

        void register(String username, String password);

    }

}
