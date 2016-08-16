package com.civfactions.sabre.cmd;

import org.bukkit.inventory.ItemStack;

import com.civfactions.sabre.util.Permission;


public class CmdAdminMore extends SabreCommand {

	public CmdAdminMore()
	{
		super();
		this.aliases.add("more");

		this.setHelpShort("Gives more items");
		
		this.errorOnToManyArgs = false;
		this.permission = Permission.ADMIN.node;
		this.visibility = CommandVisibility.SECRET;
	}

	@Override
	public void perform() 
	{
		ItemStack item = me.getPlayer().getInventory().getItemInHand();
		item.setAmount(64);
		me.getPlayer().getInventory().setItemInHand(item);
	}
}