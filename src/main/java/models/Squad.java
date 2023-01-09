package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Squad {
	final private SimpleIntegerProperty id, crewId,
			squadNum;
	final private SimpleStringProperty commanderName;

	public Squad(int id, int crewId, int squadNum, String name) {
		this.id = new SimpleIntegerProperty(id);
		this.crewId = new SimpleIntegerProperty(crewId);
		this.squadNum = new SimpleIntegerProperty(squadNum);
		this.commanderName = new SimpleStringProperty(name);
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

	public int getCrewId() {
		return crewId.get();
	}

	public void setCrewId(int crewId) {
		this.crewId.set(crewId);
	}

	public SimpleIntegerProperty crewIdProperty() {
		return crewId;
	}

	public int getSquadNum() {
		return squadNum.get();
	}

	public void setSquadNum(int squadNum) {
		this.squadNum.set(squadNum);
	}

	public SimpleIntegerProperty squadNumProperty() {
		return squadNum;
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

	public static class SquadRowMapper implements RowMapper<Squad> {
		@Override
		public Squad mapRow(ResultSet rs, int rowNum) throws SQLException {
			String name = rs.getString("fullname");
			if (name == null) name = StringUtil.UNSELECTED;
			return new Squad(
					rs.getInt("id"),
					rs.getInt("crew_id"),
					rs.getInt("squad_number"),
					name
			);
		}
	}
}
