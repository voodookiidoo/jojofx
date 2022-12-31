package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.MilitaryDistrict;

import java.util.ArrayList;

public class MilDistrictTabController implements TabController<MilitaryDistrict> {
	@FXML
	public PropertyValueFactory<Object, Object> mildistrictIdFactory = new PropertyValueFactory<>("id");
	@FXML
	public PropertyValueFactory<Object, Object> mildistrictNameFactory = new PropertyValueFactory<>("name");
	@FXML
	public PropertyValueFactory<Object, Object> mildistrictPlaceFactory = new PropertyValueFactory<>("place");
	@FXML
	public TableView militaryDistrictTable;
	@FXML
	public TableColumn mildistrict_id, mildistrict_name, mildistrict_place;
	@FXML
	public TextField districtNameField, districtPlace;
	// создание столбцов для отображения
	@FXML
	ObservableList<MilitaryDistrict> militaryDistrictObservableList = FXCollections.observableList(new ArrayList<>());

	@FXML
	private void initialize() {

		mildistrict_id.setCellValueFactory(mildistrictIdFactory);
		mildistrict_name.setCellValueFactory(mildistrictNameFactory);
		mildistrict_place.setCellValueFactory(mildistrictPlaceFactory);
		militaryDistrictObservableList.addAll(MainApplicationController.getDao().getMilitaryDistricts());
		militaryDistrictTable.setItems(militaryDistrictObservableList);

	}


	@Override
	public boolean inputDataValid() {
		return true;
	}

	@Override
	public void localUpdate() {

	}

	@Override
	public void globalUpdate() {

	}

	@Override
	public TableView<MilitaryDistrict> getTable() {
		return militaryDistrictTable;
	}
}
