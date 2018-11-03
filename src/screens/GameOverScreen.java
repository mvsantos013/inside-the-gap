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

public class GameOverScreen implements Screen, InputProcessor{
    private final ItGap screen;
    private Sprite gameOverLogo, quadro, retryButton, ladder, rate, gamesButton, newRecord;
    private Rectangle retryRec, ladderRec, rateRec, gamesRec;
    private Vector3 touchPos;

    public GameOverScreen(final ItGap screen){
        this.screen = screen;
        this.gameOverLogo = new Sprite(Storage.gameOverTex);
        this.quadro = new Sprite(Storage.recTex);
        this.retryButton = new Sprite(Storage.retryTex);
        this.ladder = new Sprite(Storage.ladderTex);
        this.rate = new Sprite(Storage.rateTex);
        this.gamesButton = new Sprite(Storage.gamesButtonTex);
        this.newRecord = new Sprite(Storage.newRecordTex);

        this.retryRec = new Rectangle();
        this.ladderRec = new Rectangle();
        this.rateRec = new Rectangle();
        this.gamesRec = new Rectangle();

        this.touchPos = new Vector3();
    }

    @Override
    public void show() {
        gameOverLogo.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameOverLogo.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * gameOverLogo.getWidth(), 0.75f * screen.camera.viewportHeight);

