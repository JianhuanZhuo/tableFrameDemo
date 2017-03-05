package cn.keepfight.frame.controller;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.scene.layout.BorderPane;

/**
 * ��������������.
 * @author Tom
 *
 */
public abstract class PaneController{

	/**
	 * ��ջ�������
	 */
	public abstract void clearContent();

	public abstract BorderPane getNode();

	/**
	 * ָʾ���忪ʼ���ػ��������
	 */
	public abstract void load();

	/**
	 * Ϊ������������Դ
	 * @param source ����Դ
	 */
	public abstract void setDataSource(DataSource source) throws InvalidSourceException;

	@SuppressWarnings("rawtypes")
	public abstract void setTStage(TStage tStage);
}
