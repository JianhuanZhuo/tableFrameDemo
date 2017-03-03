package cn.keepfight.frame.content.source;

/**
 * 数据源类型枚举，这个类型在现在看来也是面板类型
 * @author Tom
 *
 */
public enum DataSourceType {

	/**
	 *表格化数据类型
	 */
	TABLE("表格", new ViewPool("TableMenuView.fxml"), new ViewPool("TablePanel.fxml")),

	/**
	 * 图片数据类型
	 */
	PICTURE("图片", new ViewPool(), new ViewPool()),
	VIDEO("视频", new ViewPool(), new ViewPool()),     // 视频数据类型
	TEXT("文本", new ViewPool(), new ViewPool()),      // TEXT文本文件数据类型
	FILE("文件", new ViewPool(), new ViewPool()),      // 文件数据类型
	URL("链接", new ViewPool(), new ViewPool()),       // URL数据类型
	DEVICE("设备", new ViewPool(), new ViewPool()),    // 设备数据类型
	CODE("代码", new ViewPool(), new ViewPool()),      // 代码数据类型
	OPERATOR("算子", new ViewPool(), new ViewPool()),  // 算子数据类型，也就是存在把算子当作了一种数据类型的可能，这种可能是为查看某算子而准备的。
	OPERATORCHAIN("算子链", new ViewPool(), new ViewPool()),// 算子链数据类型，这是为算子链面板而准备的。
	OTHER("其他", new ViewPool(), new ViewPool());      // 其他数据类型

	private String typeName_cn;
	private ViewPool menuPool;//菜单的视图池
	private ViewPool contentPool;//内容的视图池


	private DataSourceType(String typeName_cn, ViewPool menuPool, ViewPool contentPool) {
		this.typeName_cn = typeName_cn;
		this.menuPool = menuPool;
		this.contentPool = contentPool;
	}

	public String getTypeName_cn() {
		return typeName_cn;
	}

	public ViewPool getMenuViewPool() {
		return menuPool;
	}

	public ViewPool getContentViewPool() {
		return contentPool;
	}
}
