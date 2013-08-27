package mods.middumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import cpw.mods.fml.common.registry.LanguageRegistry;

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
	
	FileWriter fileOut;
	
	/** The mod screen. */
	public ModSettingScreen myModScreen;
	/** The settings. */
	public ModSettings mySettings;
	
	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void load() {
		
		mySettings = new ModSettings("mod_MIDDumper");
		myModScreen = new ModSettingScreen("Minecraft ID Dumper");
		myModScreen.append(GuiApiHelper.makeButton("Dump All ID to TXT", "dumpID_TXT", this, true));
		myModScreen.append(GuiApiHelper.makeButton("Dump All ID to CSV", "dumpID_CSV", this, true));
		mySettings.load();
	}
	
	/**
	 * @param String s
	 * @throws IOException
	 */
	private void write (String s) throws IOException {
		fileOut.write (s+"\n");
		System.out.println (s);
	}
	
	/**
	 * This is the method that will be called if you press the
	 * "Display Settings" button. It doesn't have to be public, so you can make
	 * this private and it will work. However, since we are calling this from a
	 * button, it needs to return void.
	 */
	public void dumpID_CSV () {

		System.out.println ("Dump all ID CSV");
		
		StringBuilder displayTextBuilder = new StringBuilder();
		File fileRead = new File (Minecraft.getMinecraftDir(), "Export all id.csv");
		
		System.out.println ("");
		System.out.println ("");
		System.out.println ("===================================================");
		
		try {
			fileOut = new FileWriter (fileRead);
			
			write ("\"Block list : \";;;;;");
			
			int oldId = 0;
			for (Block b : Block.blocksList) {
				if (b != null && b.blockID != 0) {
					if (oldId != 0 && oldId != b.blockID-1) {
						write ("\"Empty : \";\""+oldId+"\";\""+(b.blockID-1)+"\";;;");
					}
					oldId = b.blockID;
					write ("\""+b.blockID+"\";\""+b.getUnlocalizedName ()+"\";\""+b.toString().replaceAll("^[\\p{Alnum}]*", "_")+"\";;;");
				}
			}
			
			write ("\"Item list : \";;;;;");
			
			oldId = 0;
			for (Item i : Item.itemsList) {
				if (i != null && i.itemID != 0) {
					if (oldId != 0 && oldId != i.itemID-1) {
						write ("\"Empty : \";\""+oldId+"\";\""+(i.itemID-1)+"\";;;");
					}
					oldId = i.itemID;
					write ("\""+i.itemID+"\";\""+i.getUnlocalizedName ()+"\";\""+(i.toString().replaceAll("^[\\p{Alnum}]*", "_"))+"\";;;");
				}
			}
			
			System.out.println ("===================================================");
			System.out.println ("");
			
			displayTextBuilder.append("Export : " + fileRead.getAbsolutePath());
			
			fileOut.close ();
			
		} catch (Exception e) {
			displayTextBuilder.append("Error : " + e.getMessage());
			System.out.println ("Error : " + e.getMessage());
		}

		GuiModScreen.show(GuiApiHelper.makeTextDisplayAndGoBack(
			"Result : ", displayTextBuilder.toString(),
			"Back", false));
		
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	/**
	 * This is the method that will be called if you press the
	 * "Display Settings" button. It doesn't have to be public, so you can make
	 * this private and it will work. However, since we are calling this from a
	 * button, it needs to return void.
	 */
	public void dumpID_TXT () {
		
		System.out.println ("Dump all ID TXT");
		
		StringBuilder displayTextBuilder = new StringBuilder();
		File fileRead = new File (Minecraft.getMinecraftDir(), "Export all id.txt");
		
		
		try {
			fileOut = new FileWriter (fileRead);

			write ("\n\n===================================================");
			write ("Block list :");
			write ("===================================================\n");
			
			int oldId = 0;
			for (Block b : Block.blocksList) {
				if (b != null && b.blockID != 0) {
					if (oldId != 0 && oldId != b.blockID-1) {
						write ("\nEmpty : "+oldId+" - "+(b.blockID-1)+"\n");
					}
					oldId = b.blockID;
					write (padRight (""+b.blockID, 10) + ": "+padRight (b.getUnlocalizedName (), 35)+": "+padRight (b.toString().replaceAll("^[\\p{Alnum}]*", "_"), 60));
				}
			}
	
			write ("\n\n===================================================");
			write ("Item list :");
			write ("===================================================\n");
			
			oldId = 0;
			for (Item i : Item.itemsList) {
				if (i != null && i.itemID != 0) {
					if (oldId != 0 && oldId != i.itemID-1) {
						write ("\nEmpty : "+oldId+" - "+(i.itemID-1)+""+"\n");
					}
					oldId = i.itemID;
					write (padRight (""+i.itemID, 10) + ": "+padRight (i.getUnlocalizedName (), 35)+": "+padRight (i.toString().replaceAll("^[\\p{Alnum}]*", "_"), 60));
				}
			}
			
			System.out.println ("===================================================");
			System.out.println ("");
			
			displayTextBuilder.append("Export : " + fileRead.getAbsolutePath());
			
			fileOut.close ();
			
		} catch (Exception e) {
			displayTextBuilder.append("Error : " + e.getMessage());
			System.out.println ("Error : " + e.getMessage());
		}
		
		GuiModScreen.show(GuiApiHelper.makeTextDisplayAndGoBack(
			"Result : ", displayTextBuilder.toString(),
			"Back", false)
		);
		
	}
	
}
