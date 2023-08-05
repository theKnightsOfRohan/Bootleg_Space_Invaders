package v2;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Arrays;
import processing.core.PImage;
import java.text.DecimalFormat;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Main extends PApplet {
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Rocket> rockets = new ArrayList<>();
    ArrayList<Image> images = new ArrayList<>();
    Player player;
    Image background;
    HealthBar healthBar;
    PImage rocketImg;
    PImage enemyImg;


    public void settings() {
        size(640, 640);
        frameRate = 60;
    }

    public void setup() {
        player = new Player(this);
        background = new Image(0, 0, 640, 1280, loadImage("Images/Space_Background.png"));
        healthBar = new HealthBar(new PImage[] {
            loadImage("Images/Interactives/Full_Health.png"),
            loadImage("Images/Interactives/3:4_Health.png"),
            loadImage("Images/Interactives/1:2_Health.png"),
            loadImage("Images/Interactives/1:4_Health.png")
        });
        rocketImg = loadImage("Images/Sprites/Rocket_Sprite.png");

        enemyImg = loadImage("Images/Sprites/Enemy_Ship.png");
        images.add(background);
        images.add(healthBar);
        images.add(player);
    }

    public void draw() {
        images.forEach(image -> image.draw(this));
    }

    public void mouseReleased() {
        if (mouseButton == LEFT) {
            
        }
    }

    public void keyReleased() {
        if (key == ' ') {
            
        }
    }

    public void keyPressed() {
        player.move(keyCode);
    }

    public static void main(String[] args) {
        PApplet.main("v2.Main");
    }
}