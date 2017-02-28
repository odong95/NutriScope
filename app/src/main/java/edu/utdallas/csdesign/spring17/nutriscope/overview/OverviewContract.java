package edu.utdallas.csdesign.spring17.nutriscope.overview;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;
import edu.utdallas.csdesign.spring17.nutriscope.data.DailySummary;

/**
 * Created by john on 2/10/17.
 */

public interface OverviewContract {

    interface View extends BaseView<Presenter> {

        void showSummaries(List<DailySummary> dailySummary);


    }

    interface Presenter extends BasePresenter {

        void loadDailySummaries();



    }

}
