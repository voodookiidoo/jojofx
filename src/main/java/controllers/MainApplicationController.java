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
}
