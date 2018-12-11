package com.su.sell.util.rmtutil;

import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;

import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * <br>
 * <b>功能：</b>系统配置文件配置替换类<br>
 * <b>作者：</b>戎伟侨<br>
 * <b>日期：</b> 2015年6月16日 <br>
 * <b>版权所有：<b>版权所有融脉科技有限公司(C) 2015<br>
 */
public class CustomPropertyPlaceholderConfigurer extends PreferencesPlaceholderConfigurer {
    private final StringEncryptor stringEncryptor;

    public CustomPropertyPlaceholderConfigurer(StringEncryptor stringEncryptor) {
        CommonUtils.validateNotNull(stringEncryptor, "Encryptor cannot be null");
        this.stringEncryptor = stringEncryptor;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props) throws BeansException {
        SysConfigHelper.setProperties(props);
        super.processProperties(beanFactory, props);

        //load properties to ctxPropertiesMap
       /* ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }*/
    }

    protected String convertPropertyValue(String originalValue) {
        return !PropertyValueEncryptionUtils.isEncryptedValue(originalValue) ? originalValue : PropertyValueEncryptionUtils.decrypt(originalValue, this.stringEncryptor);
    }

    protected String resolvePlaceholder(String placeholder, Properties props) {
        String result = super.resolvePlaceholder(placeholder, props);
        return this.convertPropertyValue(result);
    }

    protected String resolvePlaceholder(String path, String key, Preferences preferences) {
        String result = super.resolvePlaceholder(path, key, preferences);
        return this.convertPropertyValue(result);
    }
}
