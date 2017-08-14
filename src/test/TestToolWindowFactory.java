package test;

import com.intellij.ide.ui.customization.CustomActionsSchema;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TestToolWindowFactory implements ToolWindowFactory {

    private ToolWindow toolWindow;

    private JPanel mainPanel;

    private JTabbedPane tabbedPaneAppObj;

    private JPanel panel1;

    private SimpleTree tree;

    private JScrollPane scrollPane;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(this.mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void init(ToolWindow window) {
        // Do nothing.
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public boolean isDoNotActivateOnStart() {
        return true;
    }

    private void createUIComponents() {
//        this.mainPanel = new JPanel();
//        this.mainPanel.setLayout(new BorderLayout());
        this.tree = new SimpleTree();
//        this.scrollPane = new JScrollPane(this.tree);


//        this.mainPanel.add(this.scrollPane, BorderLayout.SOUTH);

        Node root = new Node(null, "Node 0");

        SimpleTreeBuilder simpleTreeBuilder = new SimpleTreeBuilder(this.tree, (DefaultTreeModel) this.tree.getModel(), new SimpleTreeStructure() {
            @Override
            public Object getRootElement() {
                return root;
            }
        }, null);

        Disposer.register(ProjectManager.getInstance().getOpenProjects()[0], simpleTreeBuilder);
        simpleTreeBuilder.initRoot();
        simpleTreeBuilder.expand(root, null);

        this.mainPanel = ToolbarDecorator.createDecorator(this.tree).setAddAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                System.out.println(anActionButton);
            }
        }).addExtraAction(new AnActionButton() {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                System.out.println(anActionEvent);
            }
        }).createPanel();

        this.tree.addMouseListener(new PopupHandler() {
            @Override
            public void invokePopup(Component component, int x, int y) {
                SimpleTree tree = (SimpleTree) component;

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                Node n = (Node) node.getUserObject();

                DefaultActionGroup group = (DefaultActionGroup) ActionManager.getInstance().getAction("testGroup");
                group.add(new TestAction2(n.text));
                final ActionPopupMenu popupMenu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.UNKNOWN, group);
                popupMenu.getComponent().show(component, x, y);
            }
        });
    }

    private static class Node extends CachingSimpleNode {

        private String text;

        public Node(SimpleNode parent, String text) {
            super(parent);
            this.text = text;
        }

        @Override
        public String getName() {
            return this.text;
        }

        @Override
        protected SimpleNode[] buildChildren() {
            return new SimpleNode[]{
                    new Node(this, "Node 0 0"),
                    new Node(this, "Node 0 1"),
                    new Node(this, "Node 0 2")
            };
        }
    }

}
