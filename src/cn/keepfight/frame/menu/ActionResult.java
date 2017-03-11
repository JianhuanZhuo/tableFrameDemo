package cn.keepfight.frame.menu;

import java.util.List;

import cn.keepfight.frame.chain.Resource;

/**
 * ����ִ�ж��������װ��
 * @author Tom
 *
 */
public class ActionResult {
	/**
	 * �����Դ
	 */
	List<Resource> resList;

	/**
	 * �Ƿ�Ϊ���ض���
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
