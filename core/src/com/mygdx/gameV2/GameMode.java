package com.mygdx.gameV2;

/**
 * Created by Nick on 2017-11-29.
 */

public interface GameMode {
    Wall[] get_Walls();
    Ball get_Ball();
    void update();
    int getScore();
    boolean getLose();
    void touch_Update();
    void lose();
    void start();
    boolean getStarted();
}
