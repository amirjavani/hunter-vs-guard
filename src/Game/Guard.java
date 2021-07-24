package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Guard {

    TranslateTransition tt = new TranslateTransition();

    Boolean isShooting= false;
    private int health;
    Image image;
    Image attackImage;
    Image normalPauseImage;
    Image deathImage;
    ImageView imageView = new ImageView(image);
    AnchorPane AP;
    double posX;
    double posY;
    double bullPosX;
    double bullPosY;
    int column;
    int row;
    boolean threadStop =false;
    boolean isAlive ;
    GuardType guardType;
    Timeline DeathTimeLine = new Timeline(new KeyFrame(Duration.seconds(1.4), e->{
        imageView.setImage(null);
    }));

    Timeline crystalTimeLine = new Timeline(new KeyFrame(Duration.seconds(5.6), e->{
        Crystal crystal = new Crystal(AP,20);
        crystal.WizardCrystal(posX,posY);
    }));

    public Guard(AnchorPane AP , GuardType guardType , double posX , double posY , int column , int row) throws FileNotFoundException {
        this.guardType = guardType;
        this.posX = posX;
        this.posY = posY;
        this.column = column;
        this.row = row;
        this.AP = AP;
        this.isAlive =true;
        transition();
        switch (guardType) {
            case ARCHER -> {
                health = 100;
                image = new Image(getClass().getResource("/image/ArcherIdle.gif").toString());
                attackImage = new Image(Objects.requireNonNull(getClass().getResource("/image/ArcherAttack.gif")).toString());
                normalPauseImage = new Image(getClass().getResource("/image/Archer.png").toString());
                deathImage = new Image(getClass().getResource("/image/ArcherDeath.gif").toString());
                imageView.setImage(image);
                this.bullPosX = locationX(posX) + 25;
                this.bullPosY = locationY(posY) + 25;
                imageView.setX(locationX(posX));
                imageView.setY(locationY(posY));
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                AP.getChildren().add(imageView);
                CoreController.guardAnchorPane.removeCrystal(50);
                rowCheck();
            }
            case WIZARD -> {
                health = 80;
                image = new Image(getClass().getResource("/image/WizardIdle.gif").toString());
                deathImage = new Image(getClass().getResource("/image/WizardDeath.gif").toString());
                CoreController.guardAnchorPane.removeCrystal(10);
                imageView.setImage(image);
                imageView.setX(locationX(posX) - 5);
                imageView.setY(locationY(posY) - 20);
                imageView.setFitHeight(80);
                imageView.setFitWidth(80);
                AP.getChildren().add(imageView);
                crystalPlay();
            }
        }

    }

    public void crystalPlay(){
        crystalTimeLine.setCycleCount(-1);
        crystalTimeLine.play();
    }

    public void crystalStop(){
        crystalTimeLine.stop();
    }

    public void transition(){
        tt.setNode(imageView);
        tt.setToY(-3);
        tt.setToX(-3);
        tt.setDuration(Duration.seconds(0.2));
        tt.setAutoReverse(true);
        tt.setCycleCount(2);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void getDamage(int damage){
        tt.play();
        setHealth(getHealth()-damage);
        if (getHealth()<=0) plantDie();
    }

    public void plantDie(){
        isAlive = false ;

        CoreController.isExist[row][column] = false;
        shooter.stop();
        this.threadStop =true;
        imageView.setImage(deathImage);
        DeathTimeLine.setCycleCount(1);
        DeathTimeLine.play();
    }

    private double locationX(double num){
        int a = 0, b = 100;
        while(true) {
            if (a < num && num <= b) {
                return (a + b) / 2 - 25;
            }
            a += 100;
            b += 100;
            if (b == 1000) break;
        }
        return 0;
    }

    private double locationY(double num){
        int a = 100, b = 200;
        while(true) {
            if (a < num && num <= b) {
                return (a + b) / 2 - 25;
            }
            a += 100;
            b += 100;
            if (b == 1000) break;
        }
        return 0;
    }
    public void rowCheck(){

        new Thread(() -> {
            int i=0;
            while (true){
                if (CoreController.hunterList[row].size()>=1) shooterStart();
                else shooterStop();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(threadStop) break;
            }
        }).start();
    }

    public void shooterStop(){
        isShooting = false;
        imageView.setImage(image);
        shooter.stop();
    }

    public void shooterStart(){
        imageView.setImage(attackImage);
        shooter.setCycleCount(Timeline.INDEFINITE);
        shooter.play();
    }

    Bullet bullet;
    Timeline shooter = new Timeline(new KeyFrame(Duration.seconds(1.5), e->{
        isShooting = true;
        try {
            bullet = new Bullet(AP,10,bullPosX,bullPosY,row);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }));


    public void guardStopper(){
        if(isAlive && guardType.equals(GuardType.ARCHER)) {
            threadStop = true;
            shooterStop();
            imageView.setImage(new Image(getClass().getResource("/image/Archer.png").toString()));
            if (isShooting) bullet.bulletStopper();
        }
        if (isAlive && guardType.equals(GuardType.WIZARD)){
            imageView.setImage(new Image(getClass().getResource("/image/Wizard.png").toString()));
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            imageView.setTranslateX(+8);
            imageView.setTranslateY(+25);
            crystalStop();
        }
    }

    public void guardResume(){
        if(isAlive && guardType.equals(GuardType.ARCHER)) {
            shooterStart();
            threadStop = false;
            rowCheck();
            if (isShooting) bullet.bulletStart();
        }
        if (isAlive && guardType.equals(GuardType.WIZARD)){
            imageView.setImage(new Image(getClass().getResource("/image/WizardIdle.gif").toString()));
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setTranslateX(0);
            imageView.setTranslateY(0);
            crystalPlay();
        }
    }
}
