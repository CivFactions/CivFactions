package com.gordonfreemanq.sabre.cmd;

import com.gordonfreemanq.sabre.Lang;
import com.gordonfreemanq.sabre.core.CommandVisibility;
import com.gordonfreemanq.sabre.core.Permission;


public class CmdAdminFly extends SabreCommand {

	public CmdAdminFly()
	{
		super();
		this.aliases.add("fly");

		this.setHelpShort("Toggles fly status");
		
		this.errorOnToManyArgs = false;
		this.senderMustBePlayer = true;
		this.permission = Permission.ADMIN.node;
		this.visibility = CommandVisibility.SECRET;
	}

	@Override
	public void perform() 
	{
		if (me.getPlayer().isFlying()) {
			me.getPlayer().setFlying(false);
			me.getPlayer().setAllowFlight(false);
			me.msg(Lang.adminFlyOff);
		} else {
			me.getPlayer().setAllowFlight(true);
			me.getPlayer().setFlying(true);
			me.msg(Lang.adminFlyOn);
		}
	}
}
