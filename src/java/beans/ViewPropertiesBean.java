package beans;

import facades.PropertyFacade;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.Property;

@ManagedBean
@SessionScoped
public class ViewPropertiesBean extends BaseBean {

    @ManagedProperty(value="#{propertyFacade}")
    PropertyFacade propertyFacade;
    
    private static final int PAGE_SIZE = 25;
    
    private String orderBy = "rent";
    private boolean ascending = true;
    private int pageNumber = 1;
    private List<String> location;
    private String type;
    private int numberofbedrooms;
    private int numberofbathrooms;
    private int numberofotherrooms;
    private double minrent;
    private double maxrent;
    private List<Property> properties;
    private Long propertyCount;
    private String status;
    
    /**
     * Creates a new instance of ViewPropertiesBean
     */
    public ViewPropertiesBean() {
    }

    public PropertyFacade getPropertyFacade() {
        return propertyFacade;
    }

    public void setPropertyFacade(PropertyFacade propertyFacade) {
        this.propertyFacade = propertyFacade;
    }
    
    public String viewPropertiesByLocation() {
        properties = propertyFacade.getByLocation(getLocation().get(0), "rent", true, em);
        pageNumber = 1;
        orderBy = "rent";
        ascending = true;
        return "/customer/view_properties.xhtml";
    }
    
    public String viewPropertiesFromSearch() {
        properties = propertyFacade.getByEverything(getLocation(), getType(), getNumberofbedrooms(), getNumberofbathrooms(),
                getNumberofotherrooms(), getMinrent(), getMaxrent(), "rent", true, em);
        pageNumber = 1;
        orderBy = "rent";
        ascending = true;
        return "/customer/view_properties.xhtml";
    }
    
    public boolean hasPreviousPage() {
        return pageNumber != 1;
    }
    
    public boolean hasNextPage() {
        return (pageNumber * PAGE_SIZE < getPropertyCount());
    }
    
    public void nextPage() {
        if ( hasNextPage() ) {
            pageNumber++;
        }
    }
    
    public void previousPage() {
        if( hasPreviousPage() ) {
            pageNumber--;
        }
    }
    
    public void sort() {
        pageNumber = 1;
        properties = propertyFacade.getByLocation(getLocation().get(0), orderBy, ascending, em);
    }
    
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Property> getProperties() {
        if( properties.isEmpty() ) {
            return properties;
        }
        
        int from = PAGE_SIZE * (pageNumber - 1);
        int to = from + PAGE_SIZE;
        
        if ( to >= properties.size() ) {
            to = properties.size();
        }
                
        return properties.subList(from, to);
    }
    
    public long getPropertyCount() {
        return properties.size();
    }
    
    public long getMaxPages() {
        double propertyCount = getPropertyCount();
        double pageSize = PAGE_SIZE;
        long maxPages = (long)Math.ceil(propertyCount / pageSize);
        return maxPages;
    }
    
    public boolean inVisitingList (Property property){
        return propertyFacade.inVisitingList(property);
    }
    
    public void addToVisitingList(Property property){
        status = propertyFacade.addToVisitingList(property);
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
     * @return the location
     */
    public List<String> getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(List<String> location) {
        this.location = location;
    }
    
}
