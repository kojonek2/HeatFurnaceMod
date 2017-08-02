package pl.com.kojonek2.heatfurnace.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import pl.com.kojonek2.heatfurnace.HeatFurnaceMod;
import pl.com.kojonek2.heatfurnace.containers.ContainerSolarFurnace;
import pl.com.kojonek2.heatfurnace.tileentities.TileEntitySolarFurnace;

public class GuiSolarFurnace extends GuiContainer{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(HeatFurnaceMod.MOD_ID, "textures/gui/solar_furnace.png");
	
	private InventoryPlayer playerInventory;
	private IInventory tileSolarFurnace;

	public GuiSolarFurnace(InventoryPlayer playerInv, IInventory solarFurnaceInventory) {
		super(new ContainerSolarFurnace(playerInv, solarFurnaceInventory));
		this.playerInventory = playerInv;
		this.tileSolarFurnace = solarFurnaceInventory;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		
		int xCenter = (this.width - this.xSize) / 2;
		int yCenter = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xCenter, yCenter, 0, 0, this.xSize, this.ySize);
        
        //draw progress
        this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 35, 177, 14, this.getCookingProgesWidth(22), 17);
        int height = this.getCookingSpeedHeight(14);
        this.drawTexturedModalRect(this.guiLeft + 84, this.guiTop + 73 - height, 176, 14 - height, 14, height);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String text = this.tileSolarFurnace.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(text, this.xSize / 2 - this.fontRenderer.getStringWidth(text) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	private int getCookingProgesWidth(int widthOfTexture) {
		int progress = this.tileSolarFurnace.getField(0);
		int maxProgress = this.tileSolarFurnace.getField(1);
		
		if(maxProgress == 0) {
			return 0;
		}
		if(progress >= maxProgress) {
			return widthOfTexture;
		}
		 
		return progress * widthOfTexture / maxProgress;
	}
	
	private int getCookingSpeedHeight(int heightOfTexture) {
		int cookingSpeed = this.tileSolarFurnace.getField(2);
		
		if(cookingSpeed >= TileEntitySolarFurnace.MAX_COOKING_SPEED) {
			return heightOfTexture;
		}
		
		return cookingSpeed * heightOfTexture / TileEntitySolarFurnace.MAX_COOKING_SPEED;
	}

}
