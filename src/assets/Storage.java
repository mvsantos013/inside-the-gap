package itgap.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Storage {
    public static TextureAtlas atlas;
    public static TextureRegion splashLogoTex, titleTex, playButtonTex, musicOnTex, musicOffTex, ladderTex, rateTex,
            adsTex, blockTex, wallTex, wallGapTex, tapLeftTex, tapRightTex, gameOverTex, recTex, retryTex, newRecordTex,
            backGroundTex, deadBlockTex, gamesButtonTex, visitButtonTex, homeButtonTex, untouchableIconTex, untouchableTitleTex;

    public static void loadStorage(){
        atlas = new TextureAtlas("Texture.atlas");

        splashLogoTex = atlas.findRegion("KmiLogo");
        titleTex = atlas.findRegion("021_Title");
        playButtonTex = atlas.findRegion("022_PlayButton");
        musicOnTex = atlas.findRegion("023_BtnMusicOn");
        musicOffTex = atlas.findRegion("024_BtnMusicOff");
        ladderTex = atlas.findRegion("025_ladder");
        rateTex = atlas.findRegion("026_rate");
        adsTex = atlas.findRegion("027_ads");
        blockTex = atlas.findRegion("031_Block");
        wallTex = atlas.findRegion("032_Wall");
        wallGapTex = atlas.findRegion("033_WallGap");
        tapLeftTex = atlas.findRegion("034_tapLeft");
        tapRightTex = atlas.findRegion("035_tapRight");
        gameOverTex = atlas.findRegion("041_gameover");
        recTex = atlas.findRegion("042_rec");
        retryTex = atlas.findRegion("043_retry");
        newRecordTex = atlas.findRegion("newRecord");
        backGroundTex = atlas.findRegion("backGround");
        deadBlockTex = atlas.findRegion("deadBlock");
        gamesButtonTex = atlas.findRegion("gamesButton");
        visitButtonTex = atlas.findRegion("visitButton");
        homeButtonTex = atlas.findRegion("homeButton");
        untouchableIconTex = atlas.findRegion("untouchableIcon");
        untouchableTitleTex = atlas.findRegion("untouchableTitle");

        splashLogoTex.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static void disposeStorage(){
        atlas.dispose();
    }
}
