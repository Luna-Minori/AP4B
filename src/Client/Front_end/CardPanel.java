package Client.Front_end;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.util.Objects;


public class CardPanel extends StackPane {

    private ImageView frontView;
    private boolean front;
    private final Integer value;
    private boolean animationPlayed;

    public CardPanel(Integer value) {
        this.value = value;
        if(value != null) {
            frontView = new ImageView(new Image((Objects.requireNonNull(getClass().getResource("/Client/Front_end/assets/Card_" + 1 + ".png")))
                    .toExternalForm()
            ));
        }
        else{
            frontView = new ImageView(new Image((Objects.requireNonNull(getClass().getResource("/Client/Front_end/assets/Card_Back.png")))
                    .toExternalForm()
            ));
        }
        frontView.setPreserveRatio(true);
        //backView.setPreserveRatio(true);

        getChildren().add(frontView);

        heightProperty().addListener((obs, oldH, newH) -> resize());
        prepareAnimation();
    }

    private void prepareAnimation() {
        frontView.setScaleX(0);
        frontView.setScaleY(0);
        animationPlayed = false;
    }

    private void resize() {

        double h = getHeight() * 0.95;

        if (h > 0) {
            frontView.setFitHeight(h);

            if (!animationPlayed && frontView.getFitHeight() > 5) {
                animationPlayed = true;

                Platform.runLater(() -> {
                    frontView.setScaleX(0.9);
                    frontView.setScaleY(0.9);
                    frontView.setRotate(90);

                    // Rotation
                    RotateTransition rotate = new RotateTransition(Duration.millis(400), frontView);
                    rotate.setFromAngle(30);
                    rotate.setToAngle(0);
                    rotate.setInterpolator(Interpolator.EASE_BOTH);

                    ScaleTransition scale = new ScaleTransition(Duration.millis(600), frontView);
                    scale.setFromX(0.9);
                    scale.setFromY(0.9);
                    scale.setToX(1.0);
                    scale.setToY(1.0);
                    scale.setInterpolator(Interpolator.EASE_OUT);

                    ParallelTransition pt = new ParallelTransition(rotate, scale);
                    pt.play();
                });
            }
        }
    }

    public boolean isRevealed(){
        return front;
    }

    public void flip() {
        front = !front;
        updateImage();
    }

    private void updateImage() {
        String path;
        if (value != null) {
            path = "/Client/Front_end/assets/Card_" + 1 + ".png";
        } else {
            path = "/Client/Front_end/assets/Card_Back.png";
        }
        Platform.runLater(() -> {
            try {
                Image img = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
                System.out.println("Mise à jour visuelle : " + path);
                this.frontView.setImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void update(int value, boolean front) {
        frontView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Front_end/Card_" + value + ".png")).toExternalForm()));
        this.front = front;
    }
}
