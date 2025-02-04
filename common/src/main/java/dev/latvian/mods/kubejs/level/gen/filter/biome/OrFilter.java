package dev.latvian.mods.kubejs.level.gen.filter.biome;

import dev.architectury.registry.level.biome.BiomeModifications;

import java.util.List;

public record OrFilter(List<BiomeFilter> list) implements BiomeFilter {
	@Override
	public boolean test(BiomeModifications.BiomeContext ctx) {
		for (var filter : list) {
			if (filter.test(ctx)) {
				return true;
			}
		}

		return false;
	}
}
