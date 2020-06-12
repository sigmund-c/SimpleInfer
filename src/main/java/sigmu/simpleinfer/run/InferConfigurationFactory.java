package sigmu.simpleinfer.run;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

public class InferConfigurationFactory extends ConfigurationFactory {
    private static final String FACTORY_NAME = "Infer configuration factory";

    InferConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    @NotNull
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new InferRunConfiguration(project, this, "Infer");
    }

    @Override
    public String getName() {
        return FACTORY_NAME;
    }


}
