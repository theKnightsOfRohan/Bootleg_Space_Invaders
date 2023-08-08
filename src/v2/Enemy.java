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
        this.hitsPlayer(main);
        this.hitsRocket(main);
        main.image(this.img, this.x, this.y);
    }

    public void move() {
        this.y += this.speed;
        if (this.y > 804) {
            this.respawn();
        }
    }

    public void hitsPlayer(Main main) {
        if (this.isTouching(main.player)) {
            main.healthBar.hitCounter++;
            main.enemies.remove(this);
            if (main.healthBar.hitCounter == 4) {
                main.player.reset(main);
                main.gameState = 2;
                main.healthBar.hitCounter = 0;
                main.enemies.clear();
                main.keyCode = 0;
                main.gameBgm.pause();
                main.gameBgm.rewind();
                main.gameOverSound.play();
                main.gameOverSound.rewind();
                main.homeBgm.play();
            }
        }
    }

    public void hitsRocket(Main main) {
        for (int i = 0; i < main.rockets.size(); i++) {
            if (this.isTouching(main.rockets.get(i))) {
                main.rockets.remove(i);
                main.hitScore += 10;
                this.respawn();
            }
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