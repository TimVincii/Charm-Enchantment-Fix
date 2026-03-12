package me.timvinci.charmenchantmentfix;

import me.timvinci.charmenchantmentfix.util.Reference;
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


public class CharmEnchantmentFix implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {} [{}].", Reference.MOD_NAME, Reference.MOD_VERSION);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            RegistryWrapper.Impl<Enchantment> enchantmentRegistry = server
                    .getRegistryManager()
                    .getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

            logEnchantmentStatus(enchantmentRegistry, Identifier.of("charm", "collection"));
            logEnchantmentStatus(enchantmentRegistry, Identifier.of("charm", "aerial_affinity"));
        });
    }

    public void logEnchantmentStatus(RegistryWrapper.Impl<Enchantment> enchantmentRegistry, Identifier identifier) {
        RegistryKey<Enchantment> key = RegistryKey.of(
                RegistryKeys.ENCHANTMENT,
                identifier
        );

        // getOptional returns Optional<RegistryEntry.Reference<T>>
        enchantmentRegistry.getOptional(key).ifPresentOrElse(
                entry -> {
                    // Whether a librarian will trade this enchantment is determined
                    // entirely by the #minecraft:tradeable tag
                    boolean tradeable = entry.isIn(EnchantmentTags.IN_ENCHANTING_TABLE);
                    LOGGER.info("[{}] {} should now be tradeable, status: {}",
                            Reference.MOD_NAME, identifier, tradeable);
                    if (!tradeable) {
                        LOGGER.warn("[{}] Something went wrong, {} is not tradeable!",
                                Reference.MOD_NAME, identifier);
                    }

                    // As for the enchanting table, the #minecraft:in_enchanting_table tag is used
                    boolean in_enchanting_table = entry.isIn(EnchantmentTags.IN_ENCHANTING_TABLE);
                    LOGGER.info("[{}] {} should now be obtainable via the enchanting table, status: {}",
                            Reference.MOD_NAME, identifier, in_enchanting_table);
                    if (!in_enchanting_table) {
                        LOGGER.warn("[{}] Something went wrong, {} is not obtainable via the enchanting table",
                                Reference.MOD_NAME, identifier);
                    }
                },
                () -> LOGGER.warn("[{}] {} not found — is Charm installed?",
                        Reference.MOD_NAME, identifier)
        );
    }
}
