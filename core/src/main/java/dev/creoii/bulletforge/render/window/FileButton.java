package dev.creoii.bulletforge.render.window;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.render.screen.element.OptionTooltip;
import dev.creoii.bulletforge.render.screen.element.Tab;
import dev.creoii.bulletforge.render.screen.element.option.OptionProvider;
import dev.creoii.bulletforge.render.screen.element.tooltip.TooltipProvider;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserIntent;

import java.util.List;

public class FileButton extends TextButton implements TooltipProvider, OptionProvider {
    private final BulletForge main;
    private final OptionTooltip tooltip;

    public FileButton(BulletForge main) {
        super(main.getI18n().get("window.controlBar.file"), GlobalAssets.SKIN);
        this.main = main;
        tooltip = new OptionTooltip(this);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isChecked()) tooltip.show();
                else tooltip.hide();
            }
        });
    }

    @Override
    public OptionTooltip getTooltip() {
        return tooltip;
    }

    @Override
    public List<Actor> getOptions() {
        TextButton newButton = new TextButton("New", GlobalAssets.SKIN);
        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getUserInterface().getTabManager().addTab(-1, new Tab(main, "Editor", new EditorScreen(main)));
            }
        });
        TextButton openButton = new TextButton("Open", GlobalAssets.SKIN);
        openButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getFileChooserConfiguration().intent = NativeFileChooserIntent.OPEN;
                main.getFileChooser().chooseFile(main.getFileChooserConfiguration(), new NativeFileChooserCallback() {
                    @Override
                    public void onFileChosen(FileHandle file) {
                        if (!file.extension().equals("json")) {
                            System.out.println("fail: " + file.name());
                            return;
                        }

                        System.out.println("success: " + file.name());
                    }

                    @Override
                    public void onCancellation() {
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
        TextButton saveButton = new TextButton("Save", GlobalAssets.SKIN);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getFileChooserConfiguration().intent = NativeFileChooserIntent.SAVE;
                main.getFileChooser().chooseFile(main.getFileChooserConfiguration(), new NativeFileChooserCallback() {
                    @Override
                    public void onFileChosen(FileHandle file) {
                        if (!file.extension().equals("json")) {
                            System.out.println("fail: " + file.name());
                            return;
                        }

                        System.out.println("success: " + file.name());
                    }

                    @Override
                    public void onCancellation() {
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
        return List.of(
            newButton,
            openButton,
            saveButton
        );
    }
}
