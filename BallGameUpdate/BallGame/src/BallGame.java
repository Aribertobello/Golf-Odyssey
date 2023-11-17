import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class BallGame extends Application {

    private static final double BALL_RADIUS = 10;
    private static final double GOAL_Y = 50;
    private static final double OBSTACLE_WIDTH = 400;

    private double ballSpeedX = 0;
    private double ballSpeedY = 0;
    private double dragStartX, dragStartY;
    private boolean dragging = false;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Circle ball = new Circle(BALL_RADIUS, Color.BLUE);
        ball.relocate(50, 400);

        Line goalLine = new Line(50, GOAL_Y, 550, GOAL_Y);
        goalLine.setStroke(Color.GREEN);

        Line obstacle = new Line(200, 300, 200 + OBSTACLE_WIDTH, 300);
        obstacle.setStroke(Color.RED);

        Line trajectoryLine = new Line();
        trajectoryLine.setStroke(Color.BLACK);

        root.getChildren().addAll(ball, goalLine, obstacle, trajectoryLine);
        

        Scene scene = new Scene(root, 600, 600);

        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
                dragging = true;
            }
        });

        scene.setOnMouseDragged(event -> {
            if (dragging) {
                double dragX = event.getSceneX() - dragStartX;
                double dragY = event.getSceneY() - dragStartY;

                trajectoryLine.setStartX(ball.getLayoutX() + BALL_RADIUS);
                trajectoryLine.setStartY(ball.getLayoutY() + BALL_RADIUS);
                trajectoryLine.setEndX(ball.getLayoutX() + BALL_RADIUS + dragX / 5);
                trajectoryLine.setEndY(ball.getLayoutY() + BALL_RADIUS + dragY / 5);
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double dragX = -event.getSceneX() + dragStartX;
                double dragY = -event.getSceneY() + dragStartY;

                ballSpeedX = dragX / 20;
                ballSpeedY = dragY / 20;

                dragging = false;
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.setLayoutX(ball.getLayoutX() + ballSpeedX);
                ball.setLayoutY(ball.getLayoutY() + ballSpeedY);

                if (ball.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                    ballSpeedY = -ballSpeedY;
                }

                if (ball.getLayoutY() <= GOAL_Y + BALL_RADIUS && ballSpeedY < 0) {
                    System.out.println("Goal!");
                    stop();
                }

                if (ball.getLayoutX() <= 0 || ball.getLayoutX() >= scene.getWidth() - BALL_RADIUS) {
                    ballSpeedX = -ballSpeedX;
                }

                if (ball.getLayoutY() <= 0 || ball.getLayoutY() >= scene.getHeight() - BALL_RADIUS) {
                    ballSpeedY = -ballSpeedY;
                }

                double speedFactor = 0.99;
                ballSpeedX *= speedFactor;
                ballSpeedY *= speedFactor;

                root.getChildren().setAll(ball, goalLine, obstacle, trajectoryLine);
            }
        };

        timer.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

