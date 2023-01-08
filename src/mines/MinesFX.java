package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MinesFX extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage Stage) throws Exception {
		VBox h;
		Controller controller=new Controller();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Game.fxml"));
			h = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		controller.setPrimaryStage(Stage);
		controller.setFields(h);
		Stage.setTitle("Mine-Sweeper");
		Stage.setScene(new Scene(h));
		Stage.show();
	}
}
