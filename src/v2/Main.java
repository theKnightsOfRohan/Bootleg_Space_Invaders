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
    ArrayList<ArrayList<Image>> images = new ArrayList<>();
    Player player;
    Image background, newGame;
    HealthBar healthBar;
    PImage rocketImg;
    PImage enemyImg;
    int gameState;


    public void settings() {
        size(640, 640);
        frameRate = 60;
    }

    public void setup() {
        gameState = 0;
        player = new Player(this);
        background = new Image(0, -640, 640, 1280, loadImage("Images/Space_Background.png"));
        newGame = new Image(width/2 - 125, height * 3/4, 250, 50, loadImage("Images/Interactives/New_Game.png"));
        healthBar = new HealthBar(new PImage[] {
            loadImage("Images/Interactives/Full_Health.png"),
            loadImage("Images/Interactives/3:4_Health.png"),
            loadImage("Images/Interactives/1:2_Health.png"),
            loadImage("Images/Interactives/1:4_Health.png")
        });
        rocketImg = loadImage("Images/Sprites/Rocket_Sprite.png");
        enemyImg = loadImage("Images/Sprites/Enemy_Ship.png");
        images.add(new ArrayList<Image>());
        images.add(new ArrayList<Image>());
        images.add(new ArrayList<Image>());
        images.get(0).add(background);
        images.get(0).add(newGame);
        images.get(0).add(player);
        images.get(1).add(background);
        images.get(1).add(healthBar);
        images.get(1).add(player);
        images.get(2).add(newGame);
    }

    public void draw() {
        images.get(gameState).forEach(image -> image.draw(this));
        bgAction();
    }

    public void bgAction() {
        if (background.y >= 0) {
            background.y = -640;
        }
        background.y += 1;
    }

    public void mouseReleased() {
        switch (gameState) {
            case 0:
                if (mouseX >= newGame.x && mouseX <= newGame.x + newGame.w && mouseY >= newGame.y && mouseY <= newGame.y + newGame.h) {
                    gameState = 1;
                }
                break;
            case 1:
                rockets.add(new Rocket(player.x + 14, player.y - 32, rocketImg));
                break;
            case 2:
                if (mouseX >= newGame.x && mouseX <= newGame.x + newGame.w && mouseY >= newGame.y && mouseY <= newGame.y + newGame.h) {
                    gameState = 1;
                }
                break;
        }
    }

    public void keyReleased() {
        if (key == ' ') {
            
        }
    }

    public void keyPressed() {
        switch (gameState) {
            case 0:
                if (key == ' ') {
                    gameState = 1;
                }
                break;
            case 1:
                if (key == ' ') {
                    rockets.add(new Rocket(player.x + 14, player.y - 32, rocketImg));
                } else player.move(keyCode);
                break;
            case 2:
                if (key == ' ') {
                    gameState = 1;
                }
                break;
        }
    }

    public static void main(String[] args) {
        PApplet.main("v2.Main");
    }
}