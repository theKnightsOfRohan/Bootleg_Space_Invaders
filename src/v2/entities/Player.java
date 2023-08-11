package v2.entities;

import v2.Image;
import v2.Main;

public class Player extends Image {
    int speed, health;

    public Player(Main main) {
        super(main.width/2 - 25, main.height - 70, PlayerSettings.WIDTH, PlayerSettings.HEIGHT, main.loadImage("Images/Sprites/Player_Spaceship.png"));
        this.speed = PlayerSettings.INITIAL_SPEED;
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
        } else if (this.x > 590) {
            this.x = 590;
        }
        if (this.y < 0) {
            this.y = 0;
        } else if (this.y > 590) {
            this.y = 590;
        }
    }

    public void reset(Main main) {
        this.x = main.width/2 - 25;
        this.y = main.height - 70;
    }
}
