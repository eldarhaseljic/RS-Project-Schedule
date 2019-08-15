package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entiteti.Cas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportController implements Initializable {

	@FXML
	private TableView<Period> table;
	@FXML
	private TableColumn<Period, String> predmet;
	@FXML
	private TableColumn<Period, LocalDate> datum;
	@FXML
	private TableColumn<Period, String> mjesto;
	@FXML
	private TableColumn<Period, String> brstud;
	@FXML
	private TableColumn<Period, Integer> predavanja;
	@FXML
	private TableColumn<Period, Integer> vjezbe;
	@FXML
	private Label vs;
	@FXML
	private Label ps;
	@FXML
	private Label monthLabel;
	@FXML
	private Label izvrsilac1;
	@FXML
	private Label izvrsilac2;
	@FXML
	private Label date;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		izvrsilac1.setText(MainController.trenutniKor.getIme() + " " + MainController.trenutniKor.getPrezime());
		izvrsilac2.setText(MainController.trenutniKor.getIme() + " " + MainController.trenutniKor.getPrezime());

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		date.setText(dateFormat.format(cal.getTime())); // datum podnosenja izvjestaja

		LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		int year = today.getYear();

		LocalDate startDate = LocalDate.of(2019, 1, 1); // random inicijalizacija
		LocalDate endDate = LocalDate.of(2019, 1, 31); // random inicijalizacija

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			startDate = LocalDate.of(year, month, 1);
			endDate = LocalDate.of(year, month, 31);
		case 2:
			startDate = LocalDate.of(year, month, 1);
			endDate = LocalDate.of(year, month, 28);
		case 4:
		case 6:
		case 9:
		case 11:
			startDate = LocalDate.of(year, month, 1);
			endDate = LocalDate.of(year, month, 30);
		}

		ObservableList<Cas> casovi = FXCollections.observableArrayList();
		for (Object e : ProfessorController.temp_list)
			casovi.add((Cas) e);

		ObservableList<Period> periods = FXCollections.observableArrayList();
		List<LocalDate> daysRange = new ArrayList<LocalDate>();
		Integer brojSatiPredavanja;
		Integer brojSatiVjezbi;
		Integer ukupnoSatiP = 0;
		Integer ukupnoSatiV = 0;
		String sem = "";

		for (Cas c : casovi) {
			String imePred = c.getImePredmeta();
			String brStud = c.getBrojStudenata();
			String lokacija = c.getImeSale() + " " + c.getvrijemePocetkaCasaSat() + ":"
					+ c.getVrijemePocetkaCasaMinuta() + "-" + c.getVrijemeZavrsetkaCasaSat() + ":"
					+ c.getVrijemeZavrsetkaCasaMinuta();

			String semestar = c.getSemestar().getOznakaSemestra();

			if ((semestar.equals("I") || semestar.equals("III") || semestar.equals("V") || semestar.equals("VII"))
					&& (month == 2 || month == 3 || month == 4 || month == 5 || month == 6 || month == 7 || month == 8
							|| month == 9))
				continue;

			if ((semestar.equals("II") || semestar.equals("IV") || semestar.equals("VI") || semestar.equals("VIII"))
					&& (month == 10 || month == 11 || month == 12 || month == 1))
				continue;

			if (semestar.equals("I") || semestar.equals("III") || semestar.equals("V") || semestar.equals("VII"))
				sem = "zimski";
			else
				sem = "ljetni";

			brojSatiPredavanja = 0;
			brojSatiVjezbi = 0;

			String tip = c.getGrupa().getTipgrupe();
			if (tip.equals("Lecture")) {
				brojSatiPredavanja = c.getVrijemeZavrsetkaCasaSat() - c.getvrijemePocetkaCasaSat();
			}

			else {
				brojSatiVjezbi = c.getVrijemeZavrsetkaCasaSat() - c.getvrijemePocetkaCasaSat();
			}

			String dan = c.getDatumOdrzavanjaCasa();

			if (dan.equals("Monday")) {
				long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
				daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays)
						.filter(date -> date.getDayOfWeek() == DayOfWeek.MONDAY).collect(Collectors.toList());
			}

			else if (dan.equals("Tuesday")) {
				long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
				daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays)
						.filter(date -> date.getDayOfWeek() == DayOfWeek.TUESDAY).collect(Collectors.toList());
			}

			else if (dan.equals("Wednesday")) {
				long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
				daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays)
						.filter(date -> date.getDayOfWeek() == DayOfWeek.WEDNESDAY).collect(Collectors.toList());
			}

			else if (dan.equals("Thursday")) {
				long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
				daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays)
						.filter(date -> date.getDayOfWeek() == DayOfWeek.THURSDAY).collect(Collectors.toList());
			}

			else if (dan.equals("Friday")) {
				long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
				daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays)
						.filter(date -> date.getDayOfWeek() == DayOfWeek.FRIDAY).collect(Collectors.toList());
			}

			else {
				long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
				daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays)
						.filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY).collect(Collectors.toList());
			}

			for (LocalDate d : daysRange) {
				ukupnoSatiP += brojSatiPredavanja;
				ukupnoSatiV += brojSatiVjezbi;
				periods.add(new Period(d, imePred, brStud, lokacija, brojSatiPredavanja, brojSatiVjezbi));
				predmet.setCellValueFactory(new PropertyValueFactory<Period, String>("predmet"));
				mjesto.setCellValueFactory(new PropertyValueFactory<Period, String>("mjesto"));
				brstud.setCellValueFactory(new PropertyValueFactory<Period, String>("brstud"));
				datum.setCellValueFactory(new PropertyValueFactory<Period, LocalDate>("datum"));
				predavanja.setCellValueFactory(new PropertyValueFactory<Period, Integer>("predavanje"));
				vjezbe.setCellValueFactory(new PropertyValueFactory<Period, Integer>("vjezbe"));
				table.setItems(periods);
			}

		}

		ps.setText(ukupnoSatiP.toString());
		vs.setText(ukupnoSatiV.toString());

		String mjesec = "";
		if (month == 1)
			mjesec = "januar";
		else if (month == 2)
			mjesec = "februar";
		else if (month == 3)
			mjesec = "mart";
		else if (month == 4)
			mjesec = "april";
		else if (month == 5)
			mjesec = "maj";
		else if (month == 6)
			mjesec = "juni";
		else if (month == 7)
			mjesec = "juli";
		else if (month == 8)
			mjesec = "august";
		else if (month == 9)
			mjesec = "septembar";
		else if (month == 10)
			mjesec = "oktobar";
		else if (month == 11)
			mjesec = "novembar";
		else
			mjesec = "decembar";

		String temp = "za mjesec " + mjesec + " " + sem + " semestar ak. " + (year - 1) + "/" + year + ". godine";
		monthLabel.setText(temp);

	}

}
