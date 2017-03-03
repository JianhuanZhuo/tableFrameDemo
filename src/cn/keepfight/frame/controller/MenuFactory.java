package cn.keepfight.frame.controller;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXMLLoader;

/**
 * �˵���ͼ������������
 * @author Tom
 *
 */
public class MenuFactory {

	/**
	 * �������������ݲ������ͽ��й��졣
	 * @return �˵���ͼ����������
	 */
	public static MenuViewController generateMenuC(DataSourceType type) {
		switch (type) {
		case TABLE:
			return new TableMenuViewController();
		case OPERATORCHAIN:
			return new ChainMenuViewController();
		default:
			break;
		}
		return null;
	}

	/**
	 * ʹ������Դ����������ͼ�Ϳ������������ؿ���������ͼ�ɴӿ������з���
	 * @param type ����Դ����
	 * @return ������
	 */
	public static MenuViewController generateMenuVC(DataSourceType type) {
		FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView(type.getMenuViewPool().getPriprorViewURL()));
		MenuViewController res = generateMenuC(type);
		loader.setController(res);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			res = null;
		}
		return res;
	}
}
