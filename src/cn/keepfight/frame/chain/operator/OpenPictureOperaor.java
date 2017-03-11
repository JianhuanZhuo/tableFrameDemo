package cn.keepfight.frame.chain.operator;

import java.io.File;
import java.net.MalformedURLException;

import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.PictureResouce;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * ����������ڲ����ӣ����ڴ򿪱����ļ������һ�������ļ���Դ
 * @author Tom
 *
 */
public class OpenPictureOperaor extends AbstractOperator{

	ChainTStage tStage;

	public OpenPictureOperaor(ChainTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 333; }
	@Override public String getName() { return "picture_import"; }
	@Override public String getLabel() { return "����ͼƬ"; }
	@Override public String getIcon() { return "picture.png"; }
	@Override public String getTips() { return "��һ������ͼƬ�ļ���Ϊ�ļ���Դ"; }
	@Override public String getDescription() { return "˫��������Ի�ȡ�Ա����ļ����������ƶ�������������"
			+ "��������ѡ�������ʻ��������Կ�ʼ�����б�Ҫ����¼��Ȼ���������ļ���λ��֮һ��"
			+ "Ҫ����µ�λ�ã��뵥�����λ�á�����б���ʾ���κ��豸����򿪵��ļ���������б��ȡʵ�ã�"
			+ "�����Դ���ɾ���ļ���ֻ���Ҽ������ļ�����Ȼ��ѡ����б���ɾ���� "
			+ "�����Զ�����б��н����������ġ���Ҫ�˽���ϸ��Ϣ��������Զ������ʹ�õ��ļ��б�"
			+ "�������ʹ���б��е����ʹ�õ��ļ������������ǽ�ֱ�Ӳ�������ļ�������"
			+ "ʹ�� Ctrl + F12 ֱ�Ӵ򿪴򿪶Ի���"; }

	@Override
	public ActionResult onAction() {
		//@TODO ���ɿ���ѡ�񱾵ػ��������
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("��ͼƬ�ļ�");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("֧�ֵ�ͼƬ�ļ���ʽ", "*.PNG", "*.GIF", "*.JPEG", "*.JPG", "*.BMP"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG �ļ� (*.PNG)", "*.PNG"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF �ļ� (*.GIF)", "*.GIF"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG �ļ� (*.JPEG)", "*.JPEG", "*.JPG"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP �ļ� (*.BMP)", "*.BMP"));
		File targetFile = fileChooser.showOpenDialog(tStage);
		if (targetFile != null) {
			Resource newResource;
			try {
				newResource = new PictureResouce(targetFile.toURI().toURL().toString());
				tStage.getPaneVC().addResource(newResource);
			} catch (MalformedURLException e) {
				new Alert(AlertType.ERROR, "ָ��·������", ButtonType.CLOSE).showAndWait();
				e.printStackTrace();
			}
	    }
		return null;
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource res = super.generateResource();
		return res;
	}
}
