package cn.keepfight.frame.chain.operator;

import java.io.File;
import java.util.List;

import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TextResouce;
import cn.keepfight.operator.AbstractOperator;
import javafx.stage.FileChooser;

/**
 * ����������ڲ����ӣ����ڴ򿪱����ļ������һ�������ļ���Դ
 * @author Tom
 *
 */
public class OpenTextOperaor extends AbstractOperator<ChainTStage>{

	File targetFile;
	ChainTStage tStage;

	public OpenTextOperaor(ChainTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 333; }
	@Override public String getName() { return "open"; }
	@Override public String getLabel() { return "��"; }
	@Override public String getIcon() { return "open.png"; }
	@Override public String getTips() { return "��һ�������ı��ļ���Ϊ�ļ���Դ"; }
	@Override public String getDescription() { return "˫��������Ի�ȡ�Ա����ļ����������ƶ�������������"
			+ "��������ѡ�������ʻ��������Կ�ʼ�����б�Ҫ����¼��Ȼ���������ļ���λ��֮һ��"
			+ "Ҫ����µ�λ�ã��뵥�����λ�á�����б���ʾ���κ��豸����򿪵��ļ���������б��ȡʵ�ã�"
			+ "�����Դ���ɾ���ļ���ֻ���Ҽ������ļ�����Ȼ��ѡ����б���ɾ���� "
			+ "�����Զ�����б��н����������ġ���Ҫ�˽���ϸ��Ϣ��������Զ������ʹ�õ��ļ��б�"
			+ "�������ʹ���б��е����ʹ�õ��ļ������������ǽ�ֱ�Ӳ�������ļ�������"
			+ "ʹ�� Ctrl + F12 ֱ�Ӵ򿪴򿪶Ի���"; }

	@Override
	public List<Resource> onAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("���ļ�");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
		targetFile = fileChooser.showOpenDialog(tStage);
		if (targetFile != null) {
			Resource newResource = new TextResouce(targetFile);
			tStage.getPaneVC().addResource(newResource);
	    }
		return null;
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource res = super.generateResource();
		return res;
	}

	@Override
	public void setTStage(ChainTStage tStage) {
		this.tStage = tStage;
	}

}
