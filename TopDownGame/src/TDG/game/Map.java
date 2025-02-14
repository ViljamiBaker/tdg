package TDG.game;

import java.util.ArrayList;

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
   public Node[] getAdjNodes(Node n){
      int x = (int)n.t.x;
      int y = (int)n.t.y;
      Tile[] adj = new Tile[8];
      adj[0] = getTile(x+1,y);
      adj[1] = getTile(x-1,y);
      adj[2] = getTile(x,y+1);
      adj[3] = getTile(x,y-1);
      adj[4] = getTile(x+1,y+1);
      adj[5] = getTile(x-1,y-1);
      adj[6] = getTile(x-1,y+1);
      adj[7] = getTile(x+1,y-1);
      ArrayList<Node> adjtiles = new ArrayList<>(8);
      for(Tile tile: adj){
         if(tile!=null){
            adjtiles.add(new Node(0,0,tile, null));
         }
      }
      return adjtiles.toArray(new Node[adjtiles.size()]);
   }
   
   public Vector2D convInto(Vector2D in){
      return in.div(squareSize);
   }
   
   public Vector2D convOut(Vector2D in){
      return in.multiply(squareSize);
   }
   
   public Path generatePath(Vector2D start, Vector2D end){
      Node startNode = new Node(0,Math.pow(start.x-end.x,2)+Math.pow(start.y-end.y,2),getTile(start),null);
      Path newPath = new Path(getTile(start), getTile(end));
      ArrayList<Node> openList = new ArrayList<>(size*size*2);
      openList.add(startNode);
      ArrayList<Node> closedList = new ArrayList<>(size*size*2);
      boolean found = false;
      do{
         int bestIndex = -1;
         double bestF = 100000000;
         for(int i = 0; i<openList.size(); i++){
            if(openList.get(i).F<bestF){
               bestIndex = i;
               bestF = openList.get(i).F;
            }
         }
         if(bestIndex==-1){
            System.out.println("WTF");
         }
         Node best = openList.get(bestIndex);
         openList.remove(best);
         closedList.add(best);
         if(best.t.equals(getTile(end))){
            ArrayList<Tile> pathTiles = new ArrayList<>();
            Node lastNode = best;
            pathTiles.add(lastNode.t);
            do{
               pathTiles.add(lastNode.t);
               lastNode = lastNode.parentNode;
            }while(!lastNode.equals(new Node(getTile(start))));
            pathTiles.add(getTile(start));
            newPath.lastTiles = pathTiles.toArray(new Tile[0]);
            return newPath;
         }
         Node[] adjNodes = getAdjNodes(best);
         for(int i = 0; i< adjNodes.length; i++){
            if(closedList.contains(adjNodes[i])||adjNodes[i].t.movement<0.0){
               continue;
            }
            if(!openList.contains(adjNodes[i])){
               openList.add(adjNodes[i]);
               adjNodes[i].G = Math.abs(adjNodes[i].t.x-start.x)+Math.abs(adjNodes[i].t.y-start.y);
               adjNodes[i].H = Math.pow(adjNodes[i].t.x-end.x,2)+Math.pow(adjNodes[i].t.y-end.y,2);
               adjNodes[i].F = adjNodes[i].G+adjNodes[i].H;
               adjNodes[i].parentNode = best;
            }else{
               double newG = Math.abs(adjNodes[i].t.x-start.x)+Math.abs(adjNodes[i].t.y-start.y);
               if(adjNodes[i].G<newG){
                  continue;
               }
               adjNodes[i].G = newG;
               adjNodes[i].F = adjNodes[i].G+adjNodes[i].H;
               adjNodes[i].parentNode = best;
            }
         }
         found = closedList.contains(new Node(getTile(end)));
      }while(!found||openList.size()!=0);
      System.out.println("NO PATH FOUND :(((");
      return newPath;
   }
}