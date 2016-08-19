package utilities;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DDFgen{
    public static void ddfgen(String name){
        List<String> get_parameters_first, get_parameters_second;
        String template_header = "<POSREC ACTIVE=\"YES\" DESCRIPTION=\"DFN\""
                + " FLOATING=\"no\" LOOPCONTROL=\"normal\" MAXLOOP=\"MAXL__\""
                + " MINLOOP=\"MINL__\" NAME=\"NAME__\" TAG=\"TAG__\""
                + " TAGPOS=\"TAGPOS__\">",
               template_inside;
        template_inside = "<POSFIELD ACTIVE=\"YES"
                + "\" DESCRIPTION=\"__DES\" FORMAT=\"FOR__\" JUSTIFICATION=\"left\""
                + " LENGTH=\"LEN_FLOAT\" MANDATORY=\"MAN_DAT\" MAXDATALEN=\"LEN_FLOAT\""
                + " MINDATALEN=\"1\" NAME=\"NAME__\" STARTPOS=\"STARTPOS_FLOAT\" TYPE="
                + "\"DATA_TYPE\"/>";
        String all_content = "", temp, final_string = "";
        try{
            boolean correct_format = true, new_header, add_in_file = false;
            String matcher;
            int count = 0;
            try (FileInputStream file = new FileInputStream(new File(name))){
                HSSFWorkbook workbook = new HSSFWorkbook(file);
                HSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                while(rowIterator.hasNext() && count <= 1){
                    matcher = "";
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while(cellIterator.hasNext()){
                        Cell cell = cellIterator.next();
                        matcher += cell.getStringCellValue();
                    }
                    if(count == 0){
                        correct_format = matcher.equals("RecordNameDescription"
                                + "RecordTagTagPositionMinMax");
                    }
                    if(count == 1){
                        correct_format = matcher.equals("FieldNameField"
                                + "DescriptionStartPositionFieldLengthMandatory"
                                + "TypeFormat");
                    }
                    count++;
                }
                if(correct_format){
                    int last_column_number = -1;
                    for(Row row : sheet){
                        last_column_number = Math.max(last_column_number, row.getLastCellNum());
                    }
                    count = 0;
                    for(Row row : sheet){
                        new_header = false;
                        if(2 < count){
                            get_parameters_first = new ArrayList<>();
                            get_parameters_second = new ArrayList<>();
                            for(int cn = 0; cn < last_column_number;cn++){
                                Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
                                if(cn == 0){
                                    new_header = cell.getCellType() != Cell.CELL_TYPE_BLANK;
                                }
                                if(new_header){
                                    if(cell.getCellType() != Cell.CELL_TYPE_BLANK){                                    	
                                    	add_in_file = true;
                                        get_parameters_first.add(cell.toString());
                                    }
                                }
                                else{
                                    if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                        add_in_file = false;
                                        get_parameters_second.add(cell.toString());
                                    }
                                }
                            }
                            if(add_in_file){
                            	if( !"".equals(all_content)){
                                	all_content += "</POSREC>\n";
                                }
                            	add_in_file = false;
                            	temp = template_header;
                            	temp = temp.replace("DFN", get_parameters_first.get(1));
                            	temp = temp.replace("MAXL__", get_parameters_first.get(5));
                            	temp = temp.replace("MINL__", get_parameters_first.get(4));
                            	temp = temp.replace("NAME__", get_parameters_first.get(0));
                            	temp = temp.replace("TAG__", get_parameters_first.get(2));
                            	temp = temp.replace("TAGPOS__", get_parameters_first.get(3));
                            	all_content += (temp + "\n");
                            }
                            else{
                            	temp = template_inside;
                            	temp = temp.replace("__DES", get_parameters_second.get(1));
                            	temp = temp.replace("FOR__", get_parameters_second.get(6));
                            	temp = temp.replace("LEN_FLOAT", get_parameters_second.get(3));
                            	temp = temp.replace("MAN_DAT", get_parameters_second.get(4));
                            	temp = temp.replace("NAME__", get_parameters_second.get(0));
                            	temp = temp.replace("STARTPOS_FLOAT", get_parameters_second.get(2));
                            	temp = temp.replace("DATA_TYPE", get_parameters_second.get(5));
                            	all_content += (temp + "\n");
                            }
                        }                        
                        count++;
                    }
                }
                else{
                    System.out.println("Please Upload a Valid Excel File"
                            + "Invalid Excel Sheet Format, ");
                }
            }
		}
        catch(Exception e){
        }
        all_content += "</POSREC>\n";
        final_string = "<GENTRANDDF VERSION=\"1.0\">\n<POSFILE ACTIVE=\"yes\""
                + " DELIM1=\"0x0d\" DELIM2=\"0x0a\" NAME=\"POSFILE\""
                + " RECLENGTH=\"0\">\n" + all_content + "</POSFILE>\n</GENTRANDDF>";
        String file_name = "";
        int index;
        Path p = Paths.get(name);
        file_name = p.getFileName().toString();
        file_name = createFolder.create(name) + "/" + file_name;
        index = file_name.lastIndexOf(".");
        file_name = file_name.substring(0, index);
        file_name += ".ddf";
        try(PrintWriter out = new PrintWriter(file_name)){
            out.println(final_string);
        }
        catch(FileNotFoundException e){
        }
        File file = new File(name);
        zip.omtZip(file.getParent() + "/output" + "/", "output.zip", file.getParent() + "/");
    }
}