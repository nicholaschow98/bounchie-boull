package com.mygdx.gameV2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nick on 2017-12-03.
 */

public class BasicBallSkin implements BallSkin {
    private Texture ballTexture;

    public BasicBallSkin(Texture ballTexture){
        this.ballTexture = ballTexture;
    }
    public Texture getTexture(){
        return this.ballTexture;
    }

    public void onCollide(Ball ball){
        //nothin
    }

    public void onDeath(Ball ball){
        //nothin
    }

    public void onPickUp(Ball ball){
        //nothin
    }

    public void onTouch(Ball ball){
        //nothin
    }

    public void skinRender(SpriteBatch batch){
        //nothin
    }
}
