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

    public void act(Rocket rocket, Player player) {
        this.move();
        this.rocketContact(rocket);
        this.hitChar(player);
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

    public void rocketContact(Rocket rocket) {
        if (this.x < rocket.x && this.x + this.sprite.width > rocket.x && this.y + this.sprite.height < rocket.y) {
            this.respawn();
            rocket.fired = false;
            hitScore = hitScore + 10;
        }
    }

    public void hitChar(Player player) {
        if (charContact(this.x, this.y, this.sprite.width, player.x, player.y, player.sprite.width)) {
            player.health--;
            this.respawn();
        }
    }
}