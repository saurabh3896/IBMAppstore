package controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.ektorp.UpdateConflictException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/main")
public class MainController{

    protected static Logger logger = Logger.getLogger("controller");
    
    User new_user = new User();
    
    private String username;
    
    @Autowired
    BlogPostRepository blogPostRepo;
	
    /**
    * Handles and retrieves the common JSP page that everyone can see
    * 
    * @param model
    * @return the name of the JSP page
    */
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String getCommonPage(ModelMap model){
        
        logger.debug("Received request to show common page");
    
        // Do your work here. Whatever you like
        // i.e call a custom service to do your business
        // Prepare a model to be used by the JSP page
        
        // This will resolve to /WEB-INF/jsp/commonpage.jsp
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(principal instanceof UserDetails){
            this.username = ((UserDetails)principal).getUsername();
        }
        else{
            this.username = principal.toString();
        }
        
        model.addAttribute("message", username);        
        
        new_user.setTitle(username);
        new_user.setId(username);
        new_user.setDateCreated(new DateTime());
        /*if(new_user.isNew()){
            blogPostRepo.add(new_user);
        }
        else{
            blogPostRepo.update(new_user);
        }*/
        
        return "welcome_page";
    }
    
    @ExceptionHandler(UpdateConflictException.class)
    public String onUpdateConflictException(HttpServletRequest req){
        return "welcome_page";
    }
    
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(){
        logger.debug("Received request to show admin page");
    
        // Do your work here. Whatever you like
        // i.e call a custom service to do your business
        // Prepare a model to be used by the JSP page
    	
        // This will resolve to /WEB-INF/jsp/adminpage.jsp
        return "adminpage";
    }
}
