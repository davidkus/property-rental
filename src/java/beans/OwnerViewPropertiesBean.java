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
        properties = sessionBean.getOwner().getProperties(orderBy, ascending, em);
        if ( hasNextPage() ) {
            pageNumber++;
        }
    }
    
    public void previousPage() {
        properties = sessionBean.getOwner().getProperties(orderBy, ascending, em);
        if( hasPreviousPage() ) {
            pageNumber--;
        }
    }
    
    public void sort() {
        properties = sessionBean.getOwner().getProperties(orderBy, ascending, em);
        pageNumber = 1;
    }
    
    public List<Property> getProperties() {
        if (properties == null) {
            properties = sessionBean.getOwner().getProperties(orderBy, ascending, em);
        }
        
        if (properties.isEmpty()) {
            return properties;
        }
        
        int from = PAGE_SIZE * (pageNumber - 1);
        int to = from + PAGE_SIZE;
                
        if (to >= properties.size()) {
            to = properties.size() - 1;
        }
        
        return properties.subList(from, to);
    }
    
    public long getPropertyCount() {
        if (properties == null) {
            properties = sessionBean.getOwner().getProperties(orderBy, ascending, em);
        }
        return properties.size();
    }
    
    public long getMaxPages() {
        long maxPages = (long)Math.ceil(getPropertyCount() / PAGE_SIZE);
        return maxPages;
    }
    
    public void deleteProperty(Property deleteProperty){
        try{
            utx.begin();
            deleteProperty.setStatus("Deleted");
            em.merge(deleteProperty);
            utx.commit();
        } catch (Exception e) {}
    }
    
}
