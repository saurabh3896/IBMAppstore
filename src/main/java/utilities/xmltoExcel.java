package utilities;

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import jxl.*;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

public class xmltoExcel{
    public static void sheetAutoFitColumns(WritableSheet sheet){
        for(int i = 0; i < sheet.getColumns();i++) {
            Cell[] cells = sheet.getColumn(i);
            int longestStrLen = -1;
            if(cells.length == 0)
                continue;
            for(Cell cell : cells){
                if(cell.getContents().length() > longestStrLen){
                    String str = cell.getContents();
                    if(str == null || str.isEmpty())
                        continue;
                    longestStrLen = str.trim().length();
                }
                else{
                }
            }
            if(longestStrLen == -1)
                continue;
            if(longestStrLen > 255)
                longestStrLen = 255;
            CellView cv = sheet.getColumnView(i);
            cv.setSize(longestStrLen * 256 + 250);
            sheet.setColumnView(i, cv);
        }
    }
    
    public static void xmlToexcel(String name){
        int totalItems;
        totalItems = 0;
        boolean capture;
        capture = false;
        String line, content, list_name;
        content = "";
        try(
            InputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);){
            while((line = br.readLine()) != null){
                if(line.contains("<CODE_LIST_XREF_ITEM>")){
                    capture = true;
                }
                if(line.contains("</CODE_LIST_XREF_ITEM>")){
                    content += (line);
                    break;
                }
                if(capture){
                    content += (line);
                }
            }
        }
        catch(Exception e){
        }
        List<String> tagNames;
        String cells[][];
        tagNames = new ArrayList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(content)));
            NodeList nl = doc.getElementsByTagName("*");
            for(int i = 1;i < nl.getLength();i++){
                tagNames.add(nl.item(i).getNodeName());
            }
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(name));

            doc.getDocumentElement().normalize();
            Element firstPersonElement;
            Node firstPersonNode;
            NodeList listOfCodeListTags;
            listOfCodeListTags = doc.getElementsByTagName("CODE_LIST_XREF_ITEM");
            totalItems = listOfCodeListTags.getLength();
            cells = new String[totalItems][tagNames.size()];
            for(int i = 0;i < listOfCodeListTags.getLength();i++){                
                firstPersonNode = listOfCodeListTags.item(i);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){                    
                    firstPersonElement = (Element) firstPersonNode;
                    for(int j = 0;j < tagNames.size();j++){
                        NodeList temp_nodeList = firstPersonElement.getElementsByTagName(tagNames.get(j));
                        Element temp_Element = (Element) temp_nodeList.item(0);
                        NodeList text_finalNodeList = temp_Element.getChildNodes();
                        try{
                            cells[i][j] = ((Node) text_finalNodeList.item(0)).getNodeValue();
                        }
                        catch(NullPointerException e){
                            cells[i][j] = "";
                        }
                    }
                }
            }
            list_name = doc.getElementsByTagName("LIST_NAME").item(0).getTextContent();
            tagNames.add(0, "PARTNER_KEY");
            tagNames.add(1, "LIST_TYPE");
            tagNames.add(2, "LIST_NAME");
            String file_name;
            int index;
            Path p = Paths.get(name);
            file_name = p.getFileName().toString();
            file_name = createFolder.create(name) + "/" + file_name;
            index = file_name.lastIndexOf(".");
            file_name = file_name.substring(0, index);
            File file = new File(file_name + ".xls");
            Label label;
            try{
                WritableWorkbook writableWorkbook = Workbook.createWorkbook(file); 
                WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);
                WritableFont font;
                font = new WritableFont(WritableFont.createFont("Calibri"), 10);
                WritableCellFormat cellFormat;
                cellFormat = new WritableCellFormat(font);
                cellFormat.setBackground(jxl.format.Colour.WHITE, jxl.format.Pattern.SOLID);
                cellFormat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
                for(int i = 0;i < tagNames.size();i++){
                    label = new Label(i, 0, tagNames.get(i), cellFormat);
                    writableSheet.addCell(label);
                    label = new Label(i, 1, "", cellFormat);
                    writableSheet.addCell(label);
                }
                for(int i = 2;i < totalItems + 2;i++){
                    for(int j = 0;j < tagNames.size() + 2;j++){
                        switch(j){
                            case 0:
                                label = new Label(j, i, "", cellFormat);
                                writableSheet.addCell(label);
                                break;
                            case 1:
                                label = new Label(j, i, "", cellFormat);
                                writableSheet.addCell(label);
                                break;
                            case 2:
                                label = new Label(j, i, list_name, cellFormat);
                                writableSheet.addCell(label);
                                break;
                            default:
                                try{
                                    label = new Label(j, i, cells[i - 2][j - 3], cellFormat);
                                    writableSheet.addCell(label);
                                }
                                catch(ArrayIndexOutOfBoundsException e){
                                }   break;
                        }
                    }
                }
                sheetAutoFitColumns(writableSheet);
                writableWorkbook.write();
                writableWorkbook.close();
            }        
            catch (IOException e) {
            }
            catch (RowsExceededException e) {
            }
            catch (WriteException e) {
            }
        }
        catch(SAXParseException err){
            System.out.println("** Parsing error" + ", line "+ err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());
        }
        catch(SAXException e){
        }
        catch(ParserConfigurationException | IOException t){
        }
        File file = new File(name);
        zip.omtZip(file.getParent() + "/output" + "/", "output.zip", file.getParent() + "/");
    }
}