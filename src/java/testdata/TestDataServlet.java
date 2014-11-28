package testdata;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import models.Agent;
import models.Owner;
import models.User;
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
            UserAccount account = new UserAccount();
            account.setEmail("agent@example.com");
            account.setUsername("agent");
            account.setFirstname("Agent");
            account.setLastname("Agent");
            account.setPassword("test");
            
            Agent user = new Agent();
            user.setUserAccount(account);
            
            em.persist(account);
            em.persist(user);
            
            utx.commit();
        } catch (Exception e) {}
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
