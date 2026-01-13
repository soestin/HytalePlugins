package com.fancyinnovations.fancycore.commands.server;

import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.fancyinnovations.fancycore.utils.TimeUtils;
import com.fancyinnovations.fancyspaces.utils.HttpRequest;
import com.fancyinnovations.versionchecker.FetchedVersion;
import com.fancyinnovations.versionchecker.VersionChecker;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import de.oliver.fancyanalytics.logger.ExtendedFancyLogger;
import de.oliver.fancyanalytics.logger.properties.StringProperty;
import de.oliver.fancyanalytics.logger.properties.ThrowableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class UpdatePluginCMD extends AbstractCommand {

    private final ExtendedFancyLogger logger;
    private final VersionChecker versionChecker;

    public UpdatePluginCMD()  {
        super("updateplugin", "Updates the FancyCore plugin to the latest version");
        this.logger = FancyCorePlugin.get().getFancyLogger();
        this.versionChecker = FancyCorePlugin.get().getVersionChecker();
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NotNull CommandContext commandContext) {
        FetchedVersion latestVersion = this.versionChecker.check();
        if (latestVersion == null) {
            // TODO (I18N): make translatable
            commandContext.sender().sendMessage(Message.raw("FancyCore is up to date"));
            logger.info("FancyCore is up to date");
            return CompletableFuture.completedFuture(null);
        }

        // TODO (I18N): make translatable
        commandContext.sender().sendMessage(
                Message.raw("A new version of FancyCore is available: " + latestVersion.name() +
                        ". It will be downloaded now. Please restart the server after the download is complete.")
        );
        logger.info(
                "A new version of FancyCore is available",
                StringProperty.of("version", latestVersion.name()),
                StringProperty.of("published_at", TimeUtils.formatDate(latestVersion.publishedAt())),
                StringProperty.of("download_link", latestVersion.downloadURL())
        );

        // TODO (I18N): make translatable
        commandContext.sender().sendMessage(
                Message.raw("Downloading FancyCore version " + latestVersion.name() + "...")
        );
        logger.info(
                "Starting to download the latest version of FancyCore...",
                StringProperty.of("version", latestVersion.name())
        );

        HttpRequest req = new HttpRequest(latestVersion.downloadURL())
                .withMethod("GET")
                .withHeader("User-Agent", "FancyCore-PluginUpdater")
                .withBodyHandler(HttpResponse.BodyHandlers.ofByteArray());

        try {
            File modsDir = new File("mods");
            for (File file : modsDir.listFiles()) {
                if (file.getName().startsWith("FancyCore") && file.getName().endsWith(".jar")) {
                    Files.delete(file.toPath());
                }
            }

            HttpResponse<byte[]> resp = req.send();
            Files.write(Path.of("mods/FancyCore.jar"), resp.body());

            commandContext.sender().sendMessage(
                    Message.raw("Successfully downloaded FancyCore version " + latestVersion.name() + ". Please restart the server to apply the update.")
            );
            logger.info(
                    "Successfully downloaded the latest version of FancyCore",
                    StringProperty.of("version", latestVersion.name()),
                    StringProperty.of("published_at", TimeUtils.formatDate(latestVersion.publishedAt())),
                    StringProperty.of("download_link", latestVersion.downloadURL())
            );

            PluginManager.get().reload(FancyCorePlugin.get().getIdentifier());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            commandContext.sender().sendMessage(
                    Message.raw("Failed to download the latest version of FancyCore. Please check the logs for more information.")
            );
            logger.error(
                    "Failed to download the latest version of FancyCore",
                    StringProperty.of("version", latestVersion.name()),
                    StringProperty.of("published_at", TimeUtils.formatDate(latestVersion.publishedAt())),
                    StringProperty.of("download_link", latestVersion.downloadURL()),
                    ThrowableProperty.of(e)
            );
        }

        return CompletableFuture.completedFuture(null);
    }
}
