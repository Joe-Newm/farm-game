package com.farmgame.farmgame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.farmgame.farmgame.HUD.ItemSelector;
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.entity.PlayerAnim;

public class Item {
    public String name;
    public Texture icon;
    public int quantity;
    public static boolean offsetFlag;

    public Item(String name, Texture icon, int quantity) {
        this.name = name;
        this.icon = icon;
        this.quantity = quantity;
        offsetFlag = false;
    }

    public void action() {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                // swing pickaxe
                if (Player.inventory[ItemSelector.selectedSlot] != null) {
                    if (Player.inventory[ItemSelector.selectedSlot].name == "pickaxe") {
                        PlayerAnim.selectedAnimation = 2;
                        PlayerAnim.swingPickaxeAnimation.setPlayMode(Animation.PlayMode.LOOP);
                        if (Player.isFlipped && PlayerAnim.selectedAnimation == 2) {
                            Player.drawOffset.x = -16;
                            offsetFlag = true;
                        }
                    }
                }


            }

            if (PlayerAnim.selectedAnimation != 2 && offsetFlag) {
                Player.drawOffset.x = 0;
                offsetFlag = false;
            }

    }

}
