package v1;
import processing.core.PApplet;
import processing.core.PImage;
import java.text.DecimalFormat;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;


public class Main extends PApplet {
    Minim loader;
    AudioPlayer gameBgm, homeBgm, gameOverSound;
    DecimalFormat df = new DecimalFormat("#.0");
    PImage player, enemy, rocket, background, star, bigRocket, playButton, healthBar, gameOver, titleCard, wasd, arrowKeys, mouseImage;

    int playerX, playerY, playerSpeed, playerSize,
        rocketX, rocketY, rocketSpeed, rocketAcc,
        enemy1X, enemy1Y, enemy1Speed,
        enemy2X, enemy2Y, enemy2Speed,
        enemy3X, enemy3Y, enemy3Speed,
        starX, starY,
        bigRocketX, bigRocketY,
        health, healthBarX, healthBarY,
        playButtonX, playButtonY,
        gameOverX, gameOverY,
        titleCardX, titleCardY,
        backgroundX, backgroundY;
    double score, time, hitScore, powerTime;
    boolean rocketFired, starSpawn, starMode, bigRocketSpawn, bigRocketMode, play, gameOverScreen;

    public void settings() {
        size(640, 640);
        frameRate = 60;
    }

    public void setup() {
        play = false;
        gameOverScreen = false;

        gameOver = loadImage("Images/Non-Interactives/Game_Over.png");
        gameOver.resize(300, 100);
        gameOverX = width/2 - gameOver.width/2;
        gameOverY = height/2 - gameOver.height;

        playButton = loadImage("Images/Interactives/New_Game.png");
        playButton.resize(250, 50);
        playButtonX = width/2 - playButton.width/2;
        playButtonY = height * 3 / 4;

        titleCard = loadImage("Images/Non-Interactives/Title_Text.png");
        titleCard.resize(300, 120);
        titleCardX = width/2 - titleCard.width/2;
        titleCardY = height/2 - titleCard.height;

        health = 4;
        healthBar = loadImage("Images/Interactives/Full_Health.png");
        healthBar.resize(100, 25);
        healthBarX = width - healthBar.width - 5;
        healthBarY = 5;

        player = loadImage("Images/Sprites/Player_Spaceship.png");
        playerSize = 50;
        player.resize(playerSize, playerSize);
        playerX = width/2 - playerSize/2;
        playerY = height - playerSize - 20;
        playerSpeed = 5;

        rocket = loadImage("Images/Sprites/Rocket_Sprite.png");
        rocket.resize(22, 32);
        rocketX = playerX + player.width / 2 - rocket.width / 2;
        rocketY = playerY - rocket.height;
        rocketSpeed = 0;
        rocketAcc = 1;

        enemy = loadImage("Images/Sprites/Enemy_Ship.png");
        enemy.resize(64, 64);

        enemy1X = (int) (Math.random() * (width - enemy.width));
        enemy1Y = (int) (Math.random() * -200) - 2000;
        enemy1Speed = (int) (Math.random() * 5) + 5;

        enemy2X = (int) (Math.random() * (width - enemy.width));
        enemy2Y = (int) (Math.random() * -200) - 2000;
        enemy2Speed = (int) (Math.random() * 5) + 5;

        enemy3X = (int) (Math.random() * (width - enemy.width));
        enemy3Y = (int) (Math.random() * -200) - 2000;
        enemy3Speed = (int) (Math.random() * 5) + 5;

        star = loadImage("Images/Sprites/Star_Powerup.png");
        starMode = false;
        starSpawn = false;

        bigRocket = loadImage("Images/Sprites/Big_Rocket.png");
        bigRocketMode = false;
        bigRocketSpawn = false;

        powerTime = 20;

        background = loadImage("Images/Space_Background.png");
        background.resize(640, 1280);
        backgroundX = 0;
        backgroundY = height - background.height;

        arrowKeys = loadImage("Images/Non-Interactives/Arrow_Image.png");
        arrowKeys.resize(100, 60);
        wasd = loadImage("Images/Non-Interactives/WASD.png");
        wasd.resize(80, 50);
        mouseImage = loadImage("Images/Non-Interactives/Mouse_Image.png");
        mouseImage.resize(90, 100);

        loader = new Minim(this);
        gameBgm = loader.loadFile("Music/Space_Theme.mp3");
        homeBgm = loader.loadFile("Music/Home_Music.mp3");
        gameOverSound = loader.loadFile("Music/Game_Over_Sound.mp3");
        homeBgm.play();

        score = 0.00;
        time = 0.00;
        hitScore = 0.0;
    }

