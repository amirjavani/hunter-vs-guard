package Game;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class GuardButton extends Button {

    String Path ;
    private String STYLE = "-fx-background-color: #d79e61;-fx-background-image: url("+Path+");-fx-background-size: 100%";
    AnchorPane mainAnchorPane ;
    ImageView draggedGuard ;


    public GuardButton(String imagePath,AnchorPane mainAnchorPane) {
        this.mainAnchorPane = mainAnchorPane;
        this.Path = imagePath;
        STYLE = "-fx-background-color: #d79e61;-fx-background-image: url("+Path+");-fx-background-size:100%;-fx-background-repeat: no-repeat;";
        setStyle(STYLE);
        setPrefHeight(50);
        setPrefWidth(50);
        initialisedButtonListener();
    }

    public void initialisedButtonListener(){

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new Glow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                draggedGuard = new ImageView(new Image(getClass().getResource(Path).toString()));
                mainAnchorPane.getChildren().add(draggedGuard);
                draggedGuard.setFitWidth(60);
                draggedGuard.setFitHeight(60);
                draggedGuard.setX(mouseEvent.getSceneX()-20);
                draggedGuard.setY(mouseEvent.getSceneY()-20);
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                draggedGuard.setX(mouseEvent.getSceneX()-20);
                draggedGuard.setY(mouseEvent.getSceneY()-20);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                draggedGuard.setImage(null);
            }
        });
    }
}
