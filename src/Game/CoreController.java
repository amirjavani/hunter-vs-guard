package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CoreController implements Initializable {
    static ArrayList<Hunter>[] hunterList = new ArrayList[10];
    static ArrayList<Hunter> AllHunterList = new ArrayList();
    static ArrayList<Guard> AllGuardList = new ArrayList();
    public AnchorPane main;
    private String BACKGROUND_TOP;
    private String BACKGROUND_MAIN;
    private String TREE_PATH_1;
    private String TEXTURE_PATH_1;
    private String TEXTURE_PATH_2;
    private String TEXTURE_ROCK_PATH;
    private String name;
    static GuardAnchorPane guardAnchorPane;
    static Boolean isExist[][] = new Boolean[10][10];
    static Guard plant[][] = new Guard[10][10];
    int rowOfMap;
    int columnOfMap;
    int keyFrameCount =1 ;
    Level lvl ;
    int nextLevel;


    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e->{
        keyFrameCount++;
        try {
            startAHunter(HunterType.SKELETON);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        if(keyFrameCount>=5) {
            try {
                startAHunter(HunterType.ONE_EYE);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        if(keyFrameCount>=11) {
            try {
                startAHunter(HunterType.MUSHROOM);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }));

    public void startAHunter(HunterType HT) throws FileNotFoundException {
        Hunter hunter = new Hunter(HT,main,rowOfMap,columnOfMap);
        hunterList[hunter.getRow()].add(hunter);
        AllHunterList.add(hunter);
    }

    Timeline healthChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), e->{
        if(guardAnchorPane.count<1){
            loseAnchorPane();
        }
    }));

    Timeline winnerChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), e->{
        int Deads = 0 ;
        for (Hunter i : AllHunterList) {
            if(!i.isAlive)Deads++;
        }
        if(Deads==AllHunterList.size()) {
            winnerAnchorPane();
        }
    }));

    Timeline crystalTimeLine = new Timeline(new KeyFrame(Duration.seconds(5), e->{
        Crystal crystal1 = new Crystal(main,20);
        crystal1.mapCrystal(rowOfMap,columnOfMap);
    }));

    public void winnerAnchorPane(){
        WinnerSound();
        winnerChecker.stop();
        crystalTimeLine.stop();
        stopper();
        PauseAnchorPane losePane = new PauseAnchorPane(main);
        main.getChildren().add(losePane);
        losePane.getChildren().remove(losePane.resume);
        losePane.winnerWizardImage(20,20);
        losePane.LoserSkeletonImage(100,67);
        guardAnchorPane.setDisable(true);
        FileManager fileManager = new FileManager();
        fileManager.change(name, nextLevel);
        losePane.playAgain.setOnMouseClicked(mouseEvent -> {
            Again();
        });
        losePane.backToMenu.setOnMouseClicked(mouseEvent -> {
            backToMenu();
        });
    }

    public void WinnerSound(){
        AudioClip sound = new AudioClip(getClass().getResource("/audio/WinnerSound.wav").toExternalForm());
        sound.setCycleCount(1);
        sound.play();
    }

    public void loseAnchorPane(){
        LoserSound();
        healthChecker.stop();
        crystalTimeLine.stop();
        stopper();
        PauseAnchorPane losePane = new PauseAnchorPane(main);
        main.getChildren().add(losePane);
        losePane.getChildren().remove(losePane.resume);
        losePane.label("You Lost :(");
        losePane.LoserArcherImage(-10,15);
        losePane.WinnerSkeletonImage(100,45);
        guardAnchorPane.setDisable(true);
        losePane.playAgain.setOnMouseClicked(mouseEvent -> {
            Again();
        });
        losePane.backToMenu.setOnMouseClicked(mouseEvent -> {
            backToMenu();
        });
    }
    public void LoserSound(){
        AudioClip sound = new AudioClip(getClass().getResource("/audio/LoseSound.wav").toExternalForm());
        sound.setCycleCount(1);
        sound.play();
    }


    public void coreBackground(int row,int column) {
        main.setStyle("-fx-background-image: url('" + BACKGROUND_MAIN + "'),url('" + BACKGROUND_TOP + "'); -fx-background-size: 100px;" +
                "-fx-background-repeat: repeat,repeat-x");
        main.setPrefHeight(row*100);
        main.setPrefWidth(column*100);

        for(int i = 0 ; i<2; i++){
            ImageView texture = new ImageView(new Image(getClass().getResource(TEXTURE_ROCK_PATH).toString()));
            texture.setY((int) (Math.random() * (60-0+1)) + 0);
            texture.setX((int) (Math.random() * ((column*100-20)-350+1)) + 350);
            texture.prefHeight(20);
            texture.prefWidth(10);
            main.getChildren().add(texture);
        }
        for(int i = 0 ; i<3; i++){
            ImageView texture = new ImageView(new Image(getClass().getResource(TREE_PATH_1).toString()));
            texture.setY((int) (Math.random() * (60+1)));
            texture.setX((int) (Math.random() * ((column*100-20)-350+1)) + 350);
            main.getChildren().add(texture);
        }
        for(int i = 0 ; i<5; i++){
            ImageView texture = new ImageView(new Image(getClass().getResource(TEXTURE_PATH_1).toString()));
            texture.setY((int) (Math.random() * ((row*100-20)-100+1)) + 100);
            texture.setX((int) (Math.random() * ((column*100-20)+1)));
            texture.prefHeight(100);
            texture.prefWidth(70);

            main.getChildren().add(texture);
        }
        for(int i = 0 ; i<5; i++){
            ImageView texture = new ImageView(new Image(getClass().getResource(TEXTURE_PATH_2).toString()));
            texture.setY((int) (Math.random() * (400-100+1)) + 100);
            texture.setX((int) (Math.random() * (600+1)));
            texture.prefHeight(100);
            texture.prefWidth(70);

            main.getChildren().add(texture);
        }

        for (int x = 0; x < hunterList.length; x++) {
            hunterList[x] = new ArrayList<>();
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                isExist[i][j] = false;
                if(i==0)isExist[i][j] = true;
            }
        }

        Image cursor = new Image(getClass().getResource("/image/cursorHand_grey.png").toString());
        main.setCursor(new ImageCursor(cursor, 15, 15));
        crystalTimeLine.setCycleCount(-1);
        crystalTimeLine.play();

    }

    public void guardChose(){
            healthChecker.setCycleCount(Timeline.INDEFINITE);
            healthChecker.play();
            guardAnchorPane = new GuardAnchorPane(main);
            guardAnchorPane.setName(name);
            main.getChildren().add(guardAnchorPane);
            guardAnchorPane.Archer.setOnMouseReleased(mouseEvent -> {
                int row = blockX(mouseEvent.getSceneY());
                int column = blockY(mouseEvent.getSceneX());
                if (isExist[row][column] == false) {
                    isExist[row][column] = true;
                    try {
                        plant[row][column] = new Guard(main, GuardType.ARCHER,mouseEvent.getSceneX(),mouseEvent.getSceneY(),column,row);
                        AllGuardList.add(plant[row][column]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else System.out.println("you can not");
                guardAnchorPane.Archer.draggedGuard.setImage(null);
            });
            guardAnchorPane.Wizard.setOnMouseReleased(mouseEvent -> {
                int row = blockY(mouseEvent.getSceneY());
                int column = blockX(mouseEvent.getSceneX());
                if (isExist[row][column] == false) {
                    isExist[row][column] = true;
                    try {
                        plant[row][column] = new Guard(main, GuardType.WIZARD,mouseEvent.getSceneX(),mouseEvent.getSceneY(),column,row);
                        AllGuardList.add(plant[row][column]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else System.out.println("you can not");
                guardAnchorPane.Wizard.draggedGuard.setImage(null);
            });


            guardAnchorPane.pause.setOnMouseClicked(mouseEvent -> pause());
        }

    public void zombieTimeLineStart(int cycleCount){
            timeline.setCycleCount(cycleCount);
            timeline.play();
            timeline.setOnFinished(actionEvent -> {
                winnerChecker.setCycleCount(Timeline.INDEFINITE);
                winnerChecker.play();
            });

        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvl = LevelMenu.lvl;
        switch (lvl){
            case FALL -> {
                nextLevel = 1;
                this.rowOfMap = 3+1;
                this.columnOfMap = 7;
                BACKGROUND_MAIN = "/image/BackGroundImage/FallBackground.png";
                BACKGROUND_TOP = "/image/BackGroundImage/FallBackground2.png";
                TREE_PATH_1 = "/image/BackGroundImage/FallTree.png";
                TEXTURE_ROCK_PATH = "/image/BackGroundImage/SpringRock.png";
                TEXTURE_PATH_1 = "/image/BackGroundImage/FallTexture1.png";
                TEXTURE_PATH_2 = "/image/BackGroundImage/SpringTexture3.png";
                coreBackground(rowOfMap,columnOfMap);
                guardChose();
                zombieTimeLineStart(8);
            }
            case SPRING -> {
                nextLevel = 2;
                this.rowOfMap = 4+1;
                this.columnOfMap = 8;
                BACKGROUND_MAIN = "/image/BackGroundImage/SpringMainBackground.png";
                BACKGROUND_TOP = "/image/BackGroundImage/SpringUIBackground.png";
                TREE_PATH_1 = "/image/BackGroundImage/SpringTree.png";
                TEXTURE_ROCK_PATH = "/image/BackGroundImage/SpringRock.png";
                TEXTURE_PATH_1 = "/image/BackGroundImage/SpringTexture2.png";
                TEXTURE_PATH_2 = "/image/BackGroundImage/SpringTexture3.png";
                coreBackground(rowOfMap,columnOfMap);
                guardChose();
                zombieTimeLineStart(12);
            }
            case WINTER -> {
                nextLevel = 2;
                this.rowOfMap = 5+1;
                this.columnOfMap = 8;
                BACKGROUND_MAIN = "/image/BackGroundImage/WinterMainBackground.png";
                BACKGROUND_TOP = "/image/BackGroundImage/WinterUIBackground.png";
                TREE_PATH_1 = "/image/BackGroundImage/WinterTree.png";
                TEXTURE_ROCK_PATH = "/image/BackGroundImage/WinterRock.png";
                TEXTURE_PATH_1 = "/image/BackGroundImage/WinterTexture1.png";
                TEXTURE_PATH_2 = "/image/BackGroundImage/SpringTexture3.png";
                coreBackground(rowOfMap,columnOfMap);
                guardChose();
                zombieTimeLineStart(15);
            }
        }
    }



    public void pause(){
        stopper();

        PauseAnchorPane pauseAnchorPane = new PauseAnchorPane(main);
        main.getChildren().add(pauseAnchorPane);
        guardAnchorPane.setDisable(true);

        pauseAnchorPane.resume.setOnMouseClicked(mouseEvent -> {
            guardAnchorPane.setDisable(false);
            main.getChildren().remove(pauseAnchorPane);
            resumes();
        });

        pauseAnchorPane.playAgain.setOnMouseClicked(mouseEvent -> {
            Again();
        });

        pauseAnchorPane.backToMenu.setOnMouseClicked(mouseEvent -> {
            backToMenu();
        });
    }

    public void backToMenu(){
        Stage primaryStage = (Stage) main.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game/Level_Menu.fxml"));
        Parent root = null;
        try { root = loader.load(); } catch (IOException e) { e.printStackTrace(); }
        LevelMenu levelMenuCon = loader.getController();
        levelMenuCon.setName(name);
        primaryStage.setTitle("Guard Vs Hunter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void Again(){
        Stage primaryStage = (Stage) main.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game/Core.fxml"));
        Parent root = null;
        try { root = loader.load(); } catch (IOException e) { e.printStackTrace(); }
        CoreController levelMenuCon = loader.getController();
        levelMenuCon.setName(name);
        primaryStage.setTitle("Guard Vs Hunter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }



    public void stopper(){
        winnerChecker.stop();
        crystalTimeLine.stop();
        for (Guard i : AllGuardList) {
            i.guardStopper();
        }
        for (Hunter i : AllHunterList) {
            i.stopper();
        }
        timeline.stop();
    }
    public void resumes(){
        winnerChecker.play();
        crystalTimeLine.play();
        for (Guard i : AllGuardList) {
            i.guardResume();
        }
        for (Hunter i : AllHunterList) {
            i.resume();
        }
        timeline.play();
    }


    public int blockX(double num){
        int a = 0, b = 100,count = 0;
        while(true) {
            if (a < num && num <= b) {
                return count;
            }
            a += 100;
            b += 100;
            if (b == 1000) break;
            count++;
        }
        return 0;
    }


    public int blockY(double num){
        int a = 0, b = 100,count = 0;
        while(true) {
            if (a < num && num <= b) {
                return count;
            }
            a += 100;
            b += 100;
            if (b == 1000) break;
            count++;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
