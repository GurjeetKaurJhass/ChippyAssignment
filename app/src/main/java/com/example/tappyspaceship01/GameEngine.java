package com.example.tappyspaceship01;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v4.math.MathUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG = "TAPPY-SPACESHIP";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;
    Bitmap background;
    int bgXposition;
    int backgroundRightside;



    double yPosition;
    double xPosition;

    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------
    Player player;
    Enemy enemy;
    Powerups powerup;
    int lives = 100;
    int numLoops;
    int score = 0;


    // ----------------------------
    // ## GAME STATS
    // ---------------------------


    //changed files
    public GameEngine(Context context, int w, int h) {
        super(context);

        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.loop);
        mp.start();

        this.holder = this.getHolder();
        this.paintbrush = new Paint();
        this.screenWidth = w;
        this.screenHeight = h - 200;
        player = new Player(this.getContext(), 100, 100);
        enemy = new Enemy(this.getContext(), 1700, 600);
        powerup = new Powerups(this.getContext(), 500,500);

        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        this.background = Bitmap.createScaledBitmap(this.background, this.screenWidth, this.screenHeight, false);
//        // @TODO: Add your sprites
//        // @TODO: Any other game setup
        this.bgXposition = 0;

    }

    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }

    private void spawnEnemyShips() {

        {

            numLoops = numLoops + 1;
            if (numLoops % 100 == 0) {
                Random r = new Random();

                int randomXPos = r.nextInt(this.screenWidth) - 100;
                int randomYPos = r.nextInt(this.screenHeight) - 100;

                this.enemy.setxPosition(randomXPos + 10);
                this.enemy.setyPosition(randomYPos - 10);
                if (this.enemy.getxPosition() <= screenHeight && this.enemy.getyPosition() <= screenHeight) {
                    this.enemy.setxPosition(randomXPos - 100);
                    this.enemy.setyPosition(randomYPos - 100);
                }

            }
        }


        //@TODO: Place the enemies in a random location


    }


    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------


    public void updatePositions() {


        this.bgXposition = this.bgXposition - 50;
        backgroundRightside = this.bgXposition + this.background.getWidth();
        if (backgroundRightside < 0) {
            this.bgXposition = 0;
        }
        numLoops = numLoops + 1;


        this.spawnEnemyShips();

        if (numLoops % 120 == 0) {
            this.randomPowerups();
        }


//player.setxPosition((int) (Math.cos(gunAngle) * distanceFromCenter));
//        player.setyPosition((int) (Math.cos(gunAngle) * distanceFromCenter));
//     //   player.x()=MathUtils.cosDeg((gunAngle)) * distanceFromCenter;
//        player.getxPosition();
//        player.getyPosition();
//


//        for (int i = 0; i < this.enemy.getBullets().size();i++)
//        {
//            Rect bullet = this.enemy.getBullets().get(i);
//
//            double a = (this.player.getyPosition()- this.enemy.getBulletxPos();
//            double b = (this.player.getyPosition() - this.enemy.getBulletyPOs());
//            double distance = Math.sqrt((a*a) + (b*b));
//
//            // 2. calculate the "rate" to move
//            double xn = (a / distance);
//            double yn = (b / distance);
//
//            // 3. move the bullet
//            this.enemy.setxPosition(this.enemy.getxPosition() + (int)(xn * 15));
//            this.enemy.setxPosition(this.enemy.getyPosition() + (int)(yn * 15));
//
//           // Log.d(TAG,"New bullet (x,y): (" + bullet.x + "," + bullet.y + ")");
//
//        }


        // @TODO: Update position of player

        this.moveplayer(this.xPosition, this.yPosition);

        //}
// Player Movement
//        if (personTapped.contentEquals("down")) {
//
//            this.player.setyPosition(this.player.getyPosition() + 30);
//
//            this.player.updatePlayerHitbox();
//            if(this.player.getyPosition() >= this.screenHeight){
//                personTapped = "up";
//
//            }
//        } else if (personTapped.contentEquals("up")) {
//
//            this.player.setyPosition(this.player.getyPosition() - 30);
//            this.player.updatePlayerHitbox();
//            if (this.player.getyPosition() <= 0) {
//                personTapped = "down";
//            }
//
//        }else if( personTapped.contentEquals("right")) {
//
//            this.player.setxPosition(this.player.getxPosition() + 30);
//
//            this.player.updatePlayerHitbox();
//            if(this.player.getxPosition() >= this.screenWidth){
//                personTapped = "left";}
//        }else if( personTapped.contentEquals("left")) {
//
//            this.player.setxPosition(this.player.getxPosition() - 30);
//
//            this.player.updatePlayerHitbox();
//            if(this.player.getxPosition() <= 0){
//                personTapped = "right";}
//        }
//



        //player bullets

        if (numLoops % 5 == 0) {
            this.player.spawnBullet();
        }


        int PLAYER_BULLET_SPEED = 50;
        for (int i = 0; i < this.player.getBullets().size(); i++) {
            Rect bullet = this.player.getBullets().get(i);
            bullet.left = bullet.left - PLAYER_BULLET_SPEED;
            bullet.right = bullet.right - PLAYER_BULLET_SPEED;
        }

        // collision detection between player bullets and enemy
        for (int i = 0; i < this.player.getBullets().size(); i++) {
            Rect playerbullet = this.player.getBullets().get(i);

            if (this.enemy.getHitbox().intersect(playerbullet) == true) {
                this.enemy.setxPosition(500);
                this.enemy.setyPosition(500);
                this.enemy.updateEnemyHitbox();
                score = score + 1;
            }


        }


        for (int i = 0; i < this.player.getBullets().size(); i++) {
            Rect playerbullet = this.player.getBullets().get(i);

            // For each bullet, check if teh bullet touched the wall
            if (playerbullet.right < 0) {
                this.player.getBullets().remove(playerbullet);
            }

        }

/////////////////////////Eneny///////////////////////////

        if (numLoops % 5 == 0) {
            this.enemy.spawnBullet();
        }


        int BULLET_SPEED = 50;
        for (int i = 0; i < this.enemy.getBullets().size(); i++) {
            Rect bullet = this.enemy.getBullets().get(i);
            bullet.left = bullet.left - BULLET_SPEED;
            bullet.right = bullet.right - BULLET_SPEED;
        }

        if (lives == 0) {
            this.gameIsRunning = false;
        }

//        if(this.player.getHitbox().intersect(this.enemy2.getHitbox())==true)
//        {
//            this.player.setxPosition(100);
//            this.player.setyPosition(600);
//            this.player.updatePlayerHitbox();
//            lives=lives-1;
//        }


        // COLLISION DETECTION ON THE BULLET AND WALL
        for (int i = 0; i < this.enemy.getBullets().size(); i++) {
            Rect bullet = this.enemy.getBullets().get(i);

            // For each bullet, check if teh bullet touched the wall
            if (bullet.right < 0) {
                this.enemy.getBullets().remove(bullet);
            }

        }

        // COLLISION DETECTION BETWEEN BULLET AND PLAYER
        for (int i = 0; i < this.enemy.getBullets().size(); i++) {
            Rect bullet = this.enemy.getBullets().get(i);

            if (this.player.getHitbox().intersect(bullet)) {
                this.player.setxPosition(100);
                this.player.setyPosition(600);
                this.player.updatePlayerHitbox();
                lives = lives - 1;
            }


        }

    }


    // MOVING THE BULLETS


    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255, 255, 255, 255));
            paintbrush.setColor(Color.BLUE);
            this.canvas.drawBitmap(this.background, this.bgXposition, 0, paintbrush);
            this.canvas.drawBitmap(this.background, this.backgroundRightside, 0, paintbrush);


            this.canvas.drawBitmap(this.player.getImage(), this.player.getxPosition(), this.player.getyPosition(), paintbrush);
            this.canvas.drawBitmap(this.enemy.getImage(), this.enemy.getxPosition(), this.enemy.getyPosition(), paintbrush);
            this.canvas.drawBitmap(this.powerup.getImage(), this.powerup.getxPosition(), this.powerup.getyPosition(), paintbrush);

       //hitboxes
            this.canvas.drawRect(this.player.getHitbox(), paintbrush);
            this.canvas.drawRect(this.enemy.getHitbox(),paintbrush);
            this.canvas.drawRect(this.powerup.getHitbox(),paintbrush);


            this.paintbrush.setColor(Color.YELLOW);

            this.paintbrush.setTextSize(65);
            this.canvas.drawText("Lives Remaining" + lives, 1100, 800, paintbrush);
            // canvas.drawText("Bullets: " + this.enemy.getBullets().size(),1100,720,paintbrush);

            canvas.drawText("Score: " + score,
                    1100,
                    720,
                    paintbrush
            );
