package sigmu.simpleinfer.parser;

import sigmu.simpleinfer.model.InferBugFix;
import sigmu.simpleinfer.model.Patch;
import sigmu.simpleinfer.model.PatchOption;

import java.io.File;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;


public class InferBugQuickFix extends BaseIntentionAction {
    String filename;

    List<Patch> patchList;
    int patchId;

    String text = "RacerDFix patch...";

    public InferBugQuickFix(PatchOption fix, String filename) {
        this.filename = filename;

        patchList = fix.getPatch();
        patchId = fix.getPatchId();


        this.text = "RacerdFix patch id: " + fix.getPatchId();
    }

    @NotNull
    @Override
    public String getText() {
        return text;
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Infer properties";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, PsiFile file) throws IncorrectOperationException {
//        VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl("file://" + filename);
//        PsiFile psi = FilenameIndex.getFilesByName(project, filename, GlobalSearchScope.projectScope(project))[0];
        Document doc = PsiDocumentManager.getInstance(project).getDocument(file);

        for (Patch patch: patchList) {
            if (patch.getKind().equals("Replace")) {
                int startOffset = doc.getLineStartOffset(patch.getStart() - 1); // -1 because getLineStartOffset is 0-indexed
                int endOffset = doc.getLineEndOffset(patch.getStop() - 1);
                doc.replaceString(startOffset, endOffset, patch.getPartialPatch());
            } else if (patch.getKind().equals("InsAfter")) {
                int insOffset = doc.getLineEndOffset(patch.getStop() - 1);
                doc.insertString(insOffset, "\n" + patch.getPartialPatch());
            } else if (patch.getKind().equals("InsBefore")) {
                int insOffset = doc.getLineStartOffset(patch.getStart() - 1);
                doc.insertString(insOffset, patch.getPartialPatch() + "\n");
            }

            Notifications.Bus.notify(new Notification("RacerDFix", "RacerDFix Patch id [" + patchId + "]",
                    patch.getDescription(), NotificationType.INFORMATION));
        }

        Notifications.Bus.notify(new Notification("RacerDFix", "RacerDFix Patch id: " + patchId,
                "======= DONE! Run RacerDFix or Infer again to re-check. ======", NotificationType.INFORMATION));
    }
}
