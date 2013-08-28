package mods.middumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import sharose.mods.guiapi.GuiApiHelper;
import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.ModSettingScreen;
import sharose.mods.guiapi.ModSettings;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;

/**
 * This mod is an id dumper for minecraft
 * 
 * @author Damien Duboeuf <Smeagolworms4>
 */
@Mod(name = "Minecraft ID Dumper", modid = "MIDDumper", version = "1.0.1", acceptedMinecraftVersions = "1.5.2", useMetadata = true)
public class MIDDumper {
	
	FileWriter fileOut;
	
	/** The mod screen. */
	public ModSettingScreen myModScreen;
	/** The settings. */
	public ModSettings mySettings;
	
	@Init
	public void load (FMLInitializationEvent event) {
		
		VersionChecker vchecker = VersionChecker.getInstance();
		vchecker.setMod (this);
		vchecker.check ();
		
		mySettings = new ModSettings("MIDDumper");
		myModScreen = new ModSettingScreen("Minecraft ID Dumper");
		myModScreen.append(GuiApiHelper.makeButton("Dump all ID to TXT", "dumpID_TXT", this, true));
		myModScreen.append(GuiApiHelper.makeButton("Dump all ID to CSV", "dumpID_CSV", this, true));
		mySettings.load();
	}

	/**
	 * Applique un str_pad à droite d'un element
	 * @param String s
	 * @param int n
	 * @return String
	 */
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	/**
	 * @param File file
	 * @param String s
	 * @throws IOException
	 */
	private void write (File file, String s) throws IOException {
		
		FileWriter fileOut = new FileWriter (file);
		fileOut.write (s+"\n");
		fileOut.close ();
		
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
		String dump = "";
		
		try {
			
			dump += "\"Block list : \";;;;;";
			
			int oldId = 0;
			for (Block b : Block.blocksList) {
				if (b != null && b.blockID != 0) {
					if (oldId != 0 && oldId != b.blockID-1) {
						dump += "\"Empty : \";\""+oldId+"\";\""+(b.blockID-1)+"\";;;";
					}
					oldId = b.blockID;
					dump += "\""+b.blockID+"\";\""+b.getUnlocalizedName ()+"\";\""+b.toString().replaceAll("^[\\p{Alnum}]*", "_")+"\";;;";
				}
			}
			
			dump += "\"Item list : \";;;;;";
			
			oldId = 0;
			for (Item i : Item.itemsList) {
				if (i != null && i.itemID != 0) {
					if (oldId != 0 && oldId != i.itemID-1) {
						dump += "\"Empty : \";\""+oldId+"\";\""+(i.itemID-1)+"\";;;";
					}
					oldId = i.itemID;
					dump += "\""+i.itemID+"\";\""+i.getUnlocalizedName ()+"\";\""+(i.toString().replaceAll("^[\\p{Alnum}]*", "_"))+"\";;;";
				}
			}
			
			System.out.println ("");
			System.out.println ("");
			System.out.println ("===================================================");
			write (fileRead, dump);
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
		String dump = "";
		
		try {

			dump += "\n\n===================================================";
			dump += "Block list :";
			dump += "===================================================\n";
			
			int oldId = 0;
			for (Block b : Block.blocksList) {
				if (b != null && b.blockID != 0) {
					if (oldId != 0 && oldId != b.blockID-1) {
						dump += "\nEmpty : "+oldId+" - "+(b.blockID-1)+"\n";
					}
					oldId = b.blockID;
					dump += padRight (""+b.blockID, 10) + ": "+padRight (b.getUnlocalizedName (), 35)+": "+padRight (b.toString().replaceAll("^[\\p{Alnum}]*", "_"), 60);
				}
			}
	
			dump += "\n\n===================================================";
			dump += "Item list :";
			dump += "===================================================\n";
			
			oldId = 0;
			for (Item i : Item.itemsList) {
				if (i != null && i.itemID != 0) {
					if (oldId != 0 && oldId != i.itemID-1) {
						dump += "\nEmpty : "+oldId+" - "+(i.itemID-1)+""+"\n";
					}
					oldId = i.itemID;
					dump += padRight (""+i.itemID, 10) + ": "+padRight (i.getUnlocalizedName (), 35)+": "+padRight (i.toString().replaceAll("^[\\p{Alnum}]*", "_"), 60);
				}
			}
			
			write (fileRead, dump);
			System.out.println ("===================================================");
			System.out.println ("");
			
			displayTextBuilder.append("Export : " + fileRead.getAbsolutePath());
			
			
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
