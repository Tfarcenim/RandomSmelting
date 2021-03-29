package tfar.randomsmelting.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.randomsmelting.MixinHooks;

@Mixin(AbstractFurnaceTileEntity.class)
public class AbstractFurnaceMixin {

	@Shadow protected NonNullList<ItemStack> items;

	@Redirect(method = "smelt",at = @At(value = "INVOKE",target = "Lnet/minecraft/item/crafting/IRecipe;getRecipeOutput()Lnet/minecraft/item/ItemStack;"))
	public ItemStack scrambleRecipes(IRecipe<?> iRecipe) {
		return MixinHooks.items.get(MixinHooks.random.nextInt(MixinHooks.items.size()));
	}

	@Inject(method = "canSmelt",at = @At("HEAD"),cancellable = true)
	private void voidFix(IRecipe<?> recipeIn, CallbackInfoReturnable<Boolean> cir) {
		if (!this.items.get(2).isEmpty()) {
			cir.setReturnValue(false);
		}
	}
}
