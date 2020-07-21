package sigmu.simpleinfer.actions;

import sigmu.simpleinfer.model.InferBug;
import sigmu.simpleinfer.parser.ResultParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.FileContentUtil;

public class ParseByBugTypeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        final Path reportPath = Paths.get(project.getBasePath() + "/infer-out/report.json");
        Map<String, List<InferBug>> a = ResultParser.getInstance(project).parseByBugType(reportPath);

        FileContentUtil.reparseFiles(FileEditorManager.getInstance(project).getOpenFiles());
    }
}
