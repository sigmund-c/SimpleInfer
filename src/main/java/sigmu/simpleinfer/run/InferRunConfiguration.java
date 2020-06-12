package sigmu.simpleinfer.run;

import sigmu.simpleinfer.ui.RunConfigurationEditor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;

public class InferRunConfiguration extends RunConfigurationBase {
    private Project project;
    String launchCommand;

    protected InferRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
        this.project = project;
        launchCommand = "infer run -- mvn package";
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RunConfigurationEditor();
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new InferRunState(this, executionEnvironment);
    }

    @NotNull
    public String getInferLaunchCmd() {
        return launchCommand;
    }

    public void setInferLaunchCmd(String command) {
        this.launchCommand = command.trim();
    }
}
