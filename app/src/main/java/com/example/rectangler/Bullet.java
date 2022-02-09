package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {

    public int x, y, bulletWidth, bulletHeight;
    public Bitmap bullet;

    public Bullet(Resources resources){

        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        bulletWidth = bullet.getWidth();
        bulletHeight = bullet.getHeight();

        bulletWidth = (int) (bulletWidth * GameView.screenXRatio);
        bulletHeight = (int) (bulletHeight * GameView.screenYRatio);

        bullet = Bitmap.createScaledBitmap(bullet, bullet.getWidth(), bullet.getHeight(), false);
    }

    public Rect getCollision(){
        return new Rect(x, y, x + bulletWidth, y + bulletHeight);
    }

}
