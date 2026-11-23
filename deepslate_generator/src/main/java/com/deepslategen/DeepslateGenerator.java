package com.deepslategen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
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
        NeoForge.EVENT_BUS.addListener(DeepslateGenerator::onNeighborNotify);
    }

    // Camino 1: cuando el juego avisa que un fluido va a poner un bloque (stone).
    private static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        BlockState state = event.getNewState();
        if (state.is(Blocks.COBBLESTONE) || state.is(Blocks.STONE)) {
            event.setNewState(Blocks.DEEPSLATE.defaultBlockState());
        }
    }

    // Camino 2: cobblestone que aparece pegado a agua Y lava a la vez.
    private static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!event.getState().is(Blocks.COBBLESTONE)) return;

        BlockPos pos = event.getPos();
        boolean hasWater = false;
        boolean hasLava = false;
        for (Direction dir : Direction.values()) {
            var fluid = level.getFluidState(pos.relative(dir));
            if (fluid.is(FluidTags.WATER)) hasWater = true;
            if (fluid.is(FluidTags.LAVA)) hasLava = true;
        }
        if (!(hasWater && hasLava)) return;

        // Lo cambiamos un instante después, para no modificar el mundo en medio de una actualización.
        level.getServer().execute(() -> {
            if (level.getBlockState(pos).is(Blocks.COBBLESTONE)) {
                level.setBlock(pos, Blocks.DEEPSLATE.defaultBlockState(), 3);
            }
        });
    }
}
