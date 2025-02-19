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
   public double rotSpeed;

   public Weapon[] weps;

   public int team;

   public Entity target;

   private GameManager owner;
   
   private Pose2D pose;
   private Map map;
   
   private Path path;
   
   private Tile nextTile;
   
   private Vector2D tilePos;

   private Vector2D lastTargetTile;
   
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
   
   public Entity(EntityTemplate et, Map map, Pose2D pose, int team, GameManager owner){
      this.maxHealth = et.maxHealth();
      this.health = et.maxHealth();
      this.s = new Sprite(et.s());
      this.armor = et.armor();
      this.speed = et.speed();
      this.rotSpeed = et.rotSpeed();
      this.team = team;
      this.map = map;
      this.pose = pose;
      this.weps = new Weapon[et.wt().length];
      for (int i = 0; i<et.wt().length; i++) {
         this.weps[i] = new Weapon(et.wt()[i], this);
      }
      this.owner = owner;
      s.p = this.getPose();
   }
   private void genTargetPoint(){
      if(target != null&&target.pose.pos.add(this.pose.pos).magnitude()<weps[0].range*1.2){
         targetPoint = target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range*0.9));
      }else{
         targetPoint = new Vector2D(nextTile.x,nextTile.y).multiply(map.squareSize).add(new Vector2D(map.squareSize/2,map.squareSize/2));
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
   private void pathfinding(){
      if(path!=null&&path.end.equals(map.getTile(tilePos))){
         path = null;
      }
      if(path == null){
         if(target == null){
            path = map.generatePath(tilePos, new Vector2D((int)(Math.random()*map.size),(int)(Math.random()*map.size)));
         }else{
            if(target.pose.pos.add(this.pose.pos).magnitude()<weps[0].range*1.2){
               targetPoint = target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range));
               path = map.generatePath(tilePos, tilePos);
            }else{
               path = map.generatePath(tilePos, target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range)).div(map.squareSize).toInt());
            }
         }
         if(path == null){
            System.out.println(tilePos);
            System.out.println(target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range)).div(map.squareSize).toInt());
            return;
         }
         getNextTile();
      }else{
         if(target!=null){
            if(!target.tilePos.equals(lastTargetTile)){
               path = map.generatePath(tilePos, target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range)).div(map.squareSize).toInt());
               getNextTile();
               if(path == null){
                  System.out.println(tilePos);
                  System.out.println(target.pose.pos.add(pose.pos.add(target.pose.pos.n()).normal().multiply(weps[0].range)).div(map.squareSize).toInt());
                  return;
               }
            }
         }
         lastTargetTile = target.tilePos;
      }
      getNextTile();
   }
   public void update(){
      tilePos = pose.pos.div(map.squareSize).toInt();
      pathfinding();
      if(targetPoint.add(pose.pos.n()).magnitude()>map.squareSize/5){
         double rotation =targetPoint.add(pose.pos.n()).convert().normal().D - pose.rot.D;
         pose.rot = pose.rot.addD(Math.min(rotation, Math.abs(rotSpeed))*Math.signum(rotSpeed)).normal().mult(speed*(1/map.getTile(tilePos).movement));
         if(targetPoint.add(pose.pos.n()).magnitude()<map.squareSize/2||Math.abs(rotation)<0.03){
            pose.pos = pose.pos.add(pose.rot.convert());
         }
      }else{
         if(target!=null){
            double rotation = target.pose.pos.add(pose.pos.n()).convert().normal().D - pose.rot.D;
            pose.rot = pose.rot.addD(Math.min(rotation, Math.abs(rotSpeed))*Math.signum(rotSpeed));
         }
      }
      s.p = this.pose;
      for(int w = 0; w<weps.length; w++){
         weps[w].s.p = weps[w].p.add(pose);
      }
      //rot = rot.addD(-0.01);
      //pos = pos.add(rot.convert());
   }
}