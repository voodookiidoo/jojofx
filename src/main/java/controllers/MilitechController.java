package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Militech;
import util.StringUtil;

import java.util.ArrayList;

public class MilitechController {
	@FXML
	public TableView<Militech> militechTable;
	@FXML
	public TableColumn id, techType, description, amount;
	@FXML
	public ObservableList<Militech> techs = FXCollections.observableList(new ArrayList<>());
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			amountValFactory = new PropertyValueFactory<>("amount"),
			techTypeValFactory = new PropertyValueFactory<>("type"),
			nameValFactory = new PropertyValueFactory<>("name");
	@FXML
	public TextField techTypeField, descField, amountField;

	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		amount.setCellValueFactory(amountValFactory);
		techType.setCellValueFactory(techTypeValFactory);
		description.setCellValueFactory(nameValFactory);
		militechTable.setItems(techs);
		techs.addAll(MainApplicationController.getDao().getTechs());
	}

	@FXML
	public void addTech(MouseEvent mouseEvent) {
		if (! inputDataValid()) return;
		String type = techTypeField.getText();
		String description = descField.getText();
		System.out.println(type + ' ' + description);
		int amount = Integer.parseInt(amountField.getText());
		MainApplicationController.getDao().addTech(description, type, amount);
		updateData();
	}

	private boolean inputDataValid() {
		String data = techTypeField.getText();
		if (data.isBlank()) return false;
		data = amountField.getText();
		if (data.isBlank() || ! StringUtil.isNumeric(data)) return false;
		data = descField.getText();
		return ! data.isBlank();
	}

	public void updateData() {
		techs.clear();
		techs.addAll(MainApplicationController.getDao().getTechs());
	}

	@FXML
	public void editTech(MouseEvent mouseEvent) {
		ObservableList<TablePosition> selectedRows = militechTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		int techId = techs.get(selectedRows.get(0).getRow()).getId();
		String type = techTypeField.getText();
		if (! type.isBlank()) {
			MainApplicationController.getDao().updateMilitechType(techId, type);
		}
		String description = descField.getText();
		if (! description.isBlank()) {
			MainApplicationController.getDao().updateMilitechDesc(techId, description);
		}
		String amountStr = amountField.getText();
		if (! amountStr.isBlank() && StringUtil.isNumeric(amountStr)) {
			int amount = Integer.parseInt(amountStr);
			MainApplicationController.getDao().updateMilitechAmount(techId, amount);
		}
		updateData();
	}

	@FXML
	public void delTech(MouseEvent mouseEvent) {
		ObservableList<TablePosition> selectedRows = militechTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		Militech tech = techs.get(selectedRows.get(0).getRow());
		int techId = tech.getId();
		MainApplicationController.getDao().deleteObjById(Militech.class, techId);
		techs.remove(tech);
	}
}
