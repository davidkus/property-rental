package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public abstract class BaseEntity implements Serializable {
    
    /*
     * Performs the given query and returns the first result from the list.
     */
    protected static <T> T performQuery(Class<T> type, final Query query) {
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        ArrayList<T> results = new ArrayList<>();
        results.addAll(resultList);
        return results.get(0);
    }
    
    /*
     * Performs the given query and returns all of the results.
     */
    protected static <T> List<T> performQueryList(Class<T> type, final Query query) {
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } 
        ArrayList<T> results = new ArrayList<>();
        results.addAll(resultList);
        return results;
    }
    
}
