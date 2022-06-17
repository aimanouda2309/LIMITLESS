package gui;


import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import dbconnector.dbConnection;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.ScrollEvent;
import javafx.util.StringConverter;
import main.Maze;

public class SizeOfMazeController  implements Initializable{
	public static int wid,heig;
	@FXML
	private TextField ID;
	@FXML
	private ChoiceBox<String> Materials;
	@FXML
	private Button Savebtn;
	@FXML
	private Button BackBtn;
	@FXML
	private TextField Pay;
	@FXML
	private Label FinalPrice;
	@FXML
	private DatePicker date;
	@FXML
	private TextField Hours;
	@FXML
	private TextField Minutes;	
	@FXML
	private TextField FirstName;
	@FXML
	private TextField LastName;
	@FXML
	private TextField Teacher;
	@FXML
	private ChoiceBox<String> Type;
	private float priceperminute;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
date.setValue(LocalDate.now());
		UnaryOperator<TextFormatter.Change> filter = c -> {
		    String text = c.getControlNewText();
		    if (validEditingState.matcher(text).matches()) {
		        return c ;
		    } else {
		        return null ;
		    }
		};

		StringConverter<Double> converter = new StringConverter<Double>() {

		    @Override
		    public Double fromString(String s) {
		        if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
		            return 0.0 ;
		        } else {
		            return Double.valueOf(s);
		        }
		    }


		    @Override
		    public String toString(Double d) {
		        return d.toString();
		    }
		};

		TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
		TextFormatter<Double> textFormatter2 = new TextFormatter<>(converter, 0.0, filter);
		TextFormatter<Double> textFormatter3 = new TextFormatter<>(converter, 0.0, filter);

		Pay.setTextFormatter(textFormatter);
		Hours.setTextFormatter(textFormatter2);
		Minutes.setTextFormatter(textFormatter3);

		//Cash, Bit, Bank, Check, Other
		/*Pay.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d.*")) {
                	Pay.setText(newValue.replaceAll("[^\\d.]", ""));
                }
            }
        });*/
		ArrayList<String> arr=dbConnection.getmaterials();
		arr.forEach((n) -> Materials.getItems().add(n));
		Type.getItems().add("Cash");
		Type.getItems().add("Bit");
		Type.getItems().add("Bank");
		Type.getItems().add("Check");
		Type.getItems().add("Other");
		Materials.setValue(arr.get(0));
		Type.setValue(Type.getItems().get(0));
		Savebtn.setVisible(false);
		Pay.setVisible(false);
		
	}
	
	public void Calculate(ActionEvent event) throws Exception {
		//System.out.println("s");
		priceperminute=dbConnection.getPrice(ID.getText(),Materials.getSelectionModel().getSelectedItem());
		if(priceperminute==-1) {
			 Alert a = new Alert(AlertType.NONE);
			 a.setAlertType(AlertType.WARNING);
	            a.setContentText("No price for ID: "+ID.getText()+" for Material: "+Materials.getSelectionModel().getSelectedItem());
	            // show the dialog
	            a.show();
		}
		else {
			FinalPrice.setText("Price: "+Float.toString(((Float.parseFloat(Hours.getText())*60+Float.parseFloat(Minutes.getText()))*priceperminute)));
			Savebtn.setVisible(true);
			Pay.setVisible(true);
		}
	}
	
	public void BackToHome(ActionEvent event) throws Exception {
		//System.out.println("s");
		
		NavigateController Backbtn=new NavigateController();
		Backbtn.openScene(event,"/gui/Home.fxml",849,670,"Home");
	}
	
	public void SavePrice(ActionEvent event) throws Exception {
        Alert a = new Alert(AlertType.NONE);
		if(ID.getText().equals("") || Pay.getText().equals("")|| FirstName.getText().equals("")|| LastName.getText().equals("")||Teacher.getText().equals("")) {
			a.setAlertType(AlertType.WARNING);
            a.setContentText("Please fill all the fields");
            // show the dialog
            a.show();
		}else {
			//System.out.println("JJ"+FinalPrice.getText());
		String result=dbConnection.SavePrice(ID.getText(),Materials.getSelectionModel().getSelectedItem(),FirstName.getText(),LastName.getText(),Float.parseFloat(Hours.getText()),Float.parseFloat(Minutes.getText()),((Float.parseFloat(Hours.getText())*60+Float.parseFloat(Minutes.getText()))*priceperminute),Float.parseFloat(Pay.getText()),Teacher.getText(),Type.getSelectionModel().getSelectedItem(),date.getValue());
		if(result.equals("Details is Already in our system")) {
			a.setAlertType(AlertType.WARNING);
            a.setContentText("Details is Already in our system");
            // show the dialog
            a.show();
		}
		else if(result.equals("successful")){
			// set alert type
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("Payment saved successfully");
            // show the dialog
            a.show();
		}
		else {
			a.setAlertType(AlertType.ERROR);
            a.setContentText("Check your connection");
            // show the dialog
            a.show();
		}
		}
	}	
}
