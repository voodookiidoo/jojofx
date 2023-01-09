package controllers;

import dao.Dao;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainApplicationController {
	private static Dao dao = new Dao();
	@FXML
	public ArmyTabController armyTabController;
	@FXML
	public TacticUnitController tacticUnitTabController;
	@FXML
	CompanyTabController companyTabController;
	@FXML
	OfficerTabController officerTabController;
	@FXML
	CrewTabController crewTabController;
	@FXML
	SquadTabController squadTabController;
	@FXML
	MilUnitTabController milUnitTabController;
	@FXML
	MilitechController militechTabController;

	public static Dao getDao() {
		return dao;
	}


	@FXML
	public void stop() throws Exception {
	}

	@FXML
	private void initialize() {
		System.out.println("i am inintializing");
		dao = new Dao();
	}

	public void updateArmy(Event event) throws IOException {
		armyTabController.globalUpdate();
	}

	public void updateTacticUnit(Event event) {
		tacticUnitTabController.globalUpdate();
	}

	public void updateCompanies(Event event) {
		companyTabController.globalUpdate();
	}

	public void updateOfficers(Event event) {
		officerTabController.updateData();
	}

	public void updateCrews(Event event) {
		crewTabController.updateData();
	}

	public void updateSquads(Event event) {
		squadTabController.globalUpdate();
	}

	public void updateMilitech(Event event) {
		militechTabController.updateData();

	}

	public void updateMilUnit(Event event) {
		milUnitTabController.update();
	}

	public void updateIfrastructure(Event event) {

	}
}
