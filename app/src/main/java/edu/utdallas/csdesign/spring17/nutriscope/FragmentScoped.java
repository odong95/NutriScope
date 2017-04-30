package edu.utdallas.csdesign.spring17.nutriscope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by john on 3/18/17.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScoped {
}