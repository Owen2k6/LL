package com.owen2k6;

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PLConfig extends Configuration
{
    public PLConfig()
    {
        super(new File("plugins/LL/config.yml"));
        this.reload();
    }

    public void reload()
    {
        this.load();
        this.write();
        this.save();
    }

    private void write()
    {
        generateConfigOption("webhook.url", "CHANGEME");
        generateConfigOption("webhook.username", "Login Logger");
        generateConfigOption("webhook.footer", "Owen2k6 Login logger ;)");
    }

    private void generateConfigOption(String key, Object defaultValue)
    {
        if (this.getProperty(key) == null) this.setProperty(key, defaultValue);
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public String getStringOption(String key)
    {
        return String.valueOf(getConfigOption(key));
    }

    public String getStringOption(String key, String defaultValue)
    {
        return String.valueOf(getConfigOption(key, defaultValue));
    }

    public Object getConfigOption(String key)
    {
        return this.getProperty(key);
    }

    public Object getConfigOption(String key, Object defaultValue)
    {
        Object value = getConfigOption(key);
        if (value == null) value = defaultValue;
        return value;
    }

    public List<String> getConfigList(String key)
    {
        return Arrays.asList(String.valueOf(getConfigOption(key, "")).trim().split(","));
    }
}
