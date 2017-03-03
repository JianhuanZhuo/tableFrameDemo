package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import javafx.stage.Stage;

/**
 * 显示面板工厂类，对应 {@link cn.keepfight.frame.content.source.DataSourceType} 进行生成面板实例。
 *
 * @author Tom
 *
 */
public class FrameFactory {

	/**
	 * 指定数据源类型进行生成展示面板
	 * @param source 数据源
	 * @return 展示面板
	 */
	@SuppressWarnings({ "rawtypes" })
	public static TStage generateBySource(DataSource source) throws InvalidSourceException, IOException {
		source.checkValid();
		TStage resTStage = null;
		switch (source.getSourceType()) {
		case TABLE:
			resTStage = new TableTStage();
			((TableTStage)resTStage).InitSource((TableDataSource)source);
			break;
		default:
			break;
		}
		return resTStage;
	}
}
