package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Officer;

import java.util.ArrayList;

public class OfficerTabController {
	@FXML
	public TableColumn id, fullname, militaryRank, education,
			rankDate, major, commType;
	@FXML
	public TableView officerTable;
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

	}


}
