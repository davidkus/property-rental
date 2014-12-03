package testdata;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
        } catch (Exception e) {
            int i = 1;
        }
    }

    private Agent createAgent() {
        UserAccount account = new UserAccount();
        account.setEmail("agent@example.com");
        account.setUsername("agent");
        account.setFirstname("Agent1");
        account.setLastname("Agent1");
        setPassword(account, "test");
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
        setPassword(account, "test");
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
        setPassword(account, "test");
        account.setMaxrent(800);

        Customer user = new Customer();
        user.setUserAccount(account);

        em.persist(account);
        em.persist(user);
        return user;
    }
    
    private void createOwnerProperties(Owner owner, int numberOfProperties) {
        Random rand = new Random();
        String[] locations = {"Toronto", "Ottawa", "Kingston", "Montreal", "Kitchener",
                                "Windsor", "London", "Belleville", "Other"};
        for( int i = 0; i < numberOfProperties; i++ ) {
            Address address = createAddress();
            Property property = new Property();
            property.setAddress(address);
            property.setOwner(owner);
            
            property.setLocation(locations[rand.nextInt(9)]);
            property.setNumberOfBathrooms((long)rand.nextInt(10) + 1);
            property.setNumberOfBedrooms((long)rand.nextInt(10) + 1);
            property.setNumberOtherRooms((long)rand.nextInt(10) + 1);
            if(rand.nextInt() % 2 == 0)
                property.setType("House");
            else
                property.setType("Apartment");
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
    
    public boolean setPassword(UserAccount userAccount, String password) {
        try {
            // randomly generate salt value
            final Random r = new SecureRandom();
            byte[] salt = new byte[32];
            r.nextBytes(salt);
            String saltString = new String(salt, "UTF-8");
            // hash password using SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPass = saltString+password;
            byte[] passhash = digest.digest(saltedPass.getBytes("UTF-8"));
            userAccount.setSalt(salt);
            userAccount.setPassword(passhash);
            return true;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | RuntimeException ex) {
            return false;
        }
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
