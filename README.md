# Charm Enchantment Fix

This simple mod fixes Charm's **Collection** and **Aerial Affinity** enchantments not being obtainable via **Villager trading** or the **Enchanting Table**.

It does so by adding `tradeable.json` and `in_enchanting_table.json` tag files as resources.

### Background
This is directed towards [this fork](https://modrinth.com/mod/charm-forked) of **Charm**, specifically tested on `charm-fabric-1.21.1-7.0.42-forked` as shipped with **BMC3 v43** for Fabric 1.21.1.

In 1.21.1, these enchantments are no longer obtainable via Villager trading or the Enchanting Table — both of which worked fine in 1.20.1 (verified by testing).

I'm assuming this is an unintentional oversight introduced during the 1.21 enchantment system overhaul, with [this issue](https://github.com/LunaPixelStudios/Better-MC/issues/2133) and [this discussion](https://www.reddit.com/r/betterminecraft/comments/1ppnp4j/collection_enchantment/) supporting that claim.

### Usage
Requires [**Fabric API**](https://modrinth.com/mod/fabric-api). Drop this mod into your mods folder alongside **Charm**. This is a **server-side only** mod — clients don't need it.

### Credits
Icon uses enchantment book textures from [Beautiful Enchanted Books](https://modrinth.com/resourcepack/beautiful-enchanted-books).

Icon tooltip generated using [Minecraft Tooltip Generator](https://rapidthrower264.github.io/MinecraftTooltipGenerator/).
