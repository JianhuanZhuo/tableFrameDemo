package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.picture.PictureDataSource;
import javafx.scene.image.Image;

public class PictureResouce extends Resource{

	String pictureURL;

	public PictureResouce(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	@Override
	public DataSourceType getDataSourceType() {
		return DataSourceType.PICTURE;
	}

	@Override
	public DataSource generateDataSource() {
		return new PictureDataSource() {

			@Override
			public String getSourceIDName() {
				return "picture-"+pictureURL;
			}

			@Override
			public Image getImage() {
				System.out.println("getImage() in PictureResouce is:"+pictureURL);
				return new Image(pictureURL);
			}
		};
	}
}
