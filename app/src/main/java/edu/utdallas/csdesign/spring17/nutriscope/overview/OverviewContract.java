package edu.utdallas.csdesign.spring17.nutriscope.overview;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 2/10/17.
 */

public interface OverviewContract {

    interface View extends BaseView<Presenter> {

        void showHistory(List<Trackable> data);


    }

    interface Presenter extends BasePresenter {

        void loadHistory();



    }

}
