/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facades.UserAccountFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import models.Customer;
import models.Owner;
import models.User;
import models.UserAccount;

/**
 *
 * @author Can Jee
 */
@ManagedBean
@RequestScoped
public class CreateAccountBean extends BaseBean {
    
    @ManagedProperty(value="#{userAccountFacade}")
    UserAccountFacade userAccountFacade;
    
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String type;
    private String status;
    private double maxrent;

    /**
     * Creates a new instance of CreateAccountBean
     */
    public CreateAccountBean() {
    }

    public UserAccountFacade getUserAccountFacade() {
        return userAccountFacade;
    }

    public void setUserAccountFacade(UserAccountFacade userAccountFacade) {
        this.userAccountFacade = userAccountFacade;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
        /**
     * @return the maxrent
     */
    public double getMaxrent() {
        return maxrent;
    }

    /**
     * @param maxrent the maxrent to set
     */
    public void setMaxrent(double maxrent) {
        this.maxrent = maxrent;
    }
    
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    
    public void createAccount() {
        try {
            utx.begin();
            UserAccount checkDuplicate = userAccountFacade.findByUsername(username, em);
            if (checkDuplicate != null){
                status = "Username already exists in the database, please enter another username.";
            } else if (maxrent <= 0 && type.equals("customer")) {
                status = "Please enter a max rent greater than zero for a customer account";
            } else if (maxrent != 0 && type.equals("owner")) {
                status = "If account type is owner, enter a max rent of 0. "
                        + "If account type if a customer, enter a max rent of greater than 0";
            } else {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                UserAccount account = new UserAccount();
                User user;
                account.setUsername(username);
                userAccountFacade.setPassword(account, password);
                account.setEmail(email);
                account.setFirstname(firstname);
                account.setLastname(lastname);
                account.setMaxrent(maxrent);

                if (type.equals("owner")) {
                    user = new Owner();
                }
                else {
                    user = new Customer();
                }
                user.setUserAccount(account);
                em.persist(account);
                em.persist(user);
                utx.commit();
                
                context.redirect(context.getRequestContextPath() +
                        "/agent/account_creation_successful.xhtml");
            }
        } catch (Exception e) {
        }

    }
    
}
