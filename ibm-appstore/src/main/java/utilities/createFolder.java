package utilities;

import java.io.File;

public class createFolder{
    /**
     *
     * @param file_name
     * @return
     */
    public static String create(String file_name){
        String directory;
        File file, theDir;
        file = new File(file_name);
        theDir = new File(file.getParent() + "/output");
        directory = file.getParent() + "/output";
        // if the directory does not exist, create it
        if(!theDir.exists()){
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
            }
        }
        return directory;
    }
}