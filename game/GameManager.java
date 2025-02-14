package Game.game;

import java.util.ArrayList;

import Game.rendering.Sprite;
import Game.util.Vector2D;
import Game.util.VectorMD;
import Game.util.Pose2D;
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
      10.0
   );
   public GameManager(){
      map = new Map(100,20);
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(100,0), new VectorMD(1,0)),2));
      entities.add(new Entity(et, map, new Pose2D(new Vector2D(1000,0), new VectorMD(1,0)),2));
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
   
   public void update(){
      for(int i = 0; i<entities.size(); i++){
         entities.get(i).update();
      }
   }
}