        quadro.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        quadro.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * quadro.getWidth(), 0.5f * screen.camera.viewportHeight - 0.43f * quadro.getHeight());

        retryButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        retryButton.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * retryButton.getWidth(), 0.5f * screen.camera.viewportHeight - 0.4f * quadro.getHeight());
        retryRec.set(0, 0, retryButton.getWidth(), retryButton.getHeight());
        retryRec.setPosition(retryButton.getX(), retryButton.getY());

        ladder.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ladder.setPosition(0.3f * screen.camera.viewportWidth - 0.5f * ladder.getWidth(), 0.24f * screen.camera.viewportHeight);
        ladderRec.set(0, 0, ladder.getWidth(), ladder.getHeight());
        ladderRec.setPosition(ladder.getX(), ladder.getY());

        rate.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rate.setPosition(0.7f * screen.camera.viewportWidth - 0.5f * rate.getWidth(), 0.24f * screen.camera.viewportHeight);
        rateRec.set(0, 0, rate.getWidth(), rate.getHeight());
        rateRec.setPosition(rate.getX(), rate.getY());

        //gamesButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gamesButton.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * gamesButton.getWidth(), 0.1f * screen.camera.viewportHeight);
        gamesRec.set(0, 0, gamesButton.getWidth(), gamesButton.getHeight());
        gamesRec.setPosition(gamesButton.getX(), gamesButton.getY());

        MenuScreen.musicON.setPosition(0.93f * screen.camera.viewportWidth - 0.5f * MenuScreen.musicON.getWidth(),
                0.94f * screen.camera.viewportHeight);
        MenuScreen.musicOnRec.setPosition(MenuScreen.musicON.getX(), MenuScreen.musicON.getY());
        MenuScreen.musicOFF.setPosition(0.93f * screen.camera.viewportWidth - 0.5f * MenuScreen.musicON.getWidth(),
                0.94f * screen.camera.viewportHeight);
        MenuScreen.musicOFFRec.setPosition(MenuScreen.musicOFF.getX(), MenuScreen.musicOFF.getY());

        newRecord.setPosition(0.5f*screen.camera.viewportWidth - 0.38f*newRecord.getWidth(), 0.5f*screen.camera.viewportHeight + 0.35f*quadro.getHeight());

        screen.camera.position.y = 0.5f* screen.camera.viewportHeight;
        ItGap.backGround.setPosition(0,0);

        if(PlayScreen.newrecord && ItGap.high_score != 0) {
            if(ItGap.music_on)
                ItGap.newRecordSound.play();
        }
        Gdx.input.setInputProcessor(this);

        if(screen.adsController.isWifiConnected() == 1 || screen.adsController.isWifiConnected() == -1)
            screen.adsController.showBannerAd();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screen.camera.update();
        screen.batch.setProjectionMatrix(screen.camera.combined);
        screen.batch.begin();
        ItGap.backGround.draw(screen.batch);
        gameOverLogo.draw(screen.batch);
        quadro.draw(screen.batch);
        retryButton.draw(screen.batch);
        ladder.draw(screen.batch);
        rate.draw(screen.batch);
        gamesButton.draw(screen.batch);

        ItGap.glyphlayout.setText(ItGap.textFont, "Score");
        ItGap.textFont.draw(screen.batch, ItGap.glyphlayout, 0.28f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width,
                0.5f * screen.camera.viewportHeight + 0.28f * quadro.getHeight());
        ItGap.glyphlayout.setText(ItGap.textFont, "Best Score");
        ItGap.textFont.draw(screen.batch, ItGap.glyphlayout, 0.72f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width,
                0.5f * screen.camera.viewportHeight + 0.28f * quadro.getHeight());

        ItGap.glyphlayout.setText(ItGap.font2, String.valueOf(PlayScreen.score));
        ItGap.font2.draw(screen.batch, ItGap.glyphlayout, 0.28f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width,
                0.5f * screen.camera.viewportHeight + 0.14f * quadro.getHeight());
        ItGap.glyphlayout.setText(ItGap.font2, String.valueOf(ItGap.settings.getInteger("high_score")));
        ItGap.font2.draw(screen.batch, ItGap.glyphlayout, 0.72f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width,
                0.5f * screen.camera.viewportHeight + 0.14f * quadro.getHeight());

        if(ItGap.music_on)
            MenuScreen.musicON.draw(screen.batch);
        else
            MenuScreen.musicOFF.draw(screen.batch);
        if(PlayScreen.newrecord && ItGap.high_score != 0) {
            newRecord.draw(screen.batch);
        }
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

        if(retryRec.contains(touchPos.x, touchPos.y)){
            retryButton.setScale(0.9f, 0.9f);
            retryButton.setScale(0.9f, 0.9f);
        }
        if(ladderRec.contains(touchPos.x, touchPos.y)){
            ladder.setScale(0.9f, 0.9f);
            ladder.setScale(0.9f, 0.9f);
        }
        if(rateRec.contains(touchPos.x, touchPos.y)){
            rate.setScale(0.9f, 0.9f);
            rate.setScale(0.9f, 0.9f);
        }

        if(gamesRec.contains(touchPos.x, touchPos.y)){
            gamesButton.setScale(0.9f, 0.9f);
            gamesButton.setScale(0.9f, 0.9f);
        }
        if(MenuScreen.musicOnRec.contains(touchPos.x, touchPos.y)){
            MenuScreen.musicON.setScale(0.9f, 0.9f);
            MenuScreen.musicOFF.setScale(0.9f, 0.9f);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        ItGap.viewport.unproject(touchPos);

        if(retryRec.contains(touchPos.x, touchPos.y)){
            screen.adsController.hideBannerAd();
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
            screen.setScreen(screen.playScreen);
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

        if(gamesRec.contains(touchPos.x, touchPos.y)){
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
            screen.setScreen(screen.gamesScreen);
        }

        if(MenuScreen.musicOnRec.contains(touchPos.x, touchPos.y)) {
            ItGap.countPressMusic++;
            if (ItGap.countPressMusic % 2 != 0)
                ItGap.music_on = false;
            else
                ItGap.music_on = true;
            ItGap.soundButtonClick.play(0.4f);
        }
        retryButton.setScale(1f, 1f);
        retryButton.setScale(1f, 1f);
        ladder.setScale(1f, 1f);
        ladder.setScale(1f, 1f);
        rate.setScale(1f, 1f);
        rate.setScale(1f, 1f);
        gamesButton.setScale(1f, 1f);
        gamesButton.setScale(1f, 1f);
        MenuScreen.musicON.setScale(1f, 1f);
        MenuScreen.musicOFF.setScale(1f, 1f);
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