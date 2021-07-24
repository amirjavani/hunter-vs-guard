package Game;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LevelMenu implements Initializable {
    public AnchorPane AP;
    private int level;
    private String name;
    ArrayList<LevelButton> levelList = new ArrayList<>(4);
    static Level lvl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkName(MainMenuCon.name);
        LevelButton();
        BackButton();
    }

    public void LevelButton(){
        LevelButton levelOne = new LevelButton("1",AP);
        levelList.add(levelOne);
        levelOne.setThisButton(levelOne);
        levelOne.setLayoutX(20);
        levelOne.setLayoutY(400);
        AP.getChildren().add(levelOne);
        levelOne.setOnMouseClicked(mouseEvent -> {
            lvl = Level.FALL;
            try {
                nextPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        LevelButton levelTwo = new LevelButton("2",AP);
        levelList.add(levelTwo);
        levelTwo.setThisButton(levelTwo);
        levelTwo.setLayoutX(120);
        levelTwo.setLayoutY(400);
        AP.getChildren().add(levelTwo);
        levelTwo.setOnMouseClicked(mouseEvent -> {
            lvl = Level.SPRING;
            try {
                nextPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        LevelButton levelThree = new LevelButton("3",AP);
        levelList.add(levelThree);
        levelThree.setThisButton(levelThree);
        levelThree.setLayoutX(220);
        levelThree.setLayoutY(400);
        AP.getChildren().add(levelThree);
        levelThree.setOnMouseClicked(mouseEvent -> {
            lvl = Level.WINTER;
            try {
                nextPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        for (int i=0; i<=level;i++){
            levelList.get(i).setDisable(false);
        }
    }

    public void nextPage() throws IOException {
        Stage primaryStage = (Stage) AP.getScene().getWindow();
        primaryStage.close();
        primaryStage =new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game/Core.fxml"));
        Parent root = loader.load();
        CoreController Core = loader.getController();
        Core.setName(name);
        primaryStage.setTitle("Guard Vs Hunter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void BackButton(){
        MenuButton back = new MenuButton("BACK");
        back.setLayoutX(10);
        back.setLayoutY(480);
        back.setPrefWidth(80);
        back.setPrefHeight(20);
        AP.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    backPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void backPage() throws IOException {
        Stage primaryStage = (Stage) AP.getScene().getWindow();
        primaryStage.close();
        primaryStage =new Stage();
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Game/Main_Menu.fxml")));
        primaryStage.setTitle("Guard Vs Hunter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void checkName(String name){
        FileManager fileManager = new FileManager();
        if (fileManager.checkRegister(name ,"data.txt")) {
            level = fileManager.getLevel();
        }
        else {
            fileManager.writePatient(name,"data.txt");
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
