package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Person implements Comparable<Person> {

	final private SimpleStringProperty name;
	final private SimpleIntegerProperty age;
	final private SimpleIntegerProperty id;

	public Person(String name, int age, int id) {
		this.name = new SimpleStringProperty(name);
		this.age = new SimpleIntegerProperty(age);
		this.id = new SimpleIntegerProperty(id);
	}

	@Override
	public String toString() {
		return "Person{" +
				"name=" + name +
				", age=" + age +
				", weight=" + id +
				'}';
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public int getAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public SimpleIntegerProperty ageProperty() {
		return age;
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

	@Override
	public int compareTo(Person o) {
		return this.getName().compareTo(o.getName());
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
}
