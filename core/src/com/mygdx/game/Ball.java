package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Nick on 2017-11-29.
 */

public class Ball extends Game_Object {
    //int gravity;

    public Ball(float x, float y, float width, float height, float xvel, float yvel, Texture texture){
        super(x, y, width, height,xvel,yvel, texture);
        //this.gravity = gravity;
    }
    public void updatePos(){
        super.updatePos();
        //this.yvel-=gravity;
    }
}
