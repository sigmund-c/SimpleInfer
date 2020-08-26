package sigmu.simpleinfer.run.racerdfix;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

public class RacerDFixConfigurationFactory extends ConfigurationFactory {
    private static final String FACTORY_NAME = "RacerDFix configuration factory";

    RacerDFixConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    @NotNull
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new RacerDFixRunConfiguration(project, this, "RacerDFix");
    }

    @Override
    public String getName() {
        return FACTORY_NAME;
    }


}
