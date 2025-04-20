package com.farmgame.farmgame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.farmgame.farmgame.GameObjects.Rock;
import com.farmgame.farmgame.HUD.HUDStage;
import com.farmgame.farmgame.collisions.Collisions;
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.FarmGame;
import com.farmgame.farmgame.items.Item;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final FarmGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Texture testMapTex;
    private Sprite testMapSprite;
    public ArrayList<Rock> rockList = new ArrayList<>();
    public ArrayList<Item> itemList = new ArrayList<>();

    private Player player;
    private Rock rock;
    private boolean cameraInitialized = false;
    private HUDStage hudStage;
    private Collisions collisions;
    public Item pickaxe;

    public GameScreen(FarmGame game) {
        this.game = game;
        this.batch = game.batch;

        player = new Player(80);
        rockList.add(new Rock(new Vector2(200, 200)));
        rockList.add(new Rock(new Vector2(250, 200)));
        rockList.add(new Rock(new Vector2(200, 250)));
        rockList.add(new Rock(new Vector2(200, 300)));

        testMapTex = new Texture(Gdx.files.internal("map/background1-big-wall.png"));
        testMapTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        testMapSprite = new Sprite(testMapTex);

        // hud
        hudStage = new HUDStage();

        // camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 180, camera);
        camera.position.set(player.position.x, player.position.y, 0);
        Gdx.graphics.setWindowedMode(1280, 720);

        //items
        itemList.add(new Item("pickaxe", new Texture(Gdx.files.internal("items/pickaxe-item.png")), 1, new Vector2(200,100)));
//        player.inventory[0] = pickaxe;

        //collisions
        collisions = new Collisions(player, hudStage, rockList, itemList);

        //input for hud
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hudStage);
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // Smooth follow camera
        if (!cameraInitialized) {
            camera.position.set(player.position.x + 8, player.position.y + 8, 0);
            cameraInitialized = true;
        }

        float lerp = 15f;
        Vector2 target = new Vector2(player.position.x + 8, player.position.y + 8);
        camera.position.x += (target.x - camera.position.x) * lerp * delta;
        camera.position.y += (target.y - camera.position.y) * lerp * delta;

        camera.update();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);


        batch.begin();
        testMapSprite.draw(batch);

        //draw rocks
        for (Rock rock : rockList) {
            rock.draw(batch, delta);
        }

        // draw items
        for (Item item : itemList) {
            item.draw(batch, delta);
        }
        player.draw(batch, delta);
        batch.end();

        //debug hitbox
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (player.hitBox != null) {
            shapeRenderer.rect(player.hitBox.x, player.hitBox.y, player.hitBox.width, player.hitBox.height);
        }
        shapeRenderer.end();

        // draw HUD
        hudStage.act(delta);
        hudStage.draw();

        collisions.rockCollision();
        collisions.rockPickaxeCollision(delta);
        collisions.itemCollision();
        removeRocks();
        handleFullscreenToggle();
        pauseGame();
    }

    private void handleFullscreenToggle() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
    }

    private void pauseGame() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this, viewport));
        }
    }

    private void removeRocks() {
            rockList.removeIf(rock -> rock.health <= 0);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hudStage);
        hudStage.itemSelector.redraw();
        Gdx.input.setInputProcessor(multiplexer);

        cameraInitialized = false;

    }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }

    @Override
    public void dispose() {
        testMapTex.dispose();
        hudStage.dispose();
    }
}


