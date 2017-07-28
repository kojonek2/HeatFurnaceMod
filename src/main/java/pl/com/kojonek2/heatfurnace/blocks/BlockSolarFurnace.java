package pl.com.kojonek2.heatfurnace.blocks;

import com.mojang.authlib.properties.Property;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.Names;

public class BlockSolarFurnace extends Block {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockSolarFurnace() {
		super(Material.ROCK);

		setUnlocalizedName(HeatFurnaceMod.MOD_ID + ":" + Names.SOLAR_FURNACE); //HeatFurnaceMod.MOD_ID + ":" + 
		setRegistryName(HeatFurnaceMod.MOD_ID, Names.SOLAR_FURNACE);
		
		setHardness(2.0f);
		setHarvestLevel("net.minecraft.item.ItemPickaxe" , 0);
		
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing value = EnumFacing.getFront(meta);
		
		if(value == EnumFacing.DOWN || value == EnumFacing.UP) {
			value = EnumFacing.NORTH;
		}
		
		return this.getDefaultState().withProperty(FACING, value);
	}

}
