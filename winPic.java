package SNA;

import java.io.File;

public class winPic {
	public void callWeb() {
		try {
			String url = "file:///Users/huoshan/Workspaces/MyEclipse%202015/ssna/WebRoot/www/relaMap.html";
			java.net.URI uri = java.net.URI.create(url);
			// 获取当前系统桌面扩展
			java.awt.Desktop dp = java.awt.Desktop.getDesktop();
			// 判断系统桌面是否支持要执行的功能
			if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
				dp.browse(uri);// 获取系统默认浏览器打开链接
			}
		} catch (java.lang.NullPointerException e) {
			// 此为uri为空时抛出异常
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// 此为无法获取系统默认浏览器
			e.printStackTrace();
		}
	}
}
