package com.artshell.arch.storage;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author artshell on 19/03/2018
 */

@StringDef({Status.SUCCESS, Status.ERROR, Status.LOADING, Status.LOADING, Status.COMPLETE, Status.CANCEL})
@Retention(SOURCE)
@Target({FIELD, PARAMETER, LOCAL_VARIABLE})
public @interface Status {
    String SUCCESS  = "success";
    String ERROR    = "error";
    String LOADING  = "loading";
    String COMPLETE = "complete";
    String CANCEL   = "cancel";
}

