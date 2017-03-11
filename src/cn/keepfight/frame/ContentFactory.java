package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.ChainPaneController;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.files.FilesPaneController;
import cn.keepfight.frame.operator.OperatorPaneController;
import cn.keepfight.frame.picture.PicturePaneController;
import cn.keepfight.frame.table.TablePaneController;
import cn.keepfight.frame.text.TextPaneController;
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
		case CHAIN:
			return new ChainPaneController();
		case OPERATOR:
			return new OperatorPaneController();
		case TEXT:
			return new TextPaneController();
		case FILES:
			return new FilesPaneController();
		case PICTURE:
			return new PicturePaneController();
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
