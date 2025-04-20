package com.farmgame.farmgame.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rock {
    public Texture rock;
    public Sprite sprite;
    Vector2 position;
    public Rectangle boundingBox;
    public float health;

    public Rock(Vector2 position) {
        rock = new Texture("objects/rock2.png");
        sprite = new Sprite(rock);
        this.position = position;
        this.health = 3f;
    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x,position.y,sprite.getWidth() ,sprite.getHeight());
    }

    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);


    }
}
