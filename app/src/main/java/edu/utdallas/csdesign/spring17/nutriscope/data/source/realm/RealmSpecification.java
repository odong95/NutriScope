package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.Specification;

/**
 * Created by john on 3/5/17.
 */

public interface RealmSpecification extends Specification {
    String toRealmQuery();
}
