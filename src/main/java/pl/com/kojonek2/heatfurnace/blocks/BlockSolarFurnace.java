package pl.com.kojonek2.heatfurnace.blocks;

import com.mojang.authlib.properties.Property;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.Names;
import pl.com.kojonek2.heatfurnace.handlers.GuiHandler;
import pl.com.kojonek2.heatfurnace.tileentities.TileEntitySolarFurnace;

public class BlockSolarFurnace extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool IS_BURNING = PropertyBool.create("is_burning");

	public BlockSolarFurnace() {
		super(Material.ROCK);

		this.setUnlocalizedName(HeatFurnaceMod.MOD_ID + ":" + Names.SOLAR_FURNACE);
		this.setRegistryName(HeatFurnaceMod.MOD_ID, Names.SOLAR_FURNACE);

		this.setHardness(2.0f);
		this.setHarvestLevel("net.minecraft.item.ItemPickaxe", 0);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_BURNING, false));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(worldIn.isRemote) {
			return true;
		} else {
			TileEntity te = worldIn.getTileEntity(pos);
			
			if(te != null && te instanceof IInventory) {
				playerIn.openGui(HeatFurnaceMod.instance, GuiHandler.GUI_SOLAR_FURNACE_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			
			return true;
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		EnumFacing value = placer.getHorizontalFacing().getOpposite();
		boolean isBurning = world.getChunkFromBlockCoords(pos).canSeeSky(pos.offset(EnumFacing.UP));
		return this.getDefaultState().withProperty(FACING, value).withProperty(IS_BURNING, isBurning);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, IS_BURNING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(IS_BURNING) ? 1 : 0;
		i = i << 3;
		return state.getValue(FACING).getIndex() | i;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing value = EnumFacing.getFront(meta & 7);

		if (value == EnumFacing.DOWN || value == EnumFacing.UP) {
			value = EnumFacing.NORTH;
		}
		 boolean isBurning = (meta >> 3 == 1) ? true : false;
		
		return this.getDefaultState().withProperty(FACING, value).withProperty(IS_BURNING, isBurning);
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
