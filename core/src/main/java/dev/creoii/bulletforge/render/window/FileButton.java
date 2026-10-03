package dev.creoii.bulletforge.render.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.editor.EditorConfig;
import dev.creoii.bulletforge.render.screen.AbstractScreen;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.render.screen.element.OptionTooltip;
import dev.creoii.bulletforge.render.screen.element.Tab;
import dev.creoii.bulletforge.render.screen.element.option.OptionProvider;
import dev.creoii.bulletforge.render.screen.element.tooltip.TooltipProvider;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserIntent;
import games.spooky.gdx.nativefilechooser.NativeFilesChooserCallback;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        TextButton newButton = new TextButton(main.getI18n().get("window.controlBar.file.new"), GlobalAssets.SKIN);
        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getUserInterface().getTabManager().addTab(-1, Tab.createEditor(main, main.getI18n().get("tab.new_pattern"), new EditorScreen(main)));
                if (!main.getCamera().position.isZero()) {
                    main.getCamera().position.setZero();
                    main.getCamera().update();
                }
            }
        });

        TextButton openButton = new TextButton(main.getI18n().get("window.controlBar.file.open"), GlobalAssets.SKIN);
        openButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getFileChooserConfiguration().intent = NativeFileChooserIntent.OPEN;
                main.getFileChooser().chooseFiles(main.getFileChooserConfiguration(), new NativeFilesChooserCallback() {
                    @Override
                    public void onFilesChosen(Array<FileHandle> files) {
                        files.forEach(file -> {
                            if (!file.extension().equals("json")) {
                                return;
                            }

                            try {
                                JsonElement jsonValue = BulletForge.GSON.fromJson(new FileReader(file.file()), JsonElement.class);
                                DataResult<EditorConfig> result = EditorConfig.CODEC.parse(JsonOps.INSTANCE, jsonValue);

                                if (result.isSuccess()) {
                                    EditorConfig config = result.getOrThrow();

                                    EditorScreen editorScreen = new EditorScreen(main);
                                    editorScreen.getConfig().set(config);
                                    main.getUserInterface().getTabManager().addTab(-1, Tab.createEditor(main, file.nameWithoutExtension(), editorScreen));
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });

                        if (!main.getCamera().position.isZero()) {
                            main.getCamera().position.setZero();
                            main.getCamera().update();
                        }
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

        TextButton saveButton = new TextButton(main.getI18n().get("window.controlBar.file.save"), GlobalAssets.SKIN);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getFileChooserConfiguration().intent = NativeFileChooserIntent.SAVE;
                main.getFileChooser().chooseFile(main.getFileChooserConfiguration(), new NativeFileChooserCallback() {
                    @Override
                    public void onFileChosen(FileHandle file) {
                        AbstractScreen screen = main.getUserInterface().getActiveScreen();
                        if (screen instanceof EditorScreen editorScreen) {
                            String path = file.path();
                            if (!file.extension().equalsIgnoreCase("json")) {
                                path += ".json";
                                file = Gdx.files.absolute(path);
                            }

                            try {
                                EditorConfig config = editorScreen.getConfig();
                                JsonElement jsonValue = EditorConfig.CODEC.encodeStart(JsonOps.INSTANCE, config).getOrThrow();

                                try (FileWriter writer = new FileWriter(file.file())) {
                                    BulletForge.GSON.toJson(jsonValue, writer);
                                }
                            } catch (IOException | RuntimeException e) {
                                e.printStackTrace();
                            }
                        }
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
        return List.of(newButton, openButton, saveButton);
    }
}
