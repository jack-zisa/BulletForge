package dev.creoii.bulletforge;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.render.screen.HomeScreen;
import dev.creoii.bulletforge.render.screen.element.tooltip.TooltipProvider;
import dev.creoii.bulletforge.render.window.Tab;
import dev.creoii.bulletforge.render.window.WindowControlBar;

import java.util.ArrayList;
import java.util.List;

public class UserInterface {
    public static final float CONTROL_BAR_HEIGHT = 32f;
    public static final float TAB_BAR_HEIGHT = 40f;
    private final BulletForge main;
    private TabManager tabManager;
    private WindowControlBar windowControlBar;
    private Container<Table> screenContainer;

    public UserInterface(BulletForge main) {
        this.main = main;
    }

    public TabManager getTabManager() {
        return tabManager;
    }

    public WindowControlBar getWindowControlBar() {
        return windowControlBar;
    }

    public Container<Table> getScreenContainer() {
        return screenContainer;
    }

    protected void createGlobalActors() {
        Table root = new Table();
        root.setFillParent(true);

        root.add(windowControlBar = new WindowControlBar(main)).height(CONTROL_BAR_HEIGHT).growX().top().row();
        root.add(tabManager = new TabManager(main)).height(TAB_BAR_HEIGHT).growX().top().row();
        root.add(screenContainer = new Container<>()).grow().fill();

        tabManager.addTab(-1, new Tab(main, "Home", new HomeScreen(main)));

        main.getGlobalStage().addActor(root);

        collectTooltips(root, new ArrayList<>()).forEach(main.getGlobalStage()::addActor);
    }

    private static List<Actor> collectTooltips(Group parent, List<Actor> list) {
        parent.getChildren().forEach(actor -> {
            if (actor instanceof TooltipProvider tooltipProvider) {
                list.add(tooltipProvider.getTooltip());
            } else if (actor instanceof Group group) collectTooltips(group, list);
        });
        return list;
    }
}
