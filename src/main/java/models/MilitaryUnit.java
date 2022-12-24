package models;

import javafx.beans.property.SimpleIntegerProperty;

public class MilitaryUnit {
	final private SimpleIntegerProperty id = new SimpleIntegerProperty();
	final private SimpleIntegerProperty tactic_unit_id = new SimpleIntegerProperty();
	final private SimpleIntegerProperty mil_district_id = new SimpleIntegerProperty();
	final private SimpleIntegerProperty commander_id = new SimpleIntegerProperty();
	final private SimpleIntegerProperty part_number = new SimpleIntegerProperty();

	public MilitaryUnit(int id, int tacticId, int milDistrictId, int commanderId, int partNumber) {
		this.id.set(id);
		this.tactic_unit_id.set(tacticId);
		this.mil_district_id.set(milDistrictId);
		this.commander_id.set(commanderId);
		this.part_number.set(partNumber);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public SimpleIntegerProperty idProperty() {
		return id;
	}

	public int getTactic_unit_id() {
		return tactic_unit_id.get();
	}

	public void setTactic_unit_id(int tactic_unit_id) {
		this.tactic_unit_id.set(tactic_unit_id);
	}

	public SimpleIntegerProperty tactic_unit_idProperty() {
		return tactic_unit_id;
	}

	public int getMil_district_id() {
		return mil_district_id.get();
	}

	public void setMil_district_id(int mil_district_id) {
		this.mil_district_id.set(mil_district_id);
	}

	public SimpleIntegerProperty mil_district_idProperty() {
		return mil_district_id;
	}

	public int getCommander_id() {
		return commander_id.get();
	}

	public void setCommander_id(int commander_id) {
		this.commander_id.set(commander_id);
	}

	public SimpleIntegerProperty commander_idProperty() {
		return commander_id;
	}

	public int getPart_number() {
		return part_number.get();
	}

	public void setPart_number(int part_number) {
		this.part_number.set(part_number);
	}

	public SimpleIntegerProperty part_numberProperty() {
		return part_number;
	}

}