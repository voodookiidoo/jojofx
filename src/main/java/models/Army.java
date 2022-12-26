package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

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
