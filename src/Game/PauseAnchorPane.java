package Game;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class PauseAnchorPane extends AnchorPane {

    AnchorPane AP;
    private String STYLE = "-fx-background-color: #ba926e;-fx-background-radius: 15px;-fx-border-width: 4px; -fx-border-color: gray;-fx-border-radius: 10px";
    MenuButton resume;
    MenuButton playAgain;
    MenuButton backToMenu;

    public PauseAnchorPane(AnchorPane Ap){
        this.AP = Ap;
        setLayoutY((AP.getPrefHeight()/2)-130);
        setLayoutX((AP.getPrefWidth()/2)-90);
        setStyle(STYLE);
        setPrefWidth(180);
        setPrefHeight(260);
        buttons();
    }

    public void label(String text){
        Label youLost = new Label(text);
        youLost.setFont(Font.loadFont(getClass().getResourceAsStream("/font/SF Atarian System Bold Italic.ttf"), 25));
        youLost.setLayoutX(20);
        youLost.setLayoutY(20);
        getChildren().add(youLost);
    }


    public void LoserArcherImage(double x ,double y){
        ImageView Archer = new ImageView(new Image(getClass().getResource("/image/LoserArcher.png").toString()));
        getChildren().add(Archer);
        Archer.setEffect(new DropShadow());
        Archer.setFitWidth(120);
        Archer.setFitHeight(120);
        Archer.setX(x);
        Archer.setY(y);
    }

    public void winnerWizardImage(double x ,double y){
        ImageView Wizard = new ImageView(new Image(getClass().getResource("/image/WInnerWizard.png").toString()));
        Wizard.setEffect(new DropShadow());
        getChildren().add(Wizard);
        Wizard.setFitWidth(80);
        Wizard.setFitHeight(80);
        Wizard.setX(x);
        Wizard.setY(y);
    }

    public void LoserSkeletonImage(double x ,double y){
        ImageView Skeleton = new ImageView(new Image(getClass().getResource("/image/LoserSkeleton.png").toString()));
        getChildren().add(Skeleton);
        Skeleton.setEffect(new DropShadow());
        Skeleton.setX(x);
        Skeleton.setY(y);
    }
    public void WinnerSkeletonImage(double x ,double y){
        ImageView Skeleton = new ImageView(new Image(getClass().getResource("/image/Skeleton.png").toString()));
        getChildren().add(Skeleton);
        Skeleton.setEffect(new DropShadow());
        Skeleton.setFitWidth(53);
        Skeleton.setFitHeight(53);
        Skeleton.setX(x);
        Skeleton.setY(y);
    }

    public void buttons(){
        resume = new MenuButton("resume");
        resume.setLayoutX(40);
        resume.setLayoutY(28);

        playAgain = new MenuButton("Again");
        playAgain.setLayoutX(resume.getLayoutX());
        playAgain.setLayoutY(resume.getLayoutY()+70);

        backToMenu = new MenuButton("Quit");
        backToMenu.setLayoutX(playAgain.getLayoutX());
        backToMenu.setLayoutY(playAgain.getLayoutY()+70);


        getChildren().addAll(resume,playAgain,backToMenu);

    }

}
