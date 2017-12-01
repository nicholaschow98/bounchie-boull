package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nick on 2017-11-29.
 */

public class Button extends Renderable{
    public boolean active;
    public Button(float x, float y, float width, float height, Texture texture){
        super(x, y, width, height, texture);
        active = true;
    }
    public Button(int x, int y, Texture texture){
        super(x, y, texture);
        active = true;
    }

    public boolean checkPressed(Vector3 touchPos){
        if((touchPos.x>=this.x&&touchPos.x<=this.x+this.width)&&(touchPos.y>=this.y&&touchPos.y<=this.y+this.height)){
            return true;
        }
        return false;
    }
}
