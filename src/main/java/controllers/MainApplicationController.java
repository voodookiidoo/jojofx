package controllers;

import dao.Dao;
import javafx.fxml.FXML;

public class MainApplicationController {
	// фабрики для заполнения столбцов таблицы

	// Объект соединения
	private static Dao dao = new Dao();

	public static Dao getDao() {
		return dao;
	}

	@FXML
	public void stop() throws Exception {
	}

	@FXML
	private void initialize() {
		dao = new Dao(); // initialize dao

	}


}
