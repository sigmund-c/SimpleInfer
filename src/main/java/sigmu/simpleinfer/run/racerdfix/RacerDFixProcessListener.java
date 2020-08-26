package sigmu.simpleinfer.run.racerdfix;

import sigmu.simpleinfer.parser.ResultParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindowManager;

public class RacerDFixProcessListener implements ProcessListener {
    private static final Logger log = Logger.getInstance(RacerDFixProcessListener.class);
    private Project project;

    RacerDFixProcessListener(Project project) {
        this.project = project;
    }

    @Override
    public void processTerminated(@NotNull ProcessEvent event) {
        if (event.getExitCode() == 0) {
            log.info("RacerDFix Process terminated successfully: Status Code 0");

            ApplicationManager.getApplication().invokeAndWait(() -> ToolWindowManager.getInstance(project).getToolWindow("Infer").activate(null, true));

            final Path reportPath = Paths.get(project.getBasePath() + "/infer-out/report.json");
            if (Files.exists(reportPath)) ResultParser.getInstance(project).parse(reportPath);
            else log.error("report.json does not exist after Infer terminated successfully: Check the Infer log");
        } else {
            log.warn("RacerDFix Process terminated unsuccessfully: Status Code " + event.getExitCode());
            Notifications.Bus.notify(new Notification("Infer", "Failure", "RacerDFix terminated unsuccessfully", NotificationType.ERROR));

        }
    }

    @Override public void startNotified(@NotNull ProcessEvent event) { }
    @Override public void processWillTerminate(@NotNull ProcessEvent event, boolean willBeDestroyed) { }
    @Override public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) { }
}
