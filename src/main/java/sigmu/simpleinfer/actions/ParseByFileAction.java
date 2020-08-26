package sigmu.simpleinfer.actions;


import sigmu.simpleinfer.model.InferBug;
import sigmu.simpleinfer.parser.ErrorAnnotator;
import sigmu.simpleinfer.parser.ResultParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.FileContentUtil;

public class ParseByFileAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        final Path reportPath = Paths.get(project.getBasePath() + "/infer-out/report.json");
        Map<String, List<InferBug>> a = ResultParser.getInstance(project).parse(reportPath);
        //ErrorAnnotator.createBugAnnotations(project, a);

        FileContentUtil.reparseFiles(project, Arrays.asList(FileEditorManager.getInstance(project).getOpenFiles()), true);
    }

    /*
    public void reparseOpenFiles(Project project) {
        if (!project.isDisposed()) {
            if (DumbService.isDumb(project)) {
                DumbService.getInstance(project).runWhenSmart(() -> reparseOpenFiles(project));
            }
        } else {
            VirtualFile[] virtualFiles = FileEditorManager.getInstance(project).getOpenFiles();
            PsiManager psiManager = PsiManager.getInstance(project);
            ArrayList<PsiFile> filesList = new ArrayList<>();

            for (VirtualFile file: virtualFiles) {
                PsiFile psiFile = psiManager.findFile(file);
                if (psiFile != null) {
                    filesList.add(psiFile);
                }
            }

            FileContentUtil.reparseFiles();
        }
    }*/
}
