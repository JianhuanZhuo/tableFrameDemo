package cn.keepfight.frame.menu;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * �˵����������. �߶�<br/>
 * <ol>
 * <li>p32=40</li>
 * <li>tp16=30</li>
 * <li>tp32-top=59
 * <li>
 * <li>tp32=44
 * <li>
 * </ol>
 *
 * @author Tom
 *
 */
public class MenuItemController{

	@FXML
	private ImageView pic;

	@FXML
	private Button btn;

	@SuppressWarnings("rawtypes")
	private TStage tStage;

	/**
	 * �˵���ť����
	 */
	private MenuItemType itemType = MenuItemType.UNKNOW;

	/**
	 * �˵�������ʾ������
	 */
	private AbstractOperator operator;


	public MenuItemController() {
	}

	@SuppressWarnings("rawtypes")
	public void attachTStage(TStage tStage){
		this.tStage = tStage;
	}
	public void setMenuItemType(MenuItemType itemType){
		this.itemType = itemType;
	}

	public void setOperator(AbstractOperator operator){
		this.operator = operator;
		setBtnText(operator.getLabel());
		setPic(operator.getIcon());
		setTipText(operator.getTips());
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ActionResult actionResult;
				try {
					actionResult = operator.onAction();
				} catch (Exception e) {
					e.printStackTrace();
					return ;
				}
				if (actionResult==null) {
					//�������ӣ������ɽ��
					return;
				}
				if (!actionResult.localAction) {
					if (actionResult.resList.size()==1) {
						tStage.getContextMaster()
						.doOperate(tStage, operator.generateResource(), actionResult.resList.get(0));
					}
				}
				//@TODO ���������
			}
		});
	}

	@FXML
	public void initialize() {
	}

	/**
	 * ��ÿ���������ʾ������ģ�Ͷ���
	 * @return ����ģ�Ͷ���
	 */
	public AbstractOperator getOperator() {
		return operator;
	}

	/**
	 * Ϊ�ò˵���ť��������
	 *
	 * @param btnText
	 *            �����õ�����
	 */
	public void setBtnText(String btnText) {
		if (btnText != null) {
			if (itemType.hasText()) {
				btn.setText(btnText);
			}
		}
	}

	/**
	 * ��ð�ť������
	 *
	 * @return ���ò˵��ް�ť���򷵻�null�����򷵻�һ���ַ�����ע������Ǹ����ַ�����
	 */
	public String getBtnText() {
		return itemType.hasText() ? btn.getText() : null;
	}

	/**
	 * ʹ��ͼ�����������á�<br/>
	 * �÷�����ʹ��{@link cn.keepfight.utils.ImageLoaderUtil.load(String, int)}
	 * ���м���<br/>
	 *
	 * @param icon
	 *            ͼ����
	 * @see cn.keepfight.utils.ImageLoadUtil
	 */
	public void setPic(String icon) {
		setPic(ImageLoadUtil.load(icon, itemType.picSize()));
		;
	}

	/**
	 * ָ��ͼƬ��Ϊ�ò˵���ťͼ�ꡣ
	 *
	 * @param image
	 *            ָ��ͼƬ
	 */
	public void setPic(Image image) {
		if (hasPic()) {
			pic.setImage(image);
		}
	}

	public boolean hasPic() {
		return itemType.hasPicture();
	}

	/**
	 * ������ʾ��Ϣ
	 *
	 * @param tip
	 *            �����õ���ʾ��Ϣ
	 */
	public void setTipText(String tip) {
		if (tip != null) {
			btn.setTooltip(new Tooltip(tip));
		}
	}
}
