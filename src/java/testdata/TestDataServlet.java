package testdata;

import java.util.Random;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.transaction.UserTransaction;
import models.Address;
import models.Agent;
import models.Customer;
import models.Owner;
import models.Property;
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
            
            Agent agent = createAgent();
            Owner owner = createOwner();
            Customer customer = createCustomer();
            
            createOwnerProperties(owner, 50);
                        
            utx.commit();
        } catch (Exception e) {}
    }

    private Agent createAgent() {
        UserAccount account = new UserAccount();
        account.setEmail("agent@example.com");
        account.setUsername("agent");
        account.setFirstname("Agent1");
        account.setLastname("Agent1");
        account.setPassword("test");
        account.setMaxrent(0.0);

        Agent user = new Agent();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
        return user;
    }
    
    private Owner createOwner() {
        UserAccount account = new UserAccount();
        account.setEmail("owner@example.com");
        account.setUsername("owner");
        account.setFirstname("Owner1");
        account.setLastname("Owner1");
        account.setPassword("test");
        account.setMaxrent(0.0);

        Owner user = new Owner();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
        return user;
    }
    
    private Customer createCustomer() {
        UserAccount account = new UserAccount();
        account.setEmail("customer@example.com");
        account.setUsername("customer");
        account.setFirstname("Customer1");
        account.setLastname("Customer1");
        account.setPassword("test");
        account.setMaxrent(800);

        Customer user = new Customer();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
        return user;
    }
    
    private void createOwnerProperties(Owner owner, int numberOfProperties) {
        Random rand = new Random();
        for( int i = 0; i < numberOfProperties; i++ ) {
            Address address = createAddress();
            Property property = new Property();
            property.setAddress(address);
            property.setOwner(owner);
            property.setLocation("Canada");
            property.setNumberOfBathrooms((long)rand.nextInt(10) + 1);
            property.setNumberOfBedrooms((long)rand.nextInt(10) + 1);
            property.setNumberOtherRooms((long)rand.nextInt(10) + 1);
            property.setType("House");
            property.setRent(rand.nextDouble() * 1000);
            em.persist(property);
        }
    }
    
    private Address createAddress() {
        Address address = new Address();
        address.setCity("Ottawa");
        address.setCountry("Canada");
        address.setPostalCode("K1N 6N5");
        address.setProvince("ON");
        address.setStreetName("Laurier Ave E");
        address.setStreetNumber(75L);
        em.persist(address);
        return address;
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
