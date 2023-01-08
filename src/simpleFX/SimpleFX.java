package simpleFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleFX extends Application {
	private int i = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage Stage) throws Exception {

		Scene scene = new Scene(createVBox());
		Stage.setTitle("Vote");
		Stage.setScene(scene);
		Stage.show();
	}

	private VBox createVBox() {
		VBox v = new VBox(10);
		HBox h = new HBox(10);
		v.setPadding(new Insets(20));
		Button b1 = new Button("Ofra Haza");
		Button b2 = new Button("Yardena Arazi");
		Label label = new Label("" + i);
		label.setStyle("-fx-background-color:RED");
		h.getChildren().addAll(b1, b2); //add to HBOX the Button 
		v.getChildren().addAll(h, label); // add to Vbox the Hbox and label
		label.setAlignment(Pos.CENTER); //position the text in the center
		label.setMaxHeight(Double.MAX_VALUE);
		label.setMaxWidth(Double.MAX_VALUE);
		class Increase implements EventHandler<ActionEvent> {//if Ofra clicked than increase counter
			@Override
			public void handle(ActionEvent event) {
				i++;
				label.setText("" + i);
			}
		}
		class Decrease implements EventHandler<ActionEvent> {//if Yardena clicked then decrease counter
			@Override
			public void handle(ActionEvent event) {
				i--;
				label.setText("" + i);

			}
		}
		b1.setOnAction(new Increase());
		b2.setOnAction(new Decrease());
		return v;
	}

}
