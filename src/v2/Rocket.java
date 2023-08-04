package v2;
import processing.core.PImage;

public class Rocket extends Main {
    PImage sprite, bigRocketSprite;
    int x, y, speed, acc;
    boolean bigRocketMode, fired;
    public Rocket(Player player) {
        this.sprite = loadImage("Images/Sprites/Rocket_Sprite.png");
        this.bigRocketSprite = loadImage("Images/Sprites/Big_Rocket.png");
        this.x = player.x + player.sprite.width/2 - this.sprite.width/2;
        this.y = player.y - this.sprite.height;
        this.speed = 0;
        this.acc = 1;
        this.fired = false;
    }
}
