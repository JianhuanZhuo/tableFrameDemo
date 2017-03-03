package cn.keepfight.frame;

import cn.keepfight.frame.content.source.DataSource;
import javafx.stage.Stage;

/**
 *
 *面板类
 * @author Tom
 *
 * @param <T> 数据源类型
 */
public abstract class TStage<T extends DataSource> extends Stage{

	//需要考虑面板间通信问题

	// 数据源
	private T source;

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
		//通知内容加载器重新加载
	}

	public void setTitleWithType(String title) {

	}
}
