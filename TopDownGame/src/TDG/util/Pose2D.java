package TDG.util;

public class Pose2D{
   public Vector2D pos;
   public VectorMD rot;
      
   public Pose2D(Vector2D pos, VectorMD rot){
      this.pos = pos;
      this.rot = rot;
   }

   public Pose2D(Pose2D cloned){
      this.pos = new Vector2D(cloned.pos.x, cloned.pos.y);
      this.rot = new VectorMD(cloned.rot.M, cloned.rot.D);
   }
   
   public Pose2D relativeTo(Pose2D other){
      return new Pose2D(other.pos.add(this.pos.n()).rotateBy(this.rot.i()), other.rot.addD(this.rot.i()));
   }

   public Pose2D add(Pose2D other){
      return new Pose2D(other.pos.add(this.pos), other.rot.addD(this.rot));
   }
}