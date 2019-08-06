package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.persistence.Query;

import entiteti.Korisnik;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdekanController {
	
	@FXML
	private TextField usr;
	
	@FXML
	private TextField email;
	
	@FXML
	private TextField titula;
	
	public void init(ActionEvent event) throws Exception {
		usr.setPromptText(MainController.trenutniKor.getIme()+" "+MainController.trenutniKor.getPrezime());
		email.setPromptText(MainController.trenutniKor.getEmail());
		titula.setPromptText("Prodekan");
	}
}
