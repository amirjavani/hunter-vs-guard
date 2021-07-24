package Game;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class GuardAnchorPane extends AnchorPane {

    Image HImage = new Image(getClass().getResource("/image/heart.png").toString());
    ImageView[] H = {new ImageView(HImage),new ImageView(HImage),new ImageView(HImage)};
    int crystal = 30;
    MenuButton pause;
    String name ;
    AnchorPane main;
    GuardButton Archer;
    GuardButton Wizard;
    Label crystallbl;
    ImageView crystalImage = new ImageView(new Image(getClass().getResource("/image/Crystal.png").toString()));

    private String STYLE = "-fx-background-color: #d79e61; -fx-background-radius: 25px;-fx-background-clip: padding-box; " +
            " -fx-border-color:gray;-fx-border-radius: 20px;-fx-border-width: 5px";

    public GuardAnchorPane(AnchorPane main){
        this.main =main;
        crystalLabel();
        Crystal();
        Pause();
        setLayoutX(5);
        setLayoutY(5);
        setPrefHeight(80);
        setPrefWidth(350);
        setStyle(STYLE);
        setEffect(new DropShadow());
        ArcherButton();
        WizardButton();
        checkPrice();
        health();
    }
    int count =3;
    public void health(){
        for (int i = 0 ; i<count;i++){
            getChildren().add(H[i]);
            H[i].setFitWidth(20);
            H[i].setFitHeight(20);
            H[i].setX(180+(i*25));
            H[i].setY(10);
        }
    }

    public void decreaseHealth(){
        count--;
        if(count>-1)getChildren().remove(H[count]);
    }


    public void ArcherButton(){
        Archer =new GuardButton("/image/Archer.png",main);
        Archer.setLayoutX(80);
        Archer.setLayoutY(23);
        getChildren().add(Archer);
    }

    public void WizardButton(){
        Wizard =new GuardButton("/image/Wizard.png",main);
        Wizard.setLayoutX(20);
        Wizard.setLayoutY(23);
        getChildren().add(Wizard);
    }

    public void Crystal(){

        getChildren().add(crystalImage);
        crystalImage.setFitWidth(30);
        crystalImage.setFitHeight(30);
        crystalImage.setTranslateY(10);
        crystalImage.setTranslateX(270);
    }

    public void crystalLabel(){
        crystallbl = new Label("        "+crystal);
        crystallbl.setFont((Font.loadFont(getClass().getResourceAsStream("/font/SF Atarian System Bold Italic.ttf"),20)));
        crystallbl.setStyle("-fx-background-color: #d49d49; -fx-background-radius: 15px;");
        crystallbl.setEffect(new DropShadow());
        crystallbl.setLayoutX(266);
        crystallbl.setLayoutY(8);
        crystallbl.setPrefHeight(35);
        crystallbl.setPrefWidth(70);
        getChildren().add(crystallbl);
    }

    public void addCrystal(int count){
        crystal += count;
        crystallbl.setText("        "+crystal);
        checkPrice();
    }


    public void checkPrice(){
        if(crystal<10) Wizard.setDisable(true);
        else{
            Wizard.setDisable(false);
        }
        if(crystal<50) Archer.setDisable(true);
        else {
            Archer.setDisable(false);
        }

    }

    public void removeCrystal(int price){
        crystal -= price;
        crystallbl.setText("        "+crystal);
        checkPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Label nameLabel = new Label(name);
        getChildren().add(nameLabel);
        nameLabel.setLayoutX(5);
        nameLabel.setLayoutY(5);
        nameLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/SF Atarian System Bold Italic.ttf"),20));
    }

    public void Pause(){
        pause = new MenuButton("pause");
        pause.setLayoutX(266);
        pause.setLayoutY(50);
        pause.setPrefHeight(35);
        pause.setPrefWidth(70);
        getChildren().add(pause);
    }
}
