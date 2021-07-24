package Game;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;

public class InfoLabel extends Label {

    private final String Font_Path= "/font/SF Atarian System Bold Italic.ttf";
    private final String LABEL_STYLE = "-fx-background-color: #d79e61;-fx-border-radius: 10px;-fx-background-radius: 15px; -fx-border-color: #9c7027;-fx-border-width: 5px; -fx-padding: 10px;";

    public InfoLabel(String text,int height,int width){
        setText(text);
        setStyle(LABEL_STYLE);
        setFont(Font.loadFont(getClass().getResourceAsStream(Font_Path), 18));
        setEffect(new DropShadow());
        setLayoutY(140);
        setPrefHeight(height);
        setPrefWidth(width);
    }
}
