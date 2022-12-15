package com.example.minirpgfinal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Main extends Application {
    static Game theGame = new Game();              // create instance of class Game

    public static void main(String[] args) {
        //we need to put the choice between console and GUI
        System.out.println("Game Start!");
        if(theGame.isItAGraphicGame()) //function to ask in Console Mode if we want GUI or console IHM
        {
            System.out.println("Before launch !");  //just used to observe if launch function is blocking funcion or not
            launch(args);                           //initialize javaFx that will call start function below
            System.out.println("After launch !");   //console Window shows that launch function is blocking
        }
        else
        {
            theGame.playGame();                     // if console Mode is selected call public member that launches the game
        }
        System.out.println("Game Stop!");
    }

    @Override
    //effective javaFx window creation
    public void start(Stage primaryStage) {
        // Create a scene
        Label label = new Label(""); //create the label
        label.setPadding(new Insets(50,50,50,50));
        theGame.lbl = label;

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().add(label);

        Scene scene = new Scene(root, 800, 480);

        // Set the scene and show the stage
        primaryStage.setMaximized(true);
        primaryStage.setTitle("miniRPG");
        scene.setFill(Color.LIGHTBLUE);
        primaryStage.setScene(scene);
        primaryStage.show(); //display javaFx window with the attached scene
        theGame.playGame(); // call public member that launches the game
    }
}
