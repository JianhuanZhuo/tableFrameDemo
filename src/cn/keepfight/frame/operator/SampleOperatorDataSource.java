package cn.keepfight.frame.operator;

public class SampleOperatorDataSource extends OperatorDataSource{

	@Override
	public String getSourceIDName() {
		return label+"-"+name+"-"+id;
	}

	private int id = 123;//算子ID
	private String name = "filter";//算子名
	private String label = "数据筛选";//算子图标按钮上的名字
	private String icon = "filter.png";//算子图标
	private String description =
			"Excel筛选 是一款方便你对excel文件中不同sheet中的数据进行合并,"
			+ "比较是否重复的软件 在日常工作生活中我们常用excel电子表格来记录数据,"
			+ "有时二组数据需要知道这二组数据合起来,并且去掉重复的,"
			+ "或者,第二组数据有那些在第一组中已经存在.例如移动公司的员工,"
			+ "要知道哪些号码重复,或者将二组号码合并起来去掉重复的记录."
			+ "对于开发人员,作者,系统管理员,来说会省去很多的时间或者减少可能出现的错误."
			+ "目前可以正常使用在 Win2000/Win2003/Winxp/Vista 平台上,无插件!"
			+ "此软件完全免费!";//算子描述信息
	private String[] inputResource = {"wz.tp_info"};//运算输入对象资源
	private String[] outputResource = {"wz.tp_info_view_1"};//运算输出对象资源
	private String[] params = {"mode=1", "by=,&."};

	@Override
	public String[] getInputResource() {
		return inputResource;
	}

	public void setInputResource(String[] inputResource) {
		this.inputResource = inputResource;
	}

	@Override
	public String[] getOutputResource() {
		return outputResource;
	}

	@Override
	public String[] getParams() {
		return params;
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getIcon() {
		return icon;
	}
}
