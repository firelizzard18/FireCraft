package com.firelizzard.firecraft.entity;

import cofh.lib.util.helpers.ServerHelper;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

// based on https://github.com/MachineMuse/MachineMusePowersuits/blob/efa395fcd49274fc13963ce5d22db9708d27efcc/src/main/scala/net/machinemuse/powersuits/entity/EntityPlasmaBolt.java
public class PlasmaBoltEntity extends EntityThrowable implements IThrowableEntity, IEntityAdditionalSpawnData, IProjectile {
	public static final double MAX_SIZE = 1;
	public static final int MAX_FLAMES = 10;
	public static final int MAX_LIFETIME = 200;
	public static final float EXPLOSSIVENESS_SCALE = 3;
	public static final float EXPLOSSIVENESS_BASE = 2;
	public static final float EXPLOSSIVENESS_EXP = 2;
	public static final float DETONATION_EXP = 0.3f;
	
	private Entity shooter;
	private float chargeLevel;
	private float detonationDamage = Float.NaN;
	
	public PlasmaBoltEntity(World world) {
		super(world);
	}
	
	public PlasmaBoltEntity(World world, Entity shooter, float chargeLevel) {
		super(world);
		this.shooter = shooter;
		this.chargeLevel = Math.min(chargeLevel, 1);
		
		Vec3 direction = shooter.getLookVec().normalize();
		calculateMotion(direction);
		calculatePosition(direction, shooter);
		
		double size = chargeLevel * MAX_SIZE;
		this.boundingBox.setBounds(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
	}
	
	private void calculateMotion(Vec3 direction) {
		double scale = 1.0;
		this.motionX = direction.xCoord * scale;
		this.motionY = direction.yCoord * scale;
		this.motionZ = direction.zCoord * scale;
	}
	
	private void calculatePosition(Vec3 direction, Entity shooter) {
		double xoffset = 1.3f + this.chargeLevel - direction.yCoord * shooter.getEyeHeight();
		double yoffset = -.2;
		double zoffset = 0.3f;
		double horzScale = Math.sqrt(direction.xCoord * direction.xCoord + direction.zCoord * direction.zCoord);
		double horzx = direction.xCoord / horzScale;
		double horzz = direction.zCoord / horzScale;
		this.posX = shooter.posX + direction.xCoord * xoffset - direction.yCoord * horzx * yoffset - horzz * zoffset;
		this.posY = shooter.posY + shooter.getEyeHeight() + direction.yCoord * xoffset + (1 - Math.abs(direction.yCoord)) * yoffset;
		this.posZ = shooter.posZ + direction.zCoord * xoffset - direction.yCoord * horzz * yoffset + horzx * zoffset;
	}
	
	public double getChargeLevel() {
		return chargeLevel;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		chargeLevel = tag.getFloat("ChargeLevel");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setFloat("ChargeLevel", chargeLevel);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(chargeLevel);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		chargeLevel = additionalData.readFloat();
	}

	@Override
	public void setThrower(Entity entity) {
		this.shooter = entity;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	protected void entityInit() {
		this.renderDistanceWeight = 10.0D;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0;
	}
	
	@Override
	public void onEntityUpdate() {
		if (!Float.isNaN(detonationDamage)) {
			worldObj.createExplosion(this, posX, posY, posZ, getExplosiveness() * detonationDamage * EXPLOSSIVENESS_SCALE, true);
			setDead();
		}
		
		super.onEntityUpdate();
		if (ticksExisted > MAX_LIFETIME) {
			setDead();
			return;
		}
		
		if (isInWater()) {
			setDead();
			for (int var3 = 0; var3 < chargeLevel * MAX_FLAMES; ++var3)
				this.worldObj.spawnParticle("flame",
						posX + Math.random(),
						posY + Math.random(),
						posZ + Math.random(),
						0.0D, 0.0D, 0.0D);
			return;
		}
	}
	
	public float getExplosiveness() {
		return (float) Math.pow(EXPLOSSIVENESS_BASE, EXPLOSSIVENESS_EXP * chargeLevel);
	}
	
	public float getExplosiveness(PlasmaBoltEntity other) {
		return  getExplosiveness() * other.getExplosiveness();
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.entityHit != null && mop.entityHit == shooter)
			return;
		
		float explosiveness = getExplosiveness();
		
		if (mop.typeOfHit == MovingObjectType.ENTITY) {
			if (mop.entityHit instanceof PlasmaBoltEntity) {
				PlasmaBoltEntity other = (PlasmaBoltEntity)mop.entityHit;
				explosiveness = getExplosiveness(other);
				other.setDead();
			} else if (mop.entityHit instanceof EntityLivingBase) {
				EntityLivingBase target = (EntityLivingBase)mop.entityHit;
				target.attackEntityFrom(DamageSource.causeThrownDamage(this, shooter), 0 * (explosiveness));
			} else
				return;
		}
		
		for (int var3 = 0; var3 < chargeLevel * MAX_FLAMES; ++var3)
			worldObj.spawnParticle("flame",
					posX + Math.random(),
					posY + Math.random(),
					posZ + Math.random(),
					0.0D, 0.0D, 0.0D);
		if (ServerHelper.isServerWorld(worldObj)) {
			worldObj.createExplosion(this, posX, posY, posZ, explosiveness * EXPLOSSIVENESS_SCALE, true);
			setDead();
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		if (source.isExplosion())
			this.detonationDamage = (float) Math.pow((damage - 1) / 8, DETONATION_EXP);
		return super.attackEntityFrom(source, damage);
	}
}
