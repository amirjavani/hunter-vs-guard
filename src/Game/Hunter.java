package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Hunter {

    private int damage ;
    private int health = 100;
    private AnchorPane scene;
    private double posX ;
    private double posY ;
    private TranslateTransition transition;
    Boolean isAlive ;
    private HunterType hunterType;

    Image hunterWalk ;
    Image hunterAttack ;
    Image hunterDeath ;
    Image hunterStop ;
    ImageView imageView = new ImageView();

    int row, column;
    Timeline moveTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.05), e->{
        row = block(imageView.getY());
        column = block(imageView.getX());
        if (CoreController.isExist[row][column] && imageView.getX() <= column*100+60){
            moveTimeLinePause();
        }
        hunterMove();
    }));

    Timeline hitTimeLine = new Timeline(new KeyFrame(Duration.seconds(2), e->{
        CoreController.plant[row][column].getDamage(damage);
        if (CoreController.isExist[row][column]==false) moveTimeLineResume();
    }));

    Timeline DeathTimeLine = new Timeline(new KeyFrame(Duration.seconds(1.1), e->{
        imageView.setImage(null);
    }));

    public void moveTimeLinePause(){
        hitTimeLine.setCycleCount(Timeline.INDEFINITE);
        moveTimeLine.pause();
        imageView.setImage(hunterAttack);
        imageView.setX(imageView.getX()-30);
        imageView.setFitWidth(78);
        hitTimeLine.play();
    }

    public void moveTimeLineResume(){
        hitTimeLine.stop();
        imageView.setImage(hunterWalk);
        imageView.setX(imageView.getX()+30);
        imageView.setFitWidth(53);
        moveTimeLine.play();
    }

    public Hunter(HunterType hunterType,AnchorPane scene, int rowOfMap, int columnOfMap) throws FileNotFoundException, FileNotFoundException {
        this.hunterType= hunterType;
        this.posY = randomRow(rowOfMap);

        this.posX = columnOfMap*100;
        this.isAlive =true;
        this.scene = scene;
        this.row = block(posY);
        switch (hunterType){
            case SKELETON -> {
                hunterWalk = new Image(getClass().getResource("/image/SkeletonWalk2.gif").toString());
                hunterAttack = new Image(getClass().getResource("/image/SkeletonAttack1.gif").toString());
                hunterDeath = new Image(getClass().getResource("/image/SkeletonDie.gif").toString());
                hunterStop =new Image(getClass().getResource("/image/Skeleton.png").toString());
                imageView.setImage(hunterWalk);
                imageView.setX(posX);
                imageView.setY(posY);
                scene.getChildren().add(imageView);
                imageView.setFitHeight(53);
                imageView.setFitWidth(53);
                this.damage = 35;
                hunterStart();
            }
            case ONE_EYE -> {
                hunterWalk = new Image(getClass().getResource("/image/OneEYEWalk.gif").toString());
                hunterAttack = new Image(getClass().getResource("/image/OneEyeAttack.gif").toString());
                hunterDeath = new Image(getClass().getResource("/image/OneEyeDeath.gif").toString());
                hunterStop =new Image(getClass().getResource("/image/OneEye.png").toString());
                imageView.setImage(hunterWalk);
                imageView.setX(posX);
                imageView.setY(posY);
                scene.getChildren().add(imageView);
                imageView.setFitHeight(53);
                imageView.setFitWidth(53);
                this.damage = 35;
                imageView.setImage(hunterWalk);
                hunterStart();
            }
            case MUSHROOM -> {
                hunterWalk = new Image(getClass().getResource("/image/MushroomWalk.gif").toString());
                hunterAttack = new Image(getClass().getResource("/image/MushroomAttack.gif").toString());
                hunterDeath = new Image(getClass().getResource("/image/MushroomDeath.gif").toString());
                hunterStop =new Image(getClass().getResource("/image/Mushroom.png").toString());
                imageView.setImage(hunterWalk);
                imageView.setX(posX);
                imageView.setY(posY);
                scene.getChildren().add(imageView);
                imageView.setFitHeight(60);
                imageView.setFitWidth(53);
                this.damage = 55;
                imageView.setImage(hunterWalk);
                hunterStart();
            }
        }

    }

    public int getRow() {
        return row;
    }



    public void hunterMove()  {
        posX = imageView.getX() - 1;
        imageView.setX(posX);
        imageView.setY(posY);
        if (posX <= -50) {
            hunterDie();
            CoreController.guardAnchorPane.decreaseHealth();
        }
    }

    public void hunterStart(){
        moveTimeLine.setCycleCount(Timeline.INDEFINITE);
        moveTimeLine.play();
    }


    public double randomRow(int rowOfMap){
        int min = 110 , max=(rowOfMap*100)-1;

        double randomNum = (int) ((Math.random() * (max - min)) + min);
        int a =100 ,b = 200;
        while (true) {
            if (a < randomNum && randomNum <= b) return a + 20;
            a += 100;
            b += 100;
            if (a >= rowOfMap*100) break;
        }
        return 0;
    }

    public int block(double num){
        int a = 0, b = 100,count = 0;
        while(true) {
            if (a < num && num <= b) {
                return count;
            }
            a += 100;
            b += 100;
            if (a == 1000) break;
            count++;
        }
        return 0;
    }

    public void hunterDamaged(int damage){
        health-= damage;
        if(health<=0) hunterDie();
    }

    public void hunterDie(){
        isAlive = false;
        imageView.setImage(hunterDeath);
        CoreController.hunterList[row].remove(0);
        moveTimeLine.stop();
        if(CoreController.isExist[row][column]) hitTimeLine.stop();
        DeathTimeLine.setCycleCount(1);
        DeathTimeLine.play();
    }

    public void stopper(){
        if(isAlive) {
            moveTimeLine.stop();
            hitTimeLine.stop();
            imageView.setImage(hunterStop);
        }
    }
    public void resume(){
        if (isAlive) moveTimeLineResume();
    }
}
