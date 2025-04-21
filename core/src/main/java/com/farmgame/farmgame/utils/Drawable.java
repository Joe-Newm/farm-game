package com.farmgame.farmgame.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Drawable {
    int getDrawLayer();
    void draw(SpriteBatch batch, float delta);
    float getY();
}
