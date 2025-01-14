package dev.latvian.mods.kubejs.client;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/**
 * @author LatvianModder
 */
public class SoundRegistryEventJS extends StartupEventJS {
	private final Consumer<ResourceLocation> registry;

	public SoundRegistryEventJS(Consumer<ResourceLocation> registry) {
		this.registry = registry;
	}

	public void register(ResourceLocation r) {
		registry.accept(r);
	}
}