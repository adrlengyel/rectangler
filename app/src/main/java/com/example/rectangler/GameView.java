package com.example.rectangler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageView;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPaused;
    private int screenSizeX, screenSizeY;
    public static float screenXRatio, screenYRatio;
    private Background bgStart, bgNext;
    private Player player;
    private Paint paint;

    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);

        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        screenYRatio = 1920f / screenSizeY;
        screenXRatio = 1080f / screenSizeX;

        bgStart = new Background(screenSizeX, screenSizeY, getResources());
        bgNext = new Background(screenSizeX, screenSizeY, getResources());

        bgStart.y = 0;
        bgNext.y = screenSizeY;

        player = new Player(screenSizeX, getResources());

        paint = new Paint();

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

        bgStart.y -= 4;// * screenYRatio;
        bgNext.y -= 4;// * screenYRatio;

        if(bgStart.y + bgStart.background.getHeight() < 0){
            bgStart.y = screenSizeY;
        }

        if(bgNext.y + bgNext.background.getHeight() < 0){
            bgNext.y = screenSizeY;
        }

        if(player.movingLeft){
            player.x -= 10 * screenXRatio;
        } else if (player.movingRight) {
            player.x += 10 * screenXRatio;
        }

        if(player.x < 0){
            player.x = 0;
        }

        if(player.x > screenSizeX - player.playerWidth){
            player.x = screenSizeX - player.playerWidth;
        }

    }

    private void draw(){

        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(bgStart.background, bgStart.x, bgStart.y, paint);
            canvas.drawBitmap(bgNext.background, bgNext.x, bgNext.y, paint);

            canvas.drawBitmap(player.getPlayer(), player.x, player.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void sleep(){

        try{
            Thread.sleep(7);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenSizeX / 2){
                    player.movingLeft = true;
                    player.movingRight = false;
                }
                else if(event.getX() > screenSizeX / 2){
                    player.movingRight = true;
                    player.movingLeft = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                player.movingRight = false;
                player.movingLeft = false;
                break;
        }

        return true;
    }
}
