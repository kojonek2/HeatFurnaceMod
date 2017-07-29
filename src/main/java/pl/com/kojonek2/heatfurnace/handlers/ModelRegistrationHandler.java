package pl.com.kojonek2.heatfurnace.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.blocks.BlockSolarFurnace;

public class ModelRegistrationHandler {

	
	@SubscribeEvent
	public void registerRenderers(ModelRegistryEvent event) {
		for(Block block : BlockRegistrationHandler.blocksToRegister) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
			HeatFurnaceMod.logger.info("Registering Model for Block" + block.getUnlocalizedName());
		}
	}
}
