package mines;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller {
	private int hSize;
	private int wSize;
	private int NumOfmines;
	private Stage primaryStage;
	private VBox menu;
	private GridPane grid;

	public int getWidthSize() {
		return wSize;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public int getNumberOfmines() {
		return NumOfmines;
	}

	public int getHeightSize() {
		return hSize;
	}

	public void setFields(VBox fields) {
		this.menu = fields;
	}

	@FXML
	private Button button;

	@FXML
	private TextField height;

	@FXML
	private TextField width;

	@FXML
	private TextField mines;

	public void startGame() {
		Image errorImage = new Image(getClass().getResourceAsStream("error.png"));
		button.setText("Reset");
		primaryStage.close(); // Close that latest stage
		// Creating new root , inside root there is the fxml file and the grid of the
		// game
		HBox root = new HBox();
		root.setStyle("-fx-background-color:LIGHTBLUE");
		root.getChildren().removeAll(grid, menu);
		root.getChildren().add(menu);
		try {
			// When clicking on the button collect the parameter fields of board size and
			// number of mines
			hSize = Integer.valueOf(height.getText());
			wSize = Integer.valueOf(width.getText());
			NumOfmines = Integer.valueOf(mines.getText());

			// Check if the number of mines is bigger the the board size
			if (NumOfmines >= hSize * wSize)
				throw new NumberFormatException();

			// Creating the game grid of buttons
			grid = createGridPane();
			root.getChildren().add(grid);

			// If the parameter given was invalid show pop up window
		} catch (NumberFormatException e) {
			openDialog(errorImage, -1); // open error dialog
		}
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

	}

	// Function that create all of the game behavior
	public GridPane createGridPane() {
		GridPane grid = new GridPane();
		// Loading all of the images
		Image bombImage = new Image(getClass().getResourceAsStream("bomb.png"));
		Image flagImage = new Image(getClass().getResourceAsStream("flag.png"));
		Image loseImage = new Image(getClass().getResourceAsStream("lose.png"));
		Image winImage = new Image(getClass().getResourceAsStream("win.png"));

		ImageView iv = new ImageView();
		iv.setFitHeight(100);
		iv.setFitWidth(200);


		VBox v = new VBox();
		// Holds all of the list of buttons
		ArrayList<ArrayList<Button>> buttonsList = new ArrayList<>();
		// Creating the engine of the game ,in the size that were given in the menu
		// section
		Mines mines = new Mines(getHeightSize(), getWidthSize(), getNumberOfmines());

		// Class for the right click of board buttons
		class RightClick implements EventHandler<MouseEvent> {
			private int i;
			private int j;

			public RightClick(int i, int j) {
				this.i = i;
				this.j = j;
			}

			// If clicking on board button(right click) , its put flag on the board (i,j)
			@Override
			public void handle(MouseEvent event) {
				mines.toggleFlag(i, j);
				ImageView fiv = new ImageView();
				fiv.setImage(flagImage);
				fiv.setFitHeight(20);
				fiv.setFitWidth(20);

				if (event.getButton() == MouseButton.SECONDARY) {
					buttonsList.get(i).get(j).setGraphic(fiv);
					buttonsList.get(i).get(j).setText("");
				}
				if (!mines.getBoard(i, j).isFlag()) {
					buttonsList.get(i).get(j).setGraphic(null);
					buttonsList.get(i).get(j).setText(".");
				}
			}
		}

		// Class for the right click of board buttons
		class LeftClick implements EventHandler<ActionEvent> {
			private int i;
			private int j;

			public LeftClick(int i, int j) {
				this.i = i;
				this.j = j;
			}

			// If clicking on board button(left click) , its reveal the result of this board
			// (i,j):
			// *bomb
			// *number of mines nearby
			// *open all of the neighbors is they are not mines
			// *check for win/lose section
			@Override
			public void handle(ActionEvent clicked) {
				if(mines.getBoard(i, j).isOpen())
					return;
				// If its flag do nothing
				if (mines.getBoard(i, j).isFlag())
					return;
				// After left clicking check for win or lose , and open board (i,j)
				if (!mines.open(i, j)) {
					mines.setShowAll(true); // Show all of the board items
					showAll(buttonsList);
					ImageView biv = new ImageView();
					biv.setImage(bombImage);
					biv.setFitHeight(20);
					biv.setFitWidth(20);
					buttonsList.get(i).get(j).setText("");
					buttonsList.get(i).get(j).setGraphic(biv);
					buttonsList.get(i).get(j).setStyle("-fx-background-color:RED");
					openDialog(loseImage, 0); // Open lose window
				} else if (mines.isDone()) {
					mines.setShowAll(true);
					showAll(buttonsList);
					openDialog(winImage, 1); // open win window
				} else
					showAll(buttonsList);
			}
			// Every win/lose/open refresh all of the buttons
			private void showAll(ArrayList<ArrayList<Button>> buttons) {
				for (int i = 0; i < getHeightSize(); i++)
					for (int j = 0; j < getWidthSize(); j++) {
						try {
							int val = Integer.valueOf(mines.get(i, j)); // Collect from getString the board in (i,j) value
							// if val = number and there is no flag in (i,j) return the number of mine
							// Nearby
							if (val == 1 && !mines.getBoard(i, j).isFlag()) {
								buttons.get(i).get(j).setText("1");
								buttons.get(i).get(j).setTextFill(Color.BLUE);
							} else if (val == 2 && !mines.getBoard(i, j).isFlag()) {
								buttons.get(i).get(j).setText("2");
								buttons.get(i).get(j).setTextFill(Color.GREEN);
							} else if (val == 3 && !mines.getBoard(i, j).isFlag()) {
								buttons.get(i).get(j).setText("3");
								buttons.get(i).get(j).setTextFill(Color.RED);
							}else if (val >= 4 && !mines.getBoard(i, j).isFlag()) {
								buttons.get(i).get(j).setText(mines.get(i, j));
								buttons.get(i).get(j).setTextFill(Color.DARKBLUE);
							}
							// If val its not a number throw NumberFormatException, catch it and check what
							// the type of board in place (i,j)
						} catch (NumberFormatException e) {
							if (mines.get(i, j) == "X") {
								if (mines.getBoard(i, j).isFlag()) { // If there is flag on mine and the user reveal it
																	// fill the board in place (i,j) to green
									buttons.get(i).get(j).setStyle("-fx-background-color:GREEN");
								} else {
									ImageView biv = new ImageView();
									biv.setImage(bombImage);
									biv.setFitHeight(20);
									biv.setFitWidth(20);
									buttons.get(i).get(j).setText("");
									buttons.get(i).get(j).setGraphic(biv);
								}
							} else if (mines.get(i, j) == "F") { // If its flag do not write from getString (image instead)
								buttons.get(i).get(j).setText("");
							} else if (mines.get(i, j) == "." && !mines.getBoard(i, j).isFlag()) { // If its empty and there is
																							// no flag write "." on
																							// button
								buttons.get(i).get(j).setText(".");
							} else if (mines.get(i, j) == " " && !mines.getBoard(i, j).isFlag()) {
								buttons.get(i).get(j).setText(" ");
							}
						}
					}
			}

		}

		// Creating all of the buttons
		ArrayList<HBox> HButtons = new ArrayList<>();

		for (int i = 0; i < getHeightSize(); i++) {
			HBox row = new HBox();
			ArrayList<Button> buttons = new ArrayList<>();
			for (int j = 0; j < getWidthSize(); j++) {
				Button bt = new Button(".");
				bt.setStyle("-fx-font-weight: bold");
				bt.setPrefSize(40, 40);
				buttons.add(bt);
				bt.setOnAction(new LeftClick(i, j));
				bt.setOnMouseClicked(new RightClick(i, j));
			}
			buttonsList.add(buttons);
			row.getChildren().addAll(buttons);
			HButtons.add(row);
		}

		// Creating and attached the logo above the buttons
		VBox logo = new VBox();
		logo.setPadding(new Insets(5));
		logo.setAlignment(Pos.CENTER);
		logo.getChildren().add(iv);
		v.getChildren().add(logo);
		grid.getChildren().add(v);
		v.getChildren().addAll(HButtons);

		return grid;
	}

	// Dialog message if the user enter wrong input , win or lose.
	// Operation: 1 = win , 0 = lose , -1 = error
	private void openDialog(Image image, int operation) {
		VBox end = new VBox();
		Stage endStage = new Stage();
		Scene endScene = new Scene(end, 200, 200);
		ImageView biv = new ImageView();
		Button loseErrorButton = new Button("Lets Play Again");
		loseErrorButton.setFont(new Font ("Ravie",12));
		Button winButton = new Button("New Game");
		winButton.setFont(new Font ("Ravie",12));
		Label errorLabel = new Label("Wrong input");
		errorLabel.setFont(new Font ("Ravie",12));
		biv.setFitHeight(120);
		biv.setFitWidth(120);
		end.setStyle("-fx-background-color:LIGHTBLUE");
		end.setAlignment(Pos.CENTER);
		end.getChildren().add(biv);

		class ButtonClicked implements EventHandler<ActionEvent> {
			Stage loseStage;

			public ButtonClicked(Stage stage) {
				loseStage = stage;
			}
			@Override
			public void handle(ActionEvent event) {
				startGame();
				loseStage.close();
			}
		}
	class ButtonClickedError implements EventHandler<ActionEvent> {
			Stage loseStage;
			public ButtonClickedError(Stage stage) {
				loseStage = stage;
			}
			@Override
			public void handle(ActionEvent event) {
				loseStage.close();
			}
		}
		loseErrorButton.setOnAction(new ButtonClicked(endStage));
		winButton.setOnAction(new ButtonClicked(endStage));
		if (operation == 1) { // Win situation
			biv.setImage(image);
			end.getChildren().add(winButton);
		} else if (operation == 0) { // Lose situation
			biv.setImage(image);
			end.getChildren().add(loseErrorButton);
		} else { // Error situation
			end.getChildren().add(errorLabel);
			biv.setImage(image);
			loseErrorButton.setOnAction(new ButtonClickedError(endStage));
			end.getChildren().add(loseErrorButton);
		}
		endStage.setAlwaysOnTop(true);
		endStage.setScene(endScene);
		endStage.show();
	}

}
