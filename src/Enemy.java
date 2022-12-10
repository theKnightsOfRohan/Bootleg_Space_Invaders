import processing.core.PImage;

public class Enemy extends Main_Advanced {
    PImage sprite;

    int x, y, speed;
    public Enemy() {
        this.sprite = loadImage("Images/Sprites/Enemy_Ship.png");
        this.sprite.resize(64, 64);
        this.x = (int)(Math.random() * (width - sprite.width));
        this.y = (int)(Math.random() * -200) - 2000;
        this.speed = (int)(Math.random() * 5) + 5;
    }

    public void act() {
        this.move();
        this.rocketContact();
        this.hitChar();
    }

    public void move() {
        this.y = this.y + this.speed;
        if (this.y > height) {
            this.respawn();
        }
    }

    public void respawn() {
        this.x = (int) (Math.random() * (width - this.sprite.width));
        this.y = (int) (Math.random() * -100) - this.sprite.height;
        this.speed = (int) (Math.random() * 5) + 5;
    }

    public void rocketContact() {
        if (this.x < rocketX && this.x + this.sprite.width > rocketX && this.y + this.sprite.height < rocketY) {
            this.respawn();
            rocketFired = false;
            hitScore = hitScore + 10;
        }
    }

    public void hitChar() {
        if (charContact(this.x, this.y, this.sprite.width, playerX, playerY, playerSize)) {
            health--;
            this.respawn();
        }
    }
}