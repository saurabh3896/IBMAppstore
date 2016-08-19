package utilities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class codelistreport{
    public static void codelist(String name){
	int index;
	String line, all_content = "", pattern = "", pattern1 = "", temp = "", temp_get = "", get1, get2, parts[];
	List<String> initial_list = new ArrayList<>(), final_list = new ArrayList<>();
	boolean flag = false;
	try(InputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);){
            while((line = br.readLine()) != null){
                if(line.contains("<PosRecord>")){
                    flag = true;
                }
                if(line.contains("</ExplicitRule>")){
                    flag = false;
                    all_content += (line + "\n");
                    if(all_content.contains("Select")){
                        initial_list.add(all_content);
                    }
                    all_content = "";
                }
                if(flag == true){
                    all_content += (line + "\n");
                }
            }
        }
        catch(Exception e){
        }
        temp_get += "<FieldName>FNAME</FieldName>\n<CodeList_Type>EXTERNAL CODELIST</CodeList_Type>\n" + 
        "<Codelist_Name> CODELISTNAME </Codelist_Name>\n" + "<Codelist_Values> CODELISTVALUE </Codelist_Values>\n";
        String file_name = "";
        int index1;
        Path pa = Paths.get(name);
        file_name = pa.getFileName().toString();
        file_name = createFolder.create(name) + "/" + file_name;
        index1 = file_name.lastIndexOf(".");
        file_name = file_name.substring(0, index1);
        file_name += ".xml"; 
        all_content = "";
        for(int i = 0;i < initial_list.size();i++){
            pattern = "<Name>(.*)</Name>";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(initial_list.get(i));
            String get = temp_get;
            if(m.find())
                get = get.replace("FNAME", m.group(1));
            parts = initial_list.get(i).split(" ");
            index = Arrays.asList(parts).indexOf("Select");
            get1 = parts[index + 1];
            get = get.replace("CODELISTVALUE", get1);
            index = Arrays.asList(parts).indexOf("name");
            get1 = parts[index + 2];
            get = get.replace("CODELISTNAME", get1);			
            pattern1 = "<OnEnd>(.*[\n]*)+</OnEnd>";
            p = Pattern.compile(pattern1);
            m = p.matcher(initial_list.get(i));
            if(m.find()){
                get1 = m.group(0);
                get1 = get1.replace("<OnEnd>", "");
                get1 = get1.replace("</OnEnd>", "");
                get += "<CodeListExtendedRule>\n" + get1 + "\n</CodeListExtendedRule>\n</FieldEntry>\n</Result>\n";
            }
            get = "<Result>\n\n<FieldEntry>\n" + get;
            all_content += get;
        }
        try(PrintWriter out = new PrintWriter(file_name)){
            out.println(all_content);
        }
        catch(FileNotFoundException e){
        }
        File file = new File(name);
        zip.omtZip(file.getParent() + "/output" + "/", "output.zip", file.getParent() + "/");
    }
}
