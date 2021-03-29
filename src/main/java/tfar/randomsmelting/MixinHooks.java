package tfar.randomsmelting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MixinHooks {

    public static List<ItemStack> items = new ArrayList<>();

    public static Random random = new Random();
}