    public void draw() {
        if(!play && !gameOverScreen) {
            //The start screen.
            background();
            image(titleCard, titleCardX, titleCardY);
            image(playButton, playButtonX, playButtonY);
            image(player, playerX, playerY);
            image(arrowKeys, 10, 10);
            image(wasd, 20, 70);
            image(mouseImage, 450, 10);
            textSize(20);
            text("TO MOVE", 120, 75);
            text("TO FIRE", 530, 80);
        } else if (play && !gameOverScreen){
            //The normal gameplay functions.
            background();
            playerMovement();
            rocketActions();
            enemyMovement();
            bigRocketCollect();
            rocketContact();
            starCollect();
            healthCheck();

            //Subtracts the time in seconds from the hitScore to get the true score. Discourages stalling.
            time = time + 1.0 / 60;
            score = hitScore - time;
            if (score < 0) {
                score = 0;
            }

            image(player, playerX, playerY);
            image(enemy, enemy1X, enemy1Y);
            image(enemy, enemy2X, enemy2Y);
            image(enemy, enemy3X, enemy3Y);
            image(healthBar, healthBarX, healthBarY);
            fill(255);
            textSize(20);
            text("Score: " + Double.valueOf(df.format(score)), 10, 20);
        } else if (!play) {
            //The gameOver screen.
            background(0);
            image(playButton, playButtonX, playButtonY);
            image(player, playerX, playerY);
            image(gameOver, gameOverX, gameOverY);
        }
    }

    public void playerMovement() {
        //Basic movement. Uses both WASD and ^v<>
        if (keyPressed) {
            if (keyCode == RIGHT || key == 'd') {
                playerX = playerX + playerSpeed;
            }
            if (keyCode == LEFT || key == 'a') {
                playerX = playerX - playerSpeed;
            }
            if (keyCode == UP || key == 'w') {
                playerY = playerY - playerSpeed;
            }
            if (keyCode == DOWN || key == 's') {
                playerY = playerY + playerSpeed;
            }
        }

        //Sets movement boundaries to window edges.
        //Left edge
        if (playerX < 0) {
            playerX = 0;
        }
        //Right edge
        if (playerX + player.width > width) {
            playerX = width - player.width;
        }
        //Top edge
        if (playerY < 0) {
            playerY = 0;
        }
        //Bottom edge
        if (playerY + player.height > height) {
            playerY = height - player.height;
        }
    }
    public void enemyMovement() {
        //Enemy movement only goes down so only Y movement.
        enemy1Y = enemy1Y + enemy1Speed;
        enemy2Y = enemy2Y + enemy2Speed;
        enemy3Y = enemy3Y + enemy3Speed;

        //Enemies respawn at top + re-randomize if top edge contacts bottom.
        if (enemy1Y > height) {
            respawn("enemy1");
        }
        if (enemy2Y > height) {
            respawn("enemy2");
        }
        if (enemy3Y > height) {
            respawn("enemy3");
        }
    }
    public boolean charContact(int char1X, int char1Y, int char1Diam, int char2X, int char2Y, int char2Diam) {
        //Finds center of both shapes for distance formula.
        int char1CenterX = char1X + char1Diam / 2;
        int char1CenterY = char1Y + char1Diam / 2;
        int char2CenterX = char2X + char2Diam / 2;
        int char2CenterY = char2Y + char2Diam / 2;
        //Uses distance formula to calculate distance between centers. Treats sprites as circles.
        double dist = Math.sqrt((char2CenterX - char1CenterX) * (char2CenterX - char1CenterX) + (char2CenterY - char1CenterY) * (char2CenterY - char1CenterY));
        //If the distance is less than the added radii, there is contact. If greater, then  there is no contact.
        return dist < char1Diam / 2.0 + char2Diam / 2.0;
    }

