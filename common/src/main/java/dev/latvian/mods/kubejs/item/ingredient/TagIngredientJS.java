package dev.latvian.mods.kubejs.item.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.Tags;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.*;

/**
 * @author LatvianModder
 */
public class TagIngredientJS implements IngredientJS {
	private static final Map<String, TagIngredientJS> tagIngredientCache = new HashMap<>();

	public static TagIngredientJS createTag(String tag) {
		return tagIngredientCache.computeIfAbsent(tag, TagIngredientJS::new).validateTag();
	}

	public static void clearTagCache() {
		tagIngredientCache.clear();
	}

	private final ResourceLocation tag;
	private Tag<Item> actualTag;

	private TagIngredientJS(String t) {
		tag = UtilsJS.getMCID(t);
	}

	public String getTag() {
		return tag.toString();
	}

	public Tag<Item> getActualTag() {
		if (actualTag == null) {
			actualTag = Tags.items().getTagOrEmpty(tag);
		}

		return actualTag;
	}

	@Override
	public boolean test(ItemStackJS stack) {
		return !stack.isEmpty() && getActualTag().contains(stack.getItem());
	}

	@Override
	public boolean testVanilla(ItemStack stack) {
		return !stack.isEmpty() && getActualTag().contains(stack.getItem());
	}

	@Override
	public boolean testVanillaItem(Item item) {
		return item != Items.AIR && getActualTag().contains(item);
	}

	@Override
	public Set<ItemStackJS> getStacks() {
		var t = getActualTag();

		if (t.getValues().size() > 0) {
			Set<ItemStackJS> set = new LinkedHashSet<>();

			for (var item : t.getValues()) {
				set.add(new ItemStackJS(new ItemStack(item)));
			}

			return set;
		}

		return Collections.emptySet();
	}

	@Override
	public Set<Item> getVanillaItems() {
		var t = getActualTag();

		if (t.getValues().size() > 0) {
			return new LinkedHashSet<>(t.getValues());
		}

		return Collections.emptySet();
	}

	@Override
	public ItemStackJS getFirst() {
		validateTag();

		for (var item : getActualTag().getValues()) {
			return new ItemStackJS(new ItemStack(item));
		}

		return ItemStackJS.EMPTY;
	}

	@Override
	public boolean isEmpty() {
		return getActualTag().getValues().isEmpty();
	}

	@Override
	public String toString() {
		return "'#" + tag + "'";
	}

	@Override
	public JsonElement toJson() {
		var json = new JsonObject();
		json.addProperty("tag", tag.toString());
		return json;
	}

	@Override
	public boolean anyStackMatches(IngredientJS ingredient) {
		if (ingredient instanceof TagIngredientJS tagIngredient && tag.equals(tagIngredient.tag)) {
			return true;
		}

		return IngredientJS.super.anyStackMatches(ingredient);
	}

	private TagIngredientJS validateTag() {
		if (RecipeJS.itemErrors && isEmpty()) {
			throw new RecipeExceptionJS(String.format("Tag '#%s' doesn't contain any items!", tag)).error();
		}
		return this;
	}

	@Override
	public Ingredient createVanillaIngredient() {
		return Ingredient.of(getActualTag());
	}
}