/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import models.Address;
import models.Photo;
import models.Property;

/**
 *
 * @author Can Jee
 */
@ManagedBean
@RequestScoped
public class AddPropertyBean extends BaseBean{
    
    private List<Photo> photos;
    private Part photo1;
    private Part photo2;
    private Part photo3;
    private Part photo4;
    private Part photo5;
    private String type;
    private Long streetnumber;
    private Long unitnumber;
    private String streetname;
    private String city;
    private String province;
    private String postalcode;
    private String country;
    private String location;
    private Long numberofbedrooms;
    private Long numberofbathrooms;
    private Long numberofotherrooms;
    private double rent;
    private String status;

    /**
     * Creates a new instance of AddPropertyBean
     */
    public AddPropertyBean() {
        photos = new ArrayList<Photo>();
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
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
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

    /**
     * @return the rent
     */
    public double getRent() {
        return rent;
    }

    /**
     * @param rent the rent to set
     */
    public void setRent(double rent) {
        this.rent = rent;
    }

    /**
     * @return the unitnumber
     */
    public Long getUnitnumber() {
        return unitnumber;
    }

    /**
     * @param unitnumber the unitnumber to set
     */
    public void setUnitnumber(Long unitnumber) {
        this.unitnumber = unitnumber;
    }

    /**
     * @return the streetname
     */
    public String getStreetname() {
        return streetname;
    }

    /**
     * @param streetname the streetname to set
     */
    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the postalcode
     */
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * @param postalcode the postalcode to set
     */
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
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
     * @return the streetnumber
     */
    public Long getStreetnumber() {
        return streetnumber;
    }

    /**
     * @param streetnumber the streetnumber to set
     */
    public void setStreetnumber(Long streetnumber) {
        this.streetnumber = streetnumber;
    }

    /**
     * @return the photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * @param photos the photos to set
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
    public void upload(Part part) {
        try {
            String baseUrl = Photo.getImageFileLocation();
            String fileName = UUID.randomUUID() + "-" + part.getSubmittedFileName();
            
            String filePath = baseUrl + fileName;
            
            try (InputStream input = part.getInputStream()) {
                Files.copy(input, Paths.get(filePath));
            }
                        
            Photo photo = new Photo();
            String name = fileName;
            Long size = part.getSize();
            photo.setName(name);
            photo.setSize(size);
            photos.add(photo);
            em.persist(photo);
        } catch (Exception e) {}
    }
    
    public void addProperty() {
        try {
            utx.begin();
            if (type.equals("") || getStreetnumber() == 0 || streetname.equals("") || city.equals("") || province.equals("") || postalcode.equals("") || country.equals("")){
                status = "Please fill out all the fields before submitting";
            }
            else if (photo1 == null && photo2 == null && photo3 == null && photo4 == null && photo5 == null){
                status = "Please insert at least one photo before submitting";
            }
            else if (numberofbathrooms <= 0 || numberofbedrooms <= 0){
                status = "The number of bathrooms and the number of bedrooms must be a number greater than zero";
            }
            else if (rent <= 0){
                status = "Rent must be a real value greater than zero";
            }
            else {
                System.out.println("here2");
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                Property property = new Property();
                Address propertyAddress = new Address();
                propertyAddress.setStreetNumber(getStreetnumber());
                propertyAddress.setUnitNumber(unitnumber);
                propertyAddress.setStreetName(streetname);
                propertyAddress.setCity(city);
                propertyAddress.setProvince(province);
                propertyAddress.setPostalCode(postalcode);
                propertyAddress.setCountry(country);
                property.setAddress(propertyAddress);
                property.setType(type);
                property.setNumberOfBathrooms(numberofbathrooms);
                property.setNumberOfBedrooms(numberofbedrooms);
                property.setNumberOtherRooms(numberofotherrooms);
                property.setRent(rent);
                property.setLocation(location);
                property.setOwner(sessionBean.getOwner());
                if (photo1 != null){
                    upload(photo1);
                }
                if (photo2 != null){
                    upload(photo2);
                }
                if (photo3 != null){
                    upload(photo3);
                }
                if (photo4 != null){
                    upload(photo4);
                }
                if (photo5 != null){
                    upload(photo5);
                }
                property.setPhotos(photos);
                
                em.persist(propertyAddress);
                em.persist(property);
                utx.commit();
                
                try {
                    context.redirect(context.getRequestContextPath() + "/owner/property_added_successfully.xhtml");
                } catch (Exception e) {
                }
                
            }
        } catch (Exception e) {}
    }

    /**
     * @return the photo1
     */
    public Part getPhoto1() {
        return photo1;
    }

    /**
     * @param photo1 the photo1 to set
     */
    public void setPhoto1(Part photo1) {
        this.photo1 = photo1;
    }

    /**
     * @return the photo2
     */
    public Part getPhoto2() {
        return photo2;
    }

    /**
     * @param photo2 the photo2 to set
     */
    public void setPhoto2(Part photo2) {
        this.photo2 = photo2;
    }

    /**
     * @return the photo3
     */
    public Part getPhoto3() {
        return photo3;
    }

    /**
     * @param photo3 the photo3 to set
     */
    public void setPhoto3(Part photo3) {
        this.photo3 = photo3;
    }

    /**
     * @return the photo4
     */
    public Part getPhoto4() {
        return photo4;
    }

    /**
     * @param photo4 the photo4 to set
     */
    public void setPhoto4(Part photo4) {
        this.photo4 = photo4;
    }

    /**
     * @return the photo5
     */
    public Part getPhoto5() {
        return photo5;
    }

    /**
     * @param photo5 the photo5 to set
     */
    public void setPhoto5(Part photo5) {
        this.photo5 = photo5;
    }
    
}
