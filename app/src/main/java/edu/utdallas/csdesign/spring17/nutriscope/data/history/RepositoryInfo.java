package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;

/**
 * Created by john on 3/20/17.
 */

public class RepositoryInfo<T> {

    private Class<?> type;
    private Repository<T> repo;


    public RepositoryInfo(Class<?> type, Repository<T> repo) {
        this.type = type;
        this.repo = repo;
    }

    public Class<?> getType() {
        return type;
    }

    public Repository<T> getRepo() {
        return repo;
    }
}
