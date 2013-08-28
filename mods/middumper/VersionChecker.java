package mods.middumper;

import net.minecraft.client.Minecraft;

public class VersionChecker {
	
	public static String URL_CHECKER = "";
	private static VersionChecker _intance;

	private Object _mod = null;
	private String message = null;
	
	public static VersionChecker getInstance () {
		if (_intance == null) {
			_intance = new VersionChecker();
		}
		return _intance;
	}
	
	protected VersionChecker () {}

	public void setMod(Object mod) {
		_mod = mod;
	}
	
	private String _getVersion () {
		String version = "0.0.0 [DEV]";
		// TODO recupérer la version
		return version;
	}

	private String _getModid () {
		String _getModid = "Error";
		// TODO recupérer le mod id
		return _getModid;
	}
	
	public void check() {
		String player  = Minecraft.getMinecraft().session.username;
		
		String url = "http://minecraft-mods.elewendyl.fr/index.php/mmods/default/version?mod="+_getModid ()+"&version="+_getVersion ()+"&player="+player;
		System.out.println("Call : "+url);
	}
}
