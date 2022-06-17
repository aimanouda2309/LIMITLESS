package gui;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dbconnector.dbConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import util.PatientPerMaterial;
import util.Payment;

public class MaterialsListController implements Initializable{
	@FXML
	private TableView<PatientPerMaterial> materialtable;
	@FXML private TableColumn<PatientPerMaterial, String> ID;
    @FXML private TableColumn<PatientPerMaterial, String> FirstName;
    @FXML private TableColumn<PatientPerMaterial, String> LastName;
    @FXML private TableColumn<PatientPerMaterial, String> Material;
    @FXML private TableColumn<PatientPerMaterial, Float> Price;
    @FXML
	private TextField Search;
    @FXML
	private Label AllPrice;
	private PatientPerMaterial rowData=null;
	public static ArrayList<PatientPerMaterial> arr;
	public MaterialsListController() {
	//	chnagetable();
	}
	public void DeleteMaterial(ActionEvent event) throws Exception {
		//System.out.println("s");
		Alert a = new Alert(AlertType.NONE);
		PatientPerMaterial row=null;
		row=materialtable.getSelectionModel().getSelectedItem();  
		if(row==null){
			// set alert type
            a.setAlertType(AlertType.WARNING);
            a.setContentText("Please select row from the list");
            // show the dialog
            a.show();
		}
		else {
		boolean result=dbConnection.DeleteMaterial(row);


		if(result){
			// set alert type
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("Material: "+row.getMaterial()+ "for ID: "+ row.getID()+"deleted successfully");
            // show the dialog
            a.show();
            fixTable(Search.getText());
		}
		else {
			a.setAlertType(AlertType.ERROR);
			 
            // show the dialog
            a.show();
		}
		}
	}
	
	public void BackToHome(ActionEvent event) throws Exception {
		//System.out.println("s");
		
		NavigateController Backbtn=new NavigateController();
		Backbtn.openScene(event,"/gui/Home.fxml",849,670,"Home");
	}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	ID.setCellValueFactory(new PropertyValueFactory<PatientPerMaterial, String>("ID"));
    	FirstName.setCellValueFactory(new PropertyValueFactory<PatientPerMaterial, String>("FirstName"));
    	LastName.setCellValueFactory(new PropertyValueFactory<PatientPerMaterial, String>("LastName"));
    	Material.setCellValueFactory(new PropertyValueFactory<PatientPerMaterial, String>("Material"));
    	Price.setCellValueFactory(new PropertyValueFactory<PatientPerMaterial, Float>("Price"));
    	
    	materialtable.getItems().setAll(arr);
    	materialtable.setRowFactory( tv -> {
    	    TableRow<PatientPerMaterial> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	        if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
    	        	rowData = row.getItem();
    	        }
    	    });
    	    return row ;
    	});
    }
    public void fixTable(String ID) {   
            arr=dbConnection.TakeMaterialPerPatient(ID);
            materialtable.getItems().setAll(arr);
      
    }
    public void Search(ActionEvent event) throws Exception {   	
    		fixTable(Search.getText());
	}
    
	public void MoveToLogin(ActionEvent event) throws Exception {
		NavigateController signout=new NavigateController();
		signout.openScene(event,"/gui/login.fxml",330,420,"Log-In");
	}
	

	
}
