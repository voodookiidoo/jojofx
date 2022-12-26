package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Company {
	final private SimpleIntegerProperty
			id, unitId, companyNumber;
	final private SimpleStringProperty commanderName;

	public Company(int id, int unitId, String name, int companyNum) {
		this.id = new SimpleIntegerProperty(id);
		this.unitId = new SimpleIntegerProperty(unitId);
		this.commanderName = new SimpleStringProperty(name);
		this.companyNumber = new SimpleIntegerProperty(companyNum);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + unitId.hashCode();
		result = 31 * result + companyNumber.hashCode();
		result = 31 * result + commanderName.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Company company = (Company) o;

		if (! Objects.equals(id, company.id)) return false;
		if (! Objects.equals(unitId, company.unitId)) return false;
		if (! Objects.equals(companyNumber, company.companyNumber))
			return false;
		return Objects.equals(commanderName, company.commanderName);
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

	public int getUnitId() {
		return unitId.get();
	}

	public void setUnitId(int unitId) {
		this.unitId.set(unitId);
	}

	public SimpleIntegerProperty unitIdProperty() {
		return unitId;
	}

	public int getCompanyNumber() {
		return companyNumber.get();
	}

	public void setCompanyNumber(int companyNumber) {
		this.companyNumber.set(companyNumber);
	}

	public SimpleIntegerProperty companyNumberProperty() {
		return companyNumber;
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

	public static class CompanyRowMapper implements RowMapper<Company> {
		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			String commanderName = rs.getString("fullname");
			if (commanderName == null)
				commanderName = StringUtil.UNSELECTED;
			return new Company(
					rs.getInt("id"),
					rs.getInt("milunit_id"),
					commanderName,
					rs.getInt("company_number")
			);

		}
	}
}
