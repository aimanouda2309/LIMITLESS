package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NavigateController {
	private AnchorPane lowerAnchorPane;
	private SplitPane splitpane;
	public void start(SplitPane splitpane) throws Exception {
		// TODO Auto-generated method stub
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Home.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(0, lowerAnchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void openScene(ActionEvent event, String path,int Width, int Height,String Title) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(path));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/model/Main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setMaxWidth((double)Width+20);
		primaryStage.setMinWidth((double)Width);
		primaryStage.setMaxHeight((double)Height+20);
		primaryStage.setMinHeight((double)Height);
		//primaryStage.setWidth(Width);
		//primaryStage.setHeight(Height+20);
		primaryStage.setTitle(Title);
		// primaryStage.setWidth(800);
		//primaryStage.resizableProperty().setValue(false);
		primaryStage.show();
		}

}
