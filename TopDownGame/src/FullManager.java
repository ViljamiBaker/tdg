package Game;

import java.util.ArrayList;
import java.awt.event.KeyEvent;

import Game.rendering.Renderer;
import Game.rendering.Camera;
import Game.util.keyListener;
import Game.game.GameManager;

//handles user interfacing
//also communicates from game to rendering
public class FullManager{
   Renderer r;
   Camera c;
   GameManager gm;
   keyListener kl;
   public FullManager(){
      gm = new GameManager();
      r = new Renderer(gm.getMap());
      c = r.getCamera();
      kl = new keyListener();
      r.addKeyListener(kl);
      run();
   }
   
   private void run(){
      double i = 0;
      while(true){
         double t = System.nanoTime()/1000000;
         checkKeys();
         i++;
         gm.update();
         r.updateEntities(gm.getEntities());
         r.updateEntity();
         r.paint();
         //c.rot = c.rot.addDD(0.01);
         //c.zoom = Math.sin(i/100.0)+1.1;
         double t2 = System.nanoTime()/1000000;
         try {Thread.sleep((int)Math.max(16-(t2-t),0));}catch(InterruptedException e){}
      }
   }
   
   private void checkKeys(){
      c.move(
         kl.keyDown(KeyEvent.VK_W),
         kl.keyDown(KeyEvent.VK_A),
         kl.keyDown(KeyEvent.VK_S),
         kl.keyDown(KeyEvent.VK_D),
         kl.keyDown(KeyEvent.VK_Q),
         kl.keyDown(KeyEvent.VK_E),
         kl.keyDown(KeyEvent.VK_R),
         kl.keyDown(KeyEvent.VK_F),
         5.0,
         0.01,
         1.02
      );
   }
   public static void main(String[] args){
      FullManager fm = new FullManager();
   }
}