package Game;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import Game.CoreController;

public class Crystal{

    ImageView crystal = new ImageView( new Image(getClass().getResource("/image/Crystal.png").toString()));
    int count;
    public Crystal(AnchorPane anchorPane,int count){
        this.count = count;
        anchorPane.getChildren().add(crystal);
    }

    public void mapCrystal(int rowOfMap,int columnOfMap){
        crystal.setFitHeight(40);
        crystal.setFitWidth(40);
        crystal.setTranslateX((int) ((Math.random() * ((columnOfMap*100) - 0)) + 0));
        crystal.setTranslateY((int) ((Math.random() * ((rowOfMap*100) - 120)) + 120));
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(crystal);
        transition.setDuration(Duration.seconds(2));
        transition.setToY(crystal.getTranslateY()+10);
        transition.play();
        crystal.setOnMouseEntered(mouseEvent -> crystal.setEffect(new Glow()));
        crystal.setOnMouseExited(mouseEvent -> crystal.setEffect(null));
        crystal.setOnMouseClicked(mouseEvent ->{

            moveTransition();
        });
    }
    public void WizardCrystal(double posX,double PosY){
        crystal.setFitHeight(30);
        crystal.setFitWidth(30);
        crystal.setTranslateX(posX);
        crystal.setTranslateY(PosY);
        moveTransition();
    }

    public void moveTransition(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(crystal);
        transition.setToX(CoreController.guardAnchorPane.crystalImage.getTranslateX());
        transition.setToY(CoreController.guardAnchorPane.crystalImage.getTranslateY());
        transition.play();
        transition.setOnFinished(actionEvent -> {
            CoreController.guardAnchorPane.addCrystal(count);
            CoreController.guardAnchorPane.checkPrice();
            crystal.setImage(null);
        });
    }
}
