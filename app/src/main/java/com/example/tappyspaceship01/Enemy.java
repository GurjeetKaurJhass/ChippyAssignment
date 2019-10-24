package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Enemy {

    // PROPERTIES:
    // Image
    // Hitbox
    private Bitmap image;
    private Rect hitbox;

    private int bulletxPos;
    private int bulletyPOs;

    public int getBulletxPos() {

        return bulletxPos;
    }

    public void setBulletxPos(int bulletxPos) {
        this.bulletxPos = this.xPosition;
    }

    public int getBulletyPOs() {
        return bulletyPOs;
    }

    public void setBulletyPOs(int bulletyPOs) {
        this.bulletyPOs = this.yPosition;
    }

    private int xPosition;
    private int yPosition;
    private final int BULLET_WIDTH=15;
    private ArrayList <Rect> bullets=new ArrayList<>();
    public Enemy(Context context, int x, int y) {
        // 1. set up the initial position of the Enemy
        this.xPosition = x;
        this.yPosition = y;

        // 2. Set the default image - all enemies have same image
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.monstereye);

        // 3. Set the default hitbox - all enemies have same hitbox
        this.hitbox = new Rect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.image.getWidth(),
                this.yPosition + this.image.getHeight()
        );
    }

    public void updateEnemyHitbox()
    {
        this.hitbox.left=this.xPosition;
        this.hitbox.top=this.yPosition;
        this.hitbox.right=this.xPosition+image.getWidth();
        this.hitbox.bottom=this.yPosition+image.getHeight();
    }


    public void spawnBullet()
    {
        Rect bullet=new Rect(this.xPosition,this.yPosition+this.image.getHeight()/2,this.xPosition+BULLET_WIDTH,this.yPosition+this.image.getHeight()/2+BULLET_WIDTH);
        this.bullets.add(bullet);
    }

    // Getter and setters
    // Autogenerate this by doing Right Click --> Generate --> Getter&Setter

    public int getBULLET_WIDTH() {
        return BULLET_WIDTH;
    }

    public ArrayList<Rect> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Rect> bullets) {
        this.bullets = bullets;
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
