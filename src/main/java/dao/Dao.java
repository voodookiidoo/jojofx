package dao;

import models.Army;
import models.MilitaryDistrict;
import models.Officer;
import models.TacticUnit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.Objects;
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
		System.out.println(dao.getFreeOfficersForClass(Army.class));
	}

	public List<String> getFreeOfficersForArmies() {
		return jdbcTemplate.query("select fullname from officers where id not in (select commander_id from army where commander_id is not null )",
				(rs, rowNum) -> rs.getString("fullname"));
	}

	public List<MilitaryDistrict> getMilitaryDistricts() {
		return jdbcTemplate.query("select * from mildistrict", new MilitaryDistrict.MilitaryDistrictPropertyMapper());
	}

	public List<Officer> getOfficers() {
		return jdbcTemplate.query("select * from officers", new Officer.OfficerRowMapper());
	}

	public List<Army> getArmies() {
		return jdbcTemplate.query("select army.id, army_number, officers.fullname\nfrom army left join officers on army.commander_id = officers.id", new Army.ArmyRowMapper());
	}

	public void addArmyNoCommander(int armyNum) {
		jdbcTemplate.update("insert into army (commander_id, army_number)" +
				" values (NULL, ?);", new Object[]{armyNum});
	}

	public void addArmy(String officerName, int armyNum) {
		jdbcTemplate.update("insert into army (commander_id, army_number)" +
						"values ((select id from officers where fullname = ?), ?);",
				new Object[]{officerName, armyNum});
	}

	public void deleteArmyById(int id) {
		jdbcTemplate.update("delete from army where id = ?", new Object[]{id});
	}

	public void updateArmyNumById(int armyId, int armyNum) {
		jdbcTemplate.update(
				"update army set army_number=? where id=?",
				new Object[]{armyNum, armyId}
		);
	}

	public void setNullArmyCommander(int armyId) {
		jdbcTemplate.update("update army set commander_id=null where id=?",
				new Object[]{armyId});
	}

	public void setArmyCommanderName(int armyId, String commanderName) {
		jdbcTemplate.update(
				"update army set commander_id=(select id from officers where fullname=?) where id=?",
				new Object[]{commanderName, armyId});
	}

	public List<TacticUnit> getTacticUnits() {
		return jdbcTemplate.query(
				"select tacticunit.id, fullname, army_id, unit_type, unit_number from tacticunit left join officers on officers.id = commander_id;",
				new TacticUnit.TacticUnitRowMapper()
		);
	}

	public List<String> getFreeOfficersForTacticUnits() {
		return jdbcTemplate.query("select fullname from officers where id not in (select commander_id from tacticunit where commander_id is not null )",
				(rs, rowNum) -> rs.getString("fullname"));
	}

	public void addTacticUnitNoCommander(int armyId, String unitType, int unitNum) {
		jdbcTemplate.update(
				"insert into tacticunit(army_id, unit_type, unit_number) " +
						"values (?, ?, ?)", new Object[]{armyId, unitType, unitNum});
	}

	public void addTacticUnit(int armyId, String commanderName, String unitType, int unitNum) {
		jdbcTemplate.update(
				"insert into tacticunit(army_id, commander_id, unit_type, unit_number) " +
						"values (?, (select id from officers where fullname = ?), ?, ?)", new Object[]{armyId, commanderName, unitType, unitNum});
	}

	public void setNullTacticUnitCommander(int unitId) {
		jdbcTemplate.update("update tacticunit set commander_id=null where id=?",
				new Object[]{unitId});
	}

	public void setTacticUnitCommander(String commanderName, int unitId) {
		jdbcTemplate.update(
				"update tacticunit set commander_id=(select id from officers where fullname=?) where id=?",
				new Object[]{commanderName, unitId});
	}

	public <T> List<String> getFreeOfficersForClass(Class<T> cls) {
		String tableName;
		if (Objects.equals(cls, TacticUnit.class)) {
			tableName = "tacticunit";
		} else {
			tableName = "army";
		}
		System.out.println(cls);
		String sqlString = "select fullname from officers where id not in (select commander_id from %s where commander_id is not null);".formatted(
				tableName
		);
		System.out.println(sqlString + "EXECUTED");

		return jdbcTemplate.query(sqlString,
				(rs, rowNum) -> rs.getString("fullname"));
	}
}
