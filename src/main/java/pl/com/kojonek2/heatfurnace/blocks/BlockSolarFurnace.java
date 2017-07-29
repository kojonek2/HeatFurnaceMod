package pl.com.kojonek2.heatfurnace.blocks;

import com.mojang.authlib.properties.Property;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.Names;
import pl.com.kojonek2.heatfurnace.tileentities.TileEntitySolarFurnace;

public class BlockSolarFurnace extends BlockContainer {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockSolarFurnace() {
		super(Material.ROCK);

		this.setUnlocalizedName(HeatFurnaceMod.MOD_ID + ":" + Names.SOLAR_FURNACE);
		this.setRegistryName(HeatFurnaceMod.MOD_ID, Names.SOLAR_FURNACE);
		
		this.setHardness(2.0f);
		this.setHarvestLevel("net.minecraft.item.ItemPickaxe" , 0);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		EnumFacing value = placer.getHorizontalFacing().getOpposite();
		return this.getDefaultState().withProperty(FACING, value);
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

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySolarFurnace();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
