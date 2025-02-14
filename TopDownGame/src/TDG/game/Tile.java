package TDG.game;

public class Tile{
   public double movement;
   public int x;
   public int y;
   public double height;
   public Tile(double height, double movement, int x, int y){
      this.movement = movement;
      this.x = x;
      this.y = y;
      this.height = height;
   }
   public Tile(Tile c){
      this.movement = c.movement;
      this.x = c.x;
      this.y = c.y;
      this.height = c.height;
   }
   public boolean equals(Tile other){
      return this.x == other.x && this.y == other.y;
   }
   @Override
   public String toString(){
      return "pos: " + x + ", " + y + " movement: " + movement + " height: " + height;
   }
}