package dev.latvian.mods.kubejs.level.gen.forge;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = "kubejs")
public class KubeJSForgeWorldgen {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onBiomesLoad(BiomeLoadingEvent event) {
		//new WorldgenRemoveEventJSForge(event).post(ScriptType.STARTUP, KubeJSEvents.WORLDGEN_REMOVE);
		//new WorldgenAddEventJSForge(event).post(ScriptType.STARTUP, KubeJSEvents.WORLDGEN_ADD);
	}
}