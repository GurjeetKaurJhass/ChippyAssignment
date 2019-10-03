package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="TAPPY-SPACESHIP";

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



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------
    Player player;
    Enemy enemy;
    Rect enemyHitbox;
    Rect playerHitbox;


    // ----------------------------
    // ## GAME STATS
    // ----------------------------

    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();
        this.screenWidth = w;
        this.screenHeight = h;
        player=new Player(this.getContext(),100,100);
        enemy=new Enemy(this.getContext(),1400,100);

        this.printScreenInfo();

        // @TODO: Add your sprites
        // @TODO: Any other game setup

    }


    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

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
        // @TODO: Update position of player
    if(fingerAction=="mousedown")
    {
        this.player.setyPosition(this.player.getyPosition()-10);
        this.playerHitbox.left=this.player.getxPosition();
        this.playerHitbox.top=this.player.getyPosition();
        this.playerHitbox.right=this.player.getxPosition()+this.player.getImage().getWidth();
        this.playerHitbox.bottom=this.player.getyPosition()+this.player.getImage().getHeight();
    }

    else if(this.fingerAction=="mouseup")
        {
            this.player.setyPosition(this.player.getyPosition()+10);
            this.playerHitbox.left=this.player.getxPosition();
            this.playerHitbox.top=this.player.getyPosition();
            this.playerHitbox.right=this.player.getxPosition()+this.player.getImage().getWidth();
            this.playerHitbox.bottom=this.player.getyPosition()+this.player.getImage().getHeight();

        }

    this.enemy.updateEnemyPosition();

    if(this.enemy.getxPosition()<=0)
    {
      enemy.setxPosition(this.screenWidth);

    }
    if(this.player.getHitbox().intersect(this.enemy.getHitbox())==true)
        {
            this.player.setxPosition(100);
            this.player.setyPosition(600);
            this.playerHitbox.left=this.player.getxPosition();
            this.playerHitbox.top=this.player.getyPosition();
            this.playerHitbox.right=this.player.getxPosition()+this.player.getImage().getWidth();
            this.playerHitbox.bottom=this.player.getyPosition()+this.player.getImage().getHeight();

        }

    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.BLUE);
            this.canvas.drawBitmap(this.player.getImage(),this.player.getxPosition(),this.player.getyPosition(),paintbrush);
            this.canvas.drawBitmap(this.enemy.getImage(),this.enemy.getxPosition(),this.enemy.getyPosition(),paintbrush);
            enemyHitbox=this.enemy.getHitbox();
            this.canvas.drawRect(enemyHitbox,paintbrush);
            playerHitbox=this.player.getHitbox();
            this.canvas.drawRect(playerHitbox,paintbrush);

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
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


String fingerAction="";
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
           // Log.d(TAG, "Person tapped the screen");
            fingerAction="mousedown";

        }
        else if (userAction == MotionEvent.ACTION_UP) {
           // Log.d(TAG, "Person lifted finger");
           fingerAction="mouseup";
        }

        return true;
    }
}
