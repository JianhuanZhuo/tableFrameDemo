package cn.keepfight.operator;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.Resource;

/**
 * 算子命令包装类
 * @author Tom
 *
 */
public class OrderPackage {

	public boolean localOperate = false;
	public List<Resource> resources = new ArrayList<Resource>();
}
