package com.kanshu.kanshu.base.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.kanshu.kanshu.base.utils.ConfigPropertieUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * 加载资源文件
 * @Author：hushengmeng
 * @Create Date：2014-11-04
 * @Modified By： 
 * @Modified Date:
 */
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private Resource[] locations;
	private String fileEncoding;
	
    public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	public void setLocations(Resource[] locations) {
        this.locations = locations;
    }
    public void loadProperties(Properties props) throws IOException {
    	String systemBasePath = "/etc/kanshu";
        if (this.locations != null) {
            PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
            for (int i = 0; i < this.locations.length; i++) {
            	InputStream is = null;
                Resource location = this.locations[i];
                File configDir = new File(systemBasePath);
            	if(!configDir.exists()) {
            		is = this.getClass().getResourceAsStream("/config/"+location.getFilename());
            	}else {
            		String  filePath = systemBasePath + File.separator + location.getFilename();
                    is = new FileInputStream(filePath);
            	}
                try {
                    if (fileEncoding != null) {
                        propertiesPersister.load(props, new InputStreamReader(is, fileEncoding));
                    } else {
                        propertiesPersister.load(props, is);
                    }
                    
                    ConfigPropertieUtils.prop = props;
                } finally {
                    if (is != null) is.close();
                }
            }
        }
    }
}
