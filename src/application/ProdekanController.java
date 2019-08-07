package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProdekanController implements Initializable{
	
	@FXML
	private Label usr;
	
	@FXML
	private Label email;
	
	@FXML
	private Label titula ;
	
	public void init(ActionEvent event) throws Exception {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usr.setText(MainController.trenutniKor.getIme()+" "+MainController.trenutniKor.getPrezime());
		email.setText(MainController.trenutniKor.getEmail());
		titula.setText("Prodekan");
	}
	
	public void addBuilding(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("addBuildingScreen.fxml"));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("New Building");
		primaryStage.show();
	}
	
}