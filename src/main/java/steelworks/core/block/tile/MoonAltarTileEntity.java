    package steelworks.core.block.tile;

    import net.minecraft.core.BlockPos;
    import net.minecraft.core.HolderLookup;
    import net.minecraft.core.particles.SimpleParticleType;
    import net.minecraft.server.level.ServerLevel;
    import net.minecraft.sounds.SoundEvents;
    import net.minecraft.sounds.SoundSource;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.item.crafting.Ingredient;
    import net.minecraft.world.item.crafting.RecipeHolder;
    import net.minecraft.world.level.Level;
    import net.minecraft.world.level.block.entity.BlockEntity;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.phys.Vec3;
    import org.checkerframework.checker.units.qual.N;
    import org.jetbrains.annotations.Nullable;
    import software.bernie.geckolib.animatable.GeoBlockEntity;
    import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
    import software.bernie.geckolib.animation.AnimatableManager;
    import software.bernie.geckolib.animation.AnimationController;
    import software.bernie.geckolib.animation.PlayState;
    import software.bernie.geckolib.animation.RawAnimation;
    import software.bernie.geckolib.util.GeckoLibUtil;
    import steelworks.core.block.MoonAltarBlock;
    import steelworks.core.container.MoonAltarContainer;
    import steelworks.core.recipe.MoonAltarRecipe;
    import steelworks.core.recipe.MoonAltarRecipeInput;
    import steelworks.core.registry.ModParticles;
    import steelworks.core.registry.ModRecipes;
    import steelworks.core.registry.ModTileEntities;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    public class MoonAltarTileEntity extends PedestalTileEntity implements GeoBlockEntity {


        private int progress = 0;
        private final int maxProgress = 200;
        private int particleTicks = 0;
        private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
        private boolean isCrafting = false;

        public MoonAltarTileEntity(BlockPos pos, BlockState state) {
            super(ModTileEntities.MOON_ALTAR_TE.get(), pos, state);
            setItemRendererOffset(1.3F);
        }


        public void tick(Level level, BlockPos pos, BlockState state) {
            updateGlow(level, pos);
            if (!isNight(level) || !isOverworld(level)) return;

            MoonAltarContainer container = createContainer();
            List<ItemStack> items = getItemsFromPedestals(pos, level);
            MoonAltarRecipeInput input = new MoonAltarRecipeInput(this.inventory.getStackInSlot(SLOT_INDEX), items);

            Optional<RecipeHolder<MoonAltarRecipe>> recipeHolder = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.MOON_ALTAR_RECIPE_TYPE.get(), input, level);



            Vec3 altarCenter = Vec3.atCenterOf(this.worldPosition).add(0, 0.5, 0);

            if (recipeHolder.isPresent()) {

                MoonAltarRecipe recipe = recipeHolder.get().value();


                if (!this.isCrafting) {
                    this.isCrafting = true;
                    triggerAnim("main", "craft");
                }

                playCraftProgressSound(pos, level);


                this.sync();

                this.particleTicks++;

                createParticleCircle(level, altarCenter, 1, 12, 1.0, ModParticles.MOON_ALTAR_PARTICLE_2.get());
                createParticleCircle(level, altarCenter, 2, 12, 1.5, ModParticles.MOON_ALTAR_PARTICLE_3.get());
                createParticleCircle(level, altarCenter, 4, 12, 2.0, ModParticles.MOON_ALTAR_PARTICLE_2.get());
                createParticleCirclesAroundPedestals(level, 0.5, 8, 0.0, container);
                increaseCraftingProgress();
                setChanged(level, pos, state);

                if (hasCraftingFinished()) {
                    craftItem(recipe, container);
                    playCraftFinishSound(pos, level);
                    resetProgress();
                    this.isCrafting = false;
                }
            } else {
                resetProgress();
                this.isCrafting = false;
            }

        }

        private void resetProgress() {
            this.progress = 0;
            this.particleTicks = 0;
        }

        private void playCraftFinishSound(BlockPos pos, Level level) {
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.ENDER_EYE_DEATH, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        private void playCraftProgressSound(BlockPos pos, Level level) {
            if (progress % 10 != 0) return;
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        private void increaseCraftingProgress() {
            this.sync();
            this.progress++;
        }


        private void removeItemsFromPedestals(MoonAltarRecipe recipe, MoonAltarContainer container) {
            for (Ingredient ingredient : recipe.ingredients()) {
                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack stack = container.getItem(i);
                    if (!stack.isEmpty() && ingredient.test(stack)) {
                        container.removeItem(i, 1);
                        break;
                    }
                }
            }
        }

        private boolean hasCraftingFinished() {
            return this.progress >= this.maxProgress;
        }

        private boolean isNight(Level level) {
            return level.isNight();
        }

        private boolean isOverworld(Level level) {
            return level.dimension() == Level.OVERWORLD;
        }

        private void createParticleCircle(Level level, Vec3 center, double radius, int count, double yOffset, SimpleParticleType particle) {

            if (!(level instanceof ServerLevel serverLevel)) return;

            if (this.particleTicks % 10 != 0) return;

            for (int i = 0; i < count; i++) {

                double angle = (2 * Math.PI / count) * i;

                double x = center.x + radius * Math.cos(angle);
                double z = center.z + radius * Math.sin(angle);

                serverLevel.sendParticles(particle,
                        x, center.y + yOffset, z,
                        1,
                        0.1, 0.01, 0.1,
                        0.0);
            }
        }

        public @Nullable List<BlockPos> findNearestPedestals(BlockPos center, Level level) {

            if (level == null) return null;

            List<BlockPos> positions = new ArrayList<>();

            int radius = 3;
            BlockPos areaStart = center.offset(-radius, 0, -radius);
            BlockPos areaEnd = center.offset(radius, 0, radius);

            for (BlockPos pos : BlockPos.betweenClosed(areaStart, areaEnd)) {

                if (level.getBlockEntity(pos) instanceof MoonAltarTileEntity) continue;

                if (level.getBlockEntity(pos) instanceof PedestalTileEntity) {
                    positions.add(pos.immutable());
                }
            }
            return positions;
        }

        private MoonAltarContainer createContainer() {
            List<BlockPos> pedestalPositions = findNearestPedestals(this.worldPosition, this.level);

            if (pedestalPositions == null) pedestalPositions = new ArrayList<>();

            List<BlockPos> filledPositions = new ArrayList<>();
            List<ItemStack> items = new ArrayList<>();

            for (BlockPos pos : pedestalPositions) {

                if (this.level.getBlockEntity(pos) instanceof PedestalTileEntity pedestal) {
                    ItemStack stack = pedestal.inventory.getStackInSlot(SLOT_INDEX);
                    if (!stack.isEmpty()) {
                        items.add(stack);
                        filledPositions.add(pos);
                    }
                }
            }

            return new MoonAltarContainer(this.level, this, filledPositions, items.toArray(new ItemStack[0]));
        }

        private List<ItemStack> getItemsFromPedestals(BlockPos center, Level level) {

            List<ItemStack> items = new ArrayList<>();
            List<BlockPos> pedestalPositions = findNearestPedestals(center, level);
            if (pedestalPositions == null) return items;

            for (BlockPos pos : pedestalPositions) {

                BlockEntity tile = level.getBlockEntity(pos);

                if (tile instanceof PedestalTileEntity pedestalTile) {

                    if (!pedestalTile.inventory.getStackInSlot(pedestalTile.SLOT_INDEX).isEmpty()) {
                        items.add(pedestalTile.inventory.getStackInSlot(pedestalTile.SLOT_INDEX));
                    }
                }
            }
            return items;
        }

        private void createParticleCirclesAroundPedestals(Level level, double radius, int count, double yOffset, MoonAltarContainer container) {

            if (!(level instanceof ServerLevel serverLevel)) return;

            if (this.particleTicks % 10 != 0) return;

            for (int p = 0; p < container.getContainerSize(); p++) {

                ItemStack stack = container.getItem(p);
                if (stack.isEmpty()) continue;

                BlockPos pedestalPos = container.getPedestalPosition(p);
                Vec3 pedestalCenter = Vec3.atCenterOf(pedestalPos).add(0, 0.5, 0);

                for (int i = 0; i < count; i++) {
                    double angle = (2 * Math.PI / count) * i;

                    double x = pedestalCenter.x + radius * Math.cos(angle);
                    double z = pedestalCenter.z + radius * Math.sin(angle);

                    serverLevel.sendParticles(ModParticles.MOON_ALTAR_PARTICLE_1.get(),
                            x, pedestalCenter.y + yOffset, z,
                            1,
                            0.0, 0.0, 0.0,
                            0.0);
                }
            }
        }

        public void updateGlow(Level level, BlockPos pos) {

            BlockState state = level.getBlockState(pos);
            boolean shouldGlow = isNight(level) && isOverworld(level);

            if (state.getValue(MoonAltarBlock.LIT) != shouldGlow) {
                level.setBlock(pos, state.setValue(MoonAltarBlock.LIT, shouldGlow), 3);
            }
        }

        private void craftItem(MoonAltarRecipe recipe, MoonAltarContainer container) {

            ItemStack result = recipe.getResultItem(this.level.registryAccess());
            container.removeCatalystItem(1, false);
            removeItemsFromPedestals(recipe, container);

            this.inventory.setStackInSlot(SLOT_INDEX, new ItemStack(result.getItem()));
        }

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
            controllerRegistrar.add(new AnimationController<>(this, "main", 5, state -> PlayState.STOP)
                    .triggerableAnim("craft", RawAnimation.begin().thenPlay("circle_anim")
                    ));

        }

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return this.cache;
        }
    }
