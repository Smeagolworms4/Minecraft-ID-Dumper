package mods.middumper;

import java.io.File;

import sharose.mods.guiapi.GuiApiHelper;
import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.ModSettingScreen;
import sharose.mods.guiapi.ModSettings;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.src.BaseMod;

/**
 * This is the BASIC example of GuiAPI usage. We are going to create a
 * ModSettings object, and use the 'easy' way of getting settings. Note, the
 * easy was is slower than the intermediate way of doing things, so if you are
 * getting your setting values several times a second you might want to read
 * that after this tutorial. As well, this tutorial will show you some usage of
 * the 'makeButton' and 'showTextDisplay' method in the GuiApiHelper class.
 * 
 * @author ShaRose
 */
public class mod_MIDDumper extends BaseMod {
	
	/** The mod screen. */
	public ModSettingScreen myModScreen;
	/** The settings. */
	public ModSettings mySettings;
	
	public String dumpFile = "Export All id.txt";
	
	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void load() {
		mySettings = new ModSettings("mod_MIDDumper");
		myModScreen = new ModSettingScreen("Minecraft ID Dumper");
		myModScreen.append(GuiApiHelper.makeButton("Dump All ID", "dumpID", this, true));
		mySettings.load();
	}
	
	/**
	 * This is the method that will be called if you press the
	 * "Display Settings" button. It doesn't have to be public, so you can make
	 * this private and it will work. However, since we are calling this from a
	 * button, it needs to return void.
	 */
	public void dumpID () {
		

		System.out.println ("Dump all ID");
		
		StringBuilder displayTextBuilder = new StringBuilder();
		File fileExport = new File (Minecraft.getMinecraftDir(), dumpFile);

		String dump = "Block list : \n";
		dump += "===================================================\n\n";
		
		int oldId = 0;
		for (Block b : Block.blocksList) {
			if (b != null && b.blockID != 0) {
				if (oldId != 0 && oldId != b.blockID-1) {
					dump += "\n\tEmpty : "+oldId+" - "+(b.blockID-1)+"\t\n\n";
				}
				dump += b.blockID + "\t: "+ b + "\n";
			}
		}

		dump += "\n\n===================================================\n";
		dump += "Item list : \n";
		dump += "===================================================\n\n";
		
		oldId = 0;
		for (Item i : Item.itemsList) {
			if (i != null && i.itemID != 0) {
				if (oldId != 0 && oldId != i.itemID-1) {
					dump += "\n\tEmpty : "+oldId+" - "+(i.itemID-1)+"\t\n\n";
				}
				dump += i.itemID + "\t: "+ i + "\n";
			}
		}

		System.out.println ("");
		System.out.println ("");
		System.out.println ("===================================================");
		System.out.println (dump);
		System.out.println ("===================================================");
		System.out.println ("");
		
		displayTextBuilder.append("Export : " + fileExport.getAbsolutePath());
		
		GuiModScreen.show(GuiApiHelper.makeTextDisplayAndGoBack(
				"Result : ", displayTextBuilder.toString(),
				"OK, Go back", false));
	}
}
