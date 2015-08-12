package thebetweenlands.world.biomes;

import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.entities.mobs.EntityAngler;
import thebetweenlands.entities.mobs.EntityBloodSnail;
import thebetweenlands.entities.mobs.EntityFirefly;
import thebetweenlands.entities.mobs.EntityLeech;
import thebetweenlands.entities.mobs.EntityMireSnail;
import thebetweenlands.entities.mobs.EntitySporeling;
import thebetweenlands.entities.mobs.EntitySwampHag;
import thebetweenlands.entities.mobs.EntityWight;
import thebetweenlands.world.WorldProviderBetweenlands;
import thebetweenlands.world.biomes.base.BiomeGenBaseBetweenlands;
import thebetweenlands.world.biomes.decorators.BiomeDecoratorSwampLands;
import thebetweenlands.world.biomes.decorators.base.BiomeDecoratorBaseBetweenlands;
import thebetweenlands.world.biomes.feature.AlgaeNoiseFeature;
import thebetweenlands.world.biomes.feature.FlatLandNoiseFeature;
import thebetweenlands.world.biomes.feature.PatchNoiseFeature;

public class BiomeSwampLands
        extends BiomeGenBaseBetweenlands {
    public BiomeSwampLands(int biomeID) {
        this(biomeID, new BiomeDecoratorSwampLands());
    }

    public BiomeSwampLands(int biomeID, BiomeDecoratorBaseBetweenlands decorator) {
        super(biomeID, decorator);
        this.setFogColor((byte) 10, (byte) 30, (byte) 12);
        setColors(0x314D31, 0x314D31);
        setWeight(20);
        this.setHeightAndVariation(WorldProviderBetweenlands.LAYER_HEIGHT - 10, 0);
        this.setBiomeName("Swamplands");
        this.setBlocks(BLBlockRegistry.betweenstone, BLBlockRegistry.swampDirt, BLBlockRegistry.deadGrass, BLBlockRegistry.mud, BLBlockRegistry.betweenlandsBedrock);
        this.setFillerBlockHeight((byte) 1);
        this.addFeature(new FlatLandNoiseFeature())
                .addFeature(new PatchNoiseFeature(0.03125D * 5.75D, 0.03125D * 5.75D, BLBlockRegistry.swampGrass))
                .addFeature(new PatchNoiseFeature(0.74D, 0.74D, BLBlockRegistry.swampDirt))
                .addFeature(new PatchNoiseFeature(0.79D, 0.79D, BLBlockRegistry.mud))
                .addFeature(new AlgaeNoiseFeature());
        this.waterColorMultiplier = 0x184220;

        spawnableMonsterList.add(new SpawnListEntry(EntitySwampHag.class, 20, 1, 1));
        spawnableMonsterList.add(new SpawnListEntry(EntityLeech.class, 15, 1, 1));
        // spawnableMonsterList.add(new SpawnListEntry(EntityTarBeast.class, 15, 1, 1)); TODO
        spawnableMonsterList.add(new SpawnListEntry(EntityWight.class, 5, 0, 0));
        spawnableWaterCreatureList.add(new SpawnListEntry(EntityAngler.class, 20, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityFirefly.class, 40, 2, 6));
        spawnableCreatureList.add(new SpawnListEntry(EntityMireSnail.class, 30, 1, 2));
        spawnableMonsterList.add(new SpawnListEntry(EntityBloodSnail.class, 15, 1, 1));
        spawnableMonsterList.add(new SpawnListEntry(EntitySporeling.class, 100, 5, 8));
    }

	/*@Override
    public int getRootHeight(int x, int z) {
		return WorldProviderBetweenlands.LAYER_HEIGHT - 10;
	}

	@Override
	public int getHeightVariation(int x, int z) {
		return 0;
	}*/
}
