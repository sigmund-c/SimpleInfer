package sigmu.simpleinfer.parser;

import sigmu.simpleinfer.model.InferBugFix;

import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;

/**
 * A quick fix implementation of future machine generated code fixes. (currently a stub)
 */
public class InferBugQuickFix extends BaseIntentionAction {

    String filename;
    String bugHash;
    int lineStart;
    int lineEnd;
    String codeSnippet;
    String text = "Fix code with ...";

    InferBugQuickFix(String filename, String bugHash, int lineStart, int lineEnd, String codeSnippet) {
        this.filename = filename;
        this.bugHash = bugHash;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.codeSnippet = codeSnippet;

        text = "Replace lines " + lineStart + "-" + lineEnd + " with " + codeSnippet;
    }

    public InferBugQuickFix(String text) {
        this.text = text;
    }

    public InferBugQuickFix(InferBugFix fix) {
        this.filename = fix.getFileName();
        this.bugHash = fix.getBugHash();
        this.lineStart = fix.getLineStart();
        this.lineEnd = fix.getLineEnd();
        this.codeSnippet = fix.getCodeSnippet();

        text = filename +": Replace lines " + lineStart + "-" + lineEnd + " with " + codeSnippet;
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
        PsiFile psi = FilenameIndex.getFilesByName(project, filename, GlobalSearchScope.projectScope(project))[0];

        Document doc = PsiDocumentManager.getInstance(project).getDocument(psi);
        int startOffset = doc.getLineStartOffset(lineStart - 1); // -1 because getLineStartOffset is 0-indexed
        int endOffset = doc.getLineEndOffset(lineEnd - 1);
        doc.replaceString(startOffset, endOffset, codeSnippet);
    }
}
