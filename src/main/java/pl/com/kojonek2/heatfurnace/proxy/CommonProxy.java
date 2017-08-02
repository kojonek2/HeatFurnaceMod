package pl.com.kojonek2.heatfurnace.proxy;

import net.minecraft.block.Block;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.handlers.BlockRegistrationHandler;
import pl.com.kojonek2.heatfurnace.handlers.GuiHandler;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new BlockRegistrationHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(HeatFurnaceMod.instance, new GuiHandler());
	}

	public void init(FMLInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {

	}
	
}
