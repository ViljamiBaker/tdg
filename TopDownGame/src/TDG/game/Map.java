package TDG.game;

import java.util.ArrayList;

import TDG.util.Vector2D;

public class Map{
   public Tile[][] tiles;
   // [htilex][htiley][disttilex][disttiley]distance
   public double[][][][] hTilesDist;
   public int size;
   public double squareSize;
   public double hMult;
   public Map(int size, double squareSize, double hMult){
      tiles = new Tile[size][size];
      this.size = size;
      this.squareSize = squareSize;
      for(int x = 0; x<size; x++){
         for(int y = 0; y<size; y++){
            tiles[x][y] = new Tile(10, Math.random()*2.5+1, x, y);
         }
      }
      this.hMult = hMult;
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
   
   private int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{-1,1},{1,-1}};

   public Tile[] getAdjTiles(Tile t){
      int x = (int)t.x;
      int y = (int)t.y;
      ArrayList<Tile> adjtiles = new ArrayList<>(1);
      for(int i = 0; i<8; i++){
         if(getTile(x+dirs[i][0],y+dirs[i][1])!=null){
            adjtiles.add(getTile(x+dirs[i][0],y+dirs[i][1]));
         }
      }
      return adjtiles.toArray(new Tile[adjtiles.size()]);
   }
   public Node[] getAdjNodes(Node n){
      int x = (int)n.t.x;
      int y = (int)n.t.y;
      Tile[] adj = getAdjTiles(new Tile(0,0,x,y));
      Node[] adjtiles = new Node[adj.length];
      for(int i = 0; i<adj.length;i++){
         adjtiles[i] = new Node(adj[i]);
      }
      return adjtiles;
   }
   
   public Vector2D convInto(Vector2D in){
      return in.div(squareSize);
   }
   
   public Vector2D convOut(Vector2D in){
      return in.multiply(squareSize);
   }
   
   public Path generatePath(Vector2D start, Vector2D end){
      if(getTile(start)==null||getTile(end)==null){
         return null;
      }
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
            }while(!(lastNode==null));
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
               adjNodes[i].G = best.G + (Math.abs(adjNodes[i].t.x-best.t.x)+Math.abs(adjNodes[i].t.y-best.t.y)) * best.t.movement;
               adjNodes[i].H = ((adjNodes[i].t.x-end.x) + (adjNodes[i].t.y-end.y))*hMult;//getClosestHTileDist(adjNodes[i].t,getTile(end));
               adjNodes[i].F = adjNodes[i].G+adjNodes[i].H;
               adjNodes[i].parentNode = best;
            }else{
               double newG = best.G + Math.abs(adjNodes[i].t.x-best.t.x)+Math.abs(adjNodes[i].t.y-best.t.y) * best.t.movement;
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
      return null;
   }
}