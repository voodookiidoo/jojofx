package dao;

import models.MilitaryDistrict;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.Properties;

public class Dao {
	private final JdbcTemplate jdbcTemplate;

	public Dao() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		Properties properties = new Properties();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/military?serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setConnectionProperties(properties);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public static void main(String... args) {
		Dao dao = new Dao();
		System.out.println(dao.getMilitaryDistricts());
	}

	public List<MilitaryDistrict> getMilitaryDistricts() {
		return jdbcTemplate.query("select * from mildistrict", new MilitaryDistrict.MilitaryDistrictPropertyMapper());
	}
}
