package com.farmgame.farmgame.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.farmgame.farmgame.utils.Drawable;

public abstract class GameObject implements Drawable {
    public Vector2 position;
    public float health;
    public Rectangle boundingBox = new Rectangle(); // prevents null


    public GameObject(Vector2 position) {
        this.position = position;
    }

    public abstract void update(float delta);


    public abstract void draw(SpriteBatch batch, float delta);

}
