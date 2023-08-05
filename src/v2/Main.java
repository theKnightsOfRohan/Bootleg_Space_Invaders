package v2;
import processing.core.PApplet;
import java.util.ArrayList;
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
    }

    public void setup() {
        //frameRate(60);
        player = new Player(this);
        background = new Image(0, 0, 640, 1280, loadImage("Images/Space_Background.png"));
        healthBar = new HealthBar(new PImage[] {
            loadImage("Images/Interactives/Full_Health.png"),
            loadImage("Images/Interactives/3:4_Health.png"),
            loadImage("Images/Interactives/1:2_Health.png"),
            loadImage("Images/Interactives/1:4_Health.png")
        });
        rocketImg = loadImage("Images/Sprites/Rocket.png");
        enemyImg = loadImage("Images/Sprites/Enemy_Spaceship.png");
        images.add(background);
        images.add(player);
        images.add(healthBar);
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