package pl.com.kojonek2.heatfurnace.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.Names;

public class TileEntitySolarFurnace extends TileEntity implements ITickable, ISidedInventory {

	private static final int SLOT_SMELT = 0;
	private static final int SLOT_RESULT = 1;
	 /** The ItemStacks that hold the items currently being used in the furnace */
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	private String furnaceCustomName;
	private IItemHandler handler = new SidedInvWrapper(this, EnumFacing.DOWN);
	
	@Override
	public void update() {
		
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
	     //sboolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
	     this.furnaceItemStacks.set(index, stack);

	     if (stack.getCount() > this.getInventoryStackLimit())
	     {
	    	 stack.setCount(this.getInventoryStackLimit());
	     }

	    /* if (index == 0 && !flag)
	     {
	    	 this.totalCookTime = this.getCookTime(stack);
	    	 this.cookTime = 0;
	    	 this.markDirty();
	     }*/
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
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
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		 this.furnaceItemStacks.clear();
	}

	@Override
	public String getName() {
		 return this.hasCustomName() ? this.furnaceCustomName : "container." + Names.SOLAR_FURNACE;
	}

	@Override
	public boolean hasCustomName() {
		return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
	}
	
	public void setCustomInventoryName(String name)
    {
        this.furnaceCustomName = name;
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
	        this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);

	        if (compound.hasKey("CustomName", 8))
	        {
	            this.furnaceCustomName = compound.getString("CustomName");
	        }
	    }

	    public NBTTagCompound writeToNBT(NBTTagCompound compound)
	    {
	        super.writeToNBT(compound);
	        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

	        if (this.hasCustomName())
	        {
	            compound.setString("CustomName", this.furnaceCustomName);
	        }

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
