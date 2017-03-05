package cn.keepfight.frame;

import java.util.List;

import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.content.source.DataSource;

/**
 * 内容管理者接口
 * @author Tom
 *
 */
public interface ContextMaster {

	public void update(ContextSlave slave);

	/**
	 * 算子执行运算结果汇报<br/>
	 * 重载方法，单输出算子执行
	 * @param slave 执行该运算的子面板
	 * @param operator 操作算子资源，需要在算子链中生成图标的
	 * @param result 运算结果
	 * @return 运算结果在算子链面板中生成的数据源
	 */
	public DataSource doOperate(ContextSlave slave, Resource operator, Resource result);

	/**
	 * 算子执行运算结果汇报<br/>
	 * 重载方法，多输出算子执行
	 * @param slave 执行该运算的子面板
	 * @param operator 操作算子资源，需要在算子链中生成图标的
	 * @param results 运算结果集合
	 * @return 运算结果集合在算子链面板中生成的数据源集合，顺序上一致
	 */
	public List<DataSource> doOperate(ContextSlave slave, Resource operator, List<Resource> results);

	/**
	 * 算子执行运算结果汇报<br/>
	 * 重载方法，多输入多输出算子执行
	 * @param slave 执行该运算的子面板集合
	 * @param operator 操作算子资源，需要在算子链中生成图标的
	 * @param results 运算结果集合
	 * @return 运算结果集合在算子链面板中生成的数据源集合，顺序上一致
	 */
	public List<DataSource> doOperate(List<ContextSlave> slaves, Resource operator, List<Resource> results);
}
