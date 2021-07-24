package Game;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class JustTest implements Initializable {
    public AnchorPane Ap;
    private Circle c1;
    private Circle c2;

    private void startAnimation() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(c1);
        transition.setToX(c2.getTranslateX());
        transition.setToY(c2.getTranslateY());
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set only the radius
        c1 = new Circle(5);
        c1.setFill(Color.BLUE);

        // Let's translate the c1 to the location we want
        c1.setTranslateX(50);
        c1.setTranslateY(60);

        // The same for circle2
        c2 = new Circle(5);
        c2.setFill(Color.RED);

        c2.setTranslateX(120);
        c2.setTranslateY(2);

        Ap.getChildren().addAll(c1, c2);

        Ap.setOnMouseClicked(e -> {
            startAnimation();
        });
    }
}
