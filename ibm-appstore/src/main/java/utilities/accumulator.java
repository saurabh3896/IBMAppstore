package utilities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class accumulator{
    
    public static void accumulatorReport(String name){
        List<String> initial_list = new ArrayList<>();
        String result_string = "", all_content = "", accumulators = "", pattern = "", pattern1 = "", pattern2 = "", pattern3 = "", pattern4 = "", put = "", temp_reg = "";
        boolean flag = false, had_accumulator = false, had_accumulators = false;
        String line;
        try(
            InputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);){
            while((line = br.readLine()) != null){
                if(line.contains("<Accumulators>")){
                    had_accumulators = true;
                }
                if(line.contains("</Accumulators>")){
                    had_accumulators = false;
                }
                if(had_accumulators == true && !line.contains("<Accumulators>")){    		
                    accumulators += (line + "\n");
                }
                if(line.contains("<Field>")){
                    flag = true;
                    all_content += (line + "\n");
                }
                if(line.contains("<UseAccumulator>")){
                    had_accumulator = true;
                }
                if(line.contains("</Field>")){
                    flag = false;
                    all_content += (line + "\n");
                    if(had_accumulator == true){
                        initial_list.add(all_content);
                        had_accumulator = false;
                        flag = false;
                        all_content = "";
                    }
                    else{
                        had_accumulator = false;
                        flag = false;
                        all_content = "";
                    }
                }
                if(flag == true){
                    all_content += (line + "\n");
                }
            }
        }
        catch(Exception e){
        }
        List<String> final_list = new ArrayList<>(), explicit_rule = new ArrayList<>();
        for(int i = 0;i < initial_list.size();i++){
            String temp_get = "";
            pattern = "(<Name>.*</Name>)";
            pattern1 = "(<Description>.*</Description>)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(initial_list.get(i));
            pattern4 = "<ExplicitRule>.*[\n]*.*</ExplicitRule>";      
            if(m.find())
                temp_get += m.group(0);
            r = Pattern.compile(pattern4);
            m = r.matcher(initial_list.get(i));	
            if(m.find()){
                String rule = temp_get + "\n";
                if(m.group(0).contains("ntoa")){
                    rule += m.group(0) + "\n";
                    rule = rule.replace("Name", "FieldName");
                    rule = rule.replace("ExplicitRule", "AccumulatorExtendedRule");
                    final_list.add(rule);
                }
            }
            r = Pattern.compile(pattern1);
            m = r.matcher(initial_list.get(i));
            temp_get += "\n";
            if(m.find())
                temp_get += m.group(0);
            pattern2 = "<Accumulator>(?:[^\n]*(\n+))+</Accumulator>";
            r = Pattern.compile(pattern2);
            m = r.matcher(initial_list.get(i));
            if(m.find()){
                temp_reg = m.group(0);
                temp_reg = temp_reg.replace("<Accumulator>", "");
                temp_reg = temp_reg.replace("</Accumulator>", "");
                temp_reg = temp_reg.replace("\n", "");
                temp_reg = temp_reg.replace("><", ">\n<");
                String parts[] = temp_reg.split(">\n");        
                put = temp_get;
                int count = 0;
                for(String temp : parts){
                    if(!">".equals(temp.substring(temp.length() - 1))){
                        temp += '>';
                    }       	
                    if(temp.contains("<AccumulatorID>")){
                        if(count == 0){ 
                            //System.out.println(temp);
                        }
                        else{
                            final_list.add(put);
                            put = temp_get;        			
                        }
                    }
                    if(count == parts.length - 1){
                        put += temp;
                        final_list.add(put);
                        break;
                    }
                    put += temp;
                    count++;
                }        
            }
        }    
        for(int i = 0;i < final_list.size();i++){
            String temp = final_list.get(i);
            if(temp.contains("AccumulatorExtendedRule")){
                continue;
            }
            temp = temp.replace("Name", "FieldName");
            temp = temp.replace("Description", "FieldDescription");
            temp = temp.replace(">>", ">");
            temp = temp.replace("><", ">\n<");
            if(!temp.contains("<AccumulatorAlternate>")){
                temp += "\n<AccumulatorAlternate></AccumulatorAlternate>";
            }
            else{
            }
            final_list.set(i, temp);
        }
        accumulators = accumulators.replace("<Accumulator>", "");
        accumulators = accumulators.replace("\n", "");
        accumulators = accumulators.replace("><", ">\n<");
        String parts[] = accumulators.split("</Accumulator>");
        for(int i = 0;i < parts.length;i++){
            parts[i] = parts[i].replace("\n", "");
            parts[i] = parts[i].replace("><", ">\n<");
        }
        for(int i = 0;i < final_list.size();i++){
            for(String part : parts){
                pattern2 = "<AccumulatorID>(.*)</AccumulatorID>";
                Pattern r = Pattern.compile(pattern2);
                Matcher m = r.matcher(final_list.get(i));
                Matcher m0 = r.matcher(part);
                if(m.find() && m0.find()){
                    String capture = m.group(0), main_capture = "";
                    int cap1 = Integer.parseInt(m.group(1));
                    int cap2 = Integer.parseInt(m0.group(1));
                    if(cap2 == cap1){
                        pattern3 = "<AccumulatorName>(.*)</AccumulatorName>";
                        r = Pattern.compile(pattern3);
                        m = r.matcher(part);
                        if(m.find()){
                            main_capture = final_list.get(i);
                            main_capture = main_capture.replace(capture, capture + "\n" + m.group(0));
                            final_list.set(i, main_capture);
                        }
                    }
                }
            }
        }
        result_string = "<Result>\n\n";
        for(int i = 0;i < final_list.size();i++){
            result_string += final_list.get(i) + "\n\n";
        }
        result_string += "</Result>";
        result_string = result_string.replace("\n\n\n", "\n\n");
        String file_name;
        int index;
        Path p = Paths.get(name);
        file_name = p.getFileName().toString();
        file_name = createFolder.create(name) + "/" + file_name;
        index = file_name.lastIndexOf(".");
        file_name = file_name.substring(0, index);
        file_name += ".xml";
        try(PrintWriter out = new PrintWriter(file_name)){
            out.println(result_string);
        }
        catch(FileNotFoundException e){
        }
        File file = new File(name);
        zip.omtZip(file.getParent() + "/output" + "/", "output.zip", file.getParent() + "/");
    }
}
