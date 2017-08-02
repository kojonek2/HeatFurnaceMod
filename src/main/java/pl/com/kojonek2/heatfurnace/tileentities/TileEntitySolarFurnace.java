package pl.com.kojonek2.heatfurnace.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.Names;
import pl.com.kojonek2.heatfurnace.blocks.BlockSolarFurnace;
import pl.com.kojonek2.heatfurnace.utils.LogHelper;

public class TileEntitySolarFurnace extends TileEntity implements ITickable, ISidedInventory, IModTileEntity {

	private static final int SLOT_SMELT = 0;
	private static final int SLOT_RESULT = 1;
	 /** The ItemStacks that hold the items currently being used in the furnace */
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	private IItemHandler handler = new SidedInvWrapper(this, EnumFacing.DOWN);
	
	private boolean seesSky = false;
	/** How long item has been cooking */
	private int cookTime = 0;
	/** How long current item has to be cooking */
	private int itemCookTime = 0;
	private int cookingSpeed = 0;
	public static final int  MAX_COOKING_SPEED = 10;//Max 10
	
	@Override
	public void update() {
		
		if(this.world.isRemote) {
			return;
		}
		
		boolean save = false;
		boolean wasBurning = this.cookingSpeed > 0;
		
		if(!this.world.getChunkFromBlockCoords(this.pos).canSeeSky(this.pos.offset(EnumFacing.UP))) {
			this.cookingSpeed = 0;
			updateState(wasBurning);
			return;
		}
		
		this.cookingSpeed = (int) ((this.world.getSunBrightnessFactor(1.0f) * 10));
		
		ItemStack cookingItem = this.furnaceItemStacks.get(SLOT_SMELT);
		ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(cookingItem);
		ItemStack stackInResultSlot = this.furnaceItemStacks.get(SLOT_RESULT);
		
		if(this.canSmelt()) {
			this.cookTime += this.cookingSpeed;
			
			if(this.cookTime >= this.itemCookTime) {
				this.cookTime = 0;
				
				if(!stackInResultSlot.isEmpty()) {
					stackInResultSlot.grow(smeltingResult.getCount());
				} else {
					this.furnaceItemStacks.set(SLOT_RESULT, smeltingResult.copy());
				}
				
				cookingItem.shrink(1);
				
				if(!cookingItem.isEmpty()) {
					this.itemCookTime = getCookTime(cookingItem);
				}
				
				save = true;
			}
		}
		
		updateState(wasBurning);
		
		if(save) {
			this.markDirty();
		}
		
	}
	
	/** Updates block state when necessary
	 * @param wasBurning true if furnace was operating before update*/
	private void updateState(boolean wasBurning) {
		if(wasBurning != this.cookingSpeed > 0) {
			IBlockState newState = this.world.getBlockState(this.pos).withProperty(BlockSolarFurnace.IS_BURNING, this.cookingSpeed > 0);
			this.world.setBlockState(this.pos, newState);
		}
	}
	
	//** Return true when result slot is not empty and item in smelt slot has recipe and stack in result slot isn't full */
	private boolean canSmelt() {
		//!cookingItem.isEmpty() && !smeltingResult.isEmpty()  &&
		//(stackInResultSlot.isEmpty() || 
			//	(stackInResultSlot.isItemEqual(smeltingResult) && smeltingResult.getCount() + stackInResultSlot.getCount() <= smeltingResult.getMaxStackSize()))
		ItemStack cookingItem = this.furnaceItemStacks.get(SLOT_SMELT);
		if(cookingItem.isEmpty()) {
			return false;
		}

		ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(SLOT_SMELT));
		if(smeltingResult.isEmpty()) {
			return false;
		}
		
		ItemStack itemInResultSlot = this.furnaceItemStacks.get(SLOT_RESULT);
		if(itemInResultSlot.isEmpty()) {
			return true;
		}
		
		if(itemInResultSlot.isItemEqual(smeltingResult)) {
			if(smeltingResult.getCount() + itemInResultSlot.getCount() <= smeltingResult.getMaxStackSize()) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getCookTime(ItemStack stack) {
		return 2000;
	}

	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.furnaceItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.furnaceItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		 ItemStack itemstack = this.furnaceItemStacks.get(index);
	     boolean isThisSameItem = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
	     this.furnaceItemStacks.set(index, stack);

	     if (stack.getCount() > this.getInventoryStackLimit())
	     {
	    	 stack.setCount(this.getInventoryStackLimit());
	     }

	     if (index == 0 && !isThisSameItem)
	     {
	    	 this.itemCookTime = this.getCookTime(stack);
	    	 this.cookTime = 0;
	    	 this.markDirty();
	     }
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == SLOT_SMELT) {
			return true;
		}
		return false;
	}

	@Override
	public int getField(int id) {
		switch(id) {
			case 0:
				return this.cookTime;
			case 1:
				return this.itemCookTime;
			case 2:
				return this.cookingSpeed;
			default:
				return -1;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
			case 0:
				this.cookTime = value;
				break;
			case 1:
				this.itemCookTime = value;
				break;
			case 2:
				this.cookingSpeed = value;
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public void clear() {
		 this.furnaceItemStacks.clear();
	}

	@Override
	public String getRegistrationName() {
		return "tileentity." + HeatFurnaceMod.MOD_ID + ":" + Names.SOLAR_FURNACE;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName());
	}
	
	@Override
	public String getName() {
		 return "container." + HeatFurnaceMod.MOD_ID + ":" + Names.SOLAR_FURNACE;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0, 1};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if(index == SLOT_SMELT) {
			return false;
		}
		return true;
	}
	
	 public void readFromNBT(NBTTagCompound compound)
	    {
	        super.readFromNBT(compound);
	        this.cookTime = compound.getInteger("cook_time");
	        this.itemCookTime = compound.getInteger("item_cook_time");
	        this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);

	    }

	    public NBTTagCompound writeToNBT(NBTTagCompound compound)
	    {
	        super.writeToNBT(compound);
	        compound.setInteger("cook_time", this.cookTime);
	        compound.setInteger("item_cook_time", this.itemCookTime);
	        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);
	        return compound;
	    }
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		 if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			 return (T) this.handler;
		 }
		return super.getCapability(capability, facing);
	}

}
