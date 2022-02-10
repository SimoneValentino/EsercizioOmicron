package es.omicron.esercizioomicron;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ServiceMenuStarter {

    public static void main(String[] args) {

        
       String userDirectory = System.getProperty("user.dir");  // PATH WD
  //SETTAGGIO FILE PROPERTIES    
        Properties properties = new Properties();
        FileReader propInput;
        File prop = new File(userDirectory+"\\config.properties");

        try {
            
            propInput = new FileReader(prop);
            properties.load(propInput);        
            properties.setProperty("inputFileDir", userDirectory +"\\input" );
            properties.setProperty("outputFileDir", userDirectory +"\\output");
      
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
                }  
        //----------FINE-----
        //CONTROLLO SE FILE JSON ESISTE
        File inputFile = new File(properties.getProperty("inputFileDir")+"\\ServiceMenu.json");
             if(!inputFile.exists()){
             
                 System.out.println("Il file di input nel percorso : \n" + inputFile.getParentFile() + 
                 "\n non esiste.\n Il programma verra terminato in modo da permettere all'utente di inserire il file. ");
                 System.exit(0);
             }
    
             
        JsonReader reader = null;     
        try {
            
            reader = new JsonReader(new FileReader(inputFile));
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        
//DESERIALIZZAZIONE JSON + CONTO MAXDEPTH
        MenuContent json = new Gson().fromJson(reader, MenuContent.class);
        List<MenuNode> list = json.getNodes();
        json.setMaxDepth(MenuContent.depthCount(list, 0));
//----------FINE-----     
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Menu " + json.getVersion());


// CREAZIONE HEADER DEL FILE EXCELL
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i <= json.getMaxDepth(); i++) {
            rowHeader.createCell(i).setCellValue(i);
        }
        rowHeader.createCell(json.getMaxDepth() + 1).setCellValue("ServiceID");
        rowHeader.createCell(json.getMaxDepth() + 2).setCellValue("NodeName");
        rowHeader.createCell(json.getMaxDepth() + 3).setCellValue("NodeType");
        rowHeader.createCell(json.getMaxDepth() + 4).setCellValue("GroupType");
        rowHeader.createCell(json.getMaxDepth() + 5).setCellValue("FlowType");
        rowHeader.createCell(json.getMaxDepth() + 6).setCellValue("ResourceId");

//----------FINE-----


// CREAZIONE FILE EXCELL + AUTOSIZE
        excelCreation(json, list, sheet, 0, 1);      
        for (int i = 0; i <= json.getMaxDepth()+6; i++) {
            sheet.autoSizeColumn(i);
        }      
         File output = new File (properties.getProperty("outputFileDir"));
         output.mkdir();
        try ( FileOutputStream outputStream = new FileOutputStream(properties.getProperty("outputFileDir")+"\\JavaBooks.xlsx")) {
            workbook.write(outputStream);
        } catch (FileNotFoundException ex) {
           System.out.println(ex.getMessage() + Arrays.toString(ex.getStackTrace()));
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + Arrays.toString(ex.getStackTrace()));
        }

    }
//----------FINE-----

    public static int excelCreation(MenuContent json, List<MenuNode> nodes, XSSFSheet sheet, int depth, int nodo) {

        for (MenuNode node : nodes) {
            Row row = sheet.createRow(nodo);
            if (node.getNodeType().equals("service")) {
                row.createCell(json.getMaxDepth() + 1).setCellValue(node.getNodeId());

            }
            row.createCell(depth).setCellValue("x");

            row.createCell(json.getMaxDepth() + 2).setCellValue(node.getNodeName());
            row.createCell(json.getMaxDepth() + 3).setCellValue(node.getNodeType());
            row.createCell(json.getMaxDepth() + 4).setCellValue(node.getGroupType());
            row.createCell(json.getMaxDepth() + 5).setCellValue(node.getFlowType());
            if (node.getResource() != null) {
                row.createCell(json.getMaxDepth() + 6).setCellValue(node.getResource().getId());
            }
            if (node.getNodes() != null) {
                nodo = excelCreation(json, node.getNodes(), sheet, depth + 1, nodo + 1);
            } else {

                nodo += 1;
            }

        }
        return nodo;
    }
}
