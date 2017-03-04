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
		J extends PaneController> extends Stage{

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
	protected J paneVC;

	/**
	 * 根节点
	 */
	@FXML
	protected BorderPane root;

	/**
	 * 在面板初始化后调用接口，给用户自定义的接口。
	 */
	protected abstract void fixAfter();


	public void setSource(T source) throws InvalidSourceException {
		source.checkValid();
		//@TODO 作reload操作
		this.source = source;
	}

	public T getSource() { return source; }

	/**
	 * 使用指定的数据源初始化面板
	 * @param source 指定的数据源
	 * @throws InvalidSourceException 数据源无效异常
	 * @throws IOException IO异常
	 */
	public void InitSource(T source) throws InvalidSourceException, IOException {
		source.checkValid();
		this.source = source;

		loadRootView();

		//生成菜单视图
		initializeMenu();
		//生成内容视图与数据加载器
		initializeContent();

		setTitleWithType(source.getSourceIDName());

		//为画板设置数据源
		paneVC.setDataSource(source);
		paneVC.load();

		//调用用户自定义接口
		fixAfter();
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

		//TODO 添加类型图标
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
	}

	@SuppressWarnings("unchecked")
	void initializeContent(){
		//初始化，加载画板和添加内容加载器
		paneVC = (J) ContentFactory.generateContentVC(source.getSourceType());
		//@FIXME 这里可能会出BUG
		root.setCenter(paneVC.getNode());
	}
}
