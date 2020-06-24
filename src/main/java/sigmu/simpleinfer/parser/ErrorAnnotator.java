package sigmu.simpleinfer.parser;

import sigmu.simpleinfer.model.InferBug;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

public class ErrorAnnotator extends ExternalAnnotator<Map<String, List<InferBug>>, Map<String, List<InferBug>>> {

    @Override
    public Map<String, List<InferBug>> collectInformation(@NotNull PsiFile file) {
        Project project = file.getProject();


        final Path reportPath = Paths.get(project.getBasePath() + "/infer-out/report.json");
        Map<String, List<InferBug>> collectedBugs = ResultParser.getInstance(project).parse(reportPath);

        return collectedBugs;
    }

    @Override
    public Map<String, List<InferBug>> doAnnotate(Map<String, List<InferBug>> collectedInfo) {
        return collectedInfo;
    }

    @Override
    public void apply(@NotNull PsiFile file, Map<String, List<InferBug>> annotationResult, @NotNull AnnotationHolder holder) {
        Project project = file.getProject();
        for (Map.Entry<String, List<InferBug>> fileBugs: annotationResult.entrySet()) {
            Document document = PsiDocumentManager.getInstance(project).getDocument(file);

            for (InferBug bug: fileBugs.getValue()) {
                TextRange bugRange = new TextRange(document.getLineStartOffset(bug.getLine() - 1), document.getLineEndOffset(bug.getLine() - 1)); // -1 because getLineEndOffset is 0-indexed
                Annotation errorProperty = holder.createErrorAnnotation(bugRange, bug.toString()); // Text that shows up when cursor hovers over annotation

                TextAttributesKey ErrorAttributes = TextAttributesKey.createTextAttributesKey("INFER_ERROR", HighlighterColors.BAD_CHARACTER);
                errorProperty.setTextAttributes(ErrorAttributes);

                // TODO: Create quick fix
                errorProperty.registerFix(new InferBugQuickFix());
            }
        }
    }

    /*
    public static void createBugAnnotations(Project project, Map<String, List<InferBug>> bugs) {
        for (Map.Entry<String, List<InferBug>> fileBugs: bugs.entrySet()) {
            PsiDocumentManager.getInstance(project).getDocument(file);

            Annotation errorProperty = holder.createErrorAnnotation(, bug.toString());
        }
    }

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        Project project = element.getProject();
        InferBug bug = getBugFromOffset(project, element.getTextOffset(), element.getContainingFile());

        if (bug != null) {
            Annotation errorProperty = holder.createErrorAnnotation(element, bug.toString());

            TextAttributesKey ErrorAttributes = TextAttributesKey.createTextAttributesKey("INFER_ERROR", HighlighterColors.BAD_CHARACTER);
            errorProperty.setTextAttributes(ErrorAttributes);

            errorProperty.registerFix(new InferBugQuickFix());
        }
    }

    private InferBug getBugFromOffset(Project project, int textOffset, PsiFile file) {
        try {
            final Path reportPath = Paths.get(project.getBasePath() + "/infer-out/report.json");
            final String json = new String(Files.readAllBytes(reportPath));
            final Type targetClassType = new TypeToken<ArrayList<InferBug>>(){}.getType();
            Collection<InferBug> targetCollection = new Gson().fromJson(json, targetClassType);

            Document document = PsiDocumentManager.getInstance(project).getDocument(file);
            int lineNumber = document.getLineNumber(textOffset) + 1; // getLineNumber returns a 0 indexed number

            for (InferBug bug: targetCollection) {
                int bugLine = bug.getLine();
                String bugName = bug.getFileName();
                String fileName = file.toString().split(":")[1];
                int a = bugLine + 1;
                if (bug.getFileName() == fileName && bug.getLine() == lineNumber) {
                    return bug;
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            return null;
        }
        return null;
    }

    /*
    public void showMarkup(InferBug bug, Editor editor, Document doc) {
        final TextAttributes attr = new TextAttributes();
        attr.setEffectColor(JBColor.red);
        attr.setEffectType(EffectType.WAVE_UNDERSCORE);

        WriteCommandAction.runWriteCommandAction(project, () -> {
            MarkupModel markup = editor.getMarkupModel();
            markup.addRangeHighlighter(startOffset,
                    endOffset,
                    HighlighterLayer.WEAK_WARNING,
                    attr,
                    HighlighterTargetArea.EXACT_RANGE);
        });

        EditorMouseMotionListener l;
        editor.addEditorMouseMotionListener(l = new EditorMouseMotionListener() {
            private void handleEvent(EditorMouseEvent e) {
                if (e.getArea().equals())
            }
            @Override
            public void mouseMoved(@NotNull EditorMouseEvent e) {

            }
        });
    }
*/
}
