package pl.com.kojonek2.heatfurnace.blocks;

import net.minecraft.block.Block;
import pl.com.kojonek2.heatfurnace.handlers.BlockRegistrationHandler;
import pl.com.kojonek2.heatfurnace.tileentities.TileEntitySolarFurnace;

public class ModBlocks {
	
	public static final Block SOLAR_FURNACE = new BlockSolarFurnace();

	public static void addBlocksToRegister() {
		BlockRegistrationHandler.addBlockToRegister(SOLAR_FURNACE);
		
		addTilesEntitiesToRegister();
	}
	
	private static void addTilesEntitiesToRegister() {
		BlockRegistrationHandler.addTileEntityToRegister(new TileEntitySolarFurnace());
	}
	
}
