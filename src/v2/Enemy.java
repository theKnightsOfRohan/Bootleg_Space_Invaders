package v2;

import processing.core.PImage;

public class Enemy extends Image {
    int speed;

    
    public Enemy(PImage img) {
        super((int)(Math.random() * 576), (int)(Math.random() * -100) - 64, 64, 64, img);
        this.speed = (int) (Math.random() * 5) + 5;
    }

    public void draw(Main main) {
        this.move();
        main.image(this.img, this.x, this.y);
    }

    public void move() {
        this.y += this.speed;
        if (this.y > 804) {
            this.respawn();
        }
    }

    public void respawn() {
        this.y = (int)(Math.random() * -100) - 64;
        this.x = (int)(Math.random() * 576);
    }

    public boolean isTouching(Image img) {
        return (this.x < img.x + img.w && this.x + this.w > img.x && this.y < img.y + img.h && this.y + this.h > img.y);
    }
}