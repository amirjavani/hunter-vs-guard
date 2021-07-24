package Game;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NameGetter extends AnchorPane {
    private final double prefWeigh = 300;
    private final double prefHeight = 200;
    private final String STYLE = "-fx-background-radius: 15px;-fx-background-color: #d79e61; -fx-border-color: #9c7027;-fx-border-radius: 10px;-fx-border-width: 5px" ;
    AnchorPane AP ;
    TextField textField;
    MenuButton back ;
    MenuButton ok ;
     String Font_Path = "/font/SF Atarian System Bold Italic.ttf";

    public NameGetter (AnchorPane AP){
        this.AP = AP;
        setPrefHeight(prefHeight);
        setPrefWidth(prefWeigh);
        setLayoutY(100);
        setLayoutX(200);
        setEffect(new DropShadow());
        setStyle(STYLE);
        addLabel();
        addTextField();
        addButton();
    }

    public void addLabel(){
        Label label = new Label("PLEASE ENTER YOUR NAME :");
        label.setTextFill(Color.rgb(1,1,1));
        label.setLayoutX(15);
        label.setLayoutY(20);
        label.setFont(Font.loadFont(getClass().getResourceAsStream(Font_Path),20));
        getChildren().add(label);
    }

    public void addTextField(){
        textField = new TextField();
        textField.setFont(Font.loadFont(getClass().getResourceAsStream(Font_Path),18));
        textField.setLayoutX(15);
        textField.setLayoutY(60);
        textField.setPrefHeight(40);
        textField.setPrefWidth(200);
        textField.setEffect(new DropShadow());
        textField.setStyle("-fx-background-color: #f3b87c;-fx-background-radius: 20px;");
        textField.setOnKeyTyped(keyEvent -> {
            clickSound();
        });
        getChildren().add(textField);

    }

    public void addButton(){
        back = new MenuButton("BACK");
        getChildren().add(back);
        back.setPrefHeight(30);
        back.setPrefWidth(80);
        back.setLayoutX(15);
        back.setLayoutY(130);

        ok = new MenuButton("OK");
        getChildren().add(ok);
        ok.setPrefHeight(30);
        ok.setPrefWidth(50);
        ok.setLayoutX(120);
        ok.setLayoutY(130);
    }

    public void clickSound(){
        new AudioClip(
                getClass()
                        .getResource("/audio/Keyboard_Button_1-fesliyanstudios.com.mp3")
                        .toExternalForm())
                .play();
    }

}
