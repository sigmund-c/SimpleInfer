package sigmu.simpleinfer.config;

import org.ini4j.Config;

import com.intellij.openapi.options.Configurable;

public class InferConfigurable implements Configurable {
    private Config config;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
