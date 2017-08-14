package test;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.tree.DefaultMutableTreeNode;

public class TestAction extends AnAction {

    public TestAction() {
        super("TEST ACTION");
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

//        anActionEvent.getDataContext()
//
//        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//        node.getUserObject();
        System.out.println(anActionEvent);
    }
}
