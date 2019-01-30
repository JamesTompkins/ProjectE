package moze_intel.projecte.gameObjs.gui;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.container.CondenserMK2Container;
import moze_intel.projecte.gameObjs.tiles.CondenserMK2Tile;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.TransmutationEMCFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class GUICondenserMK2 extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation(PECore.MODID.toLowerCase(), "textures/gui/condenser_mk2.png");
	private final CondenserMK2Container container;

	public GUICondenserMK2(InventoryPlayer invPlayer, CondenserMK2Tile tile)
	{
		super(new CondenserMK2Container(invPlayer, tile));
		this.container = ((CondenserMK2Container) inventorySlots);
		this.xSize = 255;
		this.ySize = 233;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = container.getProgressScaled();
		this.drawTexturedModalRect(x + 33, y + 10, 0, 235, progress, 10);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2)
	{
		long toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;
		String emc = TransmutationEMCFormatter.EMCFormat(toDisplay);
		this.fontRenderer.drawString(emc, 140, 10, 4210752);
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {
		long toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;

		if (toDisplay < 1e12) {
			super.renderHoveredToolTip(mouseX, mouseY);
			return;
		}

		int emcLeft = 140 + (this.width - this.xSize) / 2;
		int emcRight = emcLeft + 110;
		int emcTop = 6 + (this.height - this.ySize) / 2;
		int emcBottom = emcTop + 15;

		String emcAsString = I18n.format("pe.emc.emc_tooltip_prefix") + " " + Constants.EMC_FORMATTER.format(toDisplay);

		if (mouseX > emcLeft && mouseX < emcRight && mouseY > emcTop && mouseY < emcBottom) {
			drawHoveringText(Arrays.asList(emcAsString), mouseX, mouseY);
		} else {
			super.renderHoveredToolTip(mouseX, mouseY);
		}
	}
}
