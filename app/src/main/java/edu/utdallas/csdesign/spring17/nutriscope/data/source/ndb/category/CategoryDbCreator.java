package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.category;

import android.database.sqlite.SQLiteDatabase;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;

/**
 * Created by john on 3/24/17.
 */

public class CategoryDbCreator implements Callable{

    private CategoryDbHelper helper;

    public CategoryDbCreator(CategoryDbHelper helper) {
        this.helper = helper;


    }

    @Override
    public SQLiteDatabase call() {
        return helper.getReadableDatabase();

    }
}
