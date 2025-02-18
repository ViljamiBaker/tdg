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
   public double speed;

   public Weapon[] weps;

   public int team;

   public Entity target;
   
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
      this.maxHealth = et.maxHealth();
      this.health = et.maxHealth();
      this.s = new Sprite(et.s());
      this.armor = et.armor();
      this.speed = et.speed();
      this.team = team;
      this.map = map;
      this.pose = pose;
      this.weps = new Weapon[et.wt().length];
      for (int i = 0; i<et.wt().length; i++) {
         this.weps[i] = new Weapon(et.wt()[i], this);
      }
      s.p = this.getPose();
   }
   private void genTargetPoint(){
      if(target == null){
         targetPoint = new Vector2D(nextTile.x,nextTile.y).multiply(map.squareSize).add(new Vector2D(map.squareSize/2,map.squareSize/2));
      }else{
         if(target.pose.pos.add(this.pose.pos).magnitude()<weps[0].range*1.2){
            targetPoint = target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range));
         }else{
            targetPoint = new Vector2D(nextTile.x,nextTile.y).multiply(map.squareSize).add(new Vector2D(map.squareSize/2,map.squareSize/2));
         }
      }
   }
   private void getNextTile(){
      for(int i = 0; i<path.lastTiles.length-1; i++){
         if(path.lastTiles[i+1].equals(map.getTile(tilePos))){
            nextTile = path.lastTiles[i];
            //System.out.println("nextTile " + nextTile);
            genTargetPoint();
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
         if(target == null){
            path = map.generatePath(tilePos, new Vector2D((int)(Math.random()*map.size),(int)(Math.random()*map.size)));
         }else{
            path = map.generatePath(tilePos, target.pose.pos.div(map.size));
         }
         if(path == null)return;
         getNextTile();
      }
      if(targetPoint == null){
         genTargetPoint();
         //System.out.println("targetPoint: " + targetPoint);
      }
      if(nextTile.equals(map.getTile(tilePos))&&pose.pos.add(targetPoint.n()).magnitude()<map.squareSize){
         getNextTile();
      }
      pose.rot = targetPoint.add(pose.pos.n()).convert().normal().mult(speed).mult(1/map.getTile(tilePos).movement);
      pose.pos = pose.pos.add(pose.rot.convert());
      s.p = this.pose;
      for(int w = 0; w<weps.length; w++){
         weps[w].update();
         weps[w].s.p = weps[w].p;
      }
      //rot = rot.addD(-0.01);
      //pos = pos.add(rot.convert());
   }
}