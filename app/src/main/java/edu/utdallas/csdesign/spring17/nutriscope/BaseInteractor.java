package edu.utdallas.csdesign.spring17.nutriscope;

/**
 * Created by john on 3/16/17.
 */

public interface BaseInteractor<T> {
    void setPresenter(T presentor);
}
