package v2;
import processing.core.PImage;

public class Player extends Image {
    int speed, health;

    public Player(Main main) {
        super(main.width/2 - 25, main.height - 70, 50, 50, main.loadImage("Images/Sprites/Player_Spaceship.png"));
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

        if (this.x < 0) {
            this.x = 0;
        } else if (this.x > 576 - this.w) {
            this.x = 576 - this.w;
        }
        if (this.y < 0) {
            this.y = 0;
        } else if (this.y > 804 - this.h) {
            this.y = 804 - this.h;
        }
    }

    public void reset(Main main) {
        this.x = main.width/2 - 25;
        this.y = main.height - 70;
    }
}
