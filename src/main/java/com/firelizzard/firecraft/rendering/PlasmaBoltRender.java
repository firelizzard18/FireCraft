package com.firelizzard.firecraft.rendering;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.firelizzard.firecraft.entity.PlasmaBoltEntity;

import net.machinemuse.numina.geometry.Colour;
import net.machinemuse.numina.geometry.DrawableMuseCircle;
import net.machinemuse.numina.render.RenderState;
import net.machinemuse.utils.render.MuseRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

// heavily based on https://github.com/MachineMuse/MachineMusePowersuits/blob/efa395fcd49274fc13963ce5d22db9708d27efcc/src/main/scala/net/machinemuse/powersuits/client/render/entity/RenderPlasmaBolt.java
public class PlasmaBoltRender extends Render {
	public static final double CHARGE_MIN_SIZE = 0.5;
	public static final double CHARGE_SCALE = 1.0/16;

    protected static DrawableMuseCircle circle1;
    protected static DrawableMuseCircle circle2;
    protected static DrawableMuseCircle circle3;
    protected static DrawableMuseCircle circle4;

    public PlasmaBoltRender() {
        Colour c1 = new Colour(.6, .3, 1, 0.3);
        circle1 = new DrawableMuseCircle(c1, c1);
        c1 = new Colour(.6, .3, 1, 0.6);
        circle2 = new DrawableMuseCircle(c1, c1);
        c1 = new Colour(.6, .3, 1, 1);
        circle3 = new DrawableMuseCircle(c1, c1);
        circle4 = new DrawableMuseCircle(c1, new Colour(1, 1, 1, 1));
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker
     * function which does the actual work. In all probabilty, the class Render
     * is generic (Render<T extends Entity) and this method has signature public
     * void doRender(T entity, double d, double d1, double d2, float f, float
     * f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        PlasmaBoltEntity bolt = (PlasmaBoltEntity) entity;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        doRender(bolt.getChargeLevel());
        GL11.glPopMatrix();
    }

    public static DoubleBuffer unrotatebuffer;

    public static void doRender(double chargeLevel) {
        GL11.glPushMatrix();
        MuseRenderer.unRotate();
        double scale = (chargeLevel + CHARGE_MIN_SIZE) * CHARGE_SCALE;
        GL11.glScaled(scale, scale, scale);
        int millisPerCycle = 500;
        double timeScale = Math.cos((System.currentTimeMillis() % millisPerCycle) * 2.0 / millisPerCycle - 1.0);
        RenderState.glowOn();
        circle1.draw(4, 0, 0);
        GL11.glTranslated(0, 0, 0.001);
        circle2.draw(3 + timeScale / 2, 0, 0);
        GL11.glTranslated(0, 0, 0.001);
        circle3.draw(2 + timeScale, 0, 0);
        GL11.glTranslated(0, 0, 0.001);
        circle4.draw(1 + timeScale, 0, 0);
        for (int i = 0; i < 3; i++) {
            double angle1 = (Math.random() * 2 * Math.PI);
            double angle2 = (Math.random() * 2 * Math.PI);
            MuseRenderer.drawLightning(Math.cos(angle1) * 0.5, Math.sin(angle1) * 0.5, 0, Math.cos(angle2) * 5, Math.sin(angle2) * 5, 1, new Colour(1, 1, 1, 0.9));
        }
        RenderState.glowOff();
        GL11.glPopMatrix();
    }
}
