package v2;

import v2.MainSettings;
import v2.entities.Enemy;
import v2.entities.Player;
import v2.entities.Rocket;
import processing.core.PApplet;
import java.util.ArrayList;
import processing.core.PImage;
import java.text.DecimalFormat;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Main extends PApplet {
    public Minim loader = new Minim(this);
    public AudioPlayer gameBgm, homeBgm, gameOverSound;
    public DecimalFormat df = new DecimalFormat("#.0");
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Rocket> rockets = new ArrayList<>();
    public ArrayList<Image> startScreen = new ArrayList<>();
    public ArrayList<Image> playScreen = new ArrayList<>();
    public ArrayList<Image> deathScreen = new ArrayList<>();
    public Player player;
    public Image background, newGame;
    public HealthBar healthBar;
    public PImage rocketImg, enemyImg;
    public int gameState;
    public double score, time, hitScore, powerTime;


    public void settings() {
        size(MainSettings.WINDOW_WIDTH, MainSettings.WINDOW_HEIGHT);
        frameRate = MainSettings.FRAME_RATE;
    }

    public void setup() {
        gameState = MainSettings.INITIAL_GAME_STATE;
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
        
        startScreen.add(background);
        startScreen.add(new Image(width/2 - 150, height/2 - 120, 300, 120, loadImage("Images/Non-Interactives/Title_Text.png")));
        startScreen.add(new Image(10, 10, 100, 60, loadImage("Images/Non-Interactives/Arrow_Image.png")));
        startScreen.add(new Image(20, 70, 80, 50, loadImage("Images/Non-Interactives/WASD.png")));
        startScreen.add(new Image(450, 10, 90, 100, loadImage("Images/Non-Interactives/Mouse_Image.png")));
        startScreen.add(newGame);
        startScreen.add(player);
        playScreen.add(background);
        playScreen.add(healthBar);
        playScreen.add(player);
        deathScreen.add(newGame);
        deathScreen.add(new Image(width/2 - 150, height/2 - 50, 300, 100, loadImage("Images/Non-Interactives/Game_Over.png")));

        score = MainSettings.INITIAL_SCORE;
        time = MainSettings.INITIAL_TIME;
        hitScore = MainSettings.INITIAL_HIT_SCORE;
        textSize(20);
        fill(255);

        gameBgm = loader.loadFile("Music/Space_Theme.mp3");
        homeBgm = loader.loadFile("Music/Home_Music.mp3");
        gameOverSound = loader.loadFile("Music/Game_Over_Sound.mp3");
        homeBgm.play();
    }

    public void draw() {
        if (gameState == MainSettings.START_SCREEN_STATE) {
            startScreen.forEach(img -> img.draw(this));
            text("TO MOVE", 120, 75);
            text("TO FIRE", 530, 80);
            bgAction();
        } else if (gameState == MainSettings.PLAYING_GAME_STATE) {
            playScreen.forEach(img -> img.draw(this));
            for (int i = 0; i < rockets.size(); i++) {
                rockets.get(i).draw(this, i);
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(this);
            }
            bgAction();
            spawnEnemies();
            keyPressed();
            scoreActions();
        } else if (gameState == MainSettings.GAME_OVER_STATE) {
            background(0);
            deathScreen.forEach(img -> img.draw(this));
        }
    }

    public void scoreActions() {
        time = time + 1.0 / 60;
        score = hitScore - time;
        if (score < 0) {
            score = 0;
        }
        text("Score: " + Double.valueOf(df.format(score)), 10, 20);
    }

    public void bgAction() {
        if (background.y >= 0) {
            background.y = -640;
        }
        background.y += 1;
    }

    public void spawnEnemies() {
        if (frameCount % 500 == 0) {
            enemies.add(new Enemy(enemyImg));
        }
    }

    public void mouseReleased() {
        switch (gameState) {
            case 0:
                if (mouseX >= newGame.x && mouseX <= newGame.x + newGame.w && mouseY >= newGame.y && mouseY <= newGame.y + newGame.h) {
                    switchToPlay();
                }
                break;
            case 1:
                rockets.add(new Rocket(player.x + 14, player.y - 32, rocketImg));
                break;
            case 2:
                if (mouseX >= newGame.x && mouseX <= newGame.x + newGame.w && mouseY >= newGame.y && mouseY <= newGame.y + newGame.h) {
                    switchToPlay();
                }
                break;
        }
    }

    public void keyReleased() {
        switch (gameState) {
            case 0:
                if (key == ' ') switchToPlay();
                break;
            case 1:
                keyCode = 0;
                break;
            case 2:
                if (key == ' ') switchToPlay();
                break;
        }
    }

    public void keyPressed() {
        switch (gameState) {
            case 0:
                break;
            case 1:
                player.move(keyCode);
                break;
            case 2:
                break;
        }
    }

    public void switchToPlay() {
        gameState = 1;
        homeBgm.pause();
        homeBgm.rewind();
        gameBgm.play();
    }

    public static void main(String[] args) {
        PApplet.main("v2.Main");
    }
}