package v2.entities;

import processing.core.PImage;
import v2.Image;
import v2.Main;

public class Enemy extends Image {
    int speed;

    public Enemy(PImage img) {
        super((int) (Math.random() * 576), (int) (Math.random() * -100) - 64, 64, 64, img);
        this.speed = (int) (Math.random() * 5) + 5;
    }

    public void draw(Main main) {
        this.move();
        if (this.isTouching(main.player))
            this.hitsPlayer(main);
        for (int i = 0; i < main.rockets.size(); i++)
            if (this.isTouching(main.rockets.get(i)))
                this.hitsRocket(main, i);
        main.image(this.img, this.x, this.y);
    }

    public void move() {
        this.y += this.speed;
        if (this.y > 804) {
            this.respawn();
        }
    }

    public void hitsPlayer(Main main) {
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

    public void hitsRocket(Main main, int i) {
        main.rockets.remove(i);
        i--;
        main.hitScore += 10;
        this.respawn();
    }

    public void respawn() {
        this.y = (int) (Math.random() * -100) - 64;
        this.x = (int) (Math.random() * 576);
    }
}
