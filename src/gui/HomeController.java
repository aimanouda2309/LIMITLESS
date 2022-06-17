package gui;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.*;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import dbconnector.dbConnection;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.PatientPerMaterial;
import util.Payment;
import javafx.scene.Node;

public class HomeController {
	
	private AnchorPane lowerAnchorPane;
	private SplitPane splitpane;
	public HomeController() {
		
	}

	public void SaveExcelPayments() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet
        = workbook.createSheet("Payments");
        XSSFWorkbook workbook2 = new XSSFWorkbook();
        XSSFSheet spreadsheet2
        = workbook2.createSheet("Patients");
		ArrayList<Payment> arr=new ArrayList<Payment>();
		ArrayList<PatientPerMaterial> patient=new ArrayList<PatientPerMaterial>();
		patient=dbConnection.SaveExcelPatient();
		arr=dbConnection.SaveExcel();
		 // creating a row object
        XSSFRow row;
        XSSFRow rowpatient;
        // This data needs to be written (Object[])
        Map<String, Payment> PaymentsData
            = new TreeMap<String, Payment>();
        
        Map<String, PatientPerMaterial> PatientData
        = new TreeMap<String, PatientPerMaterial>();
        
        for(int i=0;i<arr.size();i++) {
        	PaymentsData.put(
                    Integer.toString(i+2),
                    new Payment ( arr.get(i).getID(), arr.get(i).getFirstName(), arr.get(i).getLastName(),arr.get(i).getDate(),arr.get(i).getMaterials(),arr.get(i).getHours(),arr.get(i).getMinutes(),arr.get(i).getPrice(),arr.get(i).getPay(),Float.toString(arr.get(i).getPay()-arr.get(i).getPrice()),arr.get(i).getTeacher(),arr.get(i).getType() ));
        }
   
        for(int i=0;i<patient.size();i++) {
        	PatientData.put(
                    Integer.toString(i+2),
                    new PatientPerMaterial ( patient.get(i).getID(), patient.get(i).getFirstName(), patient.get(i).getLastName(),patient.get(i).getPrice(),patient.get(i).getMaterial()));
        }
        Set<String> keyid = PaymentsData.keySet();
        Set<String> keyid2 = PatientData.keySet();
        int rowid2 = 0;
        
        // writing the data into the sheets...
        int cellid2 = 0;
        rowpatient = spreadsheet2.createRow(rowid2++);
        Cell cellPatient1 = rowpatient.createCell(cellid2++);
        cellPatient1.setCellValue("ID");
        Cell cellPatient2 = rowpatient.createCell(cellid2++);
        cellPatient2.setCellValue("FirstName");
        Cell cellPatient3 = rowpatient.createCell(cellid2++);
        cellPatient3.setCellValue("LastName");
        Cell cellPatient4 = rowpatient.createCell(cellid2++);
        cellPatient4.setCellValue("Price");
        Cell cellPatient5 = rowpatient.createCell(cellid2++);
        cellPatient5.setCellValue("Material");
        
        int rowid = 0;
  
        // writing the data into the sheets...
        int cellid = 0;
        row = spreadsheet.createRow(rowid++);
        Cell cell1 = row.createCell(cellid++);
        cell1.setCellValue("ID");
        Cell cell2 = row.createCell(cellid++);
        cell2.setCellValue("FirstName");
        Cell cell3 = row.createCell(cellid++);
        cell3.setCellValue("LastName");
        Cell cell4 = row.createCell(cellid++);
        cell4.setCellValue("DATE");
        Cell cell5 = row.createCell(cellid++);
        cell5.setCellValue("Material");
        Cell cell6 = row.createCell(cellid++);
        cell6.setCellValue("Hours");
        Cell cell7 = row.createCell(cellid++);
        cell7.setCellValue("Minutes");
        Cell cell8 = row.createCell(cellid++);
        cell8.setCellValue("Price");
        Cell cell9 = row.createCell(cellid++);
        cell9.setCellValue("Pay");
        Cell cell10 = row.createCell(cellid++);
        cell10.setCellValue("Final");
        Cell cell11 = row.createCell(cellid++);
        cell11.setCellValue("Teacher");
        Cell cell12 = row.createCell(cellid++);
        cell12.setCellValue("Type");
        
