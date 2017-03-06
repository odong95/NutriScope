package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Specification;

/**
 * Created by john on 2/21/17.
 */

public class RealmRepository implements Repository {

    private static RealmRepository INSTANCE = null;

    private RealmRepository() {


    }

    public static RealmRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new RealmRepository();
        }

        return INSTANCE;
    }


    @Override
    public void createTrackable(Trackable item, CreateCallback callback) {



    }

    @Override
    public void updateTrackable(Trackable item, UpdateCallback callback) {

    }

    @Override
    public void queryTrackable(Specification specification, QueryCallback callback) {

    }

    @Override
    public void deleteTrackable(Trackable id, DeleteCallback callback) {

    }





}
