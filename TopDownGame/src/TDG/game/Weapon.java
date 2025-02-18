package TDG.game;

import TDG.rendering.Sprite;
import TDG.util.Pose2D;
import TDG.util.VectorMD;

public class Weapon {
   public double range;
   public double damage;
   public double aoe;
   public double ap;
   public Sprite s;
   public Pose2D offset;
   public Pose2D p;
   public VectorMD maxAngle;
   public Entity owner;

  public Weapon(WeaponTemplate wt, Entity owner){
    this.range = wt.range();
    this.damage = wt.damage();
    this.aoe = wt.aoe();
    this.ap = wt.ap();
    this.s = wt.s();
    this.offset = new Pose2D(wt.offset());
    this.maxAngle = wt.maxAngle();
    this.p = new Pose2D(offset);
    this.owner = owner;
  }

  public void update(){

  }
}
