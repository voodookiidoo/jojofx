package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.TacticUnit;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TacticUnitController implements TabController<TacticUnit>, MilitaryObject<TacticUnit> {

	@FXML
	public TableView<TacticUnit> tacticUnitTable;
	@FXML
	public TableColumn id, fullname, army_id, unit_type, unit_number;
	@FXML
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			nameValFactory = new PropertyValueFactory<>("commanderName"),
			armyIdValFactory = new PropertyValueFactory<>("armyId"),
			unitTypeValFactory = new PropertyValueFactory<>("unitType"),
			unitNumberValFactory = new PropertyValueFactory<>("unitNumber");
	@FXML
	public ChoiceBox<String> possibleCommanders;
	@FXML
	public TextField armyIdField, unitNumField, unitTypeField;
	@FXML
	public Button addBtn, editBtn, delBtn;
	List<String> freeOfficers = new ArrayList<>();
	@FXML
	ObservableList<TacticUnit> tacticUnits = FXCollections.observableList(new ArrayList<>());

	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		fullname.setCellValueFactory(nameValFactory);
		army_id.setCellValueFactory(armyIdValFactory);
		unit_type.setCellValueFactory(unitTypeValFactory);
		unit_number.setCellValueFactory(unitNumberValFactory);
		tacticUnits.addAll(MainApplicationController.getDao().getTacticUnits());
		tacticUnitTable.setItems(tacticUnits);
		updateFreeOfficers();

	}
	@Override
	public void updateFreeOfficers() {
		updateFreeOfficers(TacticUnit.class);
	}

	@Override
	public ChoiceBox<String> getFreeOfficerBox() {
		return possibleCommanders;
	}

	public void addTacticUnit(MouseEvent ignored) {
		if (! inputDataValid()) return;
		int unitNum = Integer.parseInt(unitNumField.getText());
		int armyId = Integer.parseInt(armyIdField.getText());
		String unitType = unitTypeField.getText();
		if (possibleCommanders.getValue().equals(StringUtil.UNSELECTED)) {
			MainApplicationController.getDao().addTacticUnitNoCommander(
					armyId, unitType, unitNum
			);
			localUpdate();
		} else if (! possibleCommanders.getValue().equals(StringUtil.KEEP)) {
			String commanderName = possibleCommanders.getValue();
			MainApplicationController.getDao().addTacticUnit(
					armyId, commanderName, unitType, unitNum
			);
			localUpdate();
		}
	}

	@Override
	public boolean inputDataValid() {
		String text = armyIdField.getText();
		if (text.isEmpty() || ! StringUtil.isNumeric(text)) return false;
		text = unitNumField.getText();
		if (text.isEmpty() || ! StringUtil.isNumeric(text)) return false;
		text = unitTypeField.getText();
		if (text.isEmpty()) return false;
		return true;
	}

	@Override
	public void localUpdate() {
		globalUpdate();

	}

	@Override
	public void globalUpdate() {
		tacticUnits.clear();
		tacticUnits.addAll(MainApplicationController.getDao().getTacticUnits());
		updateFreeOfficers();

	}

	@Override
	public TableView<TacticUnit> getTable() {
		return tacticUnitTable;
	}

	public void editTacticUnit(MouseEvent mouseEvent) {
		TacticUnit unit = getSelectedRow(); // текущий юнит
		if (unit == null) return;
		int unitId = unit.getId();
		String commanderName = possibleCommanders.getValue(); // имя командира которое БУДЕТ назначено
		if (! commanderName.equals(StringUtil.KEEP)) { // если командира нужно изменить
			if (commanderName.equals(StringUtil.UNSELECTED)
					&& ! unit.getCommanderName().equals(StringUtil.UNSELECTED))
			// если текущий командир выбран а нужно снять его
			{
				System.out.println(1);
				MainApplicationController.getDao().setNullTacticUnitCommander(unitId);
				possibleCommanders.getItems().add(commanderName);
				unit.setCommanderName(StringUtil.UNSELECTED);
			} else if (! commanderName.equals(StringUtil.UNSELECTED)
					&& unit.getCommanderName().equals(StringUtil.UNSELECTED)) {// если текущий командир не выбран, а его нужно назначить
				MainApplicationController.getDao().setTacticUnitCommander(commanderName, unitId);
				System.out.println(2);
				unit.setCommanderName(commanderName);
				possibleCommanders.getItems().remove(commanderName);
			} else if (! commanderName.equals(StringUtil.UNSELECTED)
					&& ! unit.getCommanderName().equals(StringUtil.UNSELECTED)) { // если текущий командир выбран, и нужно назначить нового
				MainApplicationController.getDao().setTacticUnitCommander(commanderName, unitId);
				System.out.println(3);
				// убрать из возможных командиров того кого только назначили
				possibleCommanders.getItems().remove(commanderName);
				// добавить того кто освободился
				possibleCommanders.getItems().add(unit.getCommanderName());
				possibleCommanders.setValue(unit.getCommanderName());
				unit.setCommanderName(commanderName);
			}
			updateFreeOfficers();
		}
	}
}