    public void mouseReleased() {
        if (play) {
            //Only one rocket can exist at a time, so will use boolean to check if rocket is fired or not.
            rocketFired = true;
        } else {
            if (mouseX < playButtonX + playButton.width && mouseX > playButtonX && mouseY < playButtonY + playButton.width && mouseY > playButtonY) {
                play = true;
                gameOverScreen = false;
                gameBgm.play();
                homeBgm.pause();
            }
        }
    }
    public void rocketActions() {
        //If the rocket has been fired, goes up. Acceleration of 1 pixel/frame^2 for realism.
        //Draws rocket only while it is fired.
        if (rocketFired) {
            rocketSpeed = rocketSpeed - rocketAcc;
            rocketY = rocketY + rocketSpeed;
            image(rocket, rocketX, rocketY);
        } else { //If the rocket has not been fired, is not drawn and tracks front of ship.
            if (!bigRocketMode) {
                rocketSpeed = 0;
            }
            rocketX = playerX + player.width / 2 - rocket.width / 2;
            rocketY = playerY - rocket.height;
        }

        //If the rocket goes past the top edge, despawns and sets rocketFired to false to allow another fire.
        if (rocketY + rocket.height < 0) {
            rocketFired = false;
        }
    }
    public void bigRocketCollect() {
        //If the big rocket is not spawned, time is above 40, time is a multiple of 40, and the player is not currently in the middle of a powerup,
        //spawn the rocket powerup and randomize its x/y coordinates.
        if (!bigRocketSpawn && time > 40 && time % 40 < 1 && powerTime == 20) {
            bigRocketSpawn = true;
            bigRocketX = (int) (Math.random() * 400) + 100;
            bigRocketY = -bigRocket.height;
        }

        //If the collectible is spawned but the player is not in bRM, draw the collectible.
        if (bigRocketSpawn && !bigRocketMode) {
            bigRocket.resize(30, 60);
            image(bigRocket, bigRocketX, bigRocketY);
            bigRocketY = bigRocketY + 2;
        }

        //If the player shoots and hits the spawned collectible, they get into bigRocketMode
        //In bigRocketMode, the rocket doesn't speed up, but it is faster and is bigger than normal.
        if (bigRocketSpawn && rocketFired && rocketY < bigRocketY + bigRocket.height && rocketX + rocket.width > bigRocketX && rocketX < bigRocketX + bigRocket.width) {
            bigRocketMode = true;
            bigRocketSpawn = false;
            rocket = bigRocket;
            rocket.resize(60, 120);
            rocketAcc = 0;
            rocketSpeed = -10;
            rocketX = rocketX - rocket.width/2;
        } else if (bigRocketY == height) {
            bigRocketSpawn = false;
        }

        //While in bigRocketMode, the rocket will not despawn until it goes off the screen; thus, it can kill multiple ships, which is helped by its size.
        //This is done by removing the line of code which changes rocketFired to false after destroying an enemy.
        if (bigRocketMode) {
            if (rocketFired) {
                if (rocketY < enemy1Y + enemy.height && rocketX + rocket.width > enemy1X && rocketX < enemy1X + enemy.width) {
                    respawn("enemy1");
                    hitScore = hitScore + 10;
                } else if (rocketY < enemy2Y + enemy.height && rocketX + rocket.width > enemy2X && rocketX < enemy2X + enemy.width) {
                    respawn("enemy2");
                    hitScore = hitScore + 10;
                } else if (rocketY < enemy3Y + enemy.height && rocketX + rocket.width > enemy3X && rocketX < enemy3X + enemy.width) {
                    respawn("enemy3");
                    hitScore = hitScore + 10;
                }
            }

            //Decreases powerTime by 1 every second.
            powerTime = powerTime - 1.0/60;
            //If powerTime goes past 0, returns everything back to normal.
            if (powerTime < 0) {
                bigRocketMode = false;
                rocket = loadImage("Images/Sprites/Rocket_Sprite.png");
                rocket.resize(22, 32);
                powerTime = 20;
                rocketAcc = 1;
                rocketSpeed = 0;
            }
        }
    }
    public void rocketContact() {
        //Only does contact check if rocket is fired.
        //Otherwise, because rocket follows ship in front, ship has melee attacks.
        if (rocketFired) {
            //Checks if top edge of rocket contacts bottom edge of enemies.
            //Simpler check because only vertical movement.
            //Also checks if contacting within enemy's l/r boundaries.
            //If contacts, re-randomizes enemy with location closer to edge than initial spawn(doubles as despawning enemy),
            //makes rocketFired false so that rocket despawns and player can fire again,
            //and adds 10 to hitScore.
            if (rocketY < enemy1Y + enemy.height && rocketX + rocket.width > enemy1X && rocketX < enemy1X + enemy.width) {
                respawn("enemy1");
                rocketFired = false;
                hitScore = hitScore + 10;
            } else if (rocketY < enemy2Y + enemy.height && rocketX + rocket.width > enemy2X && rocketX < enemy2X + enemy.width) {
                respawn("enemy2");
                rocketFired = false;
                hitScore = hitScore + 10;
            } else if (rocketY < enemy3Y + enemy.height && rocketX + rocket.width > enemy3X && rocketX < enemy3X + enemy.width) {
                respawn("enemy3");
                rocketFired = false;
                hitScore = hitScore + 10;
            }
        }
    }
    public void starCollect() {
        //If the star is not spawned, the time is greater than 1 minute, and the time is a multiple of 1 minute, spawn the star.
        if (!starSpawn && time > 60 && time % 60 < 1 && powerTime == 20) {
            starSpawn = true;
            starX = (int) (Math.random() * 400) + 100;
            starY = -star.height;
        }
        //If the player is not in starMode and the star has been spawned, draw the star.
        if (starSpawn && !starMode) {
            star.resize(40, 40);
            image(star, starX, starY);
            starY = starY + 2;
        }
        //If the player has touched the star and the star has been spawned,
        //Turn on starMode, change the sprite to the rainbow ship, and double the speed.
        if (charContact(playerX, playerY, playerSize, starX, starY, star.width) && starSpawn) {
            starMode = true;
            starSpawn = false;
            player = loadImage("Images/Sprites/Rainbow_Player.png");
            player.resize(50, 50);
            playerSpeed = 10;
        }

        //If the player IS in starMode, if they touch an enemy ship, the ship respawns and score gets added to.
        if (starMode) {
            if (charContact(enemy1X, enemy1Y, enemy.width, playerX, playerY, player.width)) {
                respawn("enemy1");
                hitScore = hitScore + 10;
            }
            if (charContact(enemy2X, enemy2Y, enemy.width, playerX, playerY, player.width)) {
                respawn("enemy2");
                hitScore = hitScore + 10;
            }
            if (charContact(enemy3X, enemy3Y, enemy.width, playerX, playerY, player.width)) {
                respawn("enemy3");
                hitScore = hitScore + 10;
            }

            //Player has a powerTime of 20 seconds. Once starMode starts, the time will decrease by 1 each second.
            //Once the powerTime runs out, everything returns to normal.
            //Also moves star out of the way to prevent player from accidentally touching invisible star and gaining power up.
            powerTime = powerTime - 1.0/60;
            if (powerTime < 0) {
                starMode = false;
                player = loadImage("Images/Sprites/Player_Spaceship.png");
                player.resize(50, 50);
                playerSpeed = 5;
                powerTime = 20;
            }
        }
    }
    public void healthCheck() {
        //If the player is NOT in starMode, if they touch an enemy ship, they lose 1 health.
        //By putting this after the star method's calling, we are able to sidestep any overlap;
        //If the player is in starMode, the enemy ship will be destroyed before the death check.
        //If the ship is not destroyed, then the healthcheck will hurt the player.
        if (charContact(enemy1X, enemy1Y, enemy.width, playerX, playerY, player.width)) {
            health--;
            respawn("enemy1");
        }
        if (charContact(enemy2X, enemy2Y, enemy.width, playerX, playerY, player.width)) {
            health--;
            respawn("enemy2");
        }
        if (charContact(enemy3X, enemy3Y, enemy.width, playerX, playerY, player.width)) {
            health--;
            respawn("enemy3");
        }
        if (health == 4) {
            healthBar = loadImage("Images/Interactives/Full_Health.png");
            healthBar.resize(100, 25);
        } else if (health == 3) {
            healthBar = loadImage("Images/Interactives/3:4_Health.png");
            healthBar.resize(100, 25);
        } else if (health == 2) {
            healthBar = loadImage("Images/Interactives/1:2_Health.png");
            healthBar.resize(100, 25);
        } else if (health == 1) {
            healthBar = loadImage("Images/Interactives/1:4_Health.png");
            healthBar.resize(100, 25);
        } else if (health == 0) {
            gameBgm.pause();
            gameBgm.rewind();
            gameOverSound.play();
            setup();
            gameOverScreen = true;
        }
    }

