package cn.keepfight.frame.content.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ͼ����.
 * @TODO �´θĳɷ���
 * @author Tom
 *
 */
public class ViewPool {

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
}
