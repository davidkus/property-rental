package beans;

import facades.PropertyFacade;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Property;
import beans.UpdatePropertyBean;

@ManagedBean
@RequestScoped
public class ViewVisitingListBean extends BaseBean {
    
    @ManagedProperty(value="#{propertyFacade}")
    PropertyFacade propertyFacade;
    
    private static final int PAGE_SIZE = 25;
    
    private String orderBy = "rent";
    private boolean ascending = true;
    private List<Property> properties;
    private int pageNumber = 1;
    
    /**
     * Creates a new instance of ViewVisitingListBean
     */
    public ViewVisitingListBean() {}

    public PropertyFacade getPropertyFacade() {
        return propertyFacade;
    }

    public void setPropertyFacade(PropertyFacade propertyFacade) {
        this.propertyFacade = propertyFacade;
    }

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
        properties = propertyFacade.getVisitingList(sessionBean.getCustomer(), orderBy, ascending, em);
        if ( hasNextPage() ) {
            pageNumber++;
        }
    }
    
    public void previousPage() {
        properties = propertyFacade.getVisitingList(sessionBean.getCustomer(), orderBy, ascending, em);
        if( hasPreviousPage() ) {
            pageNumber--;
        }
    }
    
    public void sort() {
        properties = propertyFacade.getVisitingList(sessionBean.getCustomer(), orderBy, ascending, em);
        pageNumber = 1;
    }
    
    public List<Property> getProperties() {
        if (properties == null) {
            properties = propertyFacade.getVisitingList(sessionBean.getCustomer(), orderBy, ascending, em);
        }
        
        if (properties.isEmpty()) {
            return properties;
        }
        
        int from = PAGE_SIZE * (pageNumber - 1);
        int to = from + PAGE_SIZE;
                
        if (to >= properties.size()) {
            to = properties.size();
        }
        
        return properties.subList(from, to);
    }
    
    public long getPropertyCount() {
        if (properties == null) {
            properties = propertyFacade.getVisitingList(sessionBean.getCustomer(), orderBy, ascending, em);
        }
        return properties.size();
    }
    
    public long getMaxPages() {
        double propertyCount = getPropertyCount();
        double pageSize = PAGE_SIZE;
        long maxPages = (long)Math.ceil(propertyCount / PAGE_SIZE);
        return maxPages;
    }
    
    public void deleteProperty(Property property){
        propertyFacade.deleteProperty(property);
    }
}
