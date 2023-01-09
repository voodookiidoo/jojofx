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
import models.MiliStruct;
import models.Militech;
import util.StringUtil;

import java.util.ArrayList;

public class MilistructController {
	@FXML
	public TableView<MiliStruct> milistructTable;
	@FXML
	public TableColumn id, structType, description, amount;
	@FXML
	public ObservableList<MiliStruct> structs = FXCollections.observableList(new ArrayList<>());
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			amountValFactory = new PropertyValueFactory<>("amount"),
			structTypeValFactory = new PropertyValueFactory<>("type"),
			nameValFactory = new PropertyValueFactory<>("name");
	@FXML
	public TextField structTypeField, descField, amountField;

	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		amount.setCellValueFactory(amountValFactory);
		structType.setCellValueFactory(structTypeValFactory);
		description.setCellValueFactory(nameValFactory);
		milistructTable.setItems(structs);
		structs.addAll(MainApplicationController.getDao().getMiliStructs());
	}

	@FXML
	public void addStruct(MouseEvent mouseEvent) {
		if (! inputDataValid()) return;
		String type = structTypeField.getText();
		String description = descField.getText();
		System.out.println(type + ' ' + description);
		int amount = Integer.parseInt(amountField.getText());
		MainApplicationController.getDao().addMiliStruct(description, type, amount);
		updateData();
	}

	private boolean inputDataValid() {
		String data = structTypeField.getText();
		if (data.isBlank()) return false;
		data = amountField.getText();
		if (data.isBlank() || ! StringUtil.isNumeric(data)) return false;
		data = descField.getText();
		return ! data.isBlank();
	}

	public void updateData() {
		structs.clear();
		structs.addAll(MainApplicationController.getDao().getMiliStructs());
	}

	@FXML
	public void editStruct(MouseEvent mouseEvent) {
		ObservableList<TablePosition> selectedRows = milistructTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		int techId = structs.get(selectedRows.get(0).getRow()).getId();
		String type = structTypeField.getText();
		if (! type.isBlank()) {
			MainApplicationController.getDao().updateMiliStructType(techId, type);
		}
		String description = descField.getText();
		if (! description.isBlank()) {
			MainApplicationController.getDao().updateMiliStructDesc(techId, description);
		}
		String amountStr = amountField.getText();
		if (! amountStr.isBlank() && StringUtil.isNumeric(amountStr)) {
			int amount = Integer.parseInt(amountStr);
			MainApplicationController.getDao().updateMiliStructAmount(techId, amount);
		}
		updateData();
	}

	@FXML
	public void delStruct(MouseEvent mouseEvent) {
		ObservableList<TablePosition> selectedRows = milistructTable.getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return;
		}
		MiliStruct struct = structs.get(selectedRows.get(0).getRow());
		int techId = struct.getId();
		MainApplicationController.getDao().deleteObjById(Militech.class, techId);
		structs.remove(struct);
	}
}
