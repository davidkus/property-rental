package beans;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

public abstract class BaseBean {

    @PersistenceContext(unitName = "property-rentalPU")
    protected EntityManager em;

    @Resource
    protected UserTransaction utx;
    
    /**
     * Creates a new instance of BaseBean
     */
    public BaseBean() {
    }
    
}
