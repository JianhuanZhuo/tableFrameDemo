package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;

public interface ContextSlave {

	public void onDelete();

	/**
	 * 使得该子面板获得焦点
	 */
	public void showup();

	public void close();

	public ContextMaster getContextMaster();

	public void setContextMaster(ContextMaster master);

	public void reSetSource(DataSource resDataSource) throws InvalidSourceException, IOException ;
}
