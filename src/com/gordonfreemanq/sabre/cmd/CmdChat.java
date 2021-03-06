package com.gordonfreemanq.sabre.cmd;

import com.gordonfreemanq.sabre.Lang;
import com.gordonfreemanq.sabre.groups.SabreGroup;


public class CmdChat extends SabreCommand {

	public CmdChat()
	{
		super();
		this.aliases.add("chat");
		this.aliases.add("c");
		
		this.optionalArgs.put("group", "none");

		this.setHelpShort("Changes your chat channnel");

		senderMustBePlayer = true;
	}

	@Override
	public void perform() 
	{
		if (args.size() == 0) {
			me.setChatChannel(gc);
			msg(Lang.chatMovedGlobal);
			return;
		}
		
		String groupName = this.argAsString(0);
		
		SabreGroup g = this.checkGroupExists(groupName);
		if (g == null) {
			return;
		}
		
		// Get the correct name
		groupName = g.getName();
		
		if (g.getMember(me) == null) {
			msg(Lang.groupNotMember, groupName);
			return;
		}
		
		me.setChatChannel(g);
		msg(Lang.chatMovedGroup, groupName);
		msg(Lang.chatMovedGroupHelp, groupName);
		
		if (g.isChatMutedBy(me)) {
			g.setChatMutedBy(me,  false);
			me.msg(Lang.groupUnmute, groupName);
		}
	}
}
