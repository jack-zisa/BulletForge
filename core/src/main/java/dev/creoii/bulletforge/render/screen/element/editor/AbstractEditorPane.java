package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;

public abstract class AbstractEditorPane extends Table {
    private final EditorScreen parent;

    private final Table mainContent;
    private final Table contentContainer;
    private final Sprite expandSprite;
    private final ImageButton expandButton;

    private boolean collapsed;

    protected AbstractEditorPane(String title, EditorScreen parent, Skin skin) {
        this.parent = parent;

        setSkin(skin);
        top().left();

        mainContent = new Table();
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

        expandSprite = new Sprite(GlobalAssets.DROPARROW);
        expandSprite.rotate90(true);

        expandButton = new ImageButton(new SpriteDrawable(expandSprite));
        expandButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setCollapsed(!collapsed);
            }
        });

        add(mainContent).grow().top().left();
        add(expandButton).top().right().width(24f).fillY();
    }

    private void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;

        mainContent.setVisible(!collapsed);

        expandSprite.setRotation(collapsed ? 0f : 90f);

        invalidateHierarchy();
        pack();
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
