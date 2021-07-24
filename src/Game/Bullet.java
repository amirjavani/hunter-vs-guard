package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Bullet {
    int damage;
    Image bulletImage = new Image(getClass().getResource("/image/Arrow.png").toString());
    ImageView bulletImageView = new ImageView(bulletImage);
    AnchorPane AP;
    Hunter hunter ;
    double posX;
    double posY;
    int row;

    Timeline moveTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.02), e->{
        if (CoreController.hunterList[row].size()>=1){
            hunter = CoreController.hunterList[row].get(0);
            if(posX>hunter.imageView.getX()) bulletReached();
        }
        moveBullet();

    }));

    public void bulletStopper(){
        moveTimeLine.stop();
    }

    public void bulletStart(){
        moveTimeLine.setCycleCount(Timeline.INDEFINITE);
        moveTimeLine.play();
    }

    public Bullet(AnchorPane AP,int damage,double posX , double posY ,int row) throws FileNotFoundException {
        this.damage = damage;
        this.AP = AP;
        this.posX = posX ;
        this.posY = posY ;
        this.row = row;
        bulletImageView.setX(posX+3);
        bulletImageView.setY(posY-5);
        AP.getChildren().add(bulletImageView);
        bulletStart();
    }



    public void moveBullet(){
        posX = posX + 5;
        bulletImageView.setX(posX);
        if(posX>=600) {
            moveTimeLine.stop();
            bulletImageView.setImage(null);
        }
    }

    public void bulletReached(){
        moveTimeLine.stop();
        bulletImageView.setImage(null);
        hunter.hunterDamaged(damage);
    }
}
