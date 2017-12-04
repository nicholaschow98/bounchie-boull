package com.mygdx.gameV2;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Nick on 2017-11-29.
 */

public class Wall extends Game_Object {
    public Wall(float x, float y, float width, float height, float xvel, float yvel, Texture texture){
        super(x, y, width, height,xvel,yvel, texture);
    }
    public void updatePos(){
        super.updatePos();
        //this.x +=this.xvel;
        //this.y += this.yvel;

    }
}
