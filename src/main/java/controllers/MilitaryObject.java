package controllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import util.StringUtil;

import java.util.List;

public interface MilitaryObject<T> {
	public void updateCommander();

	public default void updateCommander(Class<T> cls, int id) {
		String commanderName = getFreeOfficerBox().getValue();
		if (commanderName.equals(StringUtil.KEEP)) return;
		if (commanderName.equals(StringUtil.UNSELECTED)) {
			MainApplicationController.getDao().updateCommanderForObj(cls, id, null);
		}
		else
		{
			MainApplicationController.getDao().updateCommanderForObj(cls, id, commanderName);
		}
		updateFreeOfficers();



	}

	/**
	 * @return Choice box containing all available commanders
	 */
	public ChoiceBox<String> getFreeOfficerBox();

	public void addObj();

	/**
	 * @param cls Reflecting class of an object to be added
	 */
	public default void addObj(Class<T> cls) {
		// добавляет объект, извлекая данные из всех форм
		if (! isValid()) return;
		int upId = Integer.parseInt(getHigherIdField().getText());
		int objNum = Integer.parseInt(getNumField().getText());
		String commanderName = getFreeOfficerBox().getValue();
		if (commanderName.equals(StringUtil.UNSELECTED)) {
			commanderName = null;
		}
		if (commanderName.equals(StringUtil.UNSELECTED)) {
			// если командир не назначен, провести добавление без него
			MainApplicationController.getDao().addObj(
					cls, upId, commanderName, objNum
			);
			// удалить командира из списка доступных
			if (commanderName != null) {
				getFreeOfficerBox().getItems().remove(
						commanderName
				);
				// установить отсутствие командира в боксе
				getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
			}
		}
	}

	/**
	 * @return true if all input field are valid to add the object
	 */
	private boolean isValid() {
		if (getNumField().getText().isEmpty() ||
				! StringUtil.isNumeric(getNumField().getText())) return false;
		if (getHigherIdField().getText().isEmpty() ||
				! StringUtil.isNumeric(getHigherIdField().getText())) return false;
		if (getFreeOfficerBox().getValue().equals(StringUtil.KEEP))
			return false;
		return true;
	}

	/**
	 * @return Text field for id of a higher entity
	 */
	public TextField getHigherIdField();

	/**
	 * @return Text field for number of the current entity
	 */
	public TextField getNumField();

	/**
	 * @param cls Reflecting class of an object to be deleted
	 * @param id  id of an object to be deleted
	 */
	public default void deleteById(Class<T> cls, int id) {
		MainApplicationController.getDao().deleteObjById(cls, id);

	}


	/**
	 * @param cls Reflecting class for entity to updated free officers for
	 */
	public default void updateFreeOfficers(Class<T> cls) {
		List<String> freeOfficers = getFreeOfficers(cls);
		getFreeOfficerBox().getItems().clear();
		getFreeOfficerBox().getItems().addAll(StringUtil.UNSELECTED, StringUtil.KEEP);
		getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
		getFreeOfficerBox().getItems().addAll(freeOfficers);
	}

	/**
	 * @param cls Reflecting class for entity
	 * @return list of free officers for provided entity
	 */
	private List<String> getFreeOfficers(Class<T> cls) {
		return MainApplicationController.getDao().getFreeOfficersForClass(cls);
	}

	public void updateFreeOfficers();
}

