module jvfxproject {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.web;
	requires java.sql;
	exports controllers;
	exports models;
	opens controllers;
	opens application;
}