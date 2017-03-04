package cn.keepfight.frame.content.source;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ͼ����.
 * @TODO �´θĳɷ���
 * @author Tom
 *
 */
public class ViewPool {

	private static ViewPool menuViewPool = new ViewPool("MenuView.fxml");

	/**
	 * ��ͼ��
	 */
	private List<String> viewUrlList = new ArrayList<>();

	/**
	 * ������ͼ�ض���
	 * @param viewList �����ʼ����ӵ�е���ͼ
	 */
	public ViewPool(String ... viewList) {
		for (String view : viewList) {
			if (view!=null && !view.equals("")) {
				viewUrlList.add(view);
			}
		}
	}

	/**
	 * �����ѡ��ͼ
	 * @return ��ͼURL
	 */
	public String getPriprorViewURL() {
		if (viewUrlList.size()!=0) {
			return viewUrlList.get(0);
		}
		return null;
	}

	public static ViewPool getMenuViewPool() {
		return menuViewPool;
	}
}
