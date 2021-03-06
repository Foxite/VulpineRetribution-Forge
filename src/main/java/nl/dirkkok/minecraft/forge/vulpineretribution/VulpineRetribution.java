package nl.dirkkok.minecraft.forge.vulpineretribution;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("vulpineretribution")
public class VulpineRetribution {
	private static final boolean DEVELOPMENT = true;

	public VulpineRetribution() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		Entity source = event.getSource().getDirectEntity();
		if (source instanceof PlayerEntity) {
			// In order to test this mod properly I would have to kill a fox. So I just assumed that it works properly based on the fact it works in dev.
			// Snowy foxes and red foxes are the same entity, with a different Type (enum, see source code) so no need to check for something like "instanceof SnowyFoxEntity".
			if (DEVELOPMENT
				? event.getEntity() instanceof ChickenEntity
				: event.getEntity() instanceof FoxEntity) {
				smiteEntity(source);
			}
		}
	}

	private void smiteEntity(Entity target) {
		LightningBoltEntity bolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, target.getCommandSenderWorld());
		bolt.setVisualOnly(true); // Because we explicitly kill the player (regardless of current health or armor)
		bolt.setPos(target.position().x, target.position().y, target.position().z);
		target.getCommandSenderWorld().addFreshEntity(bolt);
		target.kill();
	}
}
