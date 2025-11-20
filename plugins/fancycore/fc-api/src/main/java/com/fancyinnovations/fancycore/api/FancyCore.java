package com.fancyinnovations.fancycore.api;

import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.player.FancyPlayerStorage;
import de.oliver.fancyanalytics.logger.ExtendedFancyLogger;

public interface FancyCore {

    ExtendedFancyLogger getFancyLogger();

    FancyPlayerStorage getPlayerStorage();
    FancyPlayerService getPlayerService();

}
