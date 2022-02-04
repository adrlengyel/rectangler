package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {

    boolean movingLeft, movingRight;
    int x, y, playerWidth, playerHeight;
    Bitmap player;

    public Player(int screenX, Resources resources) {

        movingLeft = false;
        movingRight = false;

        player = BitmapFactory.decodeResource(resources, R.drawable.player);
        playerWidth = player.getWidth();
        playerHeight = player.getHeight();

        playerWidth *= (int) GameView.screenXRatio;
        playerHeight *= (int) GameView.screenYRatio;

        player = Bitmap.createScaledBitmap(player, playerWidth, playerHeight, false);

        x = (screenX / 2) - (playerWidth / 2);
        y = (int) (GameView.screenYRatio * (screenX + 250));

    }

    public Bitmap getPlayer(){
        return player;
    }

}
