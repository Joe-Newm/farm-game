package com.farmgame.farmgame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wood extends Item {
    public Sprite sprite;

    public Wood(Vector2 position, int quantity) {
        super(position, quantity);
        this.name = "wood";
        this.icon = new Texture(Gdx.files.internal("items/wood.png"));
        this.sprite = new Sprite(icon);
        this.quantity = quantity;
        this.position = position;
        offsetFlag = false;
    }

    @Override
    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
    }
}
