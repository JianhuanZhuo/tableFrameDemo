package cn.keepfight.frame.content.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视图池类.
 * @TODO 下次改成泛化
 * @author Tom
 *
 */
public class ViewPool {

	/**
	 * 视图池
	 */
	private List<String> viewUrlList = new ArrayList<>();

	/**
	 * 构造视图池对象
	 * @param viewList 对象初始化所拥有的试图
	 */
	public ViewPool(String ... viewList) {
		for (String view : viewList) {
			if (view!=null && !view.equals("")) {
				viewUrlList.add(view);
			}
		}
	}

	/**
	 * 获得首选视图
	 * @return 视图URL
	 */
	public String getPriprorViewURL() {
		if (viewUrlList.size()!=0) {
			return viewUrlList.get(0);
		}
		return null;
	}
}
