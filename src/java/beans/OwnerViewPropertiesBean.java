package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.Property;

@ManagedBean
@RequestScoped
public class OwnerViewPropertiesBean extends BaseBean {
    
    private static final int PAGE_SIZE = 25;
    
    private String orderBy = "rent";
    private boolean ascending = true;
    private List<Property> properties;
    private Long propertyCount;
    private int pageNumber = 1;
    
    /**
     * Creates a new instance of OwnerViewPropertiesBean
     */
    public OwnerViewPropertiesBean() {}

    public String getOrderBy() {
        return orderBy;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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
    
    public boolean hasPreviousPage() {
        return pageNumber != 1;
    }
    
    public boolean hasNextPage() {
        return (pageNumber * PAGE_SIZE < getPropertyCount());
    }
    
    public void nextPage() {
        if ( hasNextPage() ) {
            properties = null;
            propertyCount = null;
            pageNumber++;
        }
    }
    
    public void previousPage() {
        if( hasPreviousPage() ) {
            properties = null;
            propertyCount = null;
            pageNumber--;
        }
    }
    
    public void sort() {
        properties = null;
        propertyCount = null;
        pageNumber = 1;
    }
    
    public List<Property> getProperties() {
        if (properties == null) {
            properties = 
                    sessionBean.getOwner().getProperties(orderBy, ascending, PAGE_SIZE, pageNumber, em);
        }
        return properties;
    }
    
    public long getPropertyCount() {
        if (propertyCount == null) {
            propertyCount = sessionBean.getOwner().getPropertiesCount(em);
        }
        return propertyCount;
    }
    
}
