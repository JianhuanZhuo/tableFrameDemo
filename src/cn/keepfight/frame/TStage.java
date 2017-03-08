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
 *面板类
 *<pre>
 *TStage tStage = new <M extends TStage>;
 *tStage.InitSource(??);//最重要的一步。
 *tStage.show();
 *</pre>
 * @author Tom
 *
 * @param <T> 数据源类型
 * @param <K> 菜单视图控制器类型
 */
public abstract class TStage<T extends DataSource, K extends MenuViewController,
		J extends PaneController> extends Stage implements ContextSlave{

	//需要考虑面板间通信问题

	/**
	 * 面板所需展示的数据源
	 */
	private T source;

	/**
	 * 菜单视图控制器
	 */
	protected K menuVC;


	/**
	 * 画板视图控制器
	 */
	private J paneVC;

	/**
	 * 根节点
	 */
	@FXML
	protected BorderPane root;


	ContextMaster master;

	/**
	 * 在面板初始化后调用接口，给用户自定义的接口。
	 */
	protected abstract void fixAfter();

	/**
	 * 使用指定的数据源初始化面板
	 * @param master 面板所属父面板
	 * @param source 指定的数据源
	 * @throws InvalidSourceException 数据源无效异常
	 * @throws IOException IO异常
	 */
	public void initSource(ContextMaster master, T source) throws InvalidSourceException, IOException {
		source.checkValid();
		this.source = source;

		//加载面板界面，这个界面人畜无害，与具体数据源类型无关
		loadRootView();

		//生成菜单视图
		initializeMenu();
		//生成内容视图与数据加载器
		initializeContent();

		//设置父面板
		setContextMaster(master);

		//设置标题
		setTitleWithType(source.getSourceIDName());

		//为画板设置数据源
		getPaneVC().setDataSource(source);

		getPaneVC().setTStage(this);
		getMenuVC().attachTStage(this);

		//
		getMenuVC().addMenuItem();

		//调用用户自定义接口
		fixAfter();

		//初始化加载数据
		getPaneVC().load();
	}

	/**
	 *
	 */
	public void reSetSource(T source) throws InvalidSourceException, IOException {
		source.checkValid();
		//检查两个源是否同类型，不是同类型则应该更换面板
		if (this.source.getClass().equals(source.getClass())) {
			this.source = source;

			//设置标题
			setTitleWithType(source.getSourceIDName());
			//为画板设置新数据源
			getPaneVC().setDataSource(source);

			//调用用户自定义接口
			fixAfter();

			//初始化加载数据
			getPaneVC().load();
		}else {
			//更换面板
			close();
			FrameFactory.generateBySource(master, source).show();
		}
	}

	private void loadRootView() throws IOException{
		root = (BorderPane)(ViewPathUtil.getLoader("StageView.fxml").load());
		Scene scene = new Scene(root);
		setScene(scene);
		setTitle("新建未命名面板");
	}

	/**
	 * 设置带有类型面板字样的标题
	 * @param title 标题行文字
	 */
	public void setTitleWithType(String title) {
		setTitle("["+source.getSourceType().getTypeName_cn()+"面板] "+title);

		//TODO 添加类型图标，改成16*16，, ImageLoadUtil.IMG_SIZE_16
		getIcons().add(ImageLoadUtil.load(source.getSourceType().getIconURL()));
	}

	@SuppressWarnings("unchecked")
	void initializeMenu(){
		//初始化菜单
		menuVC = (K) MenuFactory.generateMenuVC(source.getSourceType());
		if (menuVC==null) {
			System.out.println("fail to load!");
			System.exit(0);
		}
		//@FIXME 这里可能会出BUG
		root.setTop(menuVC.getNode());
		menuVC.getNode().setStyle("-fx-border-color:grey;-fx-border-width:0 0 1 0;");
	}

	@SuppressWarnings("unchecked")
	void initializeContent(){
		//初始化，加载画板和添加内容加载器
		paneVC = (J) ContentFactory.generateContentVC(source.getSourceType());
		//@FIXME 这里可能会出BUG
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
