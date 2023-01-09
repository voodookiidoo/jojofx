package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.MilitaryUnit;
import util.StringUtil;

import java.util.ArrayList;

public class MilUnitTabController {

	@FXML
	public TableView<MilitaryUnit> milUnitTable;
	@FXML
	public TableColumn milUnitId, commanderName, tacticUnitId, milDistrictId, milUnitNum;
	@FXML
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			fullnameFactory = new PropertyValueFactory<>("commanderName"),
			tacticUnitIdValFactory = new PropertyValueFactory<>("tacticUnitId"),
			mildistrictIdValFactory = new PropertyValueFactory<>("mildistrictId"),
			milUnitNumValFactory = new PropertyValueFactory<>("unitNumber");
	@FXML
	public ChoiceBox<String> officerBox;
	public TextField tacticUnitIdField;
	public TextField milDistrictIdField;
	public TextField milUnitNumField;
	@FXML
	ObservableList<MilitaryUnit> militaryUnits = FXCollections.observableList(new ArrayList<>());

	@FXML
	public void initialize() {
		milUnitId.setCellValueFactory(idValFactory);
		commanderName.setCellValueFactory(fullnameFactory);
		tacticUnitId.setCellValueFactory(tacticUnitIdValFactory);
		milDistrictId.setCellValueFactory(mildistrictIdValFactory);
		milUnitNum.setCellValueFactory(milUnitNumValFactory);
		milUnitTable.setItems(militaryUnits);
		militaryUnits.addAll(MainApplicationController.getDao().getMilUnits());
		updateFreeOfficers();
	}

	private void updateFreeOfficers() {
		officerBox.getItems().clear();
		officerBox.getItems().addAll(StringUtil.UNSELECTED, StringUtil.KEEP);
		officerBox.getItems().addAll(
				MainApplicationController.getDao().getFreeOfficersForMilUnit()
		);
		officerBox.setValue(StringUtil.UNSELECTED);
	}

	@FXML
	public void delUnit(MouseEvent mouseEvent) {

		ObservableList<TablePosition> selectedRows = milUnitTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		MilitaryUnit unit = militaryUnits.get(selectedRows.get(0).getRow());
		MainApplicationController.getDao().deleteObjById(MilitaryUnit.class, unit.getId());
		if (! unit.getCommanderName().equals(StringUtil.UNSELECTED)) {
			officerBox.getItems().add(unit.getCommanderName());
		}
		militaryUnits.remove(unit);
	}

	@FXML
	public void addUnit(MouseEvent mouseEvent) {
		if (!inputDataValid()) return;
		int tactiUnitId = Integer.parseInt(tacticUnitIdField.getText());
		int milUnitNum = Integer.parseInt(milUnitNumField.getText());
		int mildistrictId = Integer.parseInt(milDistrictIdField.getText());
		String name = officerBox.getValue();
		MainApplicationController.getDao().addMilUnit(
				tactiUnitId, mildistrictId, name, milUnitNum
		);
		updateData();

	}

	private void updateData() {
		militaryUnits.clear();
		militaryUnits.addAll(MainApplicationController.getDao().getMilUnits());
		updateFreeOfficers();
	}

	private boolean inputDataValid() {
		String data = tacticUnitIdField.getText();
		if (data.isBlank() || !StringUtil.isNumeric(data)) return false;
		data = milUnitNumField.getText();
		if (data.isBlank() || !StringUtil.isNumeric(data)) return false;
		data = milDistrictIdField.getText();
		if (data.isBlank() || !StringUtil.isNumeric(data)) return false;
		return ! officerBox.getValue().equals(StringUtil.KEEP);
	}

	public void update() {
		updateData();

	}
}