    public void background() {
        //Illusion of forwards movement of space, doubled background image and once top is reached, moves back to bottom.
        //Thus, it looks like it's infinite.
        backgroundY++;
        if (backgroundY == 0) {
            backgroundY = height - background.height;
        }
        image(background, backgroundX, backgroundY);
    }

    //Does the three respawn actions for the same enemy that the input string is.
    //This would have been MUCH EASIER with character classes >:(
    public void respawn(String enemyName) {
        if (enemyName.equals("enemy1")) {
            enemy1X = (int) (Math.random() * (width - enemy.width));
            enemy1Y = (int) (Math.random() * -100) - enemy.height;
            enemy1Speed = (int) (Math.random() * 5) + 5;
        } else if (enemyName.equals("enemy2")) {
            enemy2X = (int) (Math.random() * (width - enemy.width));
            enemy2Y = (int) (Math.random() * -100) - enemy.height;
            enemy2Speed = (int) (Math.random() * 5) + 5;
        } else if (enemyName.equals("enemy3")) {
            enemy3X = (int) (Math.random() * (width - enemy.width));
            enemy3Y = (int) (Math.random() * -100) - enemy.height;
            enemy3Speed = (int) (Math.random() * 5) + 5;
        }
    }

    public static void main(String[] args) {
        PApplet.main("v1.Main");
    }
}