package v2;
import processing.core.PImage;

public class Player extends Image {
    int speed, health;

    public Player(Main main) {
        super(320, 320, 50, 50, main.loadImage("Images/Sprites/Player_Spaceship.png"));
        this.speed = 5;
    }

    public void move(int keyCode) {
        switch (keyCode) {
            case 37:
                this.x -= this.speed;
                break;
            case 38:
                this.y -= this.speed;
                break;
            case 39:
                this.x += this.speed;
                break;
            case 40:
                this.y += this.speed;
                break;
        }
    }
}