        for (String key : keyid) { 
        	cellid = 0;
            row = spreadsheet.createRow(rowid++);
            Payment objectArr = PaymentsData.get(key); 
            Cell cell13 = row.createCell(cellid++);
            cell13.setCellValue(objectArr.getID());
            Cell cell14 = row.createCell(cellid++);
            cell14.setCellValue(objectArr.getFirstName());
            Cell cell15 = row.createCell(cellid++);
            cell15.setCellValue(objectArr.getLastName());
            Cell cell16 = row.createCell(cellid++);
            cell16.setCellValue(objectArr.getDate().toString());
            Cell cell17 = row.createCell(cellid++);
            cell17.setCellValue(objectArr.getMaterials());
            Cell cell18 = row.createCell(cellid++);
            cell18.setCellValue(objectArr.getHours());
            Cell cell19 = row.createCell(cellid++);
            cell19.setCellValue(objectArr.getMinutes());
            Cell cell20 = row.createCell(cellid++);
            cell20.setCellValue(objectArr.getPrice());
            Cell cell21 = row.createCell(cellid++);
            cell21.setCellValue(objectArr.getPay());
            Cell cell22 = row.createCell(cellid++);
            cell22.setCellValue(objectArr.getFinal());
            Cell cell23 = row.createCell(cellid++);
            cell23.setCellValue(objectArr.getTeacher());
            Cell cell24 = row.createCell(cellid++);
            cell24.setCellValue(objectArr.getType());        
        }
        for (String key : keyid2) { 
        	cellid2 = 0;
        	rowpatient = spreadsheet2.createRow(rowid2++);
            PatientPerMaterial objectArr = PatientData.get(key); 
            Cell cellPatient6 = rowpatient.createCell(cellid2++);
            cellPatient6.setCellValue(objectArr.getID());
            Cell cellPatient7 = rowpatient.createCell(cellid2++);
            cellPatient7.setCellValue(objectArr.getFirstName());
            Cell cellPatient8 = rowpatient.createCell(cellid2++);
            cellPatient8.setCellValue(objectArr.getLastName());
            Cell cellPatient9 = rowpatient.createCell(cellid2++);
            cellPatient9.setCellValue(objectArr.getPrice());
            Cell cellPatient10 = rowpatient.createCell(cellid2++);
            cellPatient10.setCellValue(objectArr.getMaterial());
        }
        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        FileOutputStream out;
		try {
			out = new FileOutputStream(
			    new File("C:/savedPayments/"+System.currentTimeMillis()+".xlsx"));
			try {
				workbook.write(out);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOutputStream out2;
		try {
			out2 = new FileOutputStream(
			    new File("C:/savedPatients/"+System.currentTimeMillis()+".xlsx"));
			try {
				workbook2.write(out2);
				out2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void MoveToLogin(ActionEvent event) throws Exception {
		
		//System.out.println("s");
		NavigateController signout=new NavigateController();
		signout.openScene(event,"/gui/login.fxml",330,420,"Log-In");
	}
	public void SaveDayFinish(ActionEvent event) throws Exception {
		
		//PauseTransition pause = new PauseTransition(Duration.seconds(2));
	   // pause.setOnFinished(e ->{
	   // 	alert2.hide();
	   // });	    	
	    Thread t = new Thread(() -> {
	        //Here write all actions that you want execute on background
		    SaveExcelPayments();

	        Platform.runLater(() -> {
	            //Here the action where is finished the actions on background
	        });
	    });
	    	t.start();
	   // pause.play();
	}
	public void Generate(ActionEvent event) throws Exception {
		NavigateController MvGenerate=new NavigateController();
		MvGenerate.openScene(event,"/gui/SizeOfMaze.fxml",849,670,"Pay");
	}
	public void AddMaterial(ActionEvent event) throws Exception {
		NavigateController Material=new NavigateController();
		Material.openScene(event,"/gui/Material.fxml",849,670,"Add-Material");
	}
	public void MoveToHistory(ActionEvent event) throws Exception {
		PaymentController.arr=dbConnection.TakePayments();
		NavigateController signout=new NavigateController();
		signout.openScene(event,"/gui/History.fxml",849,670,"Payments");
		}
	public void MoveToMaterials(ActionEvent event) throws Exception {
		MaterialsListController.arr=dbConnection.TakeMaterialPerPatient("");
		NavigateController signout=new NavigateController();
		signout.openScene(event,"/gui/MaterialsList.fxml",849,670,"Materials-List");
	}
}