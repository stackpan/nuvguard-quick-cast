package io.github.stackpan.nuvguardquickcast;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;

public class NuvguardQuickCastClient implements ClientModInitializer, ClientTickEvents.EndTick {

	private static boolean[] wasPressed = new boolean[6];

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ClientTickEvents.END_CLIENT_TICK.register(this);
	}

	@Override
	public void onEndTick(MinecraftClient client) {
		if (client.player == null || client.interactionManager == null) return;

		for (int i = 2; i <= 5; i++) {
			boolean isPressed = client.options.hotbarKeys[i - 1].isPressed();

			if (isPressed && !wasPressed[i]) {
				// Switch to hotbar slot
				client.player.getInventory().setSelectedSlot(i - 1);

				// Simulate right-click
				interact(client);
			}

			wasPressed[i] = isPressed;
		}
	}

	private void interact(MinecraftClient client) {
		ClientPlayerEntity player = client.player;
		if (player == null || client.interactionManager == null) return;

		// Simulate right-click
		client.interactionManager.interactItem(player, Hand.MAIN_HAND);
	}
}