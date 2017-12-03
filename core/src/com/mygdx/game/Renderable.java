package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Nick on 2017-11-29.
 */

public abstract class Renderable {
    public float x, y, width, height;
    Texture texture;

    public Renderable(float x, float y, float width, float height, Texture texture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }
    public Renderable(float x, float y, Texture texture){
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.texture = texture;
    }
    public void drawSelf(B_Ball game){
        game.batch.draw(this.texture, this.x,this.y,this.width,this.height);
    }
    public void dispose(){
        //this.texture.dispose();
    }
}
