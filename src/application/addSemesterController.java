package application;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Semestar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addSemesterController {

	@FXML
	private TextField oznSem;

	@FXML
	private Label errBuild;

	@FXML
	private DatePicker dateBegin; 
	
	@FXML
	private DatePicker dateEnd; 

	public void addSemester(ActionEvent event) throws Exception {
		Calendar cal = Calendar.getInstance();
		
		if (oznSem.getText().isBlank())
			errBuild.setText("Enter semester label!");
		else if (dateBegin.getValue() == null)
			errBuild.setText("Enter semester begin date!");
		else if (dateEnd.getValue() == null)
			errBuild.setText("Enter semester end date!");
		else if (dateBegin.getValue().getYear() > dateEnd.getValue().getYear()) 
			errBuild.setText("Bad date");	
		else if (dateBegin.getValue().getYear() == dateEnd.getValue().getYear() && dateBegin.getValue().getMonthValue() > dateEnd.getValue().getMonthValue())
			errBuild.setText("Bad date");
		else if(dateBegin.getValue().getDayOfYear() > dateEnd.getValue().getDayOfYear() && dateBegin.getValue().getYear() == dateEnd.getValue().getYear())
			errBuild.setText("Bad date");
		else {
			boolean exists = false;
			String naziv = oznSem.getText().toUpperCase();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT s FROM Semestar s");
			@SuppressWarnings("unchecked")
			List<Semestar> semestri = q.getResultList();

			for (Semestar semestar : semestri) {
	
				if (semestar.getOznakaSemestra().equals(naziv)) {
					exists = true;

					ProdekanController.Information = "Semester already exists!";
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
					Scene scene = new Scene(root);
					primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.show();
					break;
				}

			}

			if (!exists) {
				Semestar s = new Semestar();
				s.setOznakaSemestra(naziv);
				s.setDatumPocetkaSemestra(LocalDate.of(dateBegin.getValue().getYear(), dateBegin.getValue().getMonth(), dateBegin.getValue().getDayOfMonth()));
				s.setDatumZavrsetkaSemestra(LocalDate.of(dateEnd.getValue().getYear(), dateEnd.getValue().getMonth(), dateEnd.getValue().getDayOfMonth()));
				
				em.getTransaction().begin();
				em.persist(s);
				em.getTransaction().commit();

				ProdekanController.Information = "Semester added successfully!";
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
}
