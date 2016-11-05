package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.mojang.authlib.GameProfile;

import cofh.api.tileentity.ISecurable;
import cofh.api.tileentity.ISecurable.AccessMode;
import cofh.lib.util.helpers.SecurityHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

//public class SecurityStationBlock extends BlockContainer { // fuck UIs
public class SecurityStationBlock extends Block {
	public static final String NAME = "securityStation";
	
	public SecurityStationBlock() {
        super(Material.iron);
		setBlockName(NAME);
		setCreativeTab(FireCraftMod.tab);
	}
	
	private static IChatComponent getMessageTranslation(String msgId) {
		return new ChatComponentTranslation("msg." + NAME + "." + msgId + ".txt");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
        blockIcon = register.registerIcon(FireCraftMod.MODID + ":" + NAME);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		if (world.isRemote)
			return true;
		
//		SecurityStationTile tile = (SecurityStationTile)world.getTileEntity(x, y, z);
//		if (tile == null)
//			return true;
		
		PlayerInteractEvent e = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, x, y, z, side, world);
		if (MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Result.DENY || e.useBlock == Result.DENY)
			return false;
		
//		player.openGui(FireCraftMod.instance(), FireCraftFmlGuiHandler.TILE_GUI, world, x, y, z);
		
		ItemStack stack = player.getHeldItem();
		if (stack == null)
			return false;

		if (stack.getItem() instanceof ISecurable) {
			player.addChatMessage(getMessageTranslation("isecurable"));
			return false;
		}
		
		boolean isSecure = SecurityHelper.isSecure(stack);
		if (isSecure) {
			GameProfile ownerProfile = SecurityHelper.getOwner(stack);
			GameProfile playerProfile = player.getGameProfile();
			
			if (ownerProfile == null ^ playerProfile == null) {
				player.addChatMessage(getMessageTranslation("not_owner"));
				return false;
			}
			
			if (ownerProfile != null && !ownerProfile.equals(playerProfile)) {
				player.addChatMessage(getMessageTranslation("not_owner"));
				return false;
			}
		}
		
		if (player.isSneaking()) {
			if (isSecure) {
				player.addChatMessage(getMessageTranslation("unsecuring"));
				SecurityHelper.removeSecure(stack);				
			} else {
				player.addChatMessage(getMessageTranslation("not_secure"));
			}
		} else {
			if (!isSecure) {
				player.addChatMessage(getMessageTranslation("securing"));
				SecurityHelper.setSecure(stack);
				SecurityHelper.setOwner(stack, player.getGameProfile());
			} else {
				AccessMode level = SecurityHelper.getAccess(stack);
				switch (level) {
				case PUBLIC:
					player.addChatMessage(getMessageTranslation("set_restricted"));
					SecurityHelper.setAccess(stack, AccessMode.RESTRICTED);
					break;
					
				case RESTRICTED:
					player.addChatMessage(getMessageTranslation("set_private"));
					SecurityHelper.setAccess(stack, AccessMode.PRIVATE);
					break;
					
				case PRIVATE:
					player.addChatMessage(getMessageTranslation("set_public"));
					SecurityHelper.setAccess(stack, AccessMode.PUBLIC);
					break;
				}
			}
		}
		
		return true;
	}
	
//	@Override
//	public TileEntity createNewTileEntity(World world, int meta) {
//		return new SecurityStationTile();
//	}
}
