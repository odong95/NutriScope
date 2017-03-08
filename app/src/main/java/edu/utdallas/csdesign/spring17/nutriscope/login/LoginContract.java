package edu.utdallas.csdesign.spring17.nutriscope.login;

import io.realm.ObjectServerError;
import io.realm.SyncUser;

public class LoginContract {
    public interface View {
        void onSuccess(SyncUser user);

        void onError(ObjectServerError error);

        void errorInputResponse(Boolean isValid);

        void onRegisterResponse(Boolean isRegistered);
    }

    public interface Presenter {

        void login(String username, String password);

        void register(String username, String password);

        void onSuccess(SyncUser user);

        void onError(ObjectServerError error);

        void errorInputResponse(Boolean isValid);

        void onRegisterResponse(Boolean isRegistered);

    }

    public interface Model {

        void login(String username, String password);

        void register(String username, String password);

    }

}
