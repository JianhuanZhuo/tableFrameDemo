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
	TABLE("表格", 	new ViewPool("TablePane.fxml"), 	"table.png"),

	/**
	 * 图片数据类型
	 */
	PICTURE("图片", 	new ViewPool(), 					"picture.png"),
	VIDEO("视频", 	new ViewPool(), 					"video.png"),     // 视频数据类型
	TEXT("文本",  	new ViewPool("TextPane.fxml"),	 	"text.png"),      // TEXT文本文件数据类型
	FILE("文件", 	new ViewPool(), 					"file.png"),      // 文件数据类型
	FILES("文件群", 	new ViewPool("FilesPane.fxml"), 	"files.png"),     // 文件群数据类型
	URL("链接", 		new ViewPool(), 					"URL.png"),       // URL数据类型
	DEVICE("设备", 	new ViewPool(), 					"device.png"),    // 设备数据类型
	CODE("代码", 	new ViewPool(), 					"code.png"),      // 代码数据类型
	OPERATOR("算子", new ViewPool("OperatorPane.fxml"), 	"operator.png"),  // 算子数据类型，也就是存在把算子当作了一种数据类型的可能，这种可能是为查看某算子而准备的。
	CHAIN("算子链",	new ViewPool("ChainPane.fxml"), 	"operatorchain.png"),// 算子链数据类型，这是为算子链面板而准备的。
	OTHER("其他", 	new ViewPool(), 					"other.png");      // 其他数据类型

	private String typeName_cn;
	private ViewPool contentPool;//内容的视图池
	private String iconURL;//图标URL


	private DataSourceType(String typeName_cn, ViewPool contentPool, String iconURL) {
		this.typeName_cn = typeName_cn;
		this.contentPool = contentPool;
		this.iconURL = iconURL;
	}

	public String getTypeName_cn() {
		return typeName_cn;
	}

	public ViewPool getContentViewPool() {
		return contentPool;
	}

	public String getIconURL() {
		return iconURL;
	}
}
