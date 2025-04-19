package com.farmgame.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.FarmGame;
import com.farmgame.farmgame.items.Item;

public class GameScreen implements Screen {
    private final FarmGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Texture testMapTex;
    private Sprite testMapSprite;

    private Player player;
    private Rock rock;
    private boolean cameraInitialized = false;
    private HUDStage hudStage;

    public GameScreen(FarmGame game) {
        this.game = game;
        this.batch = game.batch;

        player = new Player(80);
        rock = new Rock(new Vector2(200, 200));

        testMapTex = new Texture(Gdx.files.internal("map/background1-big-wall.png"));
        testMapTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        testMapSprite = new Sprite(testMapTex);

        // camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 180, camera);
        camera.position.set(player.position.x, player.position.y, 0);
        Gdx.graphics.setWindowedMode(1280, 720);

        // hud
        hudStage = new HUDStage();
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
            camera.position.set(player.position.x, player.position.y, 0);
            cameraInitialized = true;
        }

        float lerp = 15f;
        Vector2 target = new Vector2(player.position.x, player.position.y);
        camera.position.x += (target.x - camera.position.x) * lerp * delta;
        camera.position.y += (target.y - camera.position.y) * lerp * delta;

        camera.update();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);


        batch.begin();
        testMapSprite.draw(batch);
        rock.draw(batch, delta);
        player.draw(batch, delta);
        batch.end();

        //debug hitbox
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (Player.hitBox != null) {
            shapeRenderer.rect(Player.hitBox.x, Player.hitBox.y, Player.hitBox.width, Player.hitBox.height);
        }
        shapeRenderer.end();

        // draw HUD
        hudStage.act(delta);
        hudStage.draw();

        rockCollision();
        handleFullscreenToggle();
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

    private void rockCollision() {
        float oldX = player.prevX;
        float oldY = player.prevY;

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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void show() { }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }

    @Override
    public void dispose() {
        testMapTex.dispose();
        hudStage.dispose();
    }
}


