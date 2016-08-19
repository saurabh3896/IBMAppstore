package utilities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class xmlsplitter{
    public static void xmlsplit(String name){
	String line, path, file_name = "";
        String all_content = "", pattern = "", temp = "", get1 = "", get2, parts[];
	List<String> initial_list = new ArrayList<>();
	boolean flag = false;
	File file = new File(name);
    	path = file.getAbsolutePath();
	try(
            InputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);){
                while((line = br.readLine()) != null){
                    if(line.contains("<ORDERS05>")){
                        flag = true;
                    }
                    if(line.contains("</ORDERS05>")){
                        flag = false;
                        all_content += (line + "\n");
                        initial_list.add(all_content);
                        all_content = "";
                    }
                    if(flag == true){
                        all_content += (line + "\n");
                    }
                }
            }
        catch(Exception e){
        }
        pattern = "<.*>";
        Pattern p = Pattern.compile(pattern);
        Matcher m;
        for(int i = 0;i < initial_list.size();i++){
            m = p.matcher(initial_list.get(i));
            get2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            get2 += initial_list.get(i);
            if(m.find()){
                get1 = m.group(0);
                get1 = get1.replace(">", "");
                get1 = get1.replace("<", "");
            }
            Path path_ = Paths.get(name);
            file_name = (createFolder.create(name) + "/" + path_.getFileName().toString() + get1 + Integer.toString(i + 1) + ".xml");
            try(PrintWriter out = new PrintWriter(file_name)){
                out.println(get2);
            }
            catch(FileNotFoundException e){
            }
        }
        zip.omtZip(file.getParent() + "/output" + "/", "output.zip", file.getParent() + "/");
    }
}