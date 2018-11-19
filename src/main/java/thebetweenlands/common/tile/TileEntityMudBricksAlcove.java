package thebetweenlands.common.tile;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileEntityMudBricksAlcove extends TileEntityLootInventory {
	public boolean has_urn, greebled, top_web, bottom_web, small_candle, big_candle, out_crop;
	public int urn_type = 0, rotationOffset = 0;

	public TileEntityMudBricksAlcove() {
		super(1, "container.mud_bricks_alcove");
	}

	public void setUpGreeble() {
		Random rand = getWorld().rand;
		if(!greebled) {
			if(rand.nextInt(3) == 0)
				has_urn = true;
			if(has_urn) {
				urn_type = rand.nextInt(3);
				rotationOffset = rand.nextInt(41) - 20;
			}
			top_web = rand.nextBoolean();
			bottom_web = rand.nextBoolean();
			small_candle = rand.nextBoolean();
			big_candle = rand.nextBoolean();
			out_crop = rand.nextBoolean();
			greebled = true;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		greebled = nbt.getBoolean("greebled");
		has_urn = nbt.getBoolean("has_urn");
		urn_type = nbt.getInteger("urn_type");
		rotationOffset = nbt.getInteger("rotationOffset");
		top_web = nbt.getBoolean("top_web");
		bottom_web = nbt.getBoolean("bottom_web");
		small_candle = nbt.getBoolean("small_candle");
		big_candle = nbt.getBoolean("big_candle");
		out_crop = nbt.getBoolean("out_crop");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("greebled", greebled);
		nbt.setBoolean("has_urn", has_urn);
		nbt.setInteger("urn_type", urn_type);
		nbt.setInteger("rotationOffset", this.rotationOffset);
		nbt.setBoolean("top_web", top_web);
		nbt.setBoolean("bottom_web", bottom_web);
		nbt.setBoolean("small_candle", small_candle);
		nbt.setBoolean("big_candle", big_candle );
		nbt.setBoolean("out_crop", out_crop);
		return nbt;
	}

	@Override
    public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
        return writeToNBT(nbt);
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}
}
