package com.farmgame.farmgame.collisions;

import com.farmgame.farmgame.GameObjects.Rock;
import com.farmgame.farmgame.HUD.HUDStage;
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.items.Item;

import java.util.ArrayList;

public class Collisions {
    public Player player;
    public ArrayList<Rock> rockList;
    public ArrayList<Item> itemList;
    public HUDStage hudStage;

    public Collisions(Player player, HUDStage hudStage, ArrayList<Rock> rockList, ArrayList<Item> itemList) {
        this.player = player;
        this.rockList = rockList;
        this.itemList = itemList;
        this.hudStage = hudStage;
    }

    //player collides with item
    public void itemCollision() {
        for (Item item : itemList)
            if (player.boundingBox.overlaps(item.boundingBox)) {
                for (int i = 0; i < player.inventory.length; i++) {
                    if (player.inventory[i] == null) {
                        player.inventory[i] = item;
                        itemList.remove(item); // Remove from world
                        hudStage.itemSelector.redraw();
                        return; // Exit after picking up one item
                    }
                }
            }
    }

    public void rockCollision() {
        float oldX = player.prevX;
        float oldY = player.prevY;

        for (Rock rock : rockList) {

            player.boundingBox.setPosition(player.position.x, oldY);
            if (player.boundingBox.overlaps(rock.boundingBox)) {
                player.position.x = oldX;
            }

            player.boundingBox.setPosition(player.position.x, player.position.y);
            if (player.boundingBox.overlaps(rock.boundingBox)) {
                player.position.y = oldY;
            }

            player.boundingBox.setPosition(player.position.x, player.position.y);
        }
    }

    public void rockPickaxeCollision(float delta) {
        if (player.hitBox != null) {
            for (Rock rock : rockList) {
                if (player.hitBox.overlaps(rock.boundingBox)) {
                    rock.health -= delta;
                    System.out.println(rock.health);
                }
            }
        }
    }

}
