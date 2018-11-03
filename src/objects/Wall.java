package itgap.objects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import itgap.screens.PlayScreen;

public class Wall {
    private static final int fluctuation = 330;
    private static final int space_gap = 80;
    public static int gap_opening_height = 100;
    private static final int lowest_side_Opening = 25;
    private Vector2 posLeftWall, posRightWall, posWallGap, posWallGapUp;
    private Polygon leftPolygon, rightPolygon, gapPolygon, gapUpPolygon;
    private Random rand;
    static boolean rightCollision;
    static boolean leftCollision;
    static boolean gapCollision;
    static boolean gapUpCollision;
    private static float check_rand_reposition = 300;

    public Wall(){
        rand = new Random();

        posRightWall = new Vector2();
        posLeftWall = new Vector2();
        posWallGap = new Vector2();
        posWallGapUp = new Vector2();

        rightPolygon = new Polygon(new float[]{0,0, PlayScreen.wallBar.getWidth(),0, PlayScreen.wallBar.getWidth(), PlayScreen.wallBar.getHeight(),0, PlayScreen.wallBar.getHeight()});
        leftPolygon = new Polygon(new float[]{0,0, PlayScreen.wallBar.getWidth(),0, PlayScreen.wallBar.getWidth(), PlayScreen.wallBar.getHeight(),0, PlayScreen.wallBar.getHeight()});
        gapPolygon = new Polygon(new float[]{0,0, PlayScreen.wallGap.getWidth(),0, PlayScreen.wallGap.getWidth(), PlayScreen.wallGap.getHeight(),0, PlayScreen.wallGap.getHeight()});
        gapUpPolygon = new Polygon(new float[]{0,0, PlayScreen.wallGap.getWidth(),0, PlayScreen.wallGap.getWidth(), PlayScreen.wallGap.getHeight(),0, PlayScreen.wallGap.getHeight()});
    }

    public void setThePosition(float y, float viewportHeight){
        rand = new Random();
        posRightWall.set(rand.nextInt(fluctuation) + space_gap + lowest_side_Opening, y);
        posLeftWall.set(posRightWall.x - space_gap - PlayScreen.wallBar.getWidth(), y);
        posWallGap.set(posRightWall.x - space_gap, y - gap_opening_height);
        posWallGapUp.set(posRightWall.x - space_gap, y + gap_opening_height);
        posRightWall.add(0, 0.6f * viewportHeight);
        posLeftWall.add(0, 0.6f * viewportHeight);
        posWallGap.add(0, 0.6f * viewportHeight);
        posWallGapUp.add(0, 0.6f * viewportHeight + 14*(PlayScreen.walls_spacing + PlayScreen.walls_height));
        rightPolygon.setPosition(posRightWall.x, posRightWall.y);
        leftPolygon.setPosition(posLeftWall.x, posLeftWall.y);
        gapPolygon.setPosition(posWallGap.x, posWallGap.y);
        gapUpPolygon.setPosition(posWallGapUp.x, posWallGapUp.y);
    }

    public Vector2 getPosRightWall(){
        return posRightWall;
    }

    public Vector2 getPosLeftWall(){
        return posLeftWall;
    }

    public Vector2 getPosWallGap(){
        return posWallGap;
    }

    public Vector2 getPosWallGapUp(){
        return posWallGapUp;
    }

    public void reposition(float y){
        posRightWall.set(rand.nextInt(fluctuation) + space_gap + lowest_side_Opening, y);
        posLeftWall.set(posRightWall.x - space_gap - PlayScreen.wallBar.getWidth(), y);
        posWallGap.set(posRightWall.x - space_gap, y - gap_opening_height);
        if(PlayScreen.score >= 10)
            posWallGapUp.set(posRightWall.x - space_gap, y + gap_opening_height);

        while(Math.abs(check_rand_reposition - posRightWall.x) < 100) {
            posRightWall.set(rand.nextInt(fluctuation) + space_gap + lowest_side_Opening, y);
            posLeftWall.set(posRightWall.x - space_gap - PlayScreen.wallBar.getWidth(), y);
            posWallGap.set(posRightWall.x - space_gap, y - gap_opening_height);
            if(PlayScreen.score >= 10)
                posWallGapUp.set(posRightWall.x - space_gap, y + gap_opening_height);
        }
        rightPolygon.setPosition(posRightWall.x, posRightWall.y);
        leftPolygon.setPosition(posLeftWall.x, posLeftWall.y);
        gapPolygon.setPosition(posWallGap.x, posWallGap.y);
        if(PlayScreen.score >= 10)
            gapUpPolygon.setPosition(posWallGapUp.x, posWallGapUp.y);

        check_rand_reposition = posRightWall.x;
    }

    public Polygon getRightPolygon(){
        return rightPolygon;
    }
    public Polygon getLeftPolygon(){
        return leftPolygon;
    }

    public Polygon getGapPolygon(){
        return gapPolygon;
    }

    public Polygon getGapUpPolygon(){
        return gapUpPolygon;
    }

    public static boolean Collided(Polygon blockPolygon, Polygon rightPolygon, Polygon leftPolygon, Polygon gapPolygon){
        rightCollision = Intersector.overlapConvexPolygons(blockPolygon, rightPolygon);
        leftCollision = Intersector.overlapConvexPolygons(blockPolygon, leftPolygon);
        gapCollision = Intersector.overlapConvexPolygons(blockPolygon, gapPolygon);
        return rightCollision || leftCollision || gapCollision;
    }

    public static boolean Collided2(Polygon blockPolygon, Polygon rightPolygon, Polygon leftPolygon, Polygon gapPolygon, Polygon gapUpPolygon){
        rightCollision = Intersector.overlapConvexPolygons(blockPolygon, rightPolygon);
        leftCollision = Intersector.overlapConvexPolygons(blockPolygon, leftPolygon);
        gapCollision = Intersector.overlapConvexPolygons(blockPolygon, gapPolygon);
        gapUpCollision = Intersector.overlapConvexPolygons(blockPolygon, gapUpPolygon);
        return rightCollision || leftCollision || gapCollision || gapUpCollision;
    }
}