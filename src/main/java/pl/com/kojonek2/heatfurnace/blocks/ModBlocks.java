package pl.com.kojonek2.heatfurnace.blocks;

import pl.com.kojonek2.heatfurnace.handlers.BlockRegistrationHandler;

public class ModBlocks {

	public static void addBlocksToRegister() {
		BlockRegistrationHandler.addBlockToRegister(new BlockSolarFurnace());
	}
	
}
