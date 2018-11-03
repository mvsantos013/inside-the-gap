package itgap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import itgap.assets.Storage;
import itgap.main.ItGap;

public class MenuScreen implements Screen, InputProcessor {
    private final ItGap screen;

    private Sprite title, playButton, ladder, rate;
    public static Sprite musicON, musicOFF;
    private Rectangle playButtonRec, ladderRec, rateRec;
    public static Rectangle musicOnRec, musicOFFRec;
    private Vector3 touchPos;

    public MenuScreen(final ItGap screen){
        this.screen = screen;

        this.title = new Sprite(Storage.titleTex);
        this.playButton = new Sprite(Storage.playButtonTex);
        this.ladder = new Sprite(Storage.ladderTex);
        this.rate = new Sprite(Storage.rateTex);
        musicON = new Sprite(Storage.musicOnTex);
        musicOFF = new Sprite(Storage.musicOffTex);

        this.playButtonRec = new Rectangle();
        this.ladderRec = new Rectangle();
        this.rateRec = new Rectangle();
        musicOnRec = new Rectangle();
        musicOFFRec = new Rectangle();

        this.touchPos = new Vector3();
    }

    @Override
    public void show() {
        title.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * title.getWidth(), 0.65f * screen.camera.viewportHeight);

        playButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playButton.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * playButton.getWidth(), 0.43f * screen.camera.viewportHeight);
        playButtonRec.set(0, 0, playButton.getWidth(), playButton.getHeight());
        playButtonRec.setPosition(playButton.getX(), playButton.getY());

        ladder.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ladder.setPosition(0.3f * screen.camera.viewportWidth - 0.5f * ladder.getWidth(), 0.2f * screen.camera.viewportHeight);
        ladderRec.set(0, 0, ladder.getWidth(), ladder.getHeight());
        ladderRec.setPosition(ladder.getX(), ladder.getY());

        rate.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rate.setPosition(0.7f * screen.camera.viewportWidth - 0.5f * rate.getWidth(), 0.2f * screen.camera.viewportHeight);
        rateRec.set(0, 0, rate.getWidth(), rate.getHeight());
        rateRec.setPosition(rate.getX(), rate.getY());

        musicON.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        musicON.setPosition(0.93f * screen.camera.viewportWidth - 0.5f * musicON.getWidth(), 0.94f * screen.camera.viewportHeight);
        musicOnRec.set(0, 0, musicON.getWidth(), musicON.getHeight());
        musicOnRec.setPosition(musicON.getX(), musicON.getY());

        musicOFF.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        musicOFF.setPosition(0.93f * screen.camera.viewportWidth - 0.5f * musicOFF.getWidth(), 0.94f * screen.camera.viewportHeight);
        musicOFFRec.set(0, 0, musicOFF.getWidth(), musicOFF.getHeight());
        musicOFFRec.setPosition(musicOFF.getX(), musicOFF.getY());

        ItGap.backGround.setPosition(0,0);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screen.camera.update();
        screen.batch.setProjectionMatrix(screen.camera.combined);

        screen.batch.begin();
        ItGap.backGround.draw(screen.batch);
        title.draw(screen.batch);
        playButton.draw(screen.batch);
        ladder.draw(screen.batch);
        rate.draw(screen.batch);
        if(ItGap.music_on)
            musicON.draw(screen.batch);
        else
            musicOFF.draw(screen.batch);
        screen.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        ItGap.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        ItGap.viewport.unproject(touchPos);

        if(playButtonRec.contains(touchPos.x, touchPos.y)){
            playButton.setScale(0.9f, 0.9f);
            playButton.setScale(0.9f, 0.9f);
        }
        if(ladderRec.contains(touchPos.x, touchPos.y)){
            ladder.setScale(0.9f, 0.9f);
            ladder.setScale(0.9f, 0.9f);
        }
        if(rateRec.contains(touchPos.x, touchPos.y)){
            rate.setScale(0.9f, 0.9f);
            rate.setScale(0.9f, 0.9f);
        }
        if(musicOnRec.contains(touchPos.x, touchPos.y)){
            musicON.setScale(0.9f, 0.9f);
            musicOFF.setScale(0.9f, 0.9f);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        ItGap.viewport.unproject(touchPos);

        if(playButtonRec.contains(touchPos.x, touchPos.y)){
            screen.setScreen(screen.playScreen);
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
        }
        if(ladderRec.contains(touchPos.x, touchPos.y)){
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
            screen.playServices.showScore();
        }
        if(rateRec.contains(touchPos.x, touchPos.y)){
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
            screen.playServices.rateGame();
        }
        if(musicOnRec.contains(touchPos.x, touchPos.y)){
            ItGap.countPressMusic ++;
            if(ItGap.countPressMusic%2 != 0)
                ItGap.music_on = false;
            else
                ItGap.music_on = true;
            ItGap.soundButtonClick.play(0.4f);
        }
        playButton.setScale(1f, 1f);
        playButton.setScale(1f, 1f);
        ladder.setScale(1f, 1f);
        ladder.setScale(1f, 1f);
        rate.setScale(1f, 1f);
        rate.setScale(1f, 1f);
        musicON.setScale(1f, 1f);
        musicOFF.setScale(1f, 1f);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}