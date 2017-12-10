package com.mygdx.gameV2;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nick on 2017-12-09.
 */

public interface Pressable {
    public void activate();
    public void deactivate();
    public boolean checkPressed(Vector3 touchPos);
}
