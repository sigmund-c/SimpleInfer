package sigmu.simpleinfer.config;

import sigmu.simpleinfer.run.InferRunConfiguration;

import org.jetbrains.annotations.NotNull;

public class PluginConfiguration {
    private InferRunConfiguration runConfig;

    PluginConfiguration(@NotNull InferRunConfiguration runConfig) {
        this.runConfig = runConfig;
    }

    public InferRunConfiguration getRunConfig() {
        return runConfig;
    }
}
