package TDG.game;

import java.util.ArrayList;
import java.util.Arrays;

import TDG.util.Vector2D;

public class Map{
   public Tile[][] tiles;
   public int size;
   public double squareSize;
   public Map(int size, double squareSize){
      tiles = new Tile[size][size];
      this.size = size;
      this.squareSize = squareSize;
      for(int x = 0; x<size; x++){
         for(int y = 0; y<size; y++){
            tiles[x][y] = new Tile(10, Math.random()*4+1, x, y);
         }
      }
   }
   public Tile getTile(Vector2D xy){
      return getTile((int)xy.x,(int)xy.y);
   }
   public Tile getTile(int x, int y){
      if(x<0||x>=size||y<0||y>=size){
         return null;
      }
      return tiles[x][y];
   }
   
   public Tile[] getAdjTiles(Tile t){
      int x = (int)t.x;
      int y = (int)t.y;
      Tile[] adj = new Tile[8];
      adj[0] = getTile(x+1,y);
      adj[1] = getTile(x-1,y);
      adj[2] = getTile(x,y+1);
      adj[3] = getTile(x,y-1);
      adj[4] = getTile(x+1,y+1);
      adj[5] = getTile(x-1,y-1);
      adj[6] = getTile(x-1,y+1);
      adj[7] = getTile(x+1,y-1);
      ArrayList<Tile> adjtiles = new ArrayList<>(1);
      for(Tile tile: adj){
         if(tile!=null){
            adjtiles.add(tile);
         }
      }
      return adjtiles.toArray(new Tile[adjtiles.size()]);
   }
   
   public Vector2D convInto(Vector2D in){
      return in.div(squareSize);
   }
   
   public Vector2D convOut(Vector2D in){
      return in.multiply(squareSize);
   }
   
   public Path generatePath(Vector2D start, Vector2D end){
      Tile endTile = getTile(end);
      Path newPath = new Path(getTile(start), getTile(end));
      ArrayList<Tile> tiles = new ArrayList<>();
      tiles.add(getTile(start));
      ArrayList<Double> movement = new ArrayList<>();
      movement.add(0.0);
      ArrayList<Tile> newTiles = new ArrayList<>();
      newTiles.add(getTile(start));
      ArrayList<Tile> lastTiles = new ArrayList<>();
      lastTiles.add(getTile(start));
      boolean found = false;
      do{
         for(int i = 0; i < tiles.size(); i++){
            ArrayList<Tile> oldTiles = new ArrayList<>(newTiles.size());
            for(Tile t: newTiles){
               oldTiles.add(new Tile(t));
            }
            newTiles.clear();
            Tile tile = tiles.get(i);
            if(oldTiles.contains(tile)){
               continue;
            }
            Tile[] adj = getAdjTiles(tile);
            for(Tile a: adj){
               if(a.equals(lastTiles.get(i))||a.movement <= 0.0){// if it is our lt then dont
                  continue;
               }
               if(tiles.contains(a)){// if the tile is already checked see if we need to update it
                  double newMovement = (movement.get(i)+tile.movement)*(Math.pow(tile.x-a.x,2)+Math.pow(tile.y-a.y,2));
                  if(movement.get(tiles.indexOf(a))>newMovement){
                     int index = tiles.indexOf(a);
                     lastTiles.set(index, tile);
                     movement.set(index, newMovement);
                  }
               }else{
                  tiles.add(a);
                  lastTiles.add(tile);
                  newTiles.add(a);
                  movement.add(movement.get(i)+tile.movement);
               }
            }
         }
      }while(!tiles.contains(endTile)||newTiles.size()!=0);
      ArrayList<Tile> pathTiles = new ArrayList<>();
      Tile lastTile = getTile(end);
      pathTiles.add(lastTile);
      if(tiles.contains(endTile)){
         do{
            lastTile = lastTiles.get(tiles.indexOf(lastTile));
            pathTiles.add(lastTile);
         }while(!lastTile.equals(getTile(start)));
      }else{
         System.out.println("NO PATH FOUND :(((");
      }
      newPath.lastTiles = pathTiles.toArray(new Tile[0]);
      return newPath;
   }
}