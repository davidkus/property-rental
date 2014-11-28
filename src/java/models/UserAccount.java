package models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name="user_accounts_5939559")
public class UserAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private boolean deleted = false;
    @Lob
    private byte[] password; // salted + hashed password
    @Lob
    private byte[] salt; // the salt used for this account
    @OneToOne
    private Address address;
    @OneToOne(mappedBy = "userAccount", fetch=FetchType.EAGER)
    private User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser(EntityManager em) {
        Agent agent = Agent.getByAccount(this, em);
        if( agent != null ) {
            return agent;
        }
        
        Customer customer = Customer.getByAccount(this, em);
        if( customer != null ) {
            return customer;
        }
        
        Owner owner = Owner.getByAccount(this, em);
        
        return owner;
    }
    
    public boolean setPassword(String password) {
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
            this.setSalt(salt);
            this.setPassword(passhash);
            return true;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | RuntimeException ex) {
            return false;
        }
    }
    
    public boolean checkPassword(String password) {
        try {
            byte[] salt = this.getSalt();
            String saltString = new String(salt, "UTF-8");
            String checkPass = saltString + password;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] checkPassHash = digest.digest(checkPass.getBytes("UTF-8"));
            if (Arrays.equals(checkPassHash, this.getPassword())) {
                return true; 
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static UserAccount findByUsername(String username, EntityManager em) {
        Query query = em.createQuery("SELECT ua FROM UserAccount ua WHERE ua.username = :username");
        query.setParameter("username", username);
        UserAccount result = performQuery(UserAccount.class, query);
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.UserAccount[ id=" + id + " ]";
    }
    
}
