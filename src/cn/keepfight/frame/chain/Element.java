package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.drag.Dragable;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * 有向无环图，逻辑节点类
 * @author Tom
 *
 */
public abstract class Element extends Region implements Dragable{

	/**
	 * 入度目标节点集合
	 */
	List<Element> backEles = new ArrayList<Element>();

	/**
	 * 出度目标节点
	 */
	Element frontEle;

	Line line;


	/**
	 * 检查是否为起始节点
	 * @return 是返回true，否则返回false
	 */
	public boolean isStartEle(){
		return backEles.isEmpty();
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		// 更新连接线
	}
}
