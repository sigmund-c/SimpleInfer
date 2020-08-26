package sigmu.simpleinfer.run.racerdfix;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;

public class RacerDFixRunConfigurationType implements ConfigurationType {
    @Override
    public String getDisplayName() {
        return "RacerDFix";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Analyze & fix the sourcecode using RacerDFix";
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
        return new ConfigurationFactory[] {new RacerDFixConfigurationFactory(this)};
    }
}
