package sigmu.simpleinfer.parser;

import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;

/**
 * A quick fix implementation of future machine generated code fixes. (currently a stub)
 */
public class InferBugQuickFix extends BaseIntentionAction {

    InferBugQuickFix() {
    }

    @NotNull
    @Override
    public String getText() {
        return "Fix code with ...";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Infer properties";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return false; // Quick fix won't show up
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, PsiFile file) throws IncorrectOperationException {
        // TODO: Set quick fix here
    }
}
