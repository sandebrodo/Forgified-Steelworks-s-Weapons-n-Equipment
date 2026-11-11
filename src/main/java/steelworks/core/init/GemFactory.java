package steelworks.core.init;

import net.minecraft.resources.ResourceLocation;
import steelworks.core.item.accessories.AccessoryGem;

import java.util.*;

public final class GemFactory {

    private GemFactory() {}

    private static final Map<ResourceLocation, AccessoryGem> GEMS = new HashMap<>();

    public static AccessoryGem register(AccessoryGem manager) {

       if (GEMS.putIfAbsent(manager.id(), manager) != null) {
           throw new IllegalArgumentException("Duplicate gem effect: " + manager.id());
       }
       return manager;
    }

    public static AccessoryGem get(ResourceLocation id) {
        return GEMS.get(id);
    }

    public static boolean contains(ResourceLocation id) {
        return GEMS.containsKey(id);
    }

    public static Map<ResourceLocation, AccessoryGem> getAll() {
        return GEMS;
    }
}
