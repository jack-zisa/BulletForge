package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.util.editor.EditorGenerator;

public class ExpandableEditorPane extends Table {
    private final Table content;
    private final Image button;
    private boolean expanded;

    public ExpandableEditorPane(String title, Object target, Skin skin) {
        top().left();
        defaults().growX().left();

        Table header = new Table();

        header.add(new TextButton(title, skin));
        header.add(button = new Image());

        content = EditorGenerator.createEditorTable(target, skin);

        add(header).growX().left().row();
        add(content).growX().left().row();

        header.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setExpanded(!expanded);
            }
        });

        setExpanded(false);
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        content.setVisible(expanded);
        button.setDrawable(new TextureRegionDrawable(expanded ? GlobalAssets.DROPUP : GlobalAssets.DROPDOWN));
        invalidateHierarchy();
    }
}
