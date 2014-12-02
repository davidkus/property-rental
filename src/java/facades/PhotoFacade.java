package facades;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import models.Photo;

@ManagedBean
@SessionScoped
public class PhotoFacade extends BaseFacade {
    
    public Photo savePhoto(Part part) {
        
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
            return photo;
            
        } catch (Exception e) {}
        return null;
    }
    
}
