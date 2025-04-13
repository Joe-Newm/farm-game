package com.farmgame.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    float speed;
    Texture playTex;
    Sprite sprite;
    Vector2 position;

    public Player(float speed) {
        this.speed = speed;
        this.playTex = new Texture(Gdx.files.internal("player/player.png"));
        this.sprite = new Sprite(playTex);
        sprite.scale(1);
        position = new Vector2( 100  , 100);
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Keys.A)) {
            position.x -= speed;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            position.x += speed;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            position.y += speed;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            position.y -= speed;
        }
    }

    public void draw(SpriteBatch batch) {
        update();
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
