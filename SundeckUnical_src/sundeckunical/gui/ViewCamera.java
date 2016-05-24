package sundeckunical.gui;

import sundeckunical.core.Player;

public class ViewCamera {
	
	private static int viewCamera = 600;
	private int offSetMinY = 0;
	private int offSetMaxY;
	private int camY;
	
	public ViewCamera(int height) {
		setOffSetMaxY(height - viewCamera);
	}
	
	public static int getViewCamera() {
		return viewCamera;
	}

	public static void setViewCamera(int viewCamera) {
		ViewCamera.viewCamera = viewCamera;
	}

	public int getOffSetMinY() {
		return offSetMinY;
	}

	public void setOffSetMinY(int offSetMinY) {
		this.offSetMinY = offSetMinY;
	}

	public int getOffSetMaxY() {
		return offSetMaxY;
	}

	public void setOffSetMaxY(int offSetMaxY) {
		this.offSetMaxY = offSetMaxY;
	}

	public int getCamY() {
		return camY;
	}

	public void setCamY(int camY) {
		this.camY = camY;
	}

	public void updateOffset(Player player) {
		camY = player.getY() - (viewCamera-(viewCamera/5));
		if (camY > offSetMaxY)
		    camY = offSetMaxY;
		else if (camY < offSetMinY)
			camY = offSetMinY;
		}
}
