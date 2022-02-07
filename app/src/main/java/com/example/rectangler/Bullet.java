package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {

    int x, y, bulletWidth, bulletHeight;
    Bitmap bullet;

    Bullet(Resources resources){

        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        bulletWidth = bullet.getWidth();
        bulletHeight = bullet.getHeight();

        bulletWidth = (int) (bulletWidth * GameView.screenXRatio);
        bulletHeight = (int) (bulletHeight * GameView.screenYRatio);

        bullet = Bitmap.createScaledBitmap(bullet, bullet.getWidth(), bullet.getHeight(), false);

    }

}
