package steelworks.core.init;

import steelworks.SteelworksWeapons;
import steelworks.core.item.accessories.AccessoryGem;

public final class AccessoryGems {

    private AccessoryGems() {}

    public static final AccessoryGem RUBY = GemFactory.register(
            new AccessoryGem.Builder(SteelworksWeapons.resourceLocation("ruby"))
                    .hitEffect(((target, source) -> {

                        stack.copy();

                    }))
                    .build()
    );

}
