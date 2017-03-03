package cn.keepfight.frame.content;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.controller.ChainPaneController;
import cn.keepfight.frame.controller.PaneController;
import cn.keepfight.frame.controller.TablePaneController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXMLLoader;

public class ContentFactory {

	/**
	 * �������������ݲ������ͽ��й��졣
	 * @return �˵���ͼ����������
	 */
	public static PaneController generateContentC(DataSourceType type) {
		switch (type) {
		case TABLE:
			return new TablePaneController();
		case OPERATORCHAIN:
			return new ChainPaneController();
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
	public static PaneController generateContentVC(DataSourceType type) {
		FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView(type.getContentViewPool().getPriprorViewURL()));
		PaneController res = generateContentC(type);
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
