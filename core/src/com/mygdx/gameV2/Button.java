package com.mygdx.gameV2;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;

/**
 * Created by Nick on 2017-11-29.
 */

public class Button extends Renderable implements Pressable{
    private boolean active;
    public Button(float x, float y, float width, float height, Texture texture){
        super(x, y, width, height, texture);
        active = true;
    }
    public Button(float x, float y, float width, float height,boolean active, Texture texture){
        super(x, y, width, height, texture);
        this.active = active;
    }
    public Button(float x, float y, Texture texture){
        super(x, y, texture);
        active = true;
    }

    Sound button_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Sound7.wav"));

    public void deactivate(){
        active = false;
    }
    public void activate(){
        active = true;
    }
    public boolean checkPressed(Vector3 touchPos){
        if(active&&(touchPos.x>=this.x&&touchPos.x<=this.x+this.width)&&(touchPos.y>=this.y&&touchPos.y<=this.y+this.height)){
            button_sound.play();
            return true;
        }
        return false;
    }
}
