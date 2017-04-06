package cn.keepfight.operator;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.menu.ActionResult;

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
	 * 规定在点击该算子时所指定的动作<br/>
	 * 这个动作需要注意的是，执行一次动作后，这个算子如果保存有状态（内部变量），那么下次执行时可能会受到这个算子的影响，因为算子没变过
	 * @TODO 这个接口的规范貌似还需要多加斟酌
	 * @return 算子运算结束产生的资源结果，不产生资源则返回null
	 * @exception Exception 算子运行中产生的各种异常
	 */
	public abstract ActionResult onAction() throws Exception ;

	/**
	 * 生成算子资源
	 * @return 由该算子状态生成的算子资源
	 */
	public OperatorResource generateResource(){
		OperatorResource res = new OperatorResource(getId(), getName());
		res.setDescription(getDescription());
		res.setLabel(getLabel());
		res.setIcon(getIcon());
		res.setInputResource(new String[0]);
		res.setOutputResource(new String[0]);
		res.setParams(new String[0]);
		return res;
	}
}
