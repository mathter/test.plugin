package test;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class TestAction2 extends AnAction {

    private String text;

    public TestAction2(String text) {
        super("TEST ACTION 2 + '" + text + "'");
        this.text = text;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

//        anActionEvent.getDataContext()
//
//        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//        node.getUserObject();
        System.out.println(this.text);
    }
}
