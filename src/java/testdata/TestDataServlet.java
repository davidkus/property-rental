package testdata;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.transaction.UserTransaction;
import models.Agent;
import models.Customer;
import models.Owner;
import models.UserAccount;

/**
 *
 * @author david
 */
@WebServlet
public class TestDataServlet extends HttpServlet {

    @PersistenceContext(unitName = "property-rentalPU")
    private EntityManager em;

    @Resource
    private UserTransaction utx;
    
    @Override
    public void init() {
        try {
            utx.begin();
            
            createAgent();
            createOwner();
            createCustomer();
            
            utx.commit();
        } catch (Exception e) {}
    }

    private void createAgent() {
        UserAccount account = new UserAccount();
        account.setEmail("agent@example.com");
        account.setUsername("agent");
        account.setFirstname("Agent1");
        account.setLastname("Agent1");
        account.setPassword("test");

        Agent user = new Agent();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
    }
    
    private void createOwner() {
        UserAccount account = new UserAccount();
        account.setEmail("owner@example.com");
        account.setUsername("owner");
        account.setFirstname("Owner1");
        account.setLastname("Owner1");
        account.setPassword("test");

        Owner user = new Owner();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
    }
    
    private void createCustomer() {
        UserAccount account = new UserAccount();
        account.setEmail("customer@example.com");
        account.setUsername("customer");
        account.setFirstname("Customer1");
        account.setLastname("Customer1");
        account.setPassword("test");

        Customer user = new Customer();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
