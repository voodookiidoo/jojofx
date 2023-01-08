package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Officer;

import java.time.LocalDate;
import java.util.ArrayList;

public class OfficerTabController {
	@FXML
	public TableColumn id, fullname, militaryRank, education,
			rankDate, major, commType;
	@FXML
	public TableView<Officer> officerTable;
	@FXML
	public DatePicker rankDatePicker;
	@FXML
	public TextField educationField, nameField, majorField, milRankField;
	@FXML
	public ChoiceBox<String> typeBox;
	@FXML
	ObservableList<Officer> officers = FXCollections.observableList(new ArrayList<>());
	@FXML
	PropertyValueFactory<Object, Object> idValFactory = new PropertyValueFactory<>("id"),
			fullnameValFactory = new PropertyValueFactory<>("fullname"),
			milRankValFactory = new PropertyValueFactory<>("militaryRank"),
			eduValFactory = new PropertyValueFactory<>("education"),
			rankDateValFactory = new PropertyValueFactory<>("rankDate"),
			majorValFactory = new PropertyValueFactory<>("major"),
			commTypeValFactory = new PropertyValueFactory<>("commType");

	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		fullname.setCellValueFactory(fullnameValFactory);
		militaryRank.setCellValueFactory(milRankValFactory);
		education.setCellValueFactory(eduValFactory);
		rankDate.setCellValueFactory(rankDateValFactory);
		major.setCellValueFactory(majorValFactory);
		commType.setCellValueFactory(commTypeValFactory);
		officers.addAll(MainApplicationController.getDao().getOfficers());
		officerTable.setItems(officers);
		typeBox.getItems().addAll("private", "officer", "sergent");
		typeBox.setValue("private");
	}

	@FXML
	public void addOfficer(MouseEvent mouseEvent) {
		LocalDate date = rankDatePicker.getValue();
		if (date == null) return;
		String name = nameField.getText();
		if (name.isBlank()) return;
		String education = educationField.getText();
		if (education.isBlank()) return;
		String major = majorField.getText();
		if (major.isBlank()) return;
		String type = typeBox.getValue();
		String milRank = milRankField.getText();
		if (milRank.isBlank()) return;
		MainApplicationController.getDao().addOfficer(
				name,
				milRank,
				education,
				date,
				major,
				type
		);
		updateData();


	}

	public void updateData() {
		officers.clear();
		officers.addAll(MainApplicationController.getDao().getOfficers());

	}

	@FXML
	public void editOfficer(MouseEvent mouseEvent) {
		ObservableList<TablePosition> selectedRows = officerTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		Officer officer = officerTable.getItems().get(selectedRows.get(0).getRow());
		int id = officer.getId();
		LocalDate date = rankDatePicker.getValue();
		if (date != null) {
			MainApplicationController.getDao().updateOfficerDate(id, date);
		}
		String name = nameField.getText();
		if (! name.isBlank()) {
			MainApplicationController.getDao().updateOfficerName(id, name);
		}
		String milRank = milRankField.getText();
		if (!milRank.isBlank())
		{
			MainApplicationController.getDao().updateOfficerRank(id, milRank);
		}
		String education = educationField.getText();
		if (! education.isBlank()) {
			MainApplicationController.getDao().updateOfficerEducation(id, education);
		}
		String major = majorField.getText();
		if (! major.isBlank()) {
			MainApplicationController.getDao().updateOfficerMajor(id, major);
		}
		String type = typeBox.getValue();
		MainApplicationController.getDao().updateOfficerType(id, type);
		updateData();
	}

	public void deleteOfficer(MouseEvent mouseEvent) {
		ObservableList<TablePosition> selectedRows = officerTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		Officer officer = officerTable.getItems().get(selectedRows.get(0).getRow());
		int id = officer.getId();
		MainApplicationController.getDao().deleteOfficerById(id);
		officerTable.getItems().remove(officer);
	}
}
