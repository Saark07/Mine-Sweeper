package simpleFX2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MyController {
	private int i = 0;
	@FXML
	private Button b1;

	@FXML
	private Button b2;

	@FXML
	private Label label;

	@FXML
	void Decrease(ActionEvent event) {//if Yardena clicked then decrease counter
		i--;
		label.setText("" + i);
	}

	@FXML
	void Increase(ActionEvent event) {//if Ofra clicked than increase counter
		i++;
		label.setText("" + i);
	}

}
