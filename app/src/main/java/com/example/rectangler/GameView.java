package com.example.rectangler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPaused, GameOver = false;
    public int screenSizeX, screenSizeY;
    public static float screenXRatio, screenYRatio;
    private Background bgStart, bgNext;
    private Player player;
    private Paint paint;
    private List<Bullet> bullets;
    private Enemy[] enemies;
    private int score = 0;

    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);

        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        screenYRatio = 1920f / (float)screenSizeY;
        screenXRatio = 1080f / (float)screenSizeX;

        if(screenYRatio > 1){
            screenYRatio = 1f;
            screenXRatio = 1f;
        }

        bgStart = new Background(screenSizeX, screenSizeY, getResources());
        bgNext = new Background(screenSizeX, screenSizeY, getResources());

        bgStart.y = 0;
        bgNext.y = screenSizeY;

        player = new Player(screenSizeX, getResources(), this);

        paint = new Paint();

        bullets = new ArrayList<Bullet>();
        enemies = new Enemy[9];

        for(int i = 0; i < 9; i++){

            Enemy enemy = new Enemy(getResources());
            enemies[i] = enemy;

        }
    }

    @Override
    public void run() {
        while (!isPaused){
            update();
            draw();
            sleep();
        }
    }

    public void resume(){

        isPaused = false;
        thread = new Thread(this);
        thread.start();

    }

    public void pause(){

        try{
            isPaused = true;
            thread.join();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void update(){

        bgStart.y -= (int) (4.0 * screenYRatio);
        bgNext.y -= (int) (4.0 * screenYRatio);

        if(bgStart.y + bgStart.background.getHeight() < 0){
            bgStart.y = screenSizeY;
        }

        if(bgNext.y + bgNext.background.getHeight() < 0){
            bgNext.y = screenSizeY;
        }

        if(player.movingLeft){
            player.x -= (int) (20.0 * screenXRatio);
        } else if (player.movingRight) {
            player.x += (int) (20.0 * screenXRatio);
        }

        if(player.x < 0){
            player.x = 0;
        }

        if(player.x > screenSizeX - player.playerWidth){
            player.x = screenSizeX - player.playerWidth;
        }

        List<Bullet> bulletsToRemove = new ArrayList<Bullet>();

        for(Bullet bullet : bullets){
            if(bullet.y > screenSizeY){
                bulletsToRemove.add(bullet);
            }

            bullet.y -= (int) (20.0 * screenYRatio);

            for(Enemy enemy : enemies){

                if(Rect.intersects(enemy.getCollision(), bullet.getCollision())){
                    bullet.y = screenSizeY - 5000;
                    enemy.y = -enemy.enemyHeight - screenSizeY - 500;
                    enemy.isDead = true;
                    score++;
                    return;
                }

            }

        }

        for(Bullet bullet : bulletsToRemove){
            if(bullet.y > screenSizeY){
                bullets.remove(bullet);
            }
        }

        for(Enemy enemy : enemies){

            if(enemy.y < -enemy.enemyHeight - screenSizeY){

                Random random = new Random();

                enemy.speed = (random.nextInt(3) + 4) / screenYRatio;
                enemy.y = -enemy.enemyHeight - random.nextInt(120);
                enemy.x = random.nextInt(screenSizeX);
                if ((enemy.x + enemy.enemyWidth) <= enemy.enemyWidth) enemy.x = enemy.x + enemy.enemyWidth;
                else if((enemy.x + enemy.enemyWidth) >= screenSizeX) enemy.x = screenSizeX - enemy.enemyWidth;
            }

            enemy.y += (int) (enemy.speed / screenYRatio);

            if(Rect.intersects(enemy.getCollision(), player.getCollision())){
                GameOver = true;
                return;
            }

            if(enemy.y >= screenSizeY){
                GameOver = true;
                return;
            }
        }
    }

    private void draw(){

        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(bgStart.background, bgStart.x, bgStart.y, paint);
            canvas.drawBitmap(bgNext.background, bgNext.x, bgNext.y, paint);

            for(Enemy enemy : enemies){
                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);
            }

            if(GameOver){

                ((Activity) getContext()).finish();
                Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.putExtra("score", score);
                getContext().startActivity(intent);

            }

            canvas.drawBitmap(player.getPlayer(), player.x, player.y, paint);

            for(Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void sleep(){

        try{
            Thread.sleep((long)(2));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void doAction(MotionEvent event){

        if(event.getX() < screenSizeX / 2 - player.playerWidth){
            player.movingLeft = true;
            player.movingRight = false;
        }
        else if(event.getX() > screenSizeX / 2 + player.playerWidth){
            player.movingRight = true;
            player.movingLeft = false;
        }

        if(event.getX() >= ((screenSizeX / 2) - player.playerWidth) - 10
                &&  event.getX() <= ((screenSizeX / 2) + player.playerWidth) + 10){
            player.shots++;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_POINTER_DOWN:
                return true;
            case MotionEvent.ACTION_DOWN:
                doAction(event);
                break;
            case MotionEvent.ACTION_UP:
                player.movingRight = false;
                player.movingLeft = false;
                break;
        }

        return true;
    }

    public void newBullet() {

        Bullet bullet = new Bullet(getResources());
        bullet.x = (int) ((player.x + (player.playerWidth / 2)) - (30 / screenXRatio));
        bullet.y = (player.y - player.playerHeight) - (20 / screenSizeY);
        bullets.add(bullet);

    }
}
