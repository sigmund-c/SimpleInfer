package sigmu.simpleinfer.config;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;

public class InferConfigurableProvider extends ConfigurableProvider {
    @Nullable
    @Override
    public Configurable createConfigurable() {
        return new InferConfigurable(GlobalSettings.getInstance());
    }
}
