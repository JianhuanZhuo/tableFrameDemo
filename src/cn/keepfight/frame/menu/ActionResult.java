package cn.keepfight.frame.menu;

import java.util.List;

import cn.keepfight.frame.chain.Resource;

/**
 * 算子执行动作结果包装类
 * @author Tom
 *
 */
public class ActionResult {
	/**
	 * 结果资源
	 */
	List<Resource> resList;

	/**
	 * 是否为本地动作
	 */
	boolean localAction = false;

	public ActionResult(List<Resource> resList) {
		this.resList = resList;
	}

	public ActionResult(List<Resource> resList, boolean localAction) {
		this.resList = resList;
		this.localAction = localAction;
	}
}
