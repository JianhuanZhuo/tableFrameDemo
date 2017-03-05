package cn.keepfight.operator;

import cn.keepfight.frame.chain.OperatorResource;

/**
 * 算子模型的描述类。
 * @author Tom
 *
 */
public abstract class AbstractOperator{

	/**
	 * 获得算子 ID
	 * @return 算子 ID
	 */
	public abstract int getId();

	/**
	 * 获得算子名，指算子的标识名，如列分割算子是 spilt
	 * @return 算子的标识名
	 */
	public abstract String getName();

	/**
	 * 获得算子名，用于显示的，如列分割算子是 "列分割"
	 * @return 用于显示的算子名
	 */
	public abstract String getLabel();

	/**
	 * 获得算子图标
	 * @return 算子图标
	 */
	public abstract String getIcon();

	/**
	 * 获得算子提示字符串
	 * @return 算子提示字符串
	 */
	public abstract String getTips();

	/**
	 * 获得算子释义字符串
	 * @return 算子的解释说明字符串
	 */
	public abstract String getDescription();

	/**
	 * 规定在点击该算子时所指定的动作
	 * @TODO 这个接口的规范貌似还需要多加斟酌
	 */
	public abstract void onAction();

	/**
	 * 生成算子资源
	 * @return 由该算子状态生成的算子资源
	 */
	public abstract OperatorResource generateResource();
}
