package TDG.game;

import TDG.rendering.Sprite;
import TDG.util.Pose2D;
import TDG.util.VectorMD;

public record WeaponTemplate (double range, double damage, double aoe, double ap, Sprite s, Pose2D offset, VectorMD maxAngle){


}
