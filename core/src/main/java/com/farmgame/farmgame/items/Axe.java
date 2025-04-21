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

public class Axe extends Item{
    public Sprite sprite;
    public Axe(Vector2 position, int quantity) {
        super(position, quantity);
        this.position = position;
        this.quantity = quantity;
        this.name = "axe";
        this.icon = new Texture("items/axe-item.png");
        this.sprite = new Sprite(icon);
        offsetFlag = false;
    }

    @Override
    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void action() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // swing pickaxe
            if (Player.inventory[ItemSelector.selectedSlot] != null) {
                if (Player.inventory[ItemSelector.selectedSlot].name == "axe") {
                    PlayerAnim.selectedAnimation = 7;
                    PlayerAnim.swingAxeAnimation.setPlayMode(Animation.PlayMode.LOOP);
                    if (Player.isFlipped && PlayerAnim.selectedAnimation == 7) {
                        Player.drawOffset.x = -16;
                        offsetFlag = true;
                    }
                }
            }
        }

        if (PlayerAnim.selectedAnimation != 7 && offsetFlag) {
            Player.drawOffset.x = 0;
            offsetFlag = false;
        }

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
    }
}
