package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MilitaryUnit {
	final private SimpleIntegerProperty id, tacticUnitId, unitNumber, mildistrictId;
	final private SimpleStringProperty commanderName;

	public MilitaryUnit(int id, int tacticUnitId, String name, int mildisId, int unitNum) {
		this.id = new SimpleIntegerProperty(id);
		this.tacticUnitId = new SimpleIntegerProperty(tacticUnitId);
		this.commanderName = new SimpleStringProperty(name);
		this.mildistrictId = new SimpleIntegerProperty(mildisId);
		this.unitNumber = new SimpleIntegerProperty(unitNum);
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

	public int getTacticUnitId() {
		return tacticUnitId.get();
	}

	public void setTacticUnitId(int tacticUnitId) {
		this.tacticUnitId.set(tacticUnitId);
	}

	public SimpleIntegerProperty tacticUnitIdProperty() {
		return tacticUnitId;
	}

	public int getUnitNumber() {
		return unitNumber.get();
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber.set(unitNumber);
	}

	public SimpleIntegerProperty unitNumberProperty() {
		return unitNumber;
	}

	public int getMildistrictId() {
		return mildistrictId.get();
	}

	public void setMildistrictId(int mildistrictId) {
		this.mildistrictId.set(mildistrictId);
	}

	public SimpleIntegerProperty mildistrictIdProperty() {
		return mildistrictId;
	}

	public String getCommanderName() {
		return commanderName.get();
	}

	public void setCommanderName(String commanderName) {
		this.commanderName.set(commanderName);
	}

	public SimpleStringProperty commanderNameProperty() {
		return commanderName;
	}

	public static class MilUnitRowMapper implements RowMapper<MilitaryUnit> {
		@Override
		public MilitaryUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
			String name = rs.getString("fullname");
			if (name == null) name = StringUtil.UNSELECTED;
			return new MilitaryUnit(
					rs.getInt("id"),
					rs.getInt("tacit_unit_id"),
					name,
					rs.getInt("mildistrict_id"),
					rs.getInt("milunit_number")
			);
		}
	}
}
