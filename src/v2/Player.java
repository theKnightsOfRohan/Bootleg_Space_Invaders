package v2;
import processing.core.PImage;

public class Player extends Image {
    int speed, health;

    public Player(Main main) {
        super(320, 320, 64, 64, main.loadImage("Images/Sprites/Player_Spaceship.png"));
        this.speed = 5;
    }

    public void move(int keyCode) {
        switch (keyCode) {
            case 37:
                x -= speed;
                break;
            case 38:
                y -= speed;
                break;
            case 39:
                x += speed;
                break;
            case 40:
                y += speed;
                break;
        }
    }
}
