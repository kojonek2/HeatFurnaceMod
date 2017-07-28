package pl.com.kojonek2.heatfurnace;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.com.kojonek2.heatfurnace.blocks.BlockSolarFurnace;

public class BlockRegistrationHelper {
	
	public static List<Block> blocksToRegister = new ArrayList<>();
	
	public static void addBlockToRegister(Block block) {
		blocksToRegister.add(block);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for(Block block : blocksToRegister) {
			event.getRegistry().register(block);
			HeatFurnaceMod.logger.info("Registering Block" + block.getUnlocalizedName());
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		for(Block block : blocksToRegister) {
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
}
