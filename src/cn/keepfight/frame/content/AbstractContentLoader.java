package cn.keepfight.frame.content;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.scene.Node;

/**
 * 内容加载器抽象类.
 * @author Tom
 * @param <T> 数据源泛型
 *
 */
public abstract class AbstractContentLoader<T extends DataSource, K extends Node>{


	/**
	 * 数据源对象
	 */
	protected T source;

	/**
	 * 数据加载对象
	 */
	protected K node;

	public AbstractContentLoader(T source, K node) throws InvalidSourceException {
		setDataSource(source);
		this.node = node;
	}

	/**
	 * 设置数据源，会对数据源进行一次检查，但不自动加载
	 * @param source 欲设置的数据源
	 * @throws InvalidSourceException 数据源无效异常
	 */
	public void setDataSource(T source) throws InvalidSourceException{
		source.checkValid();
		this.source = source;
		clear();
	}

	public T getSource(){return source;}

	/**
	 * 对内容进行重新加载。
	 */
	public abstract void reload();

	/**
	 * 删除内容加载器的数据源并清空面板内容。
	 */
	public abstract void clear();
}
