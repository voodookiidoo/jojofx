package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.MilitaryDistrict;

import java.util.ArrayList;
public class MilDistrictTabController {
	@FXML
	public PropertyValueFactory<Object, Object> mildistrictIdFactory = new PropertyValueFactory<>("id");
	@FXML
	public PropertyValueFactory<Object, Object> mildistrictNameFactory = new PropertyValueFactory<>("name");
	@FXML
	public PropertyValueFactory<Object, Object> mildistrictPlaceFactory = new PropertyValueFactory<>("place");
	// создание столбцов для отображения
	@FXML
	ObservableList<MilitaryDistrict> militaryDistrictObservableList = FXCollections.observableList(new ArrayList<>());
	@FXML
	public TableView militaryDistrictTable;
	@FXML
	public TableColumn mildistrict_id;
	@FXML
	public TableColumn mildistrict_name;
	@FXML
	public TableColumn mildistrict_place;
	@FXML
	private void initialize()
	{

		mildistrict_id.setCellValueFactory(mildistrictIdFactory);
		mildistrict_name.setCellValueFactory(mildistrictNameFactory);
		mildistrict_place.setCellValueFactory(mildistrictPlaceFactory);
		militaryDistrictObservableList.addAll(MainApplicationController.getDao().getMilitaryDistricts());
		militaryDistrictTable.setItems(militaryDistrictObservableList);
	}

}
