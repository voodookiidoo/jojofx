package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Officer {
	final private SimpleIntegerProperty id;
	final private SimpleStringProperty fullname;
	final private SimpleStringProperty militaryRank;
	final private SimpleStringProperty education;
	final private SimpleStringProperty rankDate;
	final private SimpleStringProperty major;
	final private SimpleStringProperty commType;

	public Officer(int id,
	               String fullname,
	               String militaryRank,
	               String education,
	               String rankDate,
	               String major,
	               String commType) {
		this.id = new SimpleIntegerProperty(id);
		this.fullname = new SimpleStringProperty(fullname);
		this.militaryRank = new SimpleStringProperty(militaryRank);
		this.education = new SimpleStringProperty(education);
		this.rankDate = new SimpleStringProperty(rankDate);
		this.major = new SimpleStringProperty(major);
		this.commType = new SimpleStringProperty(commType);
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

	public String getFullname() {
		return fullname.get();
	}

	public void setFullname(String fullname) {
		this.fullname.set(fullname);
	}

	public SimpleStringProperty fullnameProperty() {
		return fullname;
	}

	public String getMilitaryRank() {
		return militaryRank.get();
	}

	public void setMilitaryRank(String militaryRank) {
		this.militaryRank.set(militaryRank);
	}

	public SimpleStringProperty militaryRankProperty() {
		return militaryRank;
	}

	public String getEducation() {
		return education.get();
	}

	public void setEducation(String education) {
		this.education.set(education);
	}

	public SimpleStringProperty educationProperty() {
		return education;
	}

	public String getRankDate() {
		return rankDate.get();
	}

	public void setRankDate(String rankDate) {
		this.rankDate.set(rankDate);
	}

	public SimpleStringProperty rankDateProperty() {
		return rankDate;
	}

	public String getMajor() {
		return major.get();
	}

	public void setMajor(String major) {
		this.major.set(major);
	}

	public SimpleStringProperty majorProperty() {
		return major;
	}

	public String getCommType() {
		return commType.get();
	}

	public void setCommType(String comm_type) {
		this.commType.set(comm_type);
	}

	public SimpleStringProperty commTypeProperty() {
		return commType;
	}

	@Override
	public String toString() {
		return "Officer{" +
				"id=" + id.get() +
				", fullname=" + fullname.get() +
				", militaryRank=" + militaryRank.get() +
				", education=" + education.get() +
				", rankDate=" + rankDate.get() +
				", major=" + major.get() +
				", commType=" + commType.get() +
				'}';
	}

	public static class OfficerRowMapper implements RowMapper<Officer>
	{
		@Override
		public Officer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Officer(
					rs.getInt("id"),
					rs.getString("fullname"),
					rs.getString("military_rank"),
					rs.getString("education"),
					rs.getString("rank_date"),
					rs.getString("major"),
					rs.getString("comm_type")
			);

		}
	}
}
