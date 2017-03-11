package cn.keepfight.frame.picture;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.scene.image.Image;

public abstract class PictureDataSource implements DataSource{

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.PICTURE;
	}


	@Override
	public void checkValid() throws InvalidSourceException {
	}

	public abstract Image getImage();
}
