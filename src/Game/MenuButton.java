package Game;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

public class MenuButton extends Button {





    private final String Font_Path= "/font/VIDEOPHREAK.ttf";
    private final String button_Pressed_Style = "-fx-background-color: #229bf1; -fx-background-radius:100px;";
    private final String Button_Free_Style = "-fx-background-color: #d49d49;-fx-background-radius:100px; ";

    public MenuButton(String text)  {
        setEffect(new DropShadow());
        setText(text);
        setFont(Font.loadFont(getClass().getResourceAsStream(Font_Path), 15));
        setPrefHeight(40);
        setPrefWidth(100);
        setStyle(Button_Free_Style);
        initialisedButtonListener();
    }

    public void setButtonPressed(){
        setStyle(button_Pressed_Style);
        setPrefHeight(36);
        setLayoutY(getLayoutY()+4);
    }

    public void setButtonFree(){
        setStyle(Button_Free_Style);
        setPrefHeight(39);
        setLayoutY(getLayoutY()-4);
    }

    public void initialisedButtonListener(){

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //setButtonPressed();
                setEffect(new InnerShadow());
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //setButtonFree();
                setEffect(new DropShadow());
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverSound();
                setEffect(new Glow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });

    }

    public void goUp(){
        TranslateTransition up = new TranslateTransition();

    }

    public void hoverSound(){
        AudioClip sound = new AudioClip(getClass().getResource("/audio/multimedia_rollover_037.mp3").toExternalForm());
                sound.setCycleCount(1);
                sound.play();
    }
}
