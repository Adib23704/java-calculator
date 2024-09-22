package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private TextField display;
    private double num1 = 0;
    private String operator = "";
    private boolean start = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator by Adib");

        display = new TextField();
        display.setEditable(false);
        display.setStyle("-fx-font-size: 30; -fx-text-fill: #fff; -fx-background-color: #333;");
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPrefHeight(60);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        String[] buttonLabels = {
                "MC", "MR", "M+", "M-",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "C", "+",
                "√", "%", "←", "="
        };

        int row = 1;
        int col = 0;
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.getStyleClass().add("button");
            button.setOnAction(e -> buttonClicked(label));
            grid.add(button, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        grid.add(display, 0, 0, 4, 1);

        Scene scene = new Scene(grid, 350, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        primaryStage.show();
    }
    
    private void buttonClicked(String label) {
        switch (label) {
            case "C":
                display.setText("");
                start = true;
                break;
            case "←":
                if (!display.getText().isEmpty()) {
                    display.setText(display.getText().substring(0, display.getText().length() - 1));
                }
                break;
            case "√":
                display.setText(String.valueOf(Math.sqrt(Double.parseDouble(display.getText()))));
                break;
            case "%":
                display.setText(String.valueOf(Double.parseDouble(display.getText()) / 100));
                break;
            case "=":
                double num2 = Double.parseDouble(display.getText());
                double result = calculate(num1, num2, operator);
                display.setText(String.valueOf(result));
                start = true;
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                operator = label;
                num1 = Double.parseDouble(display.getText());
                display.setText("");
                break;
            default:
                if (start) {
                    display.setText("");
                    start = false;
                }
                display.setText(display.getText() + label);
                break;
        }
    }
    
    private double calculate(double a, double b, String operator) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: return 0;
        }
    }
    
    private void handleKeyPress(KeyCode key) {
        switch (key) {
            case DIGIT0: case NUMPAD0: buttonClicked("0"); break;
            case DIGIT1: case NUMPAD1: buttonClicked("1"); break;
            case DIGIT2: case NUMPAD2: buttonClicked("2"); break;
            case DIGIT3: case NUMPAD3: buttonClicked("3"); break;
            case DIGIT4: case NUMPAD4: buttonClicked("4"); break;
            case DIGIT5: case NUMPAD5: buttonClicked("5"); break;
            case DIGIT6: case NUMPAD6: buttonClicked("6"); break;
            case DIGIT7: case NUMPAD7: buttonClicked("7"); break;
            case DIGIT8: case NUMPAD8: buttonClicked("8"); break;
            case DIGIT9: case NUMPAD9: buttonClicked("9"); break;
            case ADD: case PLUS: buttonClicked("+"); break;
            case SUBTRACT: case MINUS: buttonClicked("-"); break;
            case MULTIPLY: buttonClicked("*"); break;
            case DIVIDE: buttonClicked("/"); break;
            case ENTER: buttonClicked("="); break;
            case BACK_SPACE: buttonClicked("←"); break;
            case DELETE: buttonClicked("C"); break;
            case PERIOD: buttonClicked("."); break;
            case ESCAPE: buttonClicked("C"); break;
            default: break;
        }
    }
}
