package pl.com.kojonek2.heatfurnace.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pl.com.kojonek2.heatfurnace.tileentities.IModTileEntity;
import pl.com.kojonek2.heatfurnace.utils.LogHelper;

public class BlockRegistrationHandler {
	
	public static List<Block> blocksToRegister = new ArrayList<>();
	public static List<IModTileEntity> tileEntitiesToRegister = new ArrayList<>();
	
	public static void addBlockToRegister(Block block) {
		blocksToRegister.add(block);
	}
	
	public static void addTileEntityToRegister(IModTileEntity tileEntity) {
		tileEntitiesToRegister.add(tileEntity);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for(Block block : this.blocksToRegister) {
			event.getRegistry().register(block);
			LogHelper.info("Registering Block: " + block.getUnlocalizedName());
		}
		for(IModTileEntity tileEntity : tileEntitiesToRegister) {
			GameRegistry.registerTileEntity(((TileEntity) tileEntity).getClass(), tileEntity.getRegistrationName());
			LogHelper.info("Registering TileEntity: " + tileEntity.getRegistrationName());
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		for(Block block : this.blocksToRegister) {
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
}
