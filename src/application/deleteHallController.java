package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Sala;
import entiteti.Zgrada;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class deleteHallController implements Initializable {

	@FXML
	private ComboBox<String> buildingTitle,hallTitle;

	@FXML
	private Label errBuild,errHall;

	public void deleteHall(ActionEvent event) throws Exception {
		if (hallTitle.getSelectionModel().isEmpty() && buildingTitle.getSelectionModel().isEmpty()) {
			errBuild.setText("You didn't choose the building.");
			errHall.setText("You can't choose the hall");
		} else if (hallTitle.getSelectionModel().isEmpty() && !buildingTitle.getSelectionModel().isEmpty()) {
			errBuild.setText("");
			errHall.setText("You didn't choose the hall");
		} else {

			String nazivZ = buildingTitle.getValue();
			String nazivS = hallTitle.getValue();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT z FROM Zgrada z WHERE z.nazivZg = :n", Zgrada.class);
			q.setParameter("n", nazivZ);
			@SuppressWarnings("unchecked")
			List<Zgrada> zgrade = q.getResultList();

			for (Zgrada z : zgrade) {
				Collection<Sala> sale = z.getSale();
				for (Sala s : sale) {
					if (s.getNazivSale().equals(nazivS)) {
						// sale.remove(s);
						int id = s.getSalaId();
						Sala sala = em.find(Sala.class, id);
						if (sala != null) {
							em.getTransaction().begin();
							em.remove(sala);
							em.getTransaction().commit();

							ProdekanController.Information = "You deleted the hall \"" + nazivS
									+ "\" from the building \"" + nazivZ + "\".";
							show(event);
							break;
						}
					}
				}
			}
			em.close();
			emf.close();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		List<String> temp = new ArrayList<String>();
		for (Object e : ProdekanController.temp_list)
			temp.add(((Zgrada) e).getNazivZg());
		buildingTitle.setItems(FXCollections.observableList(temp).sorted());
		hallTitle.setDisable(true);
		// Ovo koristeno samo za test ne treba dirat
		/*
		 * List<String> listofhalls = new ArrayList<String>(); for (int i = 0; i < 10;
		 * ++i) listofhalls.add("eeee)");
		 * hallTitle.setItems(FXCollections.observableList(listofhalls));
		 */
	}

	public void enter(MouseEvent event) throws IOException {
		hallTitle.setDisable(false);
	}

	public void test(MouseEvent event) throws IOException {
		if (buildingTitle.getSelectionModel().isEmpty())
			hallTitle.setDisable(true);
		else
			hallTitle.setDisable(false);
	}

	public void Press(MouseEvent event) throws IOException {
		if (!(buildingTitle.getSelectionModel().isEmpty())) {
			String naziv = buildingTitle.getValue();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT s FROM Sala s WHERE s.zgrada.nazivZg = :n", Sala.class);
			q.setParameter("n", naziv);
			List<?> temp_list = q.getResultList();

			List<String> listofhalls = new ArrayList<String>();
			if (temp_list.isEmpty()) {
				ProdekanController.Information = "There is no hall in the selected building.";
				show(event);
			} else {
				for (Object e : temp_list)
					listofhalls.add(((Sala) e).getNazivSale());
				hallTitle.setItems(FXCollections.observableList(listofhalls).sorted());
			}
			em.close();
			emf.close();
		}
	}

	public void show(Event event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
