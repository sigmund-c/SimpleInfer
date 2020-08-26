package sigmu.simpleinfer.run.racerdfix;

import sigmu.simpleinfer.ui.RacerDFixRunConfigurationEditor;
import sigmu.simpleinfer.ui.RunConfigurationEditor;

import org.jdom.Element;
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
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;

public class RacerDFixRunConfiguration extends RunConfigurationBase {
    private static final String INFER_LAUNCH_COMMAND = "SIMPLE_INFER-INFER_LAUNCH_COMMAND";
    private static final String RACERDFIX_LAUNCH_COMMAND = "SIMPLE_INFER-RACERDFIX_LAUNCH_COMMAND";

    private Project project;
    String inferLaunchCommand;
    String racerDFixLaunchCommand;

    protected RacerDFixRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
        this.project = project;
        inferLaunchCommand = "infer run -- mvn package";
        racerDFixLaunchCommand = "java -jar RacerDFix-1.0-jar-with-dependencies.jar --config_file=\"CONFIG.json\"";
    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);
        inferLaunchCommand = JDOMExternalizerUtil.readField(element, INFER_LAUNCH_COMMAND);
        racerDFixLaunchCommand = JDOMExternalizerUtil.readField(element, RACERDFIX_LAUNCH_COMMAND);
    }

    @Override
    public void writeExternal(@NotNull Element element) throws InvalidDataException {
        super.writeExternal(element);
        JDOMExternalizerUtil.writeField(element, INFER_LAUNCH_COMMAND, inferLaunchCommand);
        JDOMExternalizerUtil.writeField(element, RACERDFIX_LAUNCH_COMMAND, racerDFixLaunchCommand);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RacerDFixRunConfigurationEditor();
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new RacerDFixRunState(this, executionEnvironment);
    }

    @NotNull
    public String getInferLaunchCmd() {
        return inferLaunchCommand;
    }

    public String getRacerDFixLaunchCmd() {
        return racerDFixLaunchCommand;
    }

    public void setInferLaunchCmd(String command) {
        this.inferLaunchCommand = command.trim();
    }

    public void setRacerDFixLaunchCmd(String command) {
        this.racerDFixLaunchCommand = command.trim();
    }

}
