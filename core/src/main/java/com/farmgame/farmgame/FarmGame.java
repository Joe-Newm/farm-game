package com.farmgame.farmgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.farmgame.farmgame.Player;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FarmGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    public Viewport viewport;
    public Camera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(250);

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720,camera);
        Gdx.graphics.setWindowedMode(1280, 720);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.2f, 0.5f, 0.2f, 1f);
        camera.update();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        player.draw(batch, delta);
        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
