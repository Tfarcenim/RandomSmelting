package tfar.randomsmelting.mixin;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.randomsmelting.MixinHooks;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow private Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes;

    @Inject(method = "apply",at = @At("RETURN"))
    private void captureItems(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn, CallbackInfo ci) {
        MixinHooks.items.clear();
        List<IRecipeType<?>> types = Lists.newArrayList(IRecipeType.BLASTING,IRecipeType.SMELTING,IRecipeType.SMOKING);
        for (IRecipeType<?> type : types) {
            MixinHooks.items.addAll(recipes.get(type).values().stream().map(iRecipe -> {
                AbstractCookingRecipe recipe = (AbstractCookingRecipe)iRecipe;
                return recipe.getRecipeOutput();
            }).collect(Collectors.toList()));
        }
    }
}
