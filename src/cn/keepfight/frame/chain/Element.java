package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.drag.Dragable;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * �����޻�ͼ���߼��ڵ���
 * @author Tom
 *
 */
public abstract class Element extends Region implements Dragable{

	/**
	 * ���Ŀ��ڵ㼯��
	 */
	List<Element> backEles = new ArrayList<Element>();

	/**
	 * ����Ŀ��ڵ�
	 */
	Element frontEle;

	Line line;


	/**
	 * ����Ƿ�Ϊ��ʼ�ڵ�
	 * @return �Ƿ���true�����򷵻�false
	 */
	public boolean isStartEle(){
		return backEles.isEmpty();
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		// ����������
	}
}
