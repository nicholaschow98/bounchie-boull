package com.mygdx.gameV2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nick on 2017-12-03.
 */

public interface BallSkin{
    public Texture getTexture();

    public String getDescription();

    public int getID();

    public String getName();

    public void onCollide(Ball ball);

    public void onDeath(Ball ball);

    public void onPickUp(Ball ball);

    public void onTouch(Ball ball);

    public void skinRender(SpriteBatch batch);

    public void playSound(float vol);
}
