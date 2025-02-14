package Game.rendering;

import Game.util.Pose2D;

public class Sprite{
   int[] xposes;
   int[] yposes;
   public Pose2D p;
   public Sprite(int[] xposes, int[] yposes){
      this.xposes = xposes;
      this.yposes = yposes;
   }
   public Sprite(Sprite s){
      this.xposes = s.xposes.clone();
      this.yposes = s.yposes.clone();
   }
}