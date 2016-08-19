package utilities;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class mapversiondowngrade{
    public static void mapversion(String name){
	String line, path, file_name = "";
        String all_content = "";
	File file = new File(name);
    	path = file.getAbsolutePath();
    	try(
            InputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);){
                while((line = br.readLine()) != null){
                    if(line.contains("ErrorForNotUsed") == false && line.contains("SuspendGroupProcessing") == false
                        && line.contains("SWIFTValidation") == false && line.contains("KeepTrailingZeroes") == false
                        && line.contains("GroupChoiceType") == false && line.contains("NotUsed") == false){
                        if(line.contains("SerializationVersion")){
                            all_content += "<SerializationVersion>823</SerializationVersion>\n";
                        }
                        else{
                            all_content += (line + "\n");
                        }
                    }		    
                }
            }
        catch(Exception e){
        }
        int index;
        Path p = Paths.get(name);
        file_name = p.getFileName().toString();
        file_name = createFolder.create(name) + "/" + file_name;
        index = file_name.lastIndexOf(".");
        file_name = file_name.substring(0, index);
        file_name += ".xml";   
        try(PrintWriter out = new PrintWriter(file_name)){
            out.println(all_content);
        }
        catch(FileNotFoundException e){
        }
        File file_ = new File(name);
        zip.omtZip(file_.getParent() + "/output" + "/", "output.zip", file_.getParent() + "/");
    }
}