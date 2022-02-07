package com.example.rectangler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Player {

    public boolean movingLeft, movingRight;
    public int x, y, playerWidth, playerHeight, shots = 0, shotSeparator = 0;
    private Bitmap player, bullet1, bullet2;
    private GameView gameView;

    public Player(int screenX, Resources resources, GameView gameView) {

        this.gameView = gameView;

        movingLeft = false;
        movingRight = false;

        player = BitmapFactory.decodeResource(resources, R.drawable.player);
        playerWidth = player.getWidth();
        playerHeight = player.getHeight();

        playerWidth = (int) (playerWidth * GameView.screenXRatio);
        playerHeight = (int) (playerHeight * GameView.screenYRatio);

        player = Bitmap.createScaledBitmap(player, playerWidth, playerHeight, false);

        bullet1 = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        bullet1 = Bitmap.createScaledBitmap(bullet1, bullet1.getWidth(), bullet1.getHeight(), false);

        bullet2 = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        bullet2 = Bitmap.createScaledBitmap(bullet1, bullet1.getWidth(), bullet1.getHeight(), false);

        x = (screenX / 2) - (playerWidth / 2);
        y = (int) (gameView.screenSizeY - (playerHeight + 35));

    }

    public Bitmap getPlayer(){

        if(shots != 0){

            if(shotSeparator == 0)
            {
                shotSeparator++;
                return bullet1;
            }
            else if(shotSeparator == 1){
                shotSeparator++;
                return bullet2;
            }

            shotSeparator = 0;
            shots--;

            gameView.newBullet();
        }

        return player;
    }

}
