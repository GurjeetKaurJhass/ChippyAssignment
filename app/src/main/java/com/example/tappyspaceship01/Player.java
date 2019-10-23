package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Player {

    // PROPERTIES
    private Bitmap image;
    private Rect hitbox;

    private int xPosition;
    private int yPosition;
    private int direction= 1;

    private int playerbulletxPos;
    private int playerbulletyPOs;

    public int getBulletxPos() {

        return playerbulletxPos;
    }
    private final int BULLET_WIDTH=15;
    private ArrayList<Rect> playerbullets=new ArrayList<>();

    public Player(Context context, int x, int y) {
        // 1. set up the initial position of the Enemy
        this.xPosition = x;
        this.yPosition = y;

        // 2. Set the default image - all enemies have same image
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        // 3. Set the default hitbox - all enemies have same hitbox
        this.hitbox = new Rect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.image.getWidth(),
                this.yPosition + this.image.getHeight()
        );
    }
    public void setBulletxPos(int bulletxPos) {
        this.playerbulletxPos = this.xPosition;
    }

    public int getBulletyPOs() {
        return playerbulletyPOs;
    }

    public void setBulletyPOs(int bulletyPOs) {
        this.playerbulletyPOs = this.yPosition;
    }

    public void updatePlayerHitbox()
    {
        this.hitbox.left=this.xPosition;
        this.hitbox.top=this.yPosition;
        this.hitbox.right=this.xPosition+this.image.getWidth();
        this.hitbox.bottom=this.yPosition+this.image.getHeight();
    }

    public void spawnBullet()
    {

        //Rect bullet = new Rect(this.playerbulletxPos,this.playerbulletyPOs+);
        Rect bullet=new Rect(this.xPosition,this.yPosition+this.image.getHeight()/2,this.xPosition+BULLET_WIDTH,this.yPosition+this.image.getHeight()/2+BULLET_WIDTH);
        this.playerbullets.add(bullet);
    }


    // GETTER AND SETTER METHODS

    public int getBULLET_WIDTH() {
        return BULLET_WIDTH;
    }

    public ArrayList<Rect> getBullets() {
        return playerbullets;
    }

    public void setBullets(ArrayList<Rect> bullets) {
        this.playerbullets = bullets;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rect hitbox) {
        this.hitbox = hitbox;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }


}
