package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import dbconnector.dbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import util.PatientPerMaterial;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

public class MaterialController implements Initializable{	
	@FXML
	private TextField ID;
	@FXML
	private TextField Price;
	@FXML
	private TextField FirstName;
	@FXML
	private TextField LastName;
	@FXML
	private ChoiceBox<String> Materials;
	@Override
    public void initialize(URL location, ResourceBundle resources) {	
		Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

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
		Price.setTextFormatter(textFormatter);
		ArrayList<String> arr=dbConnection.getmaterials();
		System.out.println(arr);
		arr.forEach((n) -> Materials.getItems().add(n));
		Materials.setValue(arr.get(0));
			}
	
	
	
	public void BackToHome(ActionEvent event) throws Exception {
		//System.out.println("s");
		
		NavigateController Backbtn=new NavigateController();
		Backbtn.openScene(event,"/gui/Home.fxml",849,670,"Home");
	}
	
	public void SavePrice(ActionEvent event) throws Exception {
		
		Alert a = new Alert(AlertType.NONE);
		if(ID.getText().equals("") || Price.getText().equals("")|| FirstName.getText().equals("")|| LastName.getText().equals("")) {
			a.setAlertType(AlertType.WARNING);
            a.setContentText("Please fill all the fields");
            // show the dialog
            a.show();
		}
		else {
			PatientPerMaterial newpatient=new PatientPerMaterial(ID.getText(),FirstName.getText(),LastName.getText(),Float.parseFloat(Price.getText()),Materials.getSelectionModel().getSelectedItem());
			boolean result=dbConnection.SaveMaterial(newpatient);
			if(result==false) {
   			a.setAlertType(AlertType.WARNING);
            a.setContentText("Material "+newpatient.getMaterial()+" already exist for this patient");
            // show the dialog
            a.show();
		}else {
			// set alert type
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("Material "+newpatient.getMaterial()+" for ID: "+newpatient.getID()+" saved successfully");
            // show the dialog
            a.show();
		}		
		}
	}
		
}
