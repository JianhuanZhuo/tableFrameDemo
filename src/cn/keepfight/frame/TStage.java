package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.controller.MenuViewController;
import cn.keepfight.frame.controller.PaneController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 *�����
 *<pre>
 *TStage tStage = new <M extends TStage>;
 *tStage.InitSource(??);//����Ҫ��һ����
 *tStage.show();
 *</pre>
 * @author Tom
 *
 * @param <T> ����Դ����
 * @param <K> �˵���ͼ����������
 */
public abstract class TStage<T extends DataSource, K extends MenuViewController,
		J extends PaneController> extends Stage{

	//��Ҫ��������ͨ������

	/**
	 * �������չʾ������Դ
	 */
	private T source;

	/**
	 * �˵���ͼ������
	 */
	protected K menuVC;

	/**
	 * ������ͼ������
	 */
	protected J paneVC;

	/**
	 * ���ڵ�
	 */
	@FXML
	protected BorderPane root;

	/**
	 * ������ʼ������ýӿڣ����û��Զ���Ľӿڡ�
	 */
	protected abstract void fixAfter();


	public void setSource(T source) throws InvalidSourceException {
		source.checkValid();
		//@TODO ��reload����
		this.source = source;
	}

	public T getSource() { return source; }

	/**
	 * ʹ��ָ��������Դ��ʼ�����
	 * @param source ָ��������Դ
	 * @throws InvalidSourceException ����Դ��Ч�쳣
	 * @throws IOException IO�쳣
	 */
	public void InitSource(T source) throws InvalidSourceException, IOException {
		source.checkValid();
		this.source = source;

		loadRootView();

		//���ɲ˵���ͼ
		initializeMenu();
		//����������ͼ�����ݼ�����
		initializeContent();

		setTitleWithType(source.getSourceIDName());

		//Ϊ������������Դ
		paneVC.setDataSource(source);
		paneVC.load();

		//�����û��Զ���ӿ�
		fixAfter();
	}

	private void loadRootView() throws IOException{
		root = (BorderPane)(ViewPathUtil.getLoader("StageView.fxml").load());
		Scene scene = new Scene(root);
		setScene(scene);
		setTitle("�½�δ�������");
	}

	/**
	 * ���ô���������������ı���
	 * @param title ����������
	 */
	public void setTitleWithType(String title) {
		setTitle("["+source.getSourceType().getTypeName_cn()+"���] "+title);

		//TODO �������ͼ��
	}

	@SuppressWarnings("unchecked")
	void initializeMenu(){
		//��ʼ���˵�
		menuVC = (K) MenuFactory.generateMenuVC(source.getSourceType());
		if (menuVC==null) {
			System.out.println("fail to load!");
			System.exit(0);
		}
		//@FIXME ������ܻ��BUG
		root.setTop(menuVC.getNode());
		menuVC.getNode().setStyle("-fx-border-color:grey;-fx-border-width:0 0 1 0;");
	}

	@SuppressWarnings("unchecked")
	void initializeContent(){
		//��ʼ�������ػ����������ݼ�����
		paneVC = (J) ContentFactory.generateContentVC(source.getSourceType());
		//@FIXME ������ܻ��BUG
		root.setCenter(paneVC.getNode());
	}
}
