package Game;


import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LevelButton extends Button {

    private String STYLE = "-fx-background-radius: 15px; -fx-background-color: #c2854a; -fx-border-radius: 10px;-fx-border-color: #845613;";
    private String HOVER_STYLE = "-fx-background-radius: 15px; -fx-background-color: #c2854a; -fx-border-radius: 10px;-fx-border-color: #845613; -fx-border-width: 3px";
    private final String Font_Path= "/font/VIDEOPHREAK.ttf";
    private double posX ;
    private double posY ;
    private LevelButton thisButton;

    public LevelButton getThisButton() {
        return thisButton;
    }

    public void setThisButton(LevelButton thisButton) {
        this.thisButton = thisButton;
    }

    public LevelButton(String text , AnchorPane AP){
        setText(text);
        setPrefHeight(50);
        setPrefWidth(50);
        setStyle(STYLE);
        setFont(Font.loadFont(getClass().getResourceAsStream(Font_Path), 15));
        setEffect(new DropShadow());
        setDisable(true);
        initialisedButtonListener();
    }

    public void initialisedButtonListener(){
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(HOVER_STYLE);
                //setEffect(new Glow());
                hoverTransitionUP();
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(STYLE);
                setEffect(null);
                hoverTransitionDown();
            }
        });
    }

    public void hoverTransitionUP(){
        TranslateTransition TTup = new TranslateTransition();
        TTup.setToY(-14);
        TTup.setDuration(Duration.seconds(0.4));
        TTup.setNode(thisButton);
        TTup.play();
    }

    public void hoverTransitionDown(){
        TranslateTransition TTDown = new TranslateTransition();
        TTDown.setToY(0);
        TTDown.setDuration(Duration.seconds(0.4));
        TTDown.setNode(thisButton);
        TTDown.play();
    }
}
