import processing.core.PImage;
import java.util.ArrayList;

public class Player extends Main_Advanced {
    PImage sprite;
    int x, y, speed, size, health;
    ArrayList<PImage> healthBar = new ArrayList<>();
    public Player() {
        this.size = 50;
        this.sprite = loadImage("Images/Sprites/Player_Spaceship.png");
        this.sprite.resize(this.size, this.size);
        this.x = width/2 - this.sprite.width/2;
        this.y = height - this.sprite.height - 20;
        this.speed = 5;
        this.health = 4;
        this.healthBar.add(loadImage("Images/Interactives/1:4_Health.png"));
        this.healthBar.add(loadImage("Images/Interactives/1:2_Health.png"));
        this.healthBar.add(loadImage("Images/Interactives/3:4_Health.png"));
        this.healthBar.add(loadImage("Images/Interactives/Full_Health.png"));
    }

    public void act() {
        this.movement();
    }

    public void movement() {
        //Basic movement. Uses both WASD and ^v<>
        if (keyPressed) {
            if (keyCode == RIGHT || key == 'd') {
                this.x += this.speed;
            }
            if (keyCode == LEFT || key == 'a') {
                this.x -= this.speed;
            }
            if (keyCode == UP || key == 'w') {
                this.y -= this.speed;
            }
            if (keyCode == DOWN || key == 's') {
                this.y += this.speed;
            }
        }

        //Sets movement boundaries to window edges.
        //Left edge
        if (this.x < 0) {
            this.x = 0;
        }
        //Right edge
        if (this.x + this.sprite.width > width) {
            this.x = width - this.sprite.width;
        }
        //Top edge
        if (this.y < 0) {
            this.y = 0;
        }
        //Bottom edge
        if (this.y + this.sprite.height > height) {
            this.y = height - this.sprite.height;
        }
    }
}
