package itgap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import itgap.assets.Storage;
import itgap.main.ItGap;
import itgap.objects.Wall;

public class PlayScreen implements Screen, InputProcessor {
    private final ItGap screen;
    private boolean block_move_left, block_move_right;
    private int leftPointer, rightPointer;
    private static int bars_movement_Y;
    private static final int walls_count = 5;
    public static final int walls_spacing = 250;
    public static final int walls_height = 10;
    private boolean GameNotStarted, NotGameOver, soundPlayOneTime;
    private Polygon blockPolygon;
    private Array<Wall> walls;
    private Sprite tapLeft, tapRight, block, deadBlock;
    public static Sprite wallBar, wallGap;
    public static int score;
    private float position_to_score;
    public static boolean newrecord;
    private Vector3 touchPos;
    private float leveling;
    private static int countDisplayAd = 0;

    public PlayScreen(final ItGap screen){
        this.screen = screen;
        this.tapLeft = new Sprite(Storage.tapLeftTex);
        this.tapRight = new Sprite(Storage.tapRightTex);
        this.block = new Sprite(Storage.blockTex);
        this.deadBlock = new Sprite(Storage.deadBlockTex);
        wallBar = new Sprite(Storage.wallTex);
        wallGap = new Sprite(Storage.wallGapTex);

        this.walls = new Array<Wall>();
        for(int i = 0; i < walls_count; i++){
            walls.add(new Wall());
        }
        blockPolygon = new Polygon(new float[]{0,0,block.getWidth(),0,block.getHeight(),block.getWidth(),0,block.getHeight()});
        this.touchPos = new Vector3();
    }

