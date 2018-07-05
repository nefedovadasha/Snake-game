package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Основной класс программы.
 */
public class Room implements Runnable{
    public final static int SIZE = 10;
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    private GraphicsContext context;
    public static Room game;

    public Room(int width, int height, Snake snake, GraphicsContext context) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
        this.context = context;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    public void run() {
        while (snake.isAlive()) {
            snake.move();   //двигаем змею
            painter();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * Выводим на экран текущее состояние игры
     */
    public void painter() {
        context.clearRect(0, 0, width, height);
        context.setFill(Color.BLUE);
        context.fillRect(mouse.getX()*SIZE,mouse.getY()*SIZE,SIZE,SIZE);
        context.setFill(Color.DARKBLUE);
        snake.getSections().forEach(section->{
            context.fillRect(section.getX()*SIZE,section.getY()*SIZE,SIZE,SIZE);
        });
    }

    /**
     * Метод вызывается, когда мышь съели
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Создает новую мышь
     */
    public void createMouse() {
        int x = (int) (Math.random() * width)/SIZE;
        int y = (int) (Math.random() * height)/SIZE;

        mouse = new Mouse(x, y);
    }


    private int initialDelay = 520;
    private int delayStep = 20;

    /**
     * Программа делает паузу, длинна которой зависит от длинны змеи.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
