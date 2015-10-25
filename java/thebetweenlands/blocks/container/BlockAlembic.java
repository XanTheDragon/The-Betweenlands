package thebetweenlands.blocks.container;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.client.particle.BLParticle;
import thebetweenlands.creativetabs.ModCreativeTabs;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.tileentities.TileEntityAlembic;

public class BlockAlembic extends BlockContainer {

	public BlockAlembic() {
		super(Material.rock);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("thebetweenlands.alembic");
		setCreativeTab(ModCreativeTabs.herbLore);
		setBlockTextureName("thebetweenlands:betweenstone");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return BLBlockRegistry.betweenstone.getIcon(0, 0);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		int rot = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, rot == 0 ? 2 : rot == 1 ? 5 : rot == 2 ? 3 : 4, 3);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (world.getTileEntity(x, y, z) instanceof TileEntityAlembic) {
				TileEntityAlembic tile = (TileEntityAlembic) world.getTileEntity(x, y, z);

				if (player.isSneaking())
					return false;

				if (player.getCurrentEquippedItem() != null) {
					ItemStack heldStack = player.getCurrentEquippedItem();
					if(heldStack.getItem() == BLItemRegistry.weedwoodBucketInfusion) {
						if(!tile.isFull()) {
							tile.addInfusion(heldStack);
							player.setCurrentItemOrArmor(0, null);
						}
					}
				}
				if(tile.hasFinished()) {
					ItemStack result = tile.getElixir();
					EntityItem itemEntity = player.dropPlayerItemWithRandomChoice(result, false);
					if(itemEntity != null) itemEntity.delayBeforeCanPickup = 0;
				}
			}
		}
		return true;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (world.getTileEntity(x, y, z) instanceof TileEntityAlembic) {
			TileEntityAlembic alembic = (TileEntityAlembic) world.getTileEntity(x, y, z);
			if (alembic.isRunning()) {
				float xx = (float) x + 0.5F;
				float yy = (float) (y + 0.35F + rand.nextFloat() * 0.5F);
				float zz = (float) z + 0.5F;
				float fixedOffset = 0.25F;
				float randomOffset = rand.nextFloat() * 0.6F - 0.3F;
				BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx - fixedOffset), (double) y + 0.75D, (double) (zz + randomOffset), 0.0D, 0.0D, 0.0D, 0);
				BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx + fixedOffset), (double) y + 0.75D, (double) (zz + randomOffset), 0.0D, 0.0D, 0.0D, 0);
				BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx + randomOffset), (double) y + 0.75D, (double) (zz - fixedOffset), 0.0D, 0.0D, 0.0D, 0);
				BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx + randomOffset), (double) y + 0.75D, (double) (zz + fixedOffset), 0.0D, 0.0D, 0.0D, 0);
			}
		}
	}
	@Override
	public int getRenderType() {
		return - 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAlembic();
	}
}