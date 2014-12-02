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
    
    private String type;    
    private List<String> locations;
    private Long numberofbedrooms;
    private Long numberofbathrooms;
    private Long numberofotherrooms;
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
    public Long getNumberofbedrooms() {
        return numberofbedrooms;
    }

    /**
     * @param numberofbedrooms the numberofbedrooms to set
     */
    public void setNumberofbedrooms(Long numberofbedrooms) {
        this.numberofbedrooms = numberofbedrooms;
    }

    /**
     * @return the numberofbathrooms
     */
    public Long getNumberofbathrooms() {
        return numberofbathrooms;
    }

    /**
     * @param numberofbathrooms the numberofbathrooms to set
     */
    public void setNumberofbathrooms(Long numberofbathrooms) {
        this.numberofbathrooms = numberofbathrooms;
    }

    /**
     * @return the numberofotherrooms
     */
    public Long getNumberofotherrooms() {
        return numberofotherrooms;
    }

    /**
     * @param numberofotherrooms the numberofotherrooms to set
     */
    public void setNumberofotherrooms(Long numberofotherrooms) {
        this.numberofotherrooms = numberofotherrooms;
    }
    
    public void searchProperty() {
        System.out.println(getLocations().toString());
        System.out.println(getType());
        System.out.println(getNumberofbedrooms());
        System.out.println(getNumberofbathrooms());
        System.out.println(getNumberofotherrooms());
        System.out.println(getMinrent());
        System.out.println(getMaxrent());
//        try {
//            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//            Property property = new Property();
//            Address propertyAddress = new Address();
//            
//            propertyAddress.setStreetNumber(getStreetnumber());
//            propertyAddress.setUnitNumber(unitnumber);
//            propertyAddress.setStreetName(streetname);
//            propertyAddress.setCity(city);
//            propertyAddress.setProvince(province);
//            propertyAddress.setPostalCode(postalcode);
//            propertyAddress.setCountry(country);
//            
//            property.setAddress(propertyAddress);
//            property.setType(type);
//            property.setNumberOfBathrooms(numberofbathrooms);
//            property.setNumberOfBedrooms(numberofbedrooms);
//            property.setNumberOtherRooms(numberofotherrooms);
//            property.setRent(rent);
//            property.setLocation(location);
//            property.setOwner(sessionBean.getOwner());
//            
//            if (photo1 != null){
//                photos.add(photoFacade.savePhoto(photo1));
//            }
//            if (photo2 != null){
//                photos.add(photoFacade.savePhoto(photo2));
//            }
//            if (photo3 != null){
//                photos.add(photoFacade.savePhoto(photo3));
//            }
//            if (photo4 != null){
//                photos.add(photoFacade.savePhoto(photo4));
//            }
//            if (photo5 != null){
//                photos.add(photoFacade.savePhoto(photo5));
//            }
//            property.setPhotos(photos);
//            
//            if( propertyFacade.addProperty(property, propertyAddress, photos) ) {
//                context.redirect(context.getRequestContextPath() +
//                        "/owner/property_added_successfully.xhtml");
//            }
//
//        } catch (Exception e) {}
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
}
