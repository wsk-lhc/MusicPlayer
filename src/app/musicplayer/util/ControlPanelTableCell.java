package app.musicplayer.util;

import app.musicplayer.model.Song;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ControlPanelTableCell<S, T> extends TableCell<S, T> {
	
	private ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			ControlPanelTableCell.this.updateItem(ControlPanelTableCell.this.getItem(), ControlPanelTableCell.this.isEmpty());
		}
	};
	
	@Override
	protected void updateItem(T item, boolean empty) {
		
		super.updateItem(item, empty);
		
		Song song = (Song) this.getTableRow().getItem();
		
		if (empty || item == null || song == null) {
			setText(null);
			setGraphic(null);
		} else if (!song.getSelected()) {
			setText(item.toString());
			setGraphic(null);
			song.selectedProperty().removeListener(listener);
			song.selectedProperty().addListener(listener);
		} else {
			try {
				Label text = new Label(item.toString());
				text.setTextOverrun(OverrunStyle.CLIP);
				String fileName = Resources.FXML + "ControlPanel.fxml";
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));
                HBox controlPanel = (HBox) loader.load();
                BorderPane cell = new BorderPane();
                cell.setRight(controlPanel);
                cell.setCenter(text);
                BorderPane.setAlignment(text, Pos.CENTER_LEFT);
                BorderPane.setAlignment(controlPanel, Pos.CENTER_LEFT);
                setText(null);
                setGraphic(cell);
                song.selectedProperty().removeListener(listener);
    			song.selectedProperty().addListener(listener);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
		}
	}
}