package TDG.game;

import TDG.rendering.Sprite;

public record EntityTemplate(Sprite s, double maxHealth, double armor, double range, double damage, double aoe, double ap, double speed, WeaponTemplate[] wt, double rotSpeed) {}