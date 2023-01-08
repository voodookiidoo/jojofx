package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Crew {
	final private SimpleIntegerProperty id, companyId,
	crewNumber;
	final private SimpleStringProperty commanderName;

	public Crew(int id, int companyId, int crewNumber, String commanderName) {
		this.id = new SimpleIntegerProperty(id);
		this.companyId = new SimpleIntegerProperty(companyId);
		this.crewNumber = new SimpleIntegerProperty(crewNumber);
		this.commanderName = new SimpleStringProperty(commanderName);
	}

	public int getId() {
		return id.get();
	}

	public SimpleIntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public int getCompanyId() {
		return companyId.get();
	}

	public SimpleIntegerProperty companyIdProperty() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId.set(companyId);
	}

	public int getCrewNumber() {
		return crewNumber.get();
	}

	public SimpleIntegerProperty crewNumberProperty() {
		return crewNumber;
	}

	public void setCrewNumber(int crewNumber) {
		this.crewNumber.set(crewNumber);
	}

	public String getCommanderName() {
		return commanderName.get();
	}

	public SimpleStringProperty commanderNameProperty() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName.set(commanderName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Crew crew = (Crew) o;

		if (! Objects.equals(id, crew.id)) return false;
		if (! Objects.equals(companyId, crew.companyId)) return false;
		if (! Objects.equals(crewNumber, crew.crewNumber)) return false;
		return Objects.equals(commanderName, crew.commanderName);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + companyId.hashCode();
		result = 31 * result + crewNumber.hashCode();
		result = 31 * result + commanderName.hashCode();
		return result;
	}
	public static class CrewRowMapper implements RowMapper<Crew>
	{
		@Override
		public Crew mapRow(ResultSet rs, int rowNum) throws SQLException {
			String commanderName = rs.getString("fullname");
			if (commanderName == null)
				commanderName = StringUtil.UNSELECTED;
			return new Crew(
					rs.getInt("id"),
					rs.getInt("company_id"),
					rs.getInt("crew_number"),
					commanderName
			);
		}
	}
}
