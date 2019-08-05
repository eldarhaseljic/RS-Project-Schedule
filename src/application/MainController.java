package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	public void Login(ActionEvent event) throws Exception {
		
		if(txtUsername.getText().equals("user") && txtPassword.getText().equals("pass"))
		{
			Parent root = FXMLLoader.load(getClass().getResource("ToDo.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			//primaryStage.setResizable(true);
			primaryStage.show();
		}
		else
		{
			txtUsername.clear();
			txtPassword.clear();
			lblStatus.setText("Login failed, please try again");
		}
	}
	
}
