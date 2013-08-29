package mods.middumper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.logging.Level;

import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;

public class VersionChecker extends Thread {
	
	public static String URL_CHECKER = "";
	private static VersionChecker _intance;

	private Object _mod = null;
	private String _message = null;
	private String _type = "";
	
	public static VersionChecker getInstance () {
		if (_intance == null) {
			_intance = new VersionChecker();
		}
		return _intance;
	}
	
	protected VersionChecker () {}

	public void check (Object mod) {
		_mod = mod;
		run ();
	}
	
	/**
	 * Renvoie la version du MOD
	 * @return String
	 */
	private String _getVersion () {
		String version = "0.0.0 [DEV]";
		
		for (Annotation annotation : this._mod.getClass().getAnnotations()) {
			if (annotation instanceof Mod) {;
				version = ((Mod)annotation).version();
			}
		}
		
		return version;
	}
	
	/**
	 * Renvoie le modID du MOD
	 * @return String
	 */
	private String _getModid () {
		String modid = "Error";
		
		for (Annotation annotation : this._mod.getClass().getAnnotations()) {
			if (annotation instanceof Mod) {
				modid = ((Mod)annotation).modid();
			}
		}
		
		return modid;
	}
	
	/**
	 * Renvoie la version de Minecraft
	 * @return String
	 */
	private String _getMVersion () {
		String modid = "Error";
		for (Annotation annotation : this._mod.getClass().getAnnotations()) {
			if (annotation instanceof Mod) {
				modid = ((Mod)annotation).acceptedMinecraftVersions();
			}
		}
		
		return modid;
	}
	
	public void run () {
		String player  = Minecraft.getMinecraft().session.username;
		
		try
        {
			URL url = new URL ("http://minecraft-mods.elewendyl.fr/index.php/mmods/default/version?mod="+_getModid ()+"&version="+_getVersion ()+"&player="+player+"&mversion="+_getMVersion ());
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
			String strJSON = bufferedreader.readLine();
			
			JdomParser parser = new JdomParser();
			JsonRootNode root = parser.parse(strJSON);

			try { _message = root.getStringValue("message");  } catch (Exception exception) {}
			try { _type    = root.getStringValue("type");     } catch (Exception exception) {}
			
			if (_type.equals("info")) {
				FMLLog.log("VersionChecker"+_getModid (), Level.INFO, _message);
			} else {
				FMLLog.log("VersionChecker"+_getModid (), Level.WARNING, _message);
			}
			
			
        } catch (Exception exception) {
        	 exception.printStackTrace();
        }
		
		System.exit (0);
	}
	
	
}
