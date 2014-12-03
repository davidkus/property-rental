/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facades.PropertyFacade;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Address;
import models.Property;

/**
 *
 * @author Nabil
 */
@ManagedBean
@RequestScoped
public class SearchPropertyBean extends BaseBean {
    
    @ManagedProperty(value="#{propertyFacade}")
    PropertyFacade propertyFacade;
    
    @ManagedProperty(value="#{viewPropertiesBean}")
    private ViewPropertiesBean viewPropertiesBean;
    
    private String type;    
    private List<String> locations;
    private int numberofbedrooms;
    private int numberofbathrooms;
    private int numberofotherrooms;
    private double minrent;
    private double maxrent;

    /**
     * Creates a new instance of AddPropertyBean
     */
    public SearchPropertyBean() {
    }

    public PropertyFacade getPropertyFacade() {
        return propertyFacade;
    }

    public void setPropertyFacade(PropertyFacade propertyFacade) {
        this.propertyFacade = propertyFacade;
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
     * @return the numberofbedrooms
     */
    public int getNumberofbedrooms() {
        return numberofbedrooms;
    }

    /**
     * @param numberofbedrooms the numberofbedrooms to set
     */
    public void setNumberofbedrooms(int numberofbedrooms) {
        this.numberofbedrooms = numberofbedrooms;
    }

    /**
     * @return the numberofbathrooms
     */
    public int getNumberofbathrooms() {
        return numberofbathrooms;
    }

    /**
     * @param numberofbathrooms the numberofbathrooms to set
     */
    public void setNumberofbathrooms(int numberofbathrooms) {
        this.numberofbathrooms = numberofbathrooms;
    }

    /**
     * @return the numberofotherrooms
     */
    public int getNumberofotherrooms() {
        return numberofotherrooms;
    }

    /**
     * @param numberofotherrooms the numberofotherrooms to set
     */
    public void setNumberofotherrooms(int numberofotherrooms) {
        this.numberofotherrooms = numberofotherrooms;
    }
    
    public void searchProperty() {     
        viewPropertiesBean.setLocation(getLocations());
        viewPropertiesBean.setType(getType());
        viewPropertiesBean.setNumberofbedrooms(getNumberofbedrooms());
        viewPropertiesBean.setNumberofbathrooms(getNumberofbathrooms());
        viewPropertiesBean.setNumberofotherrooms(getNumberofotherrooms());
        viewPropertiesBean.setMinrent(getMinrent());
        viewPropertiesBean.setMaxrent(getMaxrent());
        
        String next = viewPropertiesBean.viewPropertiesFromSearch();
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() +
                    next);
        } catch (Exception e) {}
    }    

    /**
     * @return the locations
     */
    public List<String> getLocations() {
        return locations;
    }

    /**
     * @param locations the locations to set
     */
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    /**
     * @return the minrent
     */
    public double getMinrent() {
        return minrent;
    }

    /**
     * @param minrent the minrent to set
     */
    public void setMinrent(double minrent) {
        this.minrent = minrent;
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

    /**
     * @return the propertiesBean
     */
    public ViewPropertiesBean getViewPropertiesBean() {
        return viewPropertiesBean;
    }

    /**
     * @param propertiesBean the propertiesBean to set
     */
    public void setViewPropertiesBean(ViewPropertiesBean viewPropertiesBean) {
        this.viewPropertiesBean = viewPropertiesBean;
    }
}