    @Override
    public void show() {
        block.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        block.setRotation(45f);
        block.setPosition(0.5f * (screen.camera.viewportWidth - block.getWidth()), 0.25f * screen.camera.viewportHeight);
        blockPolygon.setOrigin(0.5f * block.getWidth(), 0.5f * block.getHeight());
        blockPolygon.setRotation(45f);
        blockPolygon.setPosition(block.getX(), block.getY());
        deadBlock.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        deadBlock.setRotation(45f);
        tapLeft.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tapLeft.setPosition(0.15f * screen.camera.viewportWidth - 0.5f * tapLeft.getWidth(), 0.3f * screen.camera.viewportHeight);
        tapRight.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tapRight.setPosition(0.85f * screen.camera.viewportWidth - 0.5f * tapRight.getWidth(), 0.3f * screen.camera.viewportHeight);
        wallBar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        wallGap.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        MenuScreen.musicON.setPosition(0.93f * screen.camera.viewportWidth - 0.5f * MenuScreen.musicON.getWidth(),
                0.94f * screen.camera.viewportHeight - block.getY() + 0.2f * screen.camera.viewportHeight);
        MenuScreen.musicOnRec.setPosition(MenuScreen.musicON.getX(), MenuScreen.musicON.getY());
        MenuScreen.musicOFF.setPosition(0.93f * screen.camera.viewportWidth - 0.5f * MenuScreen.musicON.getWidth(),
                0.94f * screen.camera.viewportHeight - block.getY() + 0.2f * screen.camera.viewportHeight);
        MenuScreen.musicOFFRec.setPosition(MenuScreen.musicOFF.getX(), MenuScreen.musicOFF.getY());

        Wall.gap_opening_height = 100;
        for(int i = 0; i < walls_count; i++){
            walls.get(i).setThePosition(i*(walls_spacing + walls_height), screen.camera.viewportHeight);
        }


        GameNotStarted = true;
        NotGameOver = true;
        soundPlayOneTime = true;
        block_move_left = false;
        block_move_right = false;
        bars_movement_Y = -140;
        score = 0;
        position_to_score = 0.6f *screen.camera.viewportHeight;
        newrecord = false;
        leveling = 2;
        ItGap.high_score = ItGap.settings.getInteger("high_score", 0);
        screen.camera.position.y = block.getY() + 0.2f * screen.camera.viewportHeight;
        ItGap.backGround.setPosition(0f, -30*1.414f);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(NotGameOver) {
            if (GameNotStarted) {
                screen.camera.update();
                screen.batch.setProjectionMatrix(screen.camera.combined);


                screen.batch.begin();
                ItGap.backGround.draw(screen.batch);
                for (Wall wall : walls) {
                    wallBar.setPosition(wall.getPosRightWall().x, wall.getPosRightWall().y);
                    wallBar.draw(screen.batch);
                    wallBar.setPosition(wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                    wallBar.draw(screen.batch);
                    wallGap.setPosition(wall.getPosWallGap().x, wall.getPosWallGap().y);
                    wallGap.draw(screen.batch);
                }
                block.draw(screen.batch);
                tapLeft.draw(screen.batch);
                tapRight.draw(screen.batch);
                ItGap.glyphlayout.setText(ItGap.font, "0");
                ItGap.font.draw(screen.batch, ItGap.glyphlayout, 0.06f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width, 0.93f * screen.camera.viewportHeight);
                if (ItGap.music_on)
                    MenuScreen.musicON.draw(screen.batch);
                else
                    MenuScreen.musicOFF.draw(screen.batch);
                screen.batch.end();

                if (Gdx.input.justTouched()) {
                    GameNotStarted = false;
                }
            } else {
                handleInput();
                blockUpdate(delta);
                wallsUpdate(delta);

                screen.camera.update();
                screen.batch.setProjectionMatrix(screen.camera.combined);
                screen.batch.begin();
                ItGap.backGround.draw(screen.batch);
                for (Wall wall : walls) {
                    wallBar.setPosition(wall.getPosRightWall().x, wall.getPosRightWall().y);
                    wallBar.draw(screen.batch);
                    wallBar.setPosition(wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                    wallBar.draw(screen.batch);
                    wallGap.setPosition(wall.getPosWallGap().x, wall.getPosWallGap().y);
                    wallGap.draw(screen.batch);
                    wallGap.setPosition(wall.getPosWallGapUp().x, wall.getPosWallGapUp().y);
                    wallGap.draw(screen.batch);
                }
                block.draw(screen.batch);
                ItGap.glyphlayout.setText(ItGap.font, String.valueOf(score));
                ItGap.font.draw(screen.batch, ItGap.glyphlayout, 0.06f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width, 0.93f * screen.camera.viewportHeight);
                if (ItGap.music_on)
                    MenuScreen.musicON.draw(screen.batch);
                else
                    MenuScreen.musicOFF.draw(screen.batch);
                screen.batch.end();

                for (Wall wall : walls) {
                    if(score <= 12) {
                        if (Wall.Collided(blockPolygon, wall.getRightPolygon(), wall.getLeftPolygon(), wall.getGapPolygon())) {
                            if (score > ItGap.settings.getInteger("high_score")) {
                                newrecord = true;
                                ItGap.settings.putInteger("high_score", score);
                                ItGap.settings.flush();
                                screen.playServices.submitScore(score);
                            }
                            countDisplayAd += 1;
                            NotGameOver = false;
                            break;
                        }
                    }
                    else{
                        if (Wall.Collided2(blockPolygon, wall.getRightPolygon(), wall.getLeftPolygon(), wall.getGapPolygon(), wall.getGapUpPolygon())) {
                            if (score > ItGap.settings.getInteger("high_score")) {
                                newrecord = true;
                                ItGap.settings.putInteger("high_score", score);
                                ItGap.settings.flush();
                                screen.playServices.submitScore(score);
                            }
                            countDisplayAd += 1;
                            NotGameOver = false;
                            break;
                        }
                    }
                }
            }
        }
        else{
            deadBlock.setPosition(block.getX(), block.getY());
            screen.batch.begin();
            ItGap.backGround.draw(screen.batch);
            for (Wall wall : walls) {
                wallBar.setPosition(wall.getPosRightWall().x, wall.getPosRightWall().y);
                wallBar.draw(screen.batch);
                wallBar.setPosition(wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                wallBar.draw(screen.batch);
                wallGap.setPosition(wall.getPosWallGap().x, wall.getPosWallGap().y);
                wallGap.draw(screen.batch);
                wallGap.setPosition(wall.getPosWallGapUp().x, wall.getPosWallGapUp().y);
                wallGap.draw(screen.batch);
            }
            deadBlock.draw(screen.batch);
            ItGap.glyphlayout.setText(ItGap.font, String.valueOf(score));
            ItGap.font.draw(screen.batch, ItGap.glyphlayout, 0.06f * screen.camera.viewportWidth - 0.5f * ItGap.glyphlayout.width, 0.93f * screen.camera.viewportHeight);
            if (ItGap.music_on)
                MenuScreen.musicON.draw(screen.batch);
            else
                MenuScreen.musicOFF.draw(screen.batch);
            screen.batch.end();
            if(soundPlayOneTime){
                soundPlayOneTime = false;
                if(ItGap.music_on)
                    ItGap.loseSound.play();

                if(countDisplayAd == 6){
                    countDisplayAd = 0;
                    if(screen.adsController.isWifiConnected() == 1 || screen.adsController.isWifiConnected() == -1) {
                        Timer .schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                screen.adsController.showInterstitialAd(new Runnable() {
                                    @Override
                                    public void run() {
                                        screen.setScreen(screen.gameOverScreen);
                                    }
                                });
                            }
                        }, 1.4f);
                    }
                    else{
                        Timer .schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                screen.setScreen(screen.gameOverScreen);
                            }
                        }, 1.4f);
                    }

                }
                else {
                    Timer .schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            screen.setScreen(screen.gameOverScreen);
                        }
                    }, 1.4f);
                }
            }

        }
    }

    public void blockUpdate(float delta){
        if(block_move_left)
            block.translateX(-3.5f);
        if(block_move_right)
            block.translateX(3.5f);
        blockPolygon.setPosition(block.getX(), block.getY());

        position_to_score += bars_movement_Y*delta;
        if(position_to_score <= block.getY() + 0.4f*block.getHeight()){
            score += 1;
            if(ItGap.music_on)
                ItGap.scoreSound.play(0.5f);
            position_to_score += walls_spacing + walls_height;
            switch (score) {
                case(3):{
                    bars_movement_Y = -145;
                    break;
                }
                case(18):{
                    bars_movement_Y = -150;
                    break;
                }
                case(28):{
                    bars_movement_Y = -155;
                    break;
                }
                case(38):{
                    bars_movement_Y = -160;
                    break;
                }
            }
        }

        if(block.getX() <= 5)
            block.setX(5);
        if(block.getX() >= 445)
            block.setX(445);

    }

    public void wallsUpdate(float delta){
        for(Wall wall: walls){
            wall.getPosRightWall().add(0, bars_movement_Y * delta);
            wall.getPosLeftWall().add(0, bars_movement_Y * delta);
            wall.getPosWallGap().add(0, bars_movement_Y * delta);
            wall.getPosWallGapUp().add(0, bars_movement_Y * delta);
            wall.getRightPolygon().setPosition(wall.getPosRightWall().x, wall.getPosRightWall().y);
            wall.getLeftPolygon().setPosition(wall.getPosLeftWall().x, wall.getPosLeftWall().y);
            wall.getGapPolygon().setPosition(wall.getPosWallGap().x, wall.getPosWallGap().y);
            wall.getGapUpPolygon().setPosition(wall.getPosWallGapUp().x, wall.getPosWallGapUp().y);

            if(screen.camera.position.y - 0.1f * screen.camera.viewportHeight> wall.getPosRightWall().y + wallBar.getWidth()) {
                if (leveling % 2 == 0) {
                    switch(Wall.gap_opening_height) {
                        case (100): {
                            Wall.gap_opening_height -= 10;
                            break;
                        }
                        case (90): {
                            Wall.gap_opening_height -= 5;
                            break;
                        }
                        case (85): {
                            Wall.gap_opening_height -= 5;
                            break;
                        }
                    }
                }
                if (leveling == 32)
                    Wall.gap_opening_height -= 5;
                leveling += 1;
                wall.reposition(wall.getPosRightWall().y + (walls_height + walls_spacing) * walls_count);
            }
        }
    }

    public void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            block.translateX(-3);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            block.translateX(+3);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < 0.5f*Gdx.graphics.getWidth()){
            block_move_left = true;
            leftPointer = pointer;
        }
        if(screenX > 0.5f*Gdx.graphics.getWidth()){
            block_move_right = true;
            rightPointer = pointer;
        }
        touchPos.set(screenX, screenY, 0);
        ItGap.viewport.unproject(touchPos);
        if(MenuScreen.musicOnRec.contains(touchPos.x, touchPos.y)){
            MenuScreen.musicON.setScale(0.9f, 0.9f);
            MenuScreen.musicOFF.setScale(0.9f, 0.9f);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == leftPointer)
            block_move_left = false;
        if(pointer == rightPointer)
            block_move_right = false;
        touchPos.set(screenX, screenY, 0);
        ItGap.viewport.unproject(touchPos);
        if(MenuScreen.musicOnRec.contains(touchPos.x, touchPos.y)) {
            ItGap.countPressMusic++;
            if (ItGap.countPressMusic % 2 != 0)
                ItGap.music_on = false;
            else
                ItGap.music_on = true;
            ItGap.soundButtonClick.play(0.4f);
        }
        MenuScreen.musicON.setScale(1f, 1f);
        MenuScreen.musicOFF.setScale(1f, 1f);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}