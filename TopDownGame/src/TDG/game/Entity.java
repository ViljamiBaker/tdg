package TDG.game;

import TDG.rendering.Sprite;
import TDG.util.Vector2D;
import TDG.util.VectorMD;
import TDG.util.Pose2D;

public class Entity{
   public Sprite s;
   public double maxHealth;
   public double health;
   public double armor;
   public double range;
   public double damage;
   public double aoe;
   public double ap;
   public double speed;

   public int team;
   
   private Pose2D pose;
   private Map map;
   
   private Path path;
   
   private Tile nextTile;
   
   private Vector2D tilePos;
   
   private Vector2D targetPoint;
   
   public Pose2D getPose(){
      return pose;
   }
   
   public Path getPath(){
      return path;
   }
   
   public Vector2D[] getInfo(){
      return new Vector2D[] {tilePos,targetPoint};
   }
   
   public Entity(EntityTemplate et, Map map, Pose2D pose, int team){
      this.maxHealth = et.maxHealth;
      this.health = et.maxHealth;
      this.s = new Sprite(et.s);
      this.armor = et.armor;
      this.range = et.range;
      this.damage = et.damage;
      this.aoe = et.aoe;
      this.ap = et.ap;
      this.speed = et.speed;
      this.team = team;
      this.map = map;
      this.pose = pose;
      s.p = this.getPose();
   }
   private void getNextTile(){
      for(int i = 0; i<path.lastTiles.length-1; i++){
         if(path.lastTiles[i+1].equals(map.getTile(tilePos))){
            nextTile = path.lastTiles[i];
            targetPoint = null;
            //System.out.println("nextTile " + nextTile);
            break;
         }
      }
   }
   public void update(){
      tilePos = new Vector2D((int)(pose.pos.x/map.squareSize),(int)(pose.pos.y/map.squareSize));
      if(path!=null&&path.end.equals(map.getTile(tilePos))){
         path = null;
      }
      if(path == null){
         path = map.generatePath(tilePos, new Vector2D((int)(Math.random()*map.size),(int)(Math.random()*map.size)));
         System.out.println("path.end: " + path.end);
         getNextTile();
      }
      if(nextTile.equals(map.getTile(tilePos))&&pose.pos.add(targetPoint.n()).magnitude()<map.squareSize){
         getNextTile();
      }
      if(targetPoint == null){
         targetPoint = new Vector2D(nextTile.x,nextTile.y).multiply(map.squareSize).add(new Vector2D(map.squareSize/2,map.squareSize/2));//new Vector2D(Math.random()*map.squareSize,Math.random()*map.squareSize));
         //System.out.println("targetPoint: " + targetPoint);
      }
      pose.rot = targetPoint.add(pose.pos.n()).convert().normal().mult(speed).mult(1/map.getTile(tilePos).movement);
      pose.pos = pose.pos.add(pose.rot.convert());
      //rot = rot.addD(-0.01);
      //pos = pos.add(rot.convert());
   }
}