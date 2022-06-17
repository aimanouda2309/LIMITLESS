package main;

import java.awt.CardLayout;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import dbconnector.dbConnection;
import gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Maze extends Application{

	public static  int WIDTH = 800;
	public static  int HEIGHT = WIDTH; // best to keep these the same. variable is only created for readability.
	public static final int W = 20;
	public static JSlider genSpeedSlider;
	public static int speed = 1;
	public static boolean generated, solved;
	public static long startTime;
	
	private static final String[] GENERATION_METHODS = {"0. Kruskal's", "1. Wilson's"};
	private static final String[] SOLVING_METHODS = {"0. BFS","1. Dijkstra's"};

	private int cols, rows;
	public static String[] args;
	
	public static void main(String[] args) {
		LoginController login=new LoginController();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// TODO Auto-generated method stubtry
			Parent root = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setWidth(330);
			primaryStage.setHeight(420);
			primaryStage.setTitle("Log-In");
			primaryStage.show();

		} catch (SocketException e1) {
			System.out.println("Client Stopped!!");
		} catch (RuntimeException e2) {
			System.out.println("Client Stopped!!");
		}
	}


}