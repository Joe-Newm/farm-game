package com.farmgame.farmgame.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.farmgame.farmgame.utils.Drawable;

public class Rock extends GameObject {
    public Texture rock;
    public Sprite sprite;

    public Rock(Vector2 position) {
        super(position);
        rock = new Texture("objects/rock2.png");
        sprite = new Sprite(rock);
        this.position = position;
        this.health = 3f;

        this.boundingBox = new Rectangle(position.x,position.y,sprite.getWidth() ,sprite.getHeight());
    }

    public void update(float delta) {
        this.boundingBox.setPosition(position.x, position.y);

    }

    @Override
    public int getDrawLayer() {
        return 1; // Player layer
    }


    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
