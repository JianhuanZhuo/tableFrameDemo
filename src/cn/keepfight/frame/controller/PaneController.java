package cn.keepfight.frame.controller;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * ��������������.
 * @author Tom
 *
 */
public abstract class PaneController {

	/**
	 * ����ĸ�����
	 */
	@FXML
	BorderPane pane;

	/**
	 * ��ջ�������
	 */
	public abstract void clearContent();

	public BorderPane getNode() {
		return pane;
	}

	/**
	 * ָʾ���忪ʼ���ػ��������
	 */
	public abstract void load();

	/**
	 * Ϊ������������Դ
	 * @param source ����Դ
	 */
	public abstract void setDataSource(DataSource source) throws InvalidSourceException;
}
