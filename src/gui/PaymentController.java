package gui;

import java.net.URL;


import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dbconnector.dbConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.Maze;
import util.PatientPerMaterial;
import util.Payment;
import util.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class PaymentController implements Initializable{
	@FXML
	private TableView<Payment> paymentstable;
	@FXML private TableColumn<Payment, String> ID;
    @FXML private TableColumn<Payment, String> FirstName;
    @FXML private TableColumn<Payment, String> LastName;
    @FXML private TableColumn<Payment, Date> Date;    
    @FXML private TableColumn<Payment, String> Material;
    @FXML private TableColumn<Payment, Integer> Hours;
    @FXML private TableColumn<Payment, Integer> Minutes;
    @FXML private TableColumn<Payment, Float> Price;
    @FXML private TableColumn<Payment, Float> Pay;
    @FXML private TableColumn<Payment, String> Final;
    @FXML private TableColumn<Payment, String> Teacher;
    @FXML private TableColumn<Payment, String> Type;
    @FXML
	private TextField Search;
    @FXML
	private TextField TeacherSearch;
    @FXML
	private Label AllPrice;
	@FXML
	private ChoiceBox<String> Sort;
	private float price=0;
	private float paid=0;
    private Payment rowData=null;
    public static int count=0;
	public static ArrayList<Payment> arr;
	public PaymentController() {
	//	chnagetable();
	}
	public void DeletePayment(ActionEvent event) throws Exception {
		//System.out.println("s");
		Alert a = new Alert(AlertType.NONE);
		Payment row=null;
		row=paymentstable.getSelectionModel().getSelectedItem();  
		if(row==null){
			// set alert type
            a.setAlertType(AlertType.WARNING);
            a.setContentText("Please select row from the list");
            // show the dialog
            a.show();
		}
		else {
		boolean result=dbConnection.DeletePayment(row);

		if(result){
			// set alert type
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("Payment deleted successfully");
            // show the dialog
            a.show();
            if(Search.getText().equals("")) {
            	if(TeacherSearch.getText().equals(""))
            		fixTable(Sort.getValue(),null,null);
            	else
            		fixTable(Sort.getValue(),null,TeacherSearch.getText());
            }
            else {
            	if(TeacherSearch.getText().equals(""))
            		fixTable(Sort.getValue(),Search.getText(),null);
            	else
            	fixTable(Sort.getValue(),Search.getText(),TeacherSearch.getText());
            }
            //arr=dbConnection.TakePayments();
            //paymentstable.getItems().setAll(arr);
		}
		else {
			a.setAlertType(AlertType.ERROR);
            a.setContentText("Check your connection");
            // show the dialog
            a.show();
		}
		}
		//NavigateController Backbtn=new NavigateController();
		//Backbtn.openScene(event,"/gui/Home.fxml");
	}
	
	public void BackToHome(ActionEvent event) throws Exception {
		//System.out.println("s");
		
		NavigateController Backbtn=new NavigateController();
		Backbtn.openScene(event,"/gui/Home.fxml",849,670,"Home");
	}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	count=0;
    	ID.setCellValueFactory(new PropertyValueFactory<Payment, String>("ID"));
    	FirstName.setCellValueFactory(new PropertyValueFactory<Payment, String>("FirstName"));
    	LastName.setCellValueFactory(new PropertyValueFactory<Payment, String>("LastName"));
    	Date.setCellValueFactory(new PropertyValueFactory<Payment, Date>("date"));
    	Material.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaterials()));
    	Teacher.setCellValueFactory(new PropertyValueFactory<Payment, String>("Teacher"));
    	//Material.setCellValueFactory(new PropertyValueFactory<Payment, String>("Material"));
    	Hours.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("Hours"));
    	Minutes.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("Minutes"));
    	Price.setCellValueFactory(new PropertyValueFactory<Payment, Float>("Price"));
    	Pay.setCellValueFactory(new PropertyValueFactory<Payment, Float>("Pay"));
    	Type.setCellValueFactory(new PropertyValueFactory<Payment, String>("Type"));
    	Final.setCellValueFactory(new PropertyValueFactory<Payment, String>("Final"));
       	for(int i=0;i<arr.size();i++) {  
       		//System.out.println(arr.get(i).getTeacher()+arr.get(i).getType());
    		price+=arr.get(i).getPrice();
    		paid+=arr.get(i).getPay();
    	}
    	paymentstable.getItems().setAll(arr);

    	Final.setCellFactory(column -> {
    	        return new TableCell<Payment, String>() {
    	            @Override
    	            protected void updateItem(String item, boolean empty) {
    	                super.updateItem(item, empty);
    	                if (item == null || empty) {
    	                    setText(null);
    	                    setGraphic(null);
    	                } 
    	                else {
    	                    setText(item.toString());
    	                    setGraphic(null);

    	                    if (Float.parseFloat(item)>0) {
    	                        setTextFill(Color.GREEN);
    	                    }
    	                    else if(Float.parseFloat(item)<0){
    	                        setTextFill(Color.RED);   	                  
    	                    }
    	                }
    	            }};
    	    });
    	AllPrice.setText("Should have received "+Float.toString(price)+"\nAlready have received "+Float.toString(paid));
    	paymentstable.setRowFactory( tv -> {
    	    TableRow<Payment> row = new TableRow<>();  	   
    	        if (!row.isEmpty()) {
    	        	rowData = row.getItem();
    	        	if(Float.parseFloat(row.getItem().getFinal())>0) {
                        row.setStyle("-fx-background-color:lightcoral");
    	        	}else if(Float.parseFloat(row.getItem().getFinal())<0) {
    	        		row.setTextFill(Color.RED);
    	        	}
    	            
    	            //ArrayList<Mygenerate> arr=dbConnection.getdataofgenerate(User.currentUser,rowData);
    	            //Maze maze=new Maze();
    	    		//maze.setRowCol(arr.get(0).getWidth(), arr.get(0).getHeight());
    	    		//maze.MyHistroySwing(arr);
    	    		
    	            //dbConnection.getdataofgenerate(User.currentUser,rowData);
    	    }
    	    return row ;
    	});
    	Sort.getItems().addAll("Today","Last Month", "Last 3 Months", "All");
		Sort.setValue("All");		
    }

    public void fixTable(String time, String ID,String EmployeeID) {
    	AllPrice.setTextFill(Color.BLACK);
    	if(time.equals("Today")) {
    		 arr=dbConnection.TakePayments(LocalDateTime.now().minus(Period.ofDays(1)),ID,EmployeeID);
             paymentstable.getItems().setAll(arr);
             if(ID==null){
             	price=0;
             	paid=0;
             	for(int i=0;i<arr.size();i++) {
             		paid+=arr.get(i).getPay();
             		price+=arr.get(i).getPrice();
             	}
             	AllPrice.setText("Should have received "+Float.toString(price)+" for the last day\nAlready have recieved "+Float.toString(paid));
             
             }
             else {
             	pricemessage("today",ID);
             }
    	}
    	else if (time.equals("Last Month")) {
            arr=dbConnection.TakePayments(LocalDateTime.now().minus(Period.ofDays(30)),ID,EmployeeID);
            paymentstable.getItems().setAll(arr);
            if(ID==null){
            	price=0;
            	paid=0;
            	for(int i=0;i<arr.size();i++) {
            		paid+=arr.get(i).getPay();
            		price+=arr.get(i).getPrice();
            	}
            	AllPrice.setText("Should have received "+Float.toString(price)+" for the last month\nAlready have recieved "+Float.toString(paid));
            
            }
            else {
            	pricemessage("last month",ID);
            }

        }else if(time.equals("Last 3 Months")){
        	arr=dbConnection.TakePayments(LocalDateTime.now().minus(Period.ofDays(90)),ID,EmployeeID);
            paymentstable.getItems().setAll(arr);
            if(ID==null){
            	price=0;
            	paid=0;
            	for(int i=0;i<arr.size();i++) {
            		paid+=arr.get(i).getPay();
            		price+=arr.get(i).getPrice();
            	}
            	AllPrice.setText("Should have received "+Float.toString(price)+" for the last 3 months\nAlready have recieved "+Float.toString(paid));    
            }
            else {
            	pricemessage("last 3 months",ID);
            }
        }else {
        	arr=dbConnection.TakePayments(ID,EmployeeID);
            paymentstable.getItems().setAll(arr);
            if(ID==null){
            	price=0;
            	paid=0;
            	for(int i=0;i<arr.size();i++) {
            		paid+=arr.get(i).getPay();
            		price+=arr.get(i).getPrice();
            	}
            	AllPrice.setText("Should have received "+Float.toString(price)+"\nAlready have recieved "+Float.toString(paid));
   
            }
            else {
            pricemessage("all classes",ID);
            }
      
        }
    }
    public void pricemessage(String months,String ID) {
    	price=0;
    	paid=0;
    	for(int i=0;i<arr.size();i++) {
    		price+=arr.get(i).getPrice();
    		paid+=arr.get(i).getPay();
    	}
    	if(price-paid>0) {
    		AllPrice.setTextFill(Color.RED);
    	AllPrice.setText(ID+" must pay: "+price+" for "+months+"\nHe paid "+Float.toString(paid)+" Debt: -"+(price-paid));
    	}
    	else if(price-paid<0){
    		AllPrice.setTextFill(Color.GREEN);
        	AllPrice.setText(ID+" must pay: "+price+" for "+months+"\nHe paid "+Float.toString(paid)+" Debt: +"+(paid-price));
    	}
    	else {
    		AllPrice.setTextFill(Color.BLACK);
        	AllPrice.setText(ID+" must pay: "+price+" for "+months+"\nHe paid "+Float.toString(paid)+" Debt: "+(price-paid));
    	}
    }
    public void Search(ActionEvent event) throws Exception {
    	if(Search.getText().equals("")){
    		if(TeacherSearch.getText().equals(""))
    		fixTable(Sort.getSelectionModel().selectedItemProperty().getValue(),null,null);
    		else
        		fixTable(Sort.getSelectionModel().selectedItemProperty().getValue(),null,TeacherSearch.getText());	
    	}else {
    		if(TeacherSearch.getText().equals(""))
        		fixTable(Sort.getSelectionModel().selectedItemProperty().getValue(),Search.getText(),null);
    		else
        		fixTable(Sort.getSelectionModel().selectedItemProperty().getValue(),Search.getText(),TeacherSearch.getText());	
    	
    	}
	}
    
	public void MoveToLogin(ActionEvent event) throws Exception {
		NavigateController signout=new NavigateController();
		signout.openScene(event,"/gui/login.fxml",330,420,"Log-In");
	}
	

	
}
