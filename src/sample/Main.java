package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private Room game;
    private GraphicsContext context;
    private int width = 200;
    private int height = 200;

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(width,height);
        context = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            Snake snake = game.getSnake();
            switch (e.getCode()) {
                case UP:
                    snake.setDirection(SnakeDirection.UP);
                    break;
                case DOWN:
                    snake.setDirection(SnakeDirection.DOWN);
                    break;
                case LEFT:
                    snake.setDirection(SnakeDirection.LEFT);
                    break;
                case RIGHT:
                    snake.setDirection(SnakeDirection.RIGHT);
                    break;
                case ENTER:
                    if (!game.getSnake().isAlive()) {
                        reset();
                        new Thread(game).start();
                    }
            }
        });

        root.getChildren().add(canvas);
        stage.setResizable(false);
        stage.setTitle("Snake");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(new Scene(root));
        stage.show();

        reset();
        new Thread(game).start();
    }


    public void reset(){
        game = new Room(width, height, new Snake(3, 3),context);
        game.getSnake().setDirection(SnakeDirection.DOWN);
        game.createMouse();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
