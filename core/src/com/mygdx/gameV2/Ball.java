package com.mygdx.gameV2;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Nick on 2017-11-29.
 */

public class Ball extends Game_Object {
    //int gravity;
    BallSkin skin;
    public Ball(float x, float y, float width, float height, float xvel, float yvel, BallSkin skin){
        super(x, y, width, height,xvel,yvel, skin.getTexture());
        this.skin = skin;
    }
    public void updatePos(){
        super.updatePos();
        //this.yvel-=gravity;
    }//trying to  fix

    public void onCollide(){
        if(this.xvel != 0) {
            skin.onCollide(this);
            this.texture = skin.getTexture();
        }
    }


}
