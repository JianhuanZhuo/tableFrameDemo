package cn.keepfight.frame;

import cn.keepfight.frame.content.source.DataSource;
import javafx.stage.Stage;

/**
 *
 *�����
 * @author Tom
 *
 * @param <T> ����Դ����
 */
public abstract class TStage<T extends DataSource> extends Stage{

	//��Ҫ��������ͨ������

	// ����Դ
	private T source;

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
		//֪ͨ���ݼ��������¼���
	}

	public void setTitleWithType(String title) {

	}
}
