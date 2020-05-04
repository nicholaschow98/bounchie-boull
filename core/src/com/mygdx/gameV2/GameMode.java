package com.mygdx.gameV2;

/**
 * Created by Nick on 2017-11-29.
 * change to protected abstract class
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

/*
shit to move into GameMode (turn into abstract class)
Ball update logic
Start / Lose logic
Basic Wall generation logic (when to regenerate) - updating, coordinate generation. type generation, size and count logic left to the classes

 */