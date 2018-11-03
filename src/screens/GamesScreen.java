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

public class GamesScreen implements Screen, InputProcessor{

    private final ItGap screen;

    private Sprite visitButton, homeButton, untouchableIcon, untouchableTitle;
    private Rectangle visitRec, homeRec;
    private Vector3 touchPos;

    public GamesScreen(final ItGap screen){
        this.screen = screen;

        this.visitButton = new Sprite(Storage.visitButtonTex);
        this.homeButton = new Sprite(Storage.homeButtonTex);
        this.untouchableIcon = new Sprite(Storage.untouchableIconTex);
        this.untouchableTitle = new Sprite(Storage.untouchableTitleTex);

        this.visitRec = new Rectangle();
        this.homeRec = new Rectangle();

        this.touchPos = new Vector3();
    }

    @Override
    public void show() {
        ItGap.backGround.setPosition(0,0);

        visitButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        visitButton.setPosition(0.25f * screen.camera.viewportWidth - 0.5f * visitButton.getWidth(), 0.55f * screen.camera.viewportHeight);
        visitRec.set(0, 0, visitButton.getWidth(), visitButton.getHeight());
        visitRec.setPosition(visitButton.getX(), visitButton.getY());

        homeButton.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        homeButton.setPosition(0.5f * screen.camera.viewportWidth - 0.5f * homeButton.getWidth(), 0.11f * screen.camera.viewportHeight);
        homeRec.set(0, 0, homeButton.getWidth(), homeButton.getHeight());
        homeRec.setPosition(homeButton.getX(), homeButton.getY());

        untouchableIcon.setPosition(0.25f * screen.camera.viewportWidth - 0.5f * untouchableIcon.getWidth(), 0.65f * screen.camera.viewportHeight);
        untouchableTitle.setPosition(0.25f * screen.camera.viewportWidth - 0.5f * untouchableTitle.getWidth(), 0.89f * screen.camera.viewportHeight);

        screen.camera.position.y = 0.5f* screen.camera.viewportHeight;

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
        visitButton.draw(screen.batch);
        homeButton.draw(screen.batch);
        untouchableIcon.draw(screen.batch);
        untouchableTitle.draw(screen.batch);
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

        if(visitRec.contains(touchPos.x, touchPos.y)){
            visitButton.setScale(0.9f, 0.9f);
            visitButton.setScale(0.9f, 0.9f);
        }

        if(homeRec.contains(touchPos.x, touchPos.y)){
            homeButton.setScale(0.9f, 0.9f);
            homeButton.setScale(0.9f, 0.9f);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        ItGap.viewport.unproject(touchPos);

        if(visitRec.contains(touchPos.x, touchPos.y)){
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
            screen.playServices.visitUntouchable();
        }

        if(homeRec.contains(touchPos.x, touchPos.y)){
            screen.adsController.hideBannerAd();
            if(ItGap.music_on)
                ItGap.soundButtonClick.play(0.4f);
            screen.setScreen(screen.menuScreen);
        }

        visitButton.setScale(1f, 1f);
        homeButton.setScale(1f, 1f);
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
