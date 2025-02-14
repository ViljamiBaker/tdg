package Game.game;

import Game.rendering.Sprite;

public class EntityTemplate{
   public Sprite s;
   public double maxHealth;
   public double armor;
   public double range;
   public double damage;
   public double aoe;
   public double ap;
   public double speed;
   public EntityTemplate(Sprite s, double maxHealth, double armor, double range, double damage, double aoe, double ap, double speed){
      this.maxHealth = maxHealth;
      this.s = s;
      this.armor = armor;
      this.range = range;
      this.damage = damage;
      this.aoe = aoe;
      this.ap = ap;
      this.speed = speed;
   }
}