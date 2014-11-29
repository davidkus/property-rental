package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import static models.BaseEntity.performQueryList;

@Entity
@Table(name = "properties_5939559")
public class Property extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String location;
    private Long numberOfBedrooms;
    private Long numberOfBathrooms;
    private Long numberOtherRooms;
    private Double rent;
    private String status = "Active";
    @OneToOne
    private Address address; 
    @OneToMany(fetch=FetchType.EAGER)
    private List<Photo> photos;
    @ManyToOne
    private Owner owner;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Long numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public Long getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Long numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public Long getNumberOtherRooms() {
        return numberOtherRooms;
    }

    public void setNumberOtherRooms(Long numberOtherRooms) {
        this.numberOtherRooms = numberOtherRooms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    public static List<Property> getByLocation(String location, String orderBy, boolean ascending, EntityManager em) {
        String queryString = "SELECT p FROM Property p WHERE p.location = :location AND p.status = :status";
        
        if (orderBy.equals("bedrooms")) {
            queryString += "  ORDER BY p.numberOfBedrooms ";
        } else if (orderBy.equals("bathrooms")) {
            queryString += "  ORDER BY p.numberOfBathrooms ";
        } else if (orderBy.equals("otherrooms")) {
            queryString += "  ORDER BY p.numberOtherRooms ";
        } else {
            queryString += "  ORDER BY p.rent ";
        }
        
        if (ascending) {
            queryString += "ASC";
        } else {
            queryString += "DESC";
        }
        
        Query query = em.createQuery(queryString);
        
        query.setParameter("location", location);
        query.setParameter("status", "Active");
        
        return performQueryList(Property.class, query);
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
        if (!(object instanceof Property)) {
            return false;
        }
        Property other = (Property) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Property[ id=" + id + " ]";
    }
    
}
