package com.firelizzard.firecraft.item;

import java.util.List;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.entity.AutopickupEntityItem;

import codechicken.enderstorage.common.BlockEnderStorage;
import cofh.api.block.IBlockConfigGui;
import cofh.api.block.IBlockInfo;
import cofh.api.block.IDismantleable;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.tileentity.ITileInfo;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermaldynamics.duct.BlockDuct;
import cofh.thermalexpansion.block.cache.BlockCache;
import cofh.thermalexpansion.block.cell.BlockCell;
import cofh.thermalexpansion.block.device.BlockDevice;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.light.BlockLight;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.block.plate.BlockPlate;
import cofh.thermalexpansion.block.strongbox.BlockStrongbox;
import cofh.thermalexpansion.block.tank.BlockTank;
import cofh.thermalexpansion.block.workbench.BlockWorkbench;
import cofh.thermalexpansion.item.tool.ItemEnergyContainerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.ironchest.BlockIronChest;
import erogenousbeef.bigreactors.common.block.BlockBRDevice;
import erogenousbeef.bigreactors.common.multiblock.block.BlockFuelRod;
import erogenousbeef.bigreactors.common.multiblock.block.BlockMultiblockGlass;
import erogenousbeef.bigreactors.common.multiblock.block.BlockReactorPart;
import erogenousbeef.bigreactors.common.multiblock.block.BlockReactorRedstonePort;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbinePart;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbineRotorPart;
import logisticspipes.blocks.LogisticsSolidBlock;
import logisticspipes.modules.abstractmodules.LogisticsGuiModule;
import logisticspipes.pipes.basic.CoreRoutedPipe;
import logisticspipes.pipes.basic.CoreUnroutedPipe;
import logisticspipes.pipes.basic.LogisticsBlockGenericPipe;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.block.BlockFactoryMachine;

public class DestroyerTool extends ItemEnergyContainerBase implements IEnergyContainerItem {
	public static final String NAME = "destroyer";
	
	public static enum Modes {
		TOOL,
		WEAPON
	}
	
	static boolean isMachineBlock(Block block) {
		// thermal expansion
		if (block instanceof BlockCache)
			return true;
		if (block instanceof BlockCell)
			return true;
		if (block instanceof BlockDevice)
			return true;
		if (block instanceof BlockDynamo)
			return true;
		if (block instanceof BlockLight)
			return true;
		if (block instanceof BlockMachine)
			return true;
		if (block instanceof BlockPlate)
			return true;
		if (block instanceof BlockStrongbox)
			return true;
		if (block instanceof BlockTank)
			return true;
		if (block instanceof BlockWorkbench)
			return true;
		
		// thermal dynamics
		if (block instanceof BlockDuct)
			return true;
		
		// big reactors
		if (block instanceof BlockBRDevice)
			return true;
		if (block instanceof BlockFuelRod)
			return true;
		if (block instanceof BlockMultiblockGlass)
			return true;
		if (block instanceof BlockReactorPart)
			return true;
		if (block instanceof BlockReactorRedstonePort)
			return true;
		if (block instanceof BlockTurbinePart)
			return true;
		if (block instanceof BlockTurbineRotorPart)
			return true;
		
		// ender storage
		if (block instanceof BlockEnderStorage)
			return true;
		
		// iron chest
		if (block instanceof BlockIronChest)
			return true;
		
		// logistics
		if (block instanceof LogisticsSolidBlock)
			return true;
		if (block instanceof LogisticsBlockGenericPipe)
			return true;
		
		// modular powersuits
//		if (block instanceof BlockTinkerTable)
//			return true;
		
		// minefactory reloaded
		if (block instanceof BlockFactoryMachine)
			return true;
		
		return false;
	}

	public float minDamage = 1f;
	public float hitDamage = 15f;
	public float silkTouchMultiplier = 2f;
	
	public DestroyerTool() {
		super(NAME);
		setMaxStackSize(1);
		setCreativeTab(FireCraftMod.tab);

		maxEnergy = 20000000;
		maxTransfer = 160000;
		energyPerUse = 10000;
		
		modName = "firecraft";
	}
	
	public Modes getModeEnum(ItemStack stack) {
		int imode = getMode(stack);
		if (imode < 0 || imode >= Modes.values().length)
			return Modes.TOOL;
		return Modes.values()[imode];
	}

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
    	EntityPlayer player = null;
    	if (entity instanceof EntityPlayer)
    		player = (EntityPlayer)entity;
    	
    	if (player != null && !player.capabilities.isCreativeMode)
    		extractEnergy(stack, energyPerUse, false);

    	if (!ServerHelper.isServerWorld(world))
    		return false;
    	
		List<ItemStack> drops = BlockHelper.breakBlock(world, player, x, y, z, block, 0, true, false);
		if (drops == null || drops.size() == 0)
			return false;

