package controllers;

import javafx.scene.control.ChoiceBox;
import util.StringUtil;

import java.util.List;

public interface MilitaryObject<T> {
	public default void updateFreeOfficers(Class<T> cls) {
		List<String> freeOfficers = getFreeOfficers(cls);
		System.out.println("ALL FREE OFFICERS" + freeOfficers);
		getFreeOfficerBox().getItems().clear();
		getFreeOfficerBox().getItems().addAll(StringUtil.UNSELECTED, StringUtil.KEEP);
		getFreeOfficerBox().setValue(StringUtil.UNSELECTED);
		getFreeOfficerBox().getItems().addAll(freeOfficers);
		System.out.println("ITEMS"+getFreeOfficerBox().getItems());
		System.out.println("VALUE" + getFreeOfficerBox().getValue());
	}
	public void updateFreeOfficers();

	public ChoiceBox<String> getFreeOfficerBox();

	 private List<String> getFreeOfficers(Class<T> cls) {
		return MainApplicationController.getDao().getFreeOfficersForClass(cls);
	}
}

