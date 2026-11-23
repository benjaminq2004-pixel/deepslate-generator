package com.deepslategen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;

@Mod(DeepslateGenerator.MODID)
public class DeepslateGenerator {
    public static final String MODID = "deepslategen";

    public DeepslateGenerator() {
        NeoForge.EVENT_BUS.addListener(DeepslateGenerator::onFluidPlaceBlock);
    }

    // Se dispara cuando agua/lava van a generar un bloque por interacción.
    private static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        BlockState state = event.getNewState();
        if (state.is(Blocks.COBBLESTONE) || state.is(Blocks.STONE)) {
            event.setNewState(Blocks.DEEPSLATE.defaultBlockState());
        }
    }
}
