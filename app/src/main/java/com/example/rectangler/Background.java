package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    int x, y;
    Bitmap background;

    Background(int screenSizeX, int screenSizeY, Resources resources){

        background = BitmapFactory.decodeResource(resources, R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);

    }

}
