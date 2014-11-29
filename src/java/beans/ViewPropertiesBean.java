package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.Property;

@ManagedBean
@SessionScoped
public class ViewPropertiesBean extends BaseBean {

    private static final int PAGE_SIZE = 25;
    
    private String orderBy = "rent";
    private boolean ascending = true;
    private int pageNumber = 1;
    private String location;
    private List<Property> properties;
    private Long propertyCount;
    
    /**
     * Creates a new instance of ViewPropertiesBean
     */
    public ViewPropertiesBean() {
    }
    
    public String viewPropertiesByLocation() {
        properties = Property.getByLocation(location, "rent", true, em);
        pageNumber = 1;
        orderBy = "rent";
        ascending = true;
        return "/visitor/view_properties";
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
        properties = Property.getByLocation(location, orderBy, ascending, em);
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Property> getProperties() {
        if( properties.isEmpty() ) {
            return properties;
        }
        
        int from = PAGE_SIZE * (pageNumber - 1);
        int to = from + PAGE_SIZE;
                
        return properties.subList(from, to);
    }
    
    public long getPropertyCount() {
        return properties.size();
    }
    
}
