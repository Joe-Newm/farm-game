package com.farmgame.farmgame.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tree extends GameObject {
    public Texture texture;
    public Sprite sprite;

    public Tree(Vector2 position) {
        super(position);
        texture = new Texture("objects/tree.png");
        sprite = new Sprite(texture);
        this.health = 3f;
        this.position = position;

        this.boundingBox = new Rectangle(this.position.x + 16, this.position.y, 16, 16);
    }

    @Override
    public void update(float delta) {
        this.boundingBox.setPosition(position.x + 16, position.y);
    }


    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
    }


}
