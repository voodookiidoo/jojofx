package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class TacticUnit {
	public String getCommanderName() {
		return commanderName.get();
	}

	public SimpleStringProperty commanderNameProperty() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName.set(commanderName);
	}

	final private SimpleIntegerProperty
			id, armyId, unitNumber;
	final private SimpleStringProperty unitType, commanderName;

	public TacticUnit(
			int id, int armyId, String commanderName, String unitType, int unitNumber
	) {
		this.id = new SimpleIntegerProperty(id);
		this.armyId = new SimpleIntegerProperty(armyId);
		this.commanderName = new SimpleStringProperty(commanderName);
		this.unitNumber = new SimpleIntegerProperty(unitNumber);
		this.unitType = new SimpleStringProperty(unitType);

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

	public int getArmyId() {
		return armyId.get();
	}

	public void setArmyId(int armyId) {
		this.armyId.set(armyId);
	}

	public SimpleIntegerProperty armyIdProperty() {
		return armyId;
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

	public String getUnitType() {
		return unitType.get();
	}

	public void setUnitType(String unitType) {
		this.unitType.set(unitType);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TacticUnit unit = (TacticUnit) o;

		if (! Objects.equals(id, unit.id)) return false;
		if (! Objects.equals(armyId, unit.armyId)) return false;
		if (! Objects.equals(unitNumber, unit.unitNumber)) return false;
		if (! Objects.equals(unitType, unit.unitType)) return false;
		return Objects.equals(commanderName, unit.commanderName);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + armyId.hashCode();
		result = 31 * result + unitNumber.hashCode();
		result = 31 * result + unitType.hashCode();
		result = 31 * result + commanderName.hashCode();
		return result;
	}

	public SimpleStringProperty unitTypeProperty() {
		return unitType;
	}
	public static class TacticUnitRowMapper implements RowMapper<TacticUnit>
	{
		@Override
		public TacticUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
			String name = rs.getString("fullname");

			if (name == null)
			{
				name = StringUtil.UNSELECTED;
			}
			return new TacticUnit(
				rs.getInt("id"),
					rs.getInt("army_id"),
					name,
					rs.getString("unit_type"),
					rs.getInt("unit_number")
			);
		}
	}
}
