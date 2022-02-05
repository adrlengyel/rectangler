package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {

    int x, y;
    Bitmap bullet;

    Bullet(Resources resources){

        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        bullet = Bitmap.createScaledBitmap(bullet, bullet.getWidth(), bullet.getHeight(), false);

    }

}
