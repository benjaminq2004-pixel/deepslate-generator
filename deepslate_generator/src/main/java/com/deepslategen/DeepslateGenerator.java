package com.deepslategen;

import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;

@Mod(DeepslateGenerator.MODID)
public class DeepslateGenerator {
    public static final String MODID = "deepslategen";

    public DeepslateGenerator() {
        NeoForge.EVENT_BUS.addListener(DeepslateGenerator::onFluidPlaceBlock);
    }

    // Se dispara cuando un fluido (agua/lava) va a colocar un bloque por interacción.
    private static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        if (event.getNewState().is(Blocks.COBBLESTONE)) {
            // Para generar "cobbled deepslate" en vez de "deepslate", usa Blocks.COBBLED_DEEPSLATE
            event.setNewState(Blocks.DEEPSLATE.defaultBlockState());
        }
    }
}
