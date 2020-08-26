package sigmu.simpleinfer.run.infer;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;

public class InferRunConfigurationType implements ConfigurationType {
    @Override
    public String getDisplayName() {
        return "Infer";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Analyze the sourcecode using the Infer static analyzer";
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @NotNull
    @Override
    public String getId() {
        return "InferRunConfiguration";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[] {new InferConfigurationFactory(this)};
    }
}
