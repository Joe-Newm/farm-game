package com.farmgame.farmgame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.farmgame.farmgame.HUD.ItemSelector;
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.entity.PlayerAnim;

public class Item {
    public String name;
    public Texture icon;
    public int quantity;
    public static boolean offsetFlag;
    public Vector2 position;
    public Sprite sprite;
    public Rectangle boundingBox;

    public Item(Vector2 position, int quantity) {
        offsetFlag = false;
        this.position = position;
        this.quantity = quantity;
    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    public void action() {

    }

    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
    }

}
