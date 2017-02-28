package edu.utdallas.csdesign.spring17.nutriscope;

import android.content.Context;
import android.support.annotation.NonNull;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.HistoryRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */


public class Injection {

    public static HistoryRepository provideHistoryRepository(@NonNull Context context) {
        checkNotNull(context);
        return HistoryRepository.getInstance();
    }
}



