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
import models.Army;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ArmyTabController implements TabController<Army>, MilitaryObject<Army> {
	@FXML
	public TableView armyTable;
	@FXML
	public TableColumn id, fullname, army_number;
	@FXML
	public ObservableList<Army> armies = FXCollections.observableList(new ArrayList<>());
	@FXML
	public PropertyValueFactory<Object, Object> idValFactory = new PropertyValueFactory<>("id"),
			fullnameValFactory = new PropertyValueFactory<>("fullname"),
			armyNumValFactory = new PropertyValueFactory<>("army_number");
	@FXML
	public ChoiceBox<String> possibleCommanders;
	@FXML
	public TextField armyNumTextField;
	List<String> freeOfficers = new ArrayList<>();

	@FXML
	public void initialize() {
		// инициализация всех фабрик для ячеек
		id.setCellValueFactory(idValFactory);
		fullname.setCellValueFactory(fullnameValFactory);
		army_number.setCellValueFactory(armyNumValFactory);
		armies.addAll(MainApplicationController.getDao().getArmies());
		armyTable.setItems(armies);
		updateFreeOfficers();


	}

	@Override
	public void updateFreeOfficers() {
		updateFreeOfficers(Army.class);
		possibleCommanders.getValue();
	}

	@Override
	public ChoiceBox<String> getFreeOfficerBox() {
		return possibleCommanders;
	}

	@FXML
	public void addArmy(MouseEvent mouseEvent) {
		if (! inputDataValid()) return;
		int armyNum = Integer.parseInt(armyNumTextField.getText());
		// получить номер армии
		if (possibleCommanders.getValue().equals("NOT SELECTED")) {
			// если командир не выбрать - назначить пустого командира
			MainApplicationController.getDao().addArmyNoCommander(armyNum);
			localUpdate();
			return;
		}
		// получить имя командира
		String officerName = possibleCommanders.getValue();
		// добавить командира
		MainApplicationController.getDao().addArmy(officerName, armyNum);
		// обновить все записи
		localUpdate();

	}

	@Override
	public boolean inputDataValid() {
		if (armyNumTextField.getText().isEmpty()) {
			// если у армии нет номера её нельзя добавить
			System.out.println("ARMY NUMBER IS NOT DEFINED");
			return true;
		}
		if (! StringUtil.isNumeric(armyNumTextField.getText())) {
			// номер армии должен быть числом
			System.out.println("ARMY NUMBER IS NOT A NUMBER");
			return true;
		}
		if (possibleCommanders.getValue().equals("KEEP SAME")) {
			// мы не можем назначить текущего командира
			System.out.println("UNABLE TO CREATE AN ARMY WITH UNDEFINED COMMANDER");
			return true;
		}

		return true;
	}

	@Override
	public void localUpdate() {
		globalUpdate();

	}

	@Override
	public void globalUpdate() {
		armies.clear();
		armies.addAll(MainApplicationController.getDao().getArmies());
		updateFreeOfficers();
	}

	@Override
	public TableView<Army> getTable() {
		return armyTable;
	}

	@FXML
	public void editArmy(MouseEvent mouseEvent) {
		Army army = getSelectedRow(); // получить армию под фокусом
		if (army == null) return; // если нету - выйти из метода
		int armyId = army.getId(); // получить айдишник армии
		if (! armyNumTextField.getText().isEmpty() &&
				StringUtil.isNumeric(armyNumTextField.getText())) {
			// если номер армии указан правильно то обновить его
			int armyNum = Integer.parseInt(armyNumTextField.getText());
			MainApplicationController.getDao().updateArmyNumById(armyId, armyNum);
			army.setArmy_number(armyNum);
		}
		if (! possibleCommanders.getValue().equals("KEEP SAME")) {
			if (possibleCommanders.getValue().equals("NOT SELECTED")
			)// if you choose to set notselected
			{
				// setting null officer name for army
				MainApplicationController.getDao().setNullArmyCommander(armyId);
//				possibleCommanders.getItems().add(army.getFullname());
				army.setFullname("NOT SELECTED");
			} else {// setting new commander name
				// get commander name to set
				String commanderName = possibleCommanders.getValue();
				MainApplicationController.getDao().setArmyCommanderName(armyId, commanderName);
				// remove used commander name
				possibleCommanders.getItems().remove(commanderName);
				// add freed commander
				if (! army.getFullname().equals("NOT SELECTED")) {
					possibleCommanders.getItems().add(army.getFullname());
				}
				possibleCommanders.setValue(army.getFullname());
				army.setFullname(commanderName);
			}
//			updateFreeOfficers();
		}
	}

	public void delArmy(MouseEvent mouseEvent) {
		Army army = getSelectedRow();
		if (army == null) return;
		MainApplicationController.getDao().deleteArmyById(army.getId());
		armyTable.getItems().remove(army);
		System.out.println("TRYING TO UPDATE FREE OFFICERS");
		if (! army.getFullname().equals("NOT SELECTED")) {
			freeOfficers.add(army.getFullname());
			possibleCommanders.getItems().add(army.getFullname());
		}

	}
}
