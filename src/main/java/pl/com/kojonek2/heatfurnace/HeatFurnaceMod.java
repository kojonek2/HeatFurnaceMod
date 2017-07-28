package pl.com.kojonek2.heatfurnace;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.com.kojonek2.heatfurnace.blocks.BlockSolarFurnace;
import pl.com.kojonek2.heatfurnace.proxy.CommonProxy;

@Mod(modid = HeatFurnaceMod.MOD_ID, name = HeatFurnaceMod.NAME, version = HeatFurnaceMod.VERSION, dependencies = "required-after:FML", acceptedMinecraftVersions = "[1.12]")
public class HeatFurnaceMod {
	
	public static Logger logger;
	
	public static final String MOD_ID = "heatfurnace";
	public static final String NAME = "Heat Furnace Mod";
	public static final String VERSION = "1.12-0.1";

	@Instance(MOD_ID)
	public static HeatFurnaceMod instance;

	@SidedProxy(serverSide = "pl.com.kojonek2.heatfurnace.proxy.CommonProxy", clientSide = "pl.com.kojonek2.heatfurnace.proxy.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		
		BlockRegistrationHelper.addBlockToRegister(new BlockSolarFurnace());
		MinecraftForge.EVENT_BUS.register(new BlockRegistrationHelper());
		
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		proxy.postInit(event);
	}
		
}