// Original code by Friedrich Hudinjan of "Infer Integration"

package sigmu.simpleinfer.ui;

import sigmu.simpleinfer.parser.ResultParser;
import sigmu.simpleinfer.model.InferBug;
import sigmu.simpleinfer.model.ResultListEntry;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.treeStructure.Tree;

public class InferToolWindow extends SimpleToolWindowPanel {
    private static final Logger log = Logger.getInstance(InferToolWindow.class);

    private JPanel InferToolWindowContent;
    private Tree issueList;

    public InferToolWindow(ToolWindow toolWindow, Project project) {
        super(false, true);

        final ActionManager actionManager = com.intellij.openapi.actionSystem.ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup("ACTION_GROUP", false);
        actionGroup.add(ActionManager.getInstance().getAction("infer.menu"));

        ActionToolbar actionToolbar = actionManager.createActionToolbar("ACTION_TOOLBAR", actionGroup, false);
        actionToolbar.setOrientation(SwingConstants.HORIZONTAL);

//        Box toolBarBox = Box.createHorizontalBox();
//        toolBarBox.add(actionToolbar.getComponent());
//
//        add(toolBarBox, BorderLayout.WEST);
//
//        toolBarBox.add()

        this.setToolbar(actionToolbar.getComponent());
        actionToolbar.setTargetComponent(this);

        add(InferToolWindowContent);


        issueList.getEmptyText().setText(ResourceBundle.getBundle("strings").getString("no.bug.list.to.show"));
        issueList.setModel(new DefaultTreeModel(null));

        ResultParser.getInstance(project).addPropertyChangeListener(evt -> {
            if(evt.getNewValue() != null && evt.getPropertyName().equals("bugsMap")) {
                drawBugTree((Map<String, List<InferBug>>)evt.getNewValue());
            }
        });

        //Coloring
        issueList.setCellRenderer(new ColoredTreeCellRenderer() {
            @Override
            public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){
                if (((DefaultMutableTreeNode)value).getUserObject() instanceof ResultListEntry) {
                    final ResultListEntry bug = (ResultListEntry) ((DefaultMutableTreeNode)value).getUserObject();
                    append(bug.getFileName() + " ");
                    append("Line: " + bug.getLine(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, JBColor.blue));
                    if (bug.getColumn() >= 0) {
                        append(" Column: " + bug.getColumn(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, JBColor.blue));
                    }
                    append(" " + bug.toString());
                }

                else if (row == 0) {
                    append(value.toString(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_BOLD, null));
                    // setIcon(AllIcons.Actions.listFiles);
                }

                else {
                    append(value.toString(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, JBColor.blue));
                }

            }
        });

        issueList.addTreeSelectionListener(e -> ApplicationManager.getApplication().invokeLater(() -> {
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            final DefaultMutableTreeNode node = (DefaultMutableTreeNode) issueList.getLastSelectedPathComponent();

            if(editor == null || node == null) return;
            if (node.getUserObject() instanceof ResultListEntry) {
                final ResultListEntry bug = (ResultListEntry) node.getUserObject();
                LogicalPosition pos = new LogicalPosition(
                        bug.getLine() > 0 ? bug.getLine() - 1 : 0,
                        bug.getColumn() > 0 ? bug.getColumn() - 1 : 0);
                String fileName = bug.getFileName();

                PsiFile[] fileArray = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.projectScope(project));
                if (fileArray.length != 1) {
                    log.warn("Could not find or to many selected file(s) to navigate to: " + fileName);
                    return;
                }
                fileArray[0].navigate(false);

                editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor == null) {
                    log.warn("No editor found. Not jumping to line " + bug.getLine());
                }

                editor.getScrollingModel().scrollTo(pos, ScrollType.CENTER);
                editor.getCaretModel().moveToLogicalPosition(pos);
            }
        }));

        drawBugTree(ResultParser.getInstance(project).getBugsMap());
    }

    /*public JPanel getContent() {
        return InferToolWindowContent;
    }*/

    private void drawBugTree(Map<String, List<InferBug>> bugMap) {
        if (bugMap == null) return;

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(String.format(ResourceBundle.getBundle("strings").getString("infer.analysis.result.bugs.found"), bugMap.values().stream().mapToInt(List::size).sum()));

        for (Map.Entry<String, List<InferBug>> entry: bugMap.entrySet()) {
            DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(String.format(ResourceBundle.getBundle("strings").getString("bugs.found"), entry.getKey(), entry.getValue().size()));
            for (InferBug bug: entry.getValue()) {
                DefaultMutableTreeNode bugNode = new DefaultMutableTreeNode(bug);
                for (InferBug.BugTrace trace: bug.getBugTrace()) {
                    DefaultMutableTreeNode bugTraceNode = new DefaultMutableTreeNode(trace);
                    bugNode.add(bugTraceNode);
                }
                fileNode.add(bugNode);
            }
            root.add(fileNode);
        }
        TreeModel tm = new DefaultTreeModel(root);
        issueList.setModel(tm);
    }
}
