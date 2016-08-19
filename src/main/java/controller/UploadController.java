package controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/upload")
public class UploadController{

    private static final Logger logger = Logger.getLogger("controller");
    static String filePath;
    
    @Autowired
    private ServletContext servletContext;
	
    @RequestMapping
    public String form(){
        return "welcome_page";
    }
	
    @RequestMapping(value="/message", method=RequestMethod.POST)
    public @ResponseBody StatusResponse message(@RequestBody Message message){
        
        logger.debug("Service processing...done");
		
        return new StatusResponse(true, "Message received");
    }
	
    @RequestMapping(value="/file", method=RequestMethod.POST)
    public @ResponseBody List<UploadedFile> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws IOException{
        
        filePath = servletContext.getRealPath("/");
        filePath += ("/" + file.getOriginalFilename());
        File convFile = new File(filePath);
        convFile.createNewFile();
        try(FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
                
        logger.debug("Writing file to disk...done");
        
        List<UploadedFile> uploadedFiles = new ArrayList<>();
        UploadedFile u = new UploadedFile(file.getOriginalFilename(),
                        Long.valueOf(file.getSize()).intValue(),
                        "http://localhost:8080/spring-fileupload-tutorial/resources/" + file.getOriginalFilename());

        uploadedFiles.add(u);
        
        String output_switch = request.getParameter("getowner");
        //System.out.println(output_switch + "abc\n");
        switch(output_switch){
            case "accumulator" : utilities.accumulator.accumulatorReport(filePath);
                                 break;
            case "codelist" : utilities.codelistreport.codelist(filePath);
                                    break;
            case "ddf" : utilities.DDFgen.ddfgen(filePath);
                            break;
            case "mapversiondowngrade" : utilities.mapversiondowngrade.mapversion(filePath);
                                break;
            case "xmlsplitter" : utilities.xmlsplitter.xmlsplit(filePath);
                                 break;
            case "codelistcreator" : utilities.xmltoExcel.xmlToexcel(filePath);
                                break;
            //one more case (Excel) for vice-versa conversion in codelist creator with name : codelist_creator__                    
            default : System.out.println("Default Case.\n");
        }
        
        return uploadedFiles;
    }
}