package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Company;

import java.util.ArrayList;

public class CompanyTabController implements MilitaryObject<Company>, TabController<Company> {
	@FXML
	public TableView<Company> companyTable;
	@FXML
	public TableColumn id, fullname, company_number, unit_id;
	@FXML
	public ChoiceBox<String> possibleCommanders;
	@FXML
	public Button addBtn, editBtn, delBtn;
	@FXML
	public TextField unitIdTextField, companyNumTextField;
	@FXML
	public ObservableList<Company> companies = FXCollections.observableList(new ArrayList<>());

	@FXML
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			fullnameValFactory = new PropertyValueFactory<>("fullname"),
			companyNumValFactory = new PropertyValueFactory<>("company_number"),
			unitIdValFactory = new PropertyValueFactory<>("unit_id");


	@Override
	public void updateCommander() {
		Company company = getSelectedRow();
		if (company == null) return;
		if (getFreeOfficerBox().getValue().equals(company.getCommanderName())) return;
		updateCommander(Company.class, company.getId());
	}

	@Override
	public ChoiceBox<String> getFreeOfficerBox() {
		return possibleCommanders;
	}

	@Override
	public void addObj() {
		if (! inputDataValid()) return;
		addObj(Company.class);
	}

	@Override
	public TextField getHigherIdField() {
		return unitIdTextField;
	}

	@Override
	public TextField getNumField() {
		return companyNumTextField;
	}

	@Override
	public void updateFreeOfficers() {
		updateFreeOfficers(Company.class);
	}

	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		fullname.setCellValueFactory(fullnameValFactory);
		company_number.setCellValueFactory(companyNumValFactory);
		unit_id.setCellValueFactory(unitIdValFactory);
		companies.addAll(MainApplicationController.getDao().getCompanies());
		companyTable.setItems(companies);
		updateFreeOfficers();
	}

	public void addCompany(MouseEvent mouseEvent) {
		addObj();
	}

	@Override
	public boolean inputDataValid() {
		return true;
	}

	@Override
	public void localUpdate() {
		globalUpdate();
	}

	@Override
	public void globalUpdate() {
		updateFreeOfficers();
	}

	@Override
	public TableView<Company> getTable() {
		return companyTable;
	}

	public void editCompany(MouseEvent mouseEvent) {
	}


	public void delCompany(MouseEvent mouseEvent) {
		Company company = getSelectedRow();
		if (company == null) return;
		if (getFreeOfficerBox().getValue().equals(company.getCommanderName())) return;
		deleteById(Company.class, company.getId());
		companies.remove(company);
	}
}
