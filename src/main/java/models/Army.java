package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Army {

	final public SimpleIntegerProperty id, army_number;
	final public SimpleStringProperty fullname;

	public Army(int id, String name, int armyNum) {
		this.id = new SimpleIntegerProperty(id);
		this.fullname = new SimpleStringProperty(name);
		this.army_number = new SimpleIntegerProperty(armyNum);
	}

	public String getFullname() {
		return fullname.get();
	}

	public void setFullname(String fullname) {
		this.fullname.set(fullname);
	}

	public SimpleStringProperty fullnameProperty() {
		return fullname;
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

	public int getArmy_number() {
		return army_number.get();
	}

	public void setArmy_number(int army_number) {
		this.army_number.set(army_number);
	}

	public SimpleIntegerProperty army_numberProperty() {
		return army_number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Army army = (Army) o;

		if (! Objects.equals(id, army.id)) return false;
		if (! Objects.equals(army_number, army.army_number)) return false;
		return Objects.equals(fullname, army.fullname);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (army_number != null ? army_number.hashCode() : 0);
		result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
		return result;
	}

	public static class ArmyRowMapper implements RowMapper<Army> {

		@Override
		public Army mapRow(ResultSet rs, int rowNum) throws SQLException {
			String fullname = rs.getString("fullname");
			return new Army(
					rs.getInt("id"),
					fullname==null? StringUtil.UNSELECTED :fullname,
					rs.getInt("army_number")
			);

		}
	}
}
