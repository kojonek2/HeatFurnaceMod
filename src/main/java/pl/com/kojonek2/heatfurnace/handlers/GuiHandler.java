package pl.com.kojonek2.heatfurnace.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pl.com.kojonek2.heatfurnace.client.gui.GuiSolarFurnace;
import pl.com.kojonek2.heatfurnace.containers.ContainerSolarFurnace;
import pl.com.kojonek2.heatfurnace.tileentities.TileEntitySolarFurnace;

public class GuiHandler implements IGuiHandler {

	public static final int GUI_SOLAR_FURNACE_ID = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		switch(ID) {
			case GUI_SOLAR_FURNACE_ID:
				return new ContainerSolarFurnace(player.inventory, (IInventory) te);
			default: 
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		switch(ID) {
		case GUI_SOLAR_FURNACE_ID:
			return new GuiSolarFurnace(player.inventory, (IInventory) te);
		default: 
			return null;
	}
	}

}