// player bullets
            for (int i = 0; i < this.player.getBullets().size(); i++) {
                Rect bullet = this.player.getBullets().get(i);
                canvas.drawRect(bullet, paintbrush);
            }





            // this.canvas.drawRect(playerHitbox,paintbrush);
            ///draw bullet on screen
            this.paintbrush.setColor(Color.YELLOW);
            for (int i = 0; i < this.enemy.getBullets().size(); i++) {
                Rect bullet = this.enemy.getBullets().get(i);
                canvas.drawRect(bullet, paintbrush);
            }

            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);
            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(50);
        } catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String personTapped = "";


    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            // Log.d(TAG, "Person tapped the screen");

            this.yPosition = event.getY();
            this.xPosition = event.getX();


            Log.d(TAG, "Person's pressed: "
                    + xPosition + ","
                    + yPosition);
//            int middleOfScreen1 = this.screenWidth / 2;
//            int middleOfScreen = this.screenHeight / 2;
//            if (yPosition <= middleOfScreen) {
//                personTapped = "up";
//            } else if (yPosition > middleOfScreen) {
//                personTapped = "down";
//            } else if (xPosition <= middleOfScreen1) {
//                personTapped = "right";
//            } else if (xPosition > middleOfScreen1) {
//                personTapped = "left";
//            }
        } else if (userAction == MotionEvent.ACTION_UP) {
            // Log.d(TAG, "Person lifted finger");
//            float yPosition = event.getY();
//            float fingerXPosition = event.getX();
//            Log.d(TAG, "Person's pressed: "
//                    + fingerXPosition + ","
//                    + yPosition);
        }

        return true;
    }


    public void moveplayer(double playerxposition, double playeryposition) {


        double a = this.xPosition - this.player.getxPosition();
        double b = this.yPosition - this.player.getyPosition();
        double distance = Math.sqrt((a * a) + (b * b));


        double xn = (a / distance);
        double yn = (b / distance);

        this.player.setxPosition(this.player.getxPosition() + (int) (xn * 10));
        this.player.setyPosition(this.player.getyPosition() + (int) (yn * 10));
        
    }

    public void randomPowerups() {
        {
            Random r = new Random();

                int randomXPos = r.nextInt(this.screenWidth) - 100;
                int randomYPos = r.nextInt(this.screenHeight) - 100;

                this.powerup.setxPosition(randomXPos + 10);
                this.powerup.setyPosition(randomYPos - 10);
                if (this.powerup.getxPosition() <= screenHeight && this.powerup.getyPosition() <= screenHeight) {
                    this.powerup.setxPosition(randomXPos - 100);
                    this.powerup.setyPosition(randomYPos - 100);
                }
if(this.player.getHitbox().intersect(this.powerup.getHitbox())){

}

            }
        }

    }

