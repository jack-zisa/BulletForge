package dev.creoii.bulletforge.render.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.AbstractScreen;

public class Tab extends Table {
    private final String title;
    private final AbstractScreen screen;
    private final Label titleLabel;
    private int index;
    private boolean selected;

    public Tab(BulletForge main, String title, AbstractScreen screen) {
        this.title = title;
        this.screen = screen;
        index = -1;
        selected = false;

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(GlobalAssets.EXIT_ICON));
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getUserInterface().getTabManager().removeTab(index);
            }
        });
        add(titleLabel = new Label(title, GlobalAssets.SKIN));
        add(closeButton);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (main.getUserInterface().getTabManager().getSelectedTabIndex() == index) return;
                main.getUserInterface().getTabManager().selectTab(index);
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public AbstractScreen getScreen() {
        return screen;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        if (selected) {
            titleLabel.setColor(Color.WHITE);
        } else titleLabel.setColor(Color.GRAY);
    }

    public boolean isSelected() {
        return selected;
    }
}
