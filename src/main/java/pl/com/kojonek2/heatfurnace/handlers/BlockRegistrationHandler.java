package pl.com.kojonek2.heatfurnace.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.Names;
import pl.com.kojonek2.heatfurnace.blocks.BlockSolarFurnace;
import pl.com.kojonek2.heatfurnace.tileentities.TileEntitySolarFurnace;

public class BlockRegistrationHandler {
	
	public static List<Block> blocksToRegister = new ArrayList<>();
	
	public static void addBlockToRegister(Block block) {
		blocksToRegister.add(block);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for(Block block : this.blocksToRegister) {
			event.getRegistry().register(block);
			HeatFurnaceMod.logger.info("Registering Block" + block.getUnlocalizedName());
			if(block.hasTileEntity(block.getDefaultState())) {
				GameRegistry.registerTileEntity(TileEntitySolarFurnace.class, Names.SOLAR_FURNACE_TILE_ENTITY);
				HeatFurnaceMod.logger.info("Registering TileEntity for Block" + block.getUnlocalizedName());
			}
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		for(Block block : this.blocksToRegister) {
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
}
