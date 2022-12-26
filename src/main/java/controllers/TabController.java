package controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

public interface TabController<T> {
	public boolean inputDataValid();
	public void localUpdate();

	public void globalUpdate();

	default public T getSelectedRow() {
		ObservableList<TablePosition> selectedRows = getTable().getSelectionModel().getSelectedCells();
		if (selectedRows == null || selectedRows.size() == 0) {
			return null;
		}
		return (T) getTable().getItems().get(selectedRows.get(0).getRow());
	}

	public TableView<T> getTable();
}
