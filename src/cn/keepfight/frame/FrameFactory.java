package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.ChainDataSource;
import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import cn.keepfight.frame.operator.OperatorDataSource;

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
		case OPERATOR:
			resTStage = new OperatorTStage();
			((OperatorTStage)resTStage).InitSource((OperatorDataSource)source);
			break;
		case OPERATORCHAIN:
			resTStage = new ChainTStage();
			((ChainTStage)resTStage).InitSource((ChainDataSource)source);
			break;
		default:
			break;
		}
		return resTStage;
	}
}
