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
    ArrayList<Image> screen1 = new ArrayList<>();
    ArrayList<Image> screen2 = new ArrayList<>();
    ArrayList<Image> screen3 = new ArrayList<>();
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
        
        screen1.add(background);
        screen1.add(new Image(width/2 - 150, height/2 - 120, 300, 120, loadImage("Images/Non-Interactives/Title_Text.png")));
        screen1.add(newGame);
        screen1.add(player);
        screen2.add(background);
        screen2.add(healthBar);
        screen2.add(player);
        screen3.add(newGame);
    }

    public void draw() {
        if (gameState == 0) {
            screen1.forEach(img -> img.draw(this));
            bgAction();
        } else if (gameState == 1) {
            screen2.forEach(img -> img.draw(this));
            for (int i = 0; i < rockets.size(); i++) {
                rockets.get(i).draw(this, i);
            }
            bgAction();
        } else if (gameState == 2) {
            screen3.forEach(img -> img.draw(this));
        }
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
            rockets.add(new Rocket(player.x + 14, player.y - 32, rocketImg));
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
                player.move(keyCode);
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