package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Crew;
import models.Squad;
import util.StringUtil;

import java.util.ArrayList;

public class SquadTabController implements TabController<Squad>, MilitaryObject<Squad> {
	@FXML
	public TableView<Squad> squadTable;
	@FXML
	public TableColumn id, fullname, squad_number, crew_id;
	@FXML
	public ChoiceBox<String> possibleCommanders;
	@FXML
	public TextField squadNumTextField, crewIdTextField;
	@FXML
	ObservableList<Squad> squads = FXCollections.observableList(new ArrayList<>());
	@FXML
	public PropertyValueFactory<Object, Object>
			idValFactory = new PropertyValueFactory<>("id"),
			fullnameValFactory = new PropertyValueFactory<>("commanderName"),
			squadNumValFactory = new PropertyValueFactory<>("squadNum"),
			crewIdValFactory = new PropertyValueFactory<>("crewId");

	@Override
	public void updateCommander() {
		Squad squad = getSelectedRow();
		if (squad == null) return;
		if (getFreeOfficerBox().getValue().equals(squad.getCommanderName())) return;
		updateCommander(Squad.class, squad.getId());
	}

	@Override
	public ChoiceBox<String> getFreeOfficerBox() {
		return possibleCommanders;
	}

	@Override
	public void addObj() {
		if (! inputDataValid()) return;
		addObj(Squad.class);
		localUpdate();
	}

	@Override
	public TextField getHigherIdField() {
		return crewIdTextField;
	}

	@Override
	public TextField getNumField() {
		return squadNumTextField;
	}

	@Override
	public void updateFreeOfficers() {
	updateFreeOfficers(Squad.class);
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
		squads.clear();
		squads.addAll(MainApplicationController.getDao().getSquads());
		updateFreeOfficers();
	}

	@Override
	public TableView<Squad> getTable() {
		return squadTable;
	}

	@FXML
	public void initialize() {
		id.setCellValueFactory(idValFactory);
		fullname.setCellValueFactory(fullnameValFactory);
		squad_number.setCellValueFactory(squadNumValFactory);
		crew_id.setCellValueFactory(crewIdValFactory);
		squads.addAll(MainApplicationController.getDao().getSquads());
		squadTable.setItems(squads);
		updateFreeOfficers();
	}

	@FXML
	public void addSquad(MouseEvent mouseEvent) {
		addObj();
	}

	@FXML
	public void editSquad(MouseEvent mouseEvent) {
		Squad squad = getSelectedRow();
		if (squad == null) return;
		String commanderName = getFreeOfficerBox().getValue();
		if (! commanderName.equals(StringUtil.KEEP)) {
			if (commanderName.equals(StringUtil.UNSELECTED)
					&& ! squad.getCommanderName().equals(StringUtil.UNSELECTED)) {
				// если требуется снять командира, а командир назначен
				MainApplicationController.getDao().updateCommanderForObj(
						Squad.class, squad.getId(), commanderName
				);
				// добавить командира в список доступных
				getFreeOfficerBox().getItems().add(squad.getCommanderName());
				getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
				squad.setCommanderName(StringUtil.UNSELECTED);
			} else if (! commanderName.equals(StringUtil.UNSELECTED)
					&& squad.getCommanderName().equals(StringUtil.UNSELECTED)) {
				// если командир не назначен, но его нужно назначить
				MainApplicationController.getDao().updateCommanderForObj(
						Squad.class, squad.getId(), commanderName
				);
				// командир назначен, значит из доступных его нужно удалить
				getFreeOfficerBox().getItems().remove(commanderName);
				getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
				squad.setCommanderName(commanderName);

			}
		}
		String crewNumStr = squadNumTextField.getText();
		if (StringUtil.isNumeric(crewNumStr)) {
			MainApplicationController.getDao().updateOjbNum(Crew.class,
					squad.getId(), Integer.parseInt(crewNumStr));
		}
		String companyIdStr = crewIdTextField.getText();
		if (StringUtil.isNumeric(companyIdStr)) {
			MainApplicationController.getDao().updateHigherObjId(Crew.class,
					squad.getId(), Integer.parseInt(companyIdStr));
		}
		globalUpdate();

	}

	@FXML
	public void delSquad(MouseEvent mouseEvent) {
		Squad squad = getSelectedRow();
		if (squad == null) return;
		deleteById(Squad.class, squad.getId());
		squads.remove(squad);
		updateFreeOfficers();
	}
}
