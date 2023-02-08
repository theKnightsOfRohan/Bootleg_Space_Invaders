import processing.core.PImage;

public class Rocket extends Main_Advanced {
    PImage sprite, bigRocketSprite;
    int x, y, speed, acc;
    boolean bigRocketMode;
    public Rocket() {
        this.sprite = loadImage("Images/Sprites/Rocket_Sprite.png");
        this.bigRocketSprite = loadImage("Images/Sprites/Big_Rocket.png");
    }
}
