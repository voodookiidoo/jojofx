package dao;

import javafx.scene.control.TableColumn;
import models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import util.StringUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Dao {
	private static final Map<Class<?>, String> tableNamesMap = new HashMap<>() {{
		put(Army.class, "army");
		put(TacticUnit.class, "tacticunit");
		put(MilitaryDistrict.class, "mildistrict");
		put(MilitaryUnit.class, "milunit");
		put(Company.class, "company");
		put(Crew.class, "crew");

	}};
	private static final Map<Class<?>, String> upperTableNamesMap = new HashMap<>() {{
		put(TacticUnit.class, "army");
		put(MilitaryUnit.class, "tacticunit");
		put(Company.class, "milunit");
		put(Crew.class, "company");
	}};
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
		dao.updateCommanderForObj(Company.class, 1, "dave");
	}

	public <T> void updateCommanderForObj(Class<T> cls, int id, String commanderName) {
		String tableName = tableNamesMap.get(cls);
		String sqlString = "update %s set commander_id=(select id from officers where fullname=?) where id=?".formatted(tableName);
		jdbcTemplate.update(sqlString, commanderName, id);
	}

	/**
	 * @param cls Reflecting class of the object to be extracted
	 * @return list of free officers for provided cls table
	 */
	public <T> List<String> getFreeOfficersForClass(Class<T> cls) {
		String tableName = tableNamesMap.get(cls);
		String sqlString = "select fullname from officers where id not in (select commander_id from %s where commander_id is not null);".formatted(
				tableName
		);
		return jdbcTemplate.query(sqlString,
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
				" values (NULL, ?);", armyNum);
	}

	public void addArmy(String officerName, int armyNum) {
		jdbcTemplate.update("insert into army (commander_id, army_number)" +
						"values ((select id from officers where fullname = ?), ?);",
				officerName, armyNum);
	}

	public void updateArmyNumById(int armyId, int armyNum) {
		jdbcTemplate.update(
				"update army set army_number=? where id=?",
				armyNum, armyId);
	}

	public void setNullArmyCommander(int armyId) {
		jdbcTemplate.update("update army set commander_id=null where id=?",
				armyId);
	}

	public void setArmyCommanderName(int armyId, String commanderName) {
		jdbcTemplate.update(
				"update army set commander_id=(select id from officers where fullname=?) where id=?",
				commanderName, armyId);
	}

	public List<TacticUnit> getTacticUnits() {
		return jdbcTemplate.query(
				"select tacticunit.id, fullname, army_id, unit_type, unit_number from tacticunit left join officers on officers.id = commander_id;",
				new TacticUnit.TacticUnitRowMapper());
	}

	public void addTacticUnitNoCommander(int armyId, String unitType, int unitNum) {
		jdbcTemplate.update(
				"insert into tacticunit(army_id, unit_type, unit_number) " +
						"values (?, ?, ?)", armyId, unitType, unitNum);
	}

	public void addTacticUnit(int armyId, String commanderName, String unitType, int unitNum) {
		jdbcTemplate.update(
				"insert into tacticunit(army_id, commander_id, unit_type, unit_number) " +
						"values (?, (select id from officers where fullname = ?), ?, ?)", armyId, commanderName, unitType, unitNum);
	}

	public void setNullTacticUnitCommander(int unitId) {
		jdbcTemplate.update("update tacticunit set commander_id=null where id=?",
				unitId);
	}

	public void setTacticUnitCommander(String commanderName, int unitId) {
		jdbcTemplate.update(
				"update tacticunit set commander_id=(select id from officers where fullname=?) where id=?",
				commanderName, unitId);
	}

	/**
	 * @param cls Reflecting class of the object to be deleted
	 * @param id  id of the object to be deleted
	 */
	public <T> void deleteObjById(Class<T> cls, int id) {
		String tableName = tableNamesMap.get(cls);
		String sqlString = "delete from %s where id=?".formatted(
				tableName
		);
		jdbcTemplate.update(sqlString, id);
	}


	/**
	 * @param cls           Reflecting class of the object to be added
	 * @param upId          id of the higher entity to be referenced at
	 * @param commanderName name of the commander, NULLABLE
	 * @param objNum        number of the object to add
	 */
	public <T> void addObj(Class<T> cls, int upId, String commanderName, int objNum) {
		String tableName = tableNamesMap.get(cls);
		String higherTableName = upperTableNamesMap.get(cls);
		if (StringUtil.UNSELECTED.equals(commanderName)) {
			String sqlString = "insert into %s (commander_id, %s_number, %s_id) values (null,?,?)".formatted(tableName,
					tableName,
					higherTableName);
			jdbcTemplate.update(sqlString,
					objNum, upId);
		} else {
			String sqlString = "insert into %s (commander_id, %s_number,%s_id) values ((select id from officers where fullname = ?), ?,?)".formatted(
					tableName, tableName, higherTableName
			);
			jdbcTemplate.update(
					sqlString,
					commanderName,
					objNum,
					upId
			);
		}
	}

	public <T> void updateHigherObjId(Class<T> cls, int id, int upId) {
		//TODO реализовать обновление айдишника сущности выше по рангу
		String tableName = tableNamesMap.get(cls);
		String higherTableName = upperTableNamesMap.get(cls);
		String sqlString =
				"update %s set %s_id=? where id = ?".formatted(tableName,
						higherTableName);
		jdbcTemplate.update(sqlString, upId, id);
	}

	public <T> void updateOjbNum(Class<T> cls, int id, int objNum) {
		//TODO реализовать обновление номера объекта
		String tableName = tableNamesMap.get(cls);
		String sqlString =
				"update %s set %s_number=? where id=?".formatted(
						tableName,
						tableName
				);
		jdbcTemplate.update(sqlString, objNum, id);
	}

	public List<Company> getCompanies() {
		return jdbcTemplate.query(
				"select company.id, company_number, company.milunit_id, officers.fullname from company left join officers on company.commander_id = officers.id", new Company.CompanyRowMapper()
		);
	}

	public void addOfficer(String name, String milRank, String education, LocalDate date, String major, String commType) {
		String sqlString = "insert into officers(" +
				"fullname, military_rank," +
				"education, rank_date," +
				"major, comm_type) values " +
				"(?,?,?,?,?,?)";
		jdbcTemplate.update(sqlString,
				name, milRank, education,
				date, major, commType);
	}

	public void updateOfficerDate(int id, LocalDate date) {
		jdbcTemplate.update(
				"update officers set rank_date=? where id=?",
				date, id);
	}

	public void updateOfficerName(int id, String name) {
		jdbcTemplate.update("update officers set fullname=? where id=?",
				name, id);
	}

	public void updateOfficerEducation(int id, String education) {
		jdbcTemplate.update("update officers set education=? where id=?",
				education, id);
	}

	public void updateOfficerMajor(int id, String major) {
		jdbcTemplate.update("update officers set major=? where id=?",
				major, id);
	}

	public void updateOfficerType(int id, String type) {
		jdbcTemplate.update("update officers set comm_type=? where id=?",
				type, id);

	}

	public void deleteOfficerById(int id) {
		jdbcTemplate.update(
				"delete from officers where id=?",
				id
		);
	}

	public void updateOfficerRank(int id, String milRank) {
		jdbcTemplate.update(
				"update officers set military_rank=? where id=?",
				milRank, id
		);
	}

	public List<Crew> getCrews() {
		return jdbcTemplate.query(
				"select crew.id, crew_number, crew.company_id, officers.fullname from crew left join officers on crew.commander_id = officers.id",
				new Crew.CrewRowMapper());

	}

	public List<MilitaryUnit> getMilUnits() {
		return jdbcTemplate.query(
				"select milunit.id,tacit_unit_id,mildistrict_id,fullname,milunit_number " +
						"from milunit left join officers on milunit.commander_id = officers.id",
				new MilitaryUnit.MilUnitRowMapper()
		);
	}

	public List<String> getFreeOfficersForMilUnit() {;
		String sqlString = "select fullname from officers where id not in (select commander_id from milunit where commander_id is not null);";
		return jdbcTemplate.query(sqlString,
				(rs, rowNum) -> rs.getString("fullname"));
	}

	public void addMilUnit(int tactiUnitId, int mildistrictId, String commanderName, int milUnitNum) {
		String sqlString = "insert into milunit(tacit_unit_id,mildistrict_id, commander_id, milunit_number)" +
				"values(?,?,?,?)";
		if (commanderName.equals(StringUtil.UNSELECTED)) commanderName = null;
		jdbcTemplate.update(sqlString,
				tactiUnitId, mildistrictId, commanderName, milUnitNum);

	}
}
