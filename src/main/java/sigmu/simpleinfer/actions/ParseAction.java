package sigmu.simpleinfer.actions;


import sigmu.simpleinfer.ResultParser;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class ParseAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        final Path reportPath = Paths.get(project.getBasePath() + "/infer-out/report.json");
        ResultParser.getInstance(project).parse(reportPath);
    }
}
