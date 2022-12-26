package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MilitaryDistrict {
	final private SimpleIntegerProperty id = new SimpleIntegerProperty();
	final private SimpleStringProperty name = new SimpleStringProperty();
	final private SimpleStringProperty place = new SimpleStringProperty();
	public MilitaryDistrict() {
	}
	public MilitaryDistrict(int id, String name, String place) {
		this.id.set(id);
		this.name.set(name);
		this.place.set(place);
	}

	@Override
	public String toString() {
		return "MilitaryDistrict{" +
				"id=" + id.get() +
				", name=" + name.get() +
				", place=" + place.get() +
				'}';
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

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public String getPlace() {
		return place.get();
	}

	public void setPlace(String place) {
		this.place.set(place);
	}

	public SimpleStringProperty placeProperty() {
		return place;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MilitaryDistrict that = (MilitaryDistrict) o;

		if (id != null ? ! id.equals(that.id) : that.id != null) return false;
		if (name != null ? ! name.equals(that.name) : that.name != null) return false;
		return place != null ? place.equals(that.place) : that.place == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (place != null ? place.hashCode() : 0);
		return result;
	}

	public static class MilitaryDistrictPropertyMapper implements RowMapper<MilitaryDistrict> {

		@Override
		public MilitaryDistrict mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MilitaryDistrict(rs.getInt("id"),
					rs.getString("name"),
					rs.getString("place"));
		}
	}
}
