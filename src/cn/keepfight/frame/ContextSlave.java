package cn.keepfight.frame;

public interface ContextSlave {

	public void onDelete();

	/**
	 * 使得该子面板获得焦点
	 */
	public void requestFocus();

	public void show();
}
