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

    public Item(String name, Texture icon, int quantity, Vector2 position) {
        this.name = name;
        this.icon = icon;
        this.sprite = new Sprite(icon);
        this.quantity = quantity;
        this.position = position;
        offsetFlag = false;
    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    public void action() {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                // swing pickaxe
                if (Player.inventory[ItemSelector.selectedSlot] != null) {
                    if (Player.inventory[ItemSelector.selectedSlot].name == "pickaxe") {
                        PlayerAnim.selectedAnimation = 6;
                        PlayerAnim.swingPickaxeAnimation.setPlayMode(Animation.PlayMode.LOOP);
                        if (Player.isFlipped && PlayerAnim.selectedAnimation == 6) {
                            Player.drawOffset.x = -16;
                            offsetFlag = true;
                        }
                    }
                }


            }

            if (PlayerAnim.selectedAnimation != 6 && offsetFlag) {
                Player.drawOffset.x = 0;
                offsetFlag = false;
            }

    }

    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
    }

}
