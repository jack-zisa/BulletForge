package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.render.screen.EditorScreen;

public abstract class AbstractEditorPane extends Table {
    private final EditorScreen parent;
    private final Table contentContainer;

    protected AbstractEditorPane(String title, EditorScreen parent, Skin skin) {
        this.parent = parent;

        setSkin(skin);
        top().left();

        Table mainContent = new Table();
        mainContent.setSkin(skin);
        mainContent.top().left();
        mainContent.defaults().growX().top();

        Table header = new Table();
        header.top().left();

        header.add(new Label(title, skin));

        TextButton addButton = new TextButton("+", skin);
        addButton.addListener(onAddElement());

        header.add(addButton).right();

        mainContent.add(header).growX().row();

        contentContainer = new Table();
        contentContainer.top().left();
        contentContainer.add(onRefresh()).growX().row();

        mainContent.add(contentContainer).growX().row();

        add(mainContent).growX().fillY().top().left();
    }

    public abstract Table onRefresh();

    public abstract ClickListener onAddElement();

    public EditorScreen getEditor() {
        return parent;
    }

    public void refresh() {
        contentContainer.clearChildren();
        contentContainer.add(onRefresh()).growX().row();

        invalidateHierarchy();
        pack();
    }
}
