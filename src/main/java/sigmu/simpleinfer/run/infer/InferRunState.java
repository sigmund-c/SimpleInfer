package sigmu.simpleinfer.run.infer;


import java.io.File;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;

public class InferRunState extends CommandLineState {
    private static final Logger log = Logger.getInstance(InferRunState.class);

    private ExecutionEnvironment ee;
    private InferRunConfiguration runCfg;

    InferRunState(InferRunConfiguration runCfg, ExecutionEnvironment environment) {
        super(environment);
        this.ee = environment;
        this.runCfg = runCfg;
    }

    InferRunState(ExecutionEnvironment environment) {
        super(environment);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        final String runCmd;
        if (runCfg == null) {
            runCmd = "a";
        } else {
            runCmd = runCfg.getInferLaunchCmd();
        }

        log.info("Running Infer with Command: " + runCmd);

        String splits[] = runCmd.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        GeneralCommandLine commandLine = new GeneralCommandLine(splits);

        if(runCfg.getProject().getBasePath() == null) throw new ExecutionException("Could not acquire the project base path");
        commandLine.setWorkDirectory(new File(runCfg.getProject().getBasePath()));

        ProcessHandler ph = new ColoredProcessHandler(commandLine);
        ph.addProcessListener(new InferProcessListener(runCfg.getProject()));
        return ph;
    }
}
