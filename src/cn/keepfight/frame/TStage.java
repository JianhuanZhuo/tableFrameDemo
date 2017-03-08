package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.menu.MenuViewController;
import cn.keepfight.utils.ImageLoadUtil;
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
		J extends PaneController> extends Stage implements ContextSlave{

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
	private J paneVC;

	/**
	 * ���ڵ�
	 */
	@FXML
	protected BorderPane root;


	ContextMaster master;

	/**
	 * ������ʼ������ýӿڣ����û��Զ���Ľӿڡ�
	 */
	protected abstract void fixAfter();

	/**
	 * ʹ��ָ��������Դ��ʼ�����
	 * @param master ������������
	 * @param source ָ��������Դ
	 * @throws InvalidSourceException ����Դ��Ч�쳣
	 * @throws IOException IO�쳣
	 */
	public void initSource(ContextMaster master, T source) throws InvalidSourceException, IOException {
		source.checkValid();
		this.source = source;

		//���������棬������������޺������������Դ�����޹�
		loadRootView();

		//���ɲ˵���ͼ
		initializeMenu();
		//����������ͼ�����ݼ�����
		initializeContent();

		//���ø����
		setContextMaster(master);

		//���ñ���
		setTitleWithType(source.getSourceIDName());

		//Ϊ������������Դ
		getPaneVC().setDataSource(source);

		getPaneVC().setTStage(this);
		getMenuVC().attachTStage(this);

		//
		getMenuVC().addMenuItem();

		//�����û��Զ���ӿ�
		fixAfter();

		//��ʼ����������
		getPaneVC().load();
	}

	/**
	 *
	 */
	public void reSetSource(T source) throws InvalidSourceException, IOException {
		source.checkValid();
		//�������Դ�Ƿ�ͬ���ͣ�����ͬ������Ӧ�ø������
		if (this.source.getClass().equals(source.getClass())) {
			this.source = source;

			//���ñ���
			setTitleWithType(source.getSourceIDName());
			//Ϊ��������������Դ
			getPaneVC().setDataSource(source);

			//�����û��Զ���ӿ�
			fixAfter();

			//��ʼ����������
			getPaneVC().load();
		}else {
			//�������
			close();
			FrameFactory.generateBySource(master, source).show();
		}
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

		//TODO �������ͼ�꣬�ĳ�16*16��, ImageLoadUtil.IMG_SIZE_16
		getIcons().add(ImageLoadUtil.load(source.getSourceType().getIconURL()));
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
		root.setCenter(getPaneVC().getNode());
	}


	public T getSource() {
		return source;
	}

	public J getPaneVC() {
		return paneVC;
	}

	public K getMenuVC() {
		return menuVC;
	}

	@Override
	public ContextMaster getContextMaster() {
		return master;
	}

	@Override
	public void setContextMaster(ContextMaster master) {
		this.master = master;
	}

	@Override
	public void show(DataSource source) throws InvalidSourceException, IOException{
		if(this.getSource()!=source){
			reSetSource((T)source);
		}
		super.show();
		super.requestFocus();
	}
}
