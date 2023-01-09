package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Militech {
	private final SimpleIntegerProperty id, amount;
	private final SimpleStringProperty type, name;

	public Militech(int id, String type, String name, int amount) {
		this.id = new SimpleIntegerProperty(id);
		this.type = new SimpleStringProperty(type);
		this.name = new SimpleStringProperty(name);
		this.amount = new SimpleIntegerProperty(amount);
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

	public int getAmount() {
		return amount.get();
	}

	public void setAmount(int amount) {
		this.amount.set(amount);
	}

	public SimpleIntegerProperty amountProperty() {
		return amount;
	}

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public SimpleStringProperty typeProperty() {
		return type;
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

	public static class MilitechRowMapper implements RowMapper<Militech> {
		@Override
		public Militech mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Militech(
					rs.getInt("id"),
					rs.getString("tech_type"),
					rs.getString("description"),
					rs.getInt("amount")
					);
		}
	}
}
