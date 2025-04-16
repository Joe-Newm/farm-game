package com.farmgame.farmgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.farmgame.farmgame.Player;
import com.farmgame.farmgame.GameObjects.Rock;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FarmGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    public Viewport viewport;
    public Camera camera;
    private Texture testMapTex;
    private Sprite testMapSprite;
    private Rock rock;
    private boolean cameraInitialized = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(80);
        testMapTex = new Texture(Gdx.files.internal("map/background1-big-wall.png"));
        testMapTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        testMapSprite = new Sprite(testMapTex);

        // objects
        rock = new Rock(new Vector2(200,200));

        // camera and viewport stuff


        Gdx.graphics.setWindowedMode(1280, 720);
        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 180,camera);
        camera.position.x = player.position.x;
        camera.position.y = player.position.y;


    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        //camera follows player

        if (!cameraInitialized) {
            camera.position.set(player.position.x, player.position.y, 0);
            cameraInitialized = true;
        }

        float lerp = 15f;
        Vector2 cameraTarget = new Vector2(player.position.x, player.position.y);
        camera.position.x += (cameraTarget.x - camera.position.x) * lerp * delta;
        camera.position.y += (cameraTarget.y - camera.position.y) * lerp * delta;

//        camera.position.x = (float)Math.floor(camera.position.x) + 0.5f;
//        camera.position.y = (float)Math.floor(camera.position.y) + 0.5f;

        camera.update();
        viewport.apply();
        // Apply the camera's view matrix to the batch
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        testMapSprite.draw(batch);
        rock.draw(batch, delta);
        player.draw(batch, delta);
        batch.end();


        //collisions
        rockCollision();

        //fullscreen
        full();
    }

    // fullscreentoggle
    public void full() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                // Go back to windowed mode
                Gdx.graphics.setWindowedMode(1280, 720); // or whatever default you use
            } else {
                // Go fullscreen on the current monitor
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
    }

    // collision rock and player
    public void rockCollision() {
        // Save original position
        float oldX = player.prevX;
        float oldY = player.prevY;

        // --- Check X axis ---
        player.boundingBox.setPosition(player.position.x, oldY);
        if (player.boundingBox.overlaps(rock.boundingBox)) {
            player.position.x = oldX;
        }

        // --- Check Y axis ---
        player.boundingBox.setPosition(player.position.x, player.position.y);
        if (player.boundingBox.overlaps(rock.boundingBox)) {
            player.position.y = oldY;
        }

        // Update bounding box to new valid position
        player.boundingBox.setPosition(player.position.x, player.position.y);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
