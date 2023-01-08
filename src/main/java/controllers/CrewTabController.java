package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Crew;
import util.StringUtil;

import java.util.ArrayList;

public class CrewTabController implements MilitaryObject<Crew>, TabController<Crew> {

	@FXML
	public TableView<Crew> crewTable;
	@FXML
	public TableColumn id, fullname, crew_number, company_id;
	@FXML
	public ChoiceBox<String> possibleCommanders;
	@FXML
	public ObservableList<Crew> crews = FXCollections.observableList(new ArrayList<>());
	@FXML
	public TextField crewNumTextField, companyIdTextField;
	@FXML
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			fullnameValFactory = new PropertyValueFactory<>("commanderName"),
			crewNumValFactory = new PropertyValueFactory<>("crewNumber"),
			companyIdValFactory = new PropertyValueFactory<>("companyId");


	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		fullname.setCellValueFactory(fullnameValFactory);
		crew_number.setCellValueFactory(crewNumValFactory);
		company_id.setCellValueFactory(companyIdValFactory);
		crews.addAll(MainApplicationController.getDao().getCrews());
		crewTable.setItems(crews);
		updateFreeOfficers();
	}

	@Override
	public void updateCommander() {
		Crew crew = getSelectedRow();
		if (crew == null) return;
		if (getFreeOfficerBox().getValue().equals(crew.getCommanderName())) return;
		updateCommander(Crew.class, crew.getId());

	}

	@Override
	public ChoiceBox<String> getFreeOfficerBox() {
		return possibleCommanders;
	}

	@Override
	public void addObj() {
		if (! inputDataValid()) return;
		addObj(Crew.class);
		localUpdate();
	}

	@Override
	public TextField getHigherIdField() {
		return companyIdTextField;
	}

	@Override
	public TextField getNumField() {
		return crewNumTextField;
	}

	@Override
	public void updateFreeOfficers() {
		updateFreeOfficers(Crew.class);
	}

	public void addCrew(MouseEvent mouseEvent) {
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
		crews.clear();
		crews.addAll(MainApplicationController.getDao().getCrews());
		updateFreeOfficers();
	}

	@Override
	public TableView<Crew> getTable() {
		return crewTable;
	}

	public void editCrew(MouseEvent mouseEvent) {
		Crew crew = getSelectedRow();
		if (crew == null) return;
		String commanderName = getFreeOfficerBox().getValue();
		if (! commanderName.equals(StringUtil.KEEP)) {
			System.out.println(1);
			if (commanderName.equals(StringUtil.UNSELECTED)
					&& ! crew.getCommanderName().equals(StringUtil.UNSELECTED)) {
				System.out.println(2);
				// если требуется снять командира, а командир назначен
				MainApplicationController.getDao().updateCommanderForObj(
						Crew.class, crew.getId(), commanderName
				);
				// добавить командира в список доступных
				getFreeOfficerBox().getItems().add(crew.getCommanderName());
				getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
				crew.setCommanderName(StringUtil.UNSELECTED);
			} else if (! commanderName.equals(StringUtil.UNSELECTED)
					&& crew.getCommanderName().equals(StringUtil.UNSELECTED)) {
				System.out.println(3);
				// если командир не назначен, но его нужно назначить
				MainApplicationController.getDao().updateCommanderForObj(
						Crew.class, crew.getId(), commanderName
				);
				// командир назначен, значит из доступных его нужно удалить
				getFreeOfficerBox().getItems().remove(commanderName);
				getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
				crew.setCommanderName(commanderName);

			}
		}
		String crewNumStr = crewNumTextField.getText();
		if (StringUtil.isNumeric(crewNumStr)) {
			MainApplicationController.getDao().updateOjbNum(Crew.class,
					crew.getId(), Integer.parseInt(crewNumStr));
		}
		String companyIdStr = companyIdTextField.getText();
		if (StringUtil.isNumeric(companyIdStr)) {
			MainApplicationController.getDao().updateHigherObjId(Crew.class,
					crew.getId(), Integer.parseInt(companyIdStr));
		}
		updateData();
	}

	public void updateData() {
		globalUpdate();
	}

	public void delCrew(MouseEvent mouseEvent) {
		Crew crew = getSelectedRow();
		if (crew == null) return;
		deleteById(Crew.class, crew.getId());
		crews.remove(crew);
		updateFreeOfficers();
	}
}
