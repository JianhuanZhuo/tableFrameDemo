package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.ChainMenuViewController;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.ViewPool;
import cn.keepfight.frame.menu.MenuViewController;
import cn.keepfight.frame.operator.OperatorMenuViewController;
import cn.keepfight.frame.table.TableMenuViewController;
import cn.keepfight.frame.text.TextMenuViewController;
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
		case CHAIN:
			return new ChainMenuViewController();
		case OPERATOR:
			return new OperatorMenuViewController();
		case TEXT:
			return new TextMenuViewController();
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
		FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView(ViewPool.getMenuViewPool().getPriprorViewURL()));
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
