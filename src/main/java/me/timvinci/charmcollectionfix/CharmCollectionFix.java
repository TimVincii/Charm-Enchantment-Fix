package me.timvinci.charmcollectionfix;

import me.timvinci.charmcollectionfix.util.Reference;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CharmCollectionFix implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {} [{}].", Reference.MOD_NAME, Reference.MOD_VERSION);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            RegistryWrapper.Impl<Enchantment> enchantmentRegistry = server
                    .getRegistryManager()
                    .getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

            Identifier collectionIdentifier = Identifier.of("charm", "collection");
            RegistryKey<Enchantment> collectionKey = RegistryKey.of(
                    RegistryKeys.ENCHANTMENT,
                    collectionIdentifier
            );

            // getOptional returns Optional<RegistryEntry.Reference<T>>
            enchantmentRegistry.getOptional(collectionKey).ifPresentOrElse(
                    entry -> {
                        // Whether a librarian will trade this enchantment is determined
                        // entirely by the #minecraft:tradeable tag
                        boolean tradeable = entry.isIn(EnchantmentTags.IN_ENCHANTING_TABLE);
                        LOGGER.info("[{}] {} should now be tradeable, status: {}",
                                Reference.MOD_NAME, collectionIdentifier, tradeable);
                        if (!tradeable) {
                            LOGGER.warn("[{}] Something went wrong, {} is not tradeable!",
                                    Reference.MOD_NAME, collectionIdentifier);
                        }

                        // As for the enchanting table, the #minecraft:in_enchanting_table tag is used
                        boolean in_enchanting_table = entry.isIn(EnchantmentTags.IN_ENCHANTING_TABLE);
                        LOGGER.info("[{}] {} should now be obtainable via the enchanting table, status: {}",
                                Reference.MOD_NAME, collectionIdentifier, in_enchanting_table);
                        if (!in_enchanting_table) {
                            LOGGER.warn("[{}] Something went wrong, {} is not obtainable via the enchanting table",
                                    Reference.MOD_NAME, collectionIdentifier);
                        }
                    },
                    () -> LOGGER.warn("[{}] {} not found — is Charm installed?",
                            Reference.MOD_NAME, collectionIdentifier)
            );
        });
    }
}
