package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class InfoController implements Initializable {

	@FXML
	private Label info;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		info.setText(ProdekanController.Information);
	}

	public void close(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

}