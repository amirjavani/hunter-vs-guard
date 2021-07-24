package Game;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuCon implements Initializable {



    public AnchorPane AP;
    double buttonsX = 30;
    double buttonsY = 120;
    private InfoLabel infoLabel ;
    private NameGetter nameGetter ;
    static String name ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartButton();
        HelpButton();
        AboutButton();
        ExitButton();
    }

    public void StartButton(){
        MenuButton startButton = new MenuButton("START");
        startButton.setLayoutY(buttonsY);
        startButton.setLayoutX(buttonsX);
        AP.getChildren().add(startButton);
        startButton.setOnMouseClicked(mouseEvent -> {
            clickSound();
            AP.getChildren().remove(infoLabel);
            nameGetter = new NameGetter(AP);
            AP.getChildren().add(nameGetter);
            FadeTransition(nameGetter);
            nameGetter.back.setOnMouseClicked(mouseEvent1 -> {
                clickSound();
                AP.getChildren().remove(nameGetter);
            });
            nameGetter.textField.setOnAction(actionEvent -> {
                name  = nameGetter.textField.getText();
                try {
                    NextPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            nameGetter.ok.setOnMouseClicked(mouseEvent12 -> {
                clickSound();
                name = nameGetter.textField.getText();
                try {
                    NextPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        });
    }

    public void NextPage() throws IOException {
        Stage primaryStage = (Stage) AP.getScene().getWindow();
        primaryStage.close();
        primaryStage =new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game/Level_Menu.fxml"));
        Parent root = loader.load();
        LevelMenu levelMenuCon = loader.getController();
        levelMenuCon.setName(name);
        primaryStage.setTitle("Guard Vs Hunter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void HelpButton(){
        MenuButton helpButton = new MenuButton("HELP");
        helpButton.setLayoutY(buttonsY +=60);
        helpButton.setLayoutX(buttonsX +=50);
        AP.getChildren().add(helpButton);
        helpButton.setOnMouseClicked(mouseEvent -> {
            clickSound();
            AP.getChildren().remove(nameGetter);
            AP.getChildren().remove(infoLabel);
            infoLabel = new InfoLabel("This game is a sample of plant vs zombie \n hunter attacks your home from right of the map\n" +
                    "you have to place your guard in the map and for that you need money for each guard \n and the money will respawn in map and \n you have " +
                    "to pick them \n if you could defend your house successfully you win",250,300);
            AP.getChildren().add(infoLabel);
            transition(infoLabel);
        });
    }
    public void AboutButton(){
        MenuButton aboutButton = new MenuButton("ABOUT");
        aboutButton.setLayoutY(buttonsY +=60);
        aboutButton.setLayoutX(buttonsX +=50);
        AP.getChildren().add(aboutButton);
        aboutButton.setOnMouseClicked(mouseEvent -> {
            clickSound();
            AP.getChildren().remove(nameGetter);
            AP.getChildren().remove(infoLabel);
            infoLabel = new InfoLabel("amir javani\ninsta: @amir_Jaw\nMail : amirjavani@outlook.com ",200,250);
            AP.getChildren().add(infoLabel);
            transition(infoLabel);
        });
    }
    public void ExitButton(){
        MenuButton exitButton = new MenuButton("EXIT");
        exitButton.setLayoutY(buttonsY +=60);
        exitButton.setLayoutX(buttonsX +=50);
        AP.getChildren().add(exitButton);
        exitButton.setOnMouseClicked(mouseEvent -> {
            clickSound();
            Stage stage = (Stage) AP.getScene().getWindow();
            stage.close();
        });


    }

    public void transition(Label label){
        TranslateTransition TT = new TranslateTransition();
        TT.setNode(label);
        TT.setDuration(Duration.seconds(2));
        TT.setFromX(800);
        TT.setToX(300);
        TT.setCycleCount(1);
        TT.play();
    }

    public void FadeTransition(AnchorPane pane){
        FadeTransition ft = new FadeTransition(Duration.millis(1000), pane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
    }

    public void clickSound(){
        new AudioClip(
                getClass()
                        .getResource("/audio/switch3.mp3")
                        .toExternalForm())
                .play();
    }

}
