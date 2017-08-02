package pl.com.kojonek2.heatfurnace.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.blocks.ModBlocks;
import pl.com.kojonek2.heatfurnace.inventory.SlotSolarFurnaceOutput;

public class ContainerSolarFurnace extends Container {

	private final IInventory tileSolarFurnace;
	private int cookTime;
	private int itemCookTime;
	private int cookingSpeed;

	public ContainerSolarFurnace(InventoryPlayer playerInv, IInventory solarFurnaceInventory) {
		this.tileSolarFurnace = solarFurnaceInventory;

		this.addSlotToContainer(new Slot(solarFurnaceInventory, 0, 56, 34));
		this.addSlotToContainer(new SlotSolarFurnaceOutput(solarFurnaceInventory, 1, 116, 35));

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 3; y++) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileSolarFurnace);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);
			
			if(this.cookTime != this.tileSolarFurnace.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, this.tileSolarFurnace.getField(0));
			}
			
			if(this.itemCookTime != this.tileSolarFurnace.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, this.tileSolarFurnace.getField(1));
			}
			
			if(this.cookingSpeed != this.tileSolarFurnace.getField(2)) {
				icontainerlistener.sendWindowProperty(this, 2, this.tileSolarFurnace.getField(2));
			}
			
			this.cookTime = this.tileSolarFurnace.getField(0);
			this.itemCookTime = this.tileSolarFurnace.getField(1);
			this.cookingSpeed = this.tileSolarFurnace.getField(2);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileSolarFurnace.setField(id, data);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot == null || !slot.getHasStack()) {
			return ItemStack.EMPTY;
		}
		
		ItemStack itemStackInInv = slot.getStack();
		itemStack = itemStackInInv.copy();
		
		if(index == 0) {
			if(!this.mergeItemStack(itemStackInInv, 2, 38, false)) {
				return ItemStack.EMPTY;
			}
		} else if(index == 1) {
			if(!this.mergeItemStack(itemStackInInv, 2, 38, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			if(!this.mergeItemStack(itemStackInInv, 0, 1, false)) {
				return ItemStack.EMPTY;
			}
		}
		
		
		
		if (itemStackInInv.isEmpty())
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else
        {
            slot.onSlotChanged();
        }
		
		if (itemStackInInv.getCount() == itemStack.getCount())
        {
            return ItemStack.EMPTY;
        }
		
		slot.onTake(player, itemStackInInv);

		return itemStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
