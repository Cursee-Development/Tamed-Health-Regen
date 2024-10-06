package com.cursee.pet_health_regen;

import com.cursee.monolib.platform.Services;
import com.cursee.monolib.util.toml.Toml;
import com.cursee.monolib.util.toml.TomlWriter;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Configuration implements IMixinConfigPlugin {

    public static boolean enabled = true;
    public static long max_difficulty = 0L;

    public static final Map<String, Object> defaults = new HashMap<String, Object>();

    @Override
    public void onLoad(String mixinPackage) {

        Configuration.defaults.put("enabled", enabled);
        Configuration.defaults.put("max_difficulty", max_difficulty);

        final File CONFIG_DIRECTORY = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");

        if (!CONFIG_DIRECTORY.isDirectory()) CONFIG_DIRECTORY.mkdir();

        final String CONFIG_FILEPATH = CONFIG_DIRECTORY + File.separator + Constants.MOD_ID + ".toml";

        File CONFIG_FILE = new File(CONFIG_FILEPATH);

        Configuration.handle(CONFIG_FILE);
    }

    private static void handle(File file) {

        final boolean FILE_NOT_FOUND = !file.isFile();

        if (FILE_NOT_FOUND) createConfigurationFile(file);
        else loadConfigurationFile(file);
    }

    private static void createConfigurationFile(File file) {
        try {
            TomlWriter writer = new TomlWriter();
            writer.write(defaults, file);
        }
        catch (IOException exception) {
            Constants.LOG.error("Fatal error occurred while attempting to write " + Constants.MOD_ID + ".toml");
            Constants.LOG.error("Did another process delete the config directory during writing?");
            Constants.LOG.error(exception.getMessage());
        }
    }

    private static void loadConfigurationFile(File file) {
        try {
            Toml toml = new Toml().read(file);
            Configuration.enabled = toml.getBoolean("enabled");
            Configuration.max_difficulty = toml.getLong("max_difficulty");
        }
        catch (IllegalStateException exception) {
            Constants.LOG.error("Fatal error occurred while attempting to read " + Constants.MOD_ID + ".toml");
            Constants.LOG.error("Did another process delete the file during reading?");
            Constants.LOG.error(exception.getMessage());
        }
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
