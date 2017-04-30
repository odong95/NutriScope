package edu.utdallas.csdesign.spring17.nutriscope.data.user;

/**
 * Created by john on 4/29/17.
 */

public interface TaskStatus {
    void success(UserStatus msg);
    void failure(UserStatus msg);
}