package Game.game;

import Game.util.Vector2D;

public class Path{
   public Tile start;
   public Tile[] lastTiles;
   public Tile end;
   double[] movement;
   public Path(Tile start, Tile end){
      this.start = start;
      this.end = end;
   }
}