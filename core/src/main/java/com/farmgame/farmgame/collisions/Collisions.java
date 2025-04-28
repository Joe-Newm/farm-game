package com.farmgame.farmgame.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.farmgame.farmgame.GameObjects.GameObject;
import com.farmgame.farmgame.GameObjects.Rock;
import com.farmgame.farmgame.GameObjects.Tree;
import com.farmgame.farmgame.HUD.HUDStage;
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.items.Item;
import com.farmgame.farmgame.items.Stone;
import com.farmgame.farmgame.items.Wood;

import java.util.ArrayList;

public class Collisions {
    public Player player;
    public ArrayList<GameObject> gameObjectList;
    public ArrayList<Item> itemList;
    public HUDStage hudStage;

    public Collisions(Player player, HUDStage hudStage, ArrayList<GameObject> gameObjectList, ArrayList<Item> itemList) {
        this.player = player;
        this.gameObjectList = gameObjectList;
        this.itemList = itemList;
        this.hudStage = hudStage;
    }

    //player collides with item
    public void itemCollision() {
        for (Item item : itemList)
            if (player.boundingBox.overlaps(item.boundingBox)) {
                for (int i = 0; i < player.inventory.length; i++) {
                    if (player.inventory[i] != null) {
                        if (item.name == player.inventory[i].name) {
                            player.inventory[i].quantity += item.quantity;
                            itemList.remove(item);
                            hudStage.itemSelector.redraw();
                            hudStage.playerInventory.drawInventory();
                            return;
                        }
                    }
                    else if (player.inventory[i] == null) {
                        player.inventory[i] = item;
                        itemList.remove(item); // Remove from world
                        hudStage.itemSelector.redraw();
                        hudStage.playerInventory.drawInventory();
                        return; // Exit after picking up one item
                    }
                }
            }
    }

    public void objectCollision() {
        float oldX = player.prevX;
        float oldY = player.prevY;

        for (GameObject obj : gameObjectList) {
                player.boundingBox.setPosition(player.position.x, oldY);
                if (player.boundingBox.overlaps(obj.boundingBox)) {
                    player.position.x = oldX;
                }

                player.boundingBox.setPosition(player.position.x, player.position.y);
                if (player.boundingBox.overlaps(obj.boundingBox)) {
                    player.position.y = oldY;
                }

                player.boundingBox.setPosition(player.position.x, player.position.y);

        }
    }

    public void rockPickaxeCollision(float delta) {
        if (player.hitBox != null) {
            for (GameObject obj : gameObjectList) {
                if (obj instanceof Rock) {
                    Rock rock = (Rock) obj;
                    if (player.hitBox.overlaps(rock.boundingBox)) {
                        rock.health -= delta;
                        System.out.println(rock.health);
                    }
                }
            }
        }
    }

    public void treeAxeCollision(float delta) {
        if (player.hitBox != null) {
            for (GameObject obj : gameObjectList) {
                if (obj instanceof Tree) {
                    Tree tree = (Tree) obj;
                    if (player.hitBox.overlaps(tree.boundingBox)) {
                        tree.health -= delta;
                        System.out.println(tree.health);
                    }
                }
            }
        }
    }

    public void treeRemove() {
        ArrayList<GameObject> treeRemoveList = new ArrayList<>();
        for (GameObject obj : gameObjectList) {
            if (obj instanceof Tree) {
                Tree tree = (Tree) obj;
                if (tree.health <= 0) {
                    treeRemoveList.add(tree);
                    itemList.add(new Wood(new Vector2(tree.position.x, tree.position.y), 1));
                }
            }
        }
        gameObjectList.removeAll(treeRemoveList);
    }

    public void rockRemove() {
        ArrayList<GameObject> rockRemoveList = new ArrayList<>();
        for (GameObject obj : gameObjectList) {
            if (obj instanceof Rock) {
                Rock rock = (Rock) obj;
                if (rock.health <= 0) {
                    rockRemoveList.add(rock);
                    itemList.add(new Stone(new Vector2(rock.position.x, rock.position.y), 1));
                }
            }
        }
        gameObjectList.removeAll(rockRemoveList);
    }

}
