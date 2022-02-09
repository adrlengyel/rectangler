package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Enemy {

    public boolean isDead = false;
    public int x, y, enemyWidth, enemyHeight, shots = 0;
    public float speed = 5f;
    private Bitmap enemy;

    public Enemy(Resources resources){

        enemy = BitmapFactory.decodeResource(resources, R.drawable.enemy);

        enemyWidth = enemy.getWidth();
        enemyHeight = enemy.getHeight();

        enemyWidth = (int) (enemyWidth * GameView.screenXRatio);
        enemyHeight = (int) (enemyHeight * GameView.screenYRatio);

        enemy = Bitmap.createScaledBitmap(enemy, enemyWidth, enemyHeight, false);

        y = -enemyHeight - 800;
    }

    public Bitmap getEnemy(){

        return enemy;
    }

    public Rect getCollision(){
        return new Rect(x, y, x + enemyWidth, y + enemyHeight);
    }

}
