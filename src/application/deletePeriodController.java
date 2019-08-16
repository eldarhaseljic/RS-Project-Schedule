package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.jfoenix.controls.JFXTextField;

import entiteti.Cas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class deletePeriodController implements Initializable {

	@FXML
	private TableView<Cas> table;
	@FXML
	private TableColumn<Cas,String> hall;
	@FXML
	private TableColumn<Cas,String> subject;
	@FXML
	private TableColumn<Cas,String> time;
	@FXML
	private TableColumn<Cas,String> type;
	@FXML
	private TableColumn<Cas,String> teacher;
	@FXML
	private ChoiceBox<String> choices;
	@FXML
	private JFXTextField searchField;
	@FXML
	private Label errBuild;

	public void deletePeriod(ActionEvent event) throws Exception {
		if (table.getSelectionModel().isEmpty())
			errBuild.setText("You didn't choose the period.");
		
		else {
			ObservableList<Cas> temp = table.getSelectionModel().getSelectedItems();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			for (Cas cas : temp) 
			{
				Cas c = em.find(Cas.class, cas.getId());
				em.getTransaction().begin();
				em.remove(c);
			   	em.getTransaction().commit();
			   	ProdekanController.Information = "You deleted a period successfully.";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
				Scene scene = new Scene(root);
				primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			
			em.close();
			emf.close();
 }

}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		ObservableList<Cas> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add(((Cas) e));
		
		hall.setCellValueFactory(new PropertyValueFactory<Cas,String>("imeSale"));
		subject.setCellValueFactory(new PropertyValueFactory<Cas,String>("imePredmeta"));
		time.setCellValueFactory(new PropertyValueFactory<Cas,String>("vrijeme"));
		teacher.setCellValueFactory(new PropertyValueFactory<Cas,String>("imeNastavnika"));
		type.setCellValueFactory(new PropertyValueFactory<Cas,String>("tip"));
		
		FilteredList<Cas> casovi = new FilteredList<Cas>(temp,p->true);
		
		table.setItems(casovi);
		
		choices.getItems().addAll("Hall","Subject","Time","Teacher","Type");
		
		searchField.setOnKeyReleased(keyEvent ->
		{
			switch(choices.getValue())
			{
			case "Hall":
				casovi.setPredicate(p -> p.getImeSale().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Subject":
				casovi.setPredicate(p -> p.getImePredmeta().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Time":
				casovi.setPredicate(p -> p.getVrijeme().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Teacher":
				casovi.setPredicate(p -> p.getImeNastavnika().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Type":
				casovi.setPredicate(p -> p.getTip().toLowerCase().contains(searchField.getText().trim()));
				break;
			}
		});
		
		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                searchField.setText("");
                casovi.setPredicate(null);
            }
        });
	}
}