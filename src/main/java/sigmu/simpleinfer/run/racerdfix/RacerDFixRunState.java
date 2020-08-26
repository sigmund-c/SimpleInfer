package sigmu.simpleinfer.run.racerdfix;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;
import com.sun.tools.javac.comp.Infer;

public class RacerDFixRunState extends CommandLineState {
    private static final Logger log = Logger.getInstance(RacerDFixRunState.class);

    private ExecutionEnvironment ee;
    private RacerDFixRunConfiguration runCfg;

    RacerDFixRunState(RacerDFixRunConfiguration runCfg, ExecutionEnvironment environment) {
        super(environment);
        this.ee = environment;
        this.runCfg = runCfg;
    }

    RacerDFixRunState(ExecutionEnvironment environment) {
        super(environment);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        final String RacerDFixRunCmd;
        final String InferRunCmd;
        if (runCfg == null) {
            RacerDFixRunCmd = "a";
            InferRunCmd = "Not found";
        } else {
            RacerDFixRunCmd = runCfg.getRacerDFixLaunchCmd();
            InferRunCmd = runCfg.getInferLaunchCmd();
        }

        log.info("Running RacerDFix with Command: " + RacerDFixRunCmd);
        log.info("Setting CONFIG.json with Infer Command: " + InferRunCmd);

        setupConfigFile(InferRunCmd);

        //String splits[] = RacerDFixRunCmd.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(RacerDFixRunCmd);
        String nextArg;
        boolean hasIntelliJParam = false;
        while (sc.hasNext()) {
            nextArg = sc.next();
            args.add(nextArg);
            if (nextArg.equals("--intellij=true")) {
                hasIntelliJParam = true;
            }
        }

        if (!hasIntelliJParam) {
            log.info("--intellij=true parameter not found. Adding said parameter to RacerDFix run command.");
            args.add("--intellij=true");
        }

        GeneralCommandLine commandLine = new GeneralCommandLine(args);

        if(runCfg.getProject().getBasePath() == null) throw new ExecutionException("Could not acquire the project base path");
        commandLine.setWorkDirectory(new File(runCfg.getProject().getBasePath()));

        ProcessHandler ph = new ColoredProcessHandler(commandLine);
        ph.addProcessListener(new RacerDFixProcessListener(runCfg.getProject()));
        return ph;
    }

    private void setupConfigFile(String inferRunCmd) throws ExecutionException {
        if (inferRunCmd.equals("")) {
            return;
        }

        // variables to put into CONFIG.json
        String inferVersion;
        List<String> options = new ArrayList<>();
        String jsonPath = "./infer-out/"; // default
        List<String> targetOptions = new ArrayList<>();

        Scanner sc = new Scanner(inferRunCmd);
        inferVersion = sc.next();
        sc.next(); // run or capture, ignored

        boolean parsingTargetOptions = false;
        while (sc.hasNext()) {
            String param = sc.next();
            if (parsingTargetOptions) {
                targetOptions.add(param);
                continue;
            }
            if (param.equals("--")) {
                targetOptions.add(param);
                parsingTargetOptions = true;
            } else if (param.equals("-o")) {
                jsonPath = sc.next();
            } else {
                options.add(param);
            }
        }


        JSONObject config = new JSONObject();
        config.put("infer", inferVersion);
        config.put("options", options);
        config.put("json_path", jsonPath);
        config.put("target_options", targetOptions);
        config.put("prio_files", new JSONArray());
        config.put("iterations", 1);

        FileWriter file = null;
        try {
            if(runCfg.getProject().getBasePath() == null) throw new ExecutionException("Could not acquire the project base path");
            file = new FileWriter(runCfg.getProject().getBasePath() + "/CONFIG.json");
            file.write(config.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
