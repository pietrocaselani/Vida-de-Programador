package com.pc.programmerslife;

public class Manager {
	private static final String SMALL_COMMIC_LINK = "-150x150.png";
	private static final String MEDIUM_COMMIC_LINK = "-300x300.png";
	
	private static Manager singleton;
	
	public static Manager getInstance() {
		if (singleton == null)
			singleton = new Manager();
		return singleton;
	}
	
	public String getCommicPath(String content) {
		int start = content.indexOf("http://vidadeprogramador.com.br/wp-content/uploads/");
		
		String path = content.substring(start);
		int end = path.indexOf("\"");
		
		path = content.substring(start, end + start);
		
		return path;
	}
	
	public String getSmallCommicPath(String commicPath) {
		int index = commicPath.indexOf(".png");
		
		String result = commicPath.substring(0, index);
		
		return result + SMALL_COMMIC_LINK;
	}
	
	public String getMediumCommicPath(String commicPath) {
		int index = commicPath.indexOf(".png");
		
		String result = commicPath.substring(0, index);
		
		return result + MEDIUM_COMMIC_LINK;
	}
	
	public String getCommicNumber(String commicPath) {
		int s = commicPath.lastIndexOf("a") + 1;
		int e = commicPath.indexOf(".png");
		
		String result = commicPath.substring(s, e);
		
		return result;
	}
}