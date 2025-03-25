package TDG.game;

import java.util.ArrayList;

import TDG.rendering.Sprite;
import TDG.util.Vector2D;
import TDG.util.VectorMD;
import TDG.util.Pose2D;
public class GameManager{
   private ArrayList<Entity> entities = new ArrayList<>();
   private Map map;
   private EntityTemplate et = 
   new EntityTemplate( // Sprite s, double maxHealth, double armor, double range, double damage, double aoe, double ap
      new Sprite(
         new int[] {0,-2,10,10},
         new int[] {0,30,10,20}
      ),
      100,
      0.25,
      100,
      10,
      5,
      0.25,
      1.0,
      new WeaponTemplate[] {
         new WeaponTemplate(
            200, 
            1, 
            5, 
            0.25, 
            new Sprite(
               new int[] {0,0}, 
               new int[] {0, 50}
            ),
            new Pose2D(new Vector2D(10, 10), new VectorMD(1, 0)),
            new VectorMD(1, 45) 
         )
      },
      0.05 
   );
   public GameManager(){
      map = new Map(100,100.0, 1.5);
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),1,this));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1500,1500), new VectorMD(1,0)),2,this));
   }
   public Entity[] getEntities(){
      Entity[] entitesa = new Entity[entities.size()];
      
      for(int i = 0; i<entities.size(); i++){
         entitesa[i] = entities.get(i);
      }
      return entitesa;
   }
   
   public Map getMap(){
      return map;
   }
   
   private void generateTarget(Entity e){
      ArrayList<Entity> validTargets = new ArrayList<>();
      for (Entity entity : entities) {
         if(entity.team!=e.team){
            validTargets.add(entity);
         }
      }
      int lowestInt = -1;
      double lowestDist = 1000000000;
      for (int j = 0; j < validTargets.size(); j++) {
         if(validTargets.get(j).getPose().pos.add(e.getPose().pos.n()).magnitude()<lowestDist){
            lowestDist = validTargets.get(j).getPose().pos.add(e.getPose().pos.n()).magnitude();
            lowestInt = j;
         }
      }
      e.target = validTargets.get(lowestInt);
   }

   public void update(){
      for(int i = 0; i<entities.size(); i++){
         if(entities.get(i).target==null){
            generateTarget(entities.get(i));
         }
         entities.get(i).update();
         for (int j = 0; j < entities.get(i).weps.length; j++) {
            entities.get(i).weps[j].update();
         }
      }
   }
}