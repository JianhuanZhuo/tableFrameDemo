package cn.keepfight.frame.content.source;

/**
 * 数据源类型枚举，这个类型在现在看来也是面板类型
 * @author Tom
 *
 */
public enum DataSourceType {

	TABLE,     // 表格化数据类型
	PICTURE,   // 图片数据类型
	VIDEO,     // 视频数据类型
	TEXT,      // TEXT文本文件数据类型
	FILE,      // 文件数据类型
	URL,       // URL数据类型
	DEVICE,    // 设备数据类型
	CODE,      // 代码数据类型
	OPERATOR,  // 算子数据类型，也就是存在把算子当作了一种数据类型的可能，这种可能是为查看某算子而准备的。
	OPERATORCHAIN,// 算子链数据类型，这是为算子链面板而准备的。
	OTHER      // 其他数据类型
}