		tryGiveToPlayer(world, player, x, y, z, drops);
		return true;
    }
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return 3;
	}

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
    	if (getEnergyStored(stack) < energyPerUse)
    		return 0f;
    	
    	if (isMachineBlock(block))
    		return 1f;
    	
		return 20000f;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase player) {
    	float damage = Math.max(minDamage, hitDamage * extractEnergy(stack, energyPerUse, false) / maxEnergy);
    	entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player), damage);
        return false;
    }
    
    boolean onItemUse_machine(ItemStack stack, EntityPlayer player, World world, Block block, int x, int y, int z, int hitSide) {
    	if (block instanceof IDismantleable && player.isSneaking())
    		if (onItemUse_dismantle(stack, player, world, (IDismantleable)block, x, y, z, hitSide))
    			return true;
    	
		if (ServerHelper.isClientWorld(world))
			return onItemUse_client(stack, player, world, block, x, y, z, hitSide);
		
		if (player.isSneaking())
			return onItemUse_break(stack, player, world, block, x, y, z, hitSide);

		if (block instanceof IBlockConfigGui) {
			if (((IBlockConfigGui) block).openConfigGui(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[hitSide], player))
				return true;
			return false;
		}
		
		if (block instanceof LogisticsBlockGenericPipe)
			return onItemUse_logisticsPipe(stack, player, world, (LogisticsBlockGenericPipe)block, x, y, z, hitSide);
		
		return false;
    }
    
    boolean onItemUse_dismantle(ItemStack stack, EntityPlayer player, World world, IDismantleable block, int x, int y, int z, int hitSide) {
    	if (!block.canDismantle(player, world, x, y, z))
    		return false;
    	
    	List<ItemStack> drops = block.dismantleBlock(player, world, x, y, z, true);
    	if (drops == null || drops.isEmpty())
    		return false;
    	
    	tryGiveToPlayer(world, player, x, y, z, drops);
    	return true;
    }
    
    boolean onItemUse_client(ItemStack stack, EntityPlayer player, World world, Block block, int x, int y, int z, int hitSide) {
		if (block instanceof IBlockConfigGui || block instanceof IBlockInfo)
			return true;
		
		TileEntity theTile = world.getTileEntity(x, y, z);
		return theTile instanceof ITileInfo;
    }

    boolean onItemUse_break(ItemStack stack, EntityPlayer player, World world, Block block, int x, int y, int z, int hitSide) {
    	List<ItemStack> drops = BlockHelper.breakBlock(world, player, x, y, z, block, 0, true, false);
		if (drops == null || drops.size() == 0)
			return false;
		
		ejectIntoWorld(world, player, x, y, z, drops);
		return true;
    }

    boolean onItemUse_logisticsPipe(ItemStack stack, EntityPlayer player, World world, LogisticsBlockGenericPipe block, int x, int y, int z, int hitSide) {
		CoreUnroutedPipe pipe = LogisticsBlockGenericPipe.getPipe(world, x, y, z);
		
		if (!LogisticsBlockGenericPipe.isValid(pipe))
			return false;
		
		if (!(pipe instanceof CoreRoutedPipe))
			return false;
		
		CoreRoutedPipe rpipe = (CoreRoutedPipe)pipe;
		if (!(rpipe.getLogisticsModule() instanceof LogisticsGuiModule))
			return false;

		LogisticsGuiModule gui = (LogisticsGuiModule)rpipe.getLogisticsModule();
		gui.getPipeGuiProviderForModule().setTilePos(pipe.getContainer()).open(player);
		return true;
    }

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		
		if (isMachineBlock(block))
			return onItemUse_machine(stack, player, world, block, x, y, z, hitSide);
		
		if (!block.canSilkHarvest(world, player, x, y, z, metadata))
			return false;
		
		int cost = (int)(energyPerUse * silkTouchMultiplier);
		if (!player.capabilities.isCreativeMode && extractEnergy(stack, cost, false) != cost)
			return false;

		List<ItemStack> drops = BlockHelper.breakBlock(world, player, x, y, z, block, 0, true, true);
		if (drops == null || drops.size() == 0)
			return false;

		tryGiveToPlayer(world, player, x, y, z, drops);
		return true;
	}
	
	void tryGiveToPlayer(World world, EntityPlayer player, int x, int y, int z, List<ItemStack> drops) {
    	for (ItemStack drop : drops) {
    		if (drop.stackSize == 0)
    			continue;
    		if (player.inventory.addItemStackToInventory(drop))
    			continue;
    		ejectIntoWorld(world, null, x, y, z, drop);
    	}
	}
	
	void ejectIntoWorld(World world, EntityPlayer player, int x, int y, int z, List<ItemStack> drops) {
    	for (ItemStack drop : drops)
    		ejectIntoWorld(world, player, x, y, z, drop);
	}
	
	void ejectIntoWorld(World world, EntityPlayer player, int x, int y, int z, ItemStack drop) {
		EntityItem item;
		
		if (player == null)
			item = new EntityItem(world, x, y, z, drop);
		else
			item = new AutopickupEntityItem(world, player, x, y, z, drop);
		
		item.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(item);
	}
	
	
	
	/* minecraft item */
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(FireCraftMod.MODID + ":" + NAME);
	}
	
	
	
	/* thermal expansion tool */
	
	@Override
	protected void addInformationDelegate(ItemStack stack, EntityPlayer player, List<String> list, boolean check) {
		super.addInformationDelegate(stack, player, list, check);
		list.add("Mode: " + getModeEnum(stack));
	}
	
	@Override
	public int getNumModes(ItemStack stack) {
		return Modes.values().length;
	}

	
	
	
	/* override TE's name stuff */
	
	@Override
	public String getUnlocalizedName() {
        return "item." + NAME;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
}
