package com.civfactions.sabre.groups;

import java.util.UUID;

import com.civfactions.sabre.SabrePlayer;
import com.civfactions.sabre.util.Guard;
import com.civfactions.sabre.util.INamed;

/**
 * @brief Represents a member of a sabre group
 * @author GFQ
 */
public class SabreMember implements INamed {

	private final SabreGroup group;
	private final SabrePlayer player;
	private Rank rank;
	
	/**
	 * @brief Creates a new instance of a player member
	 * @param group The player group
	 * @param player The player instance
	 * @param rank The player group rank
	 */
	public SabreMember(SabreGroup group, SabrePlayer player, Rank rank) {
		Guard.ArgumentNotNull(group, "group");
		Guard.ArgumentNotNull(player, "player");
		Guard.ArgumentNotNull(rank, "rank");
		
		this.group = group;
		this.player = player;
		this.rank = rank;
	}
	
	
	/**
	 * Gets the player instance
	 * @return The player instance
	 */
	public SabrePlayer getPlayer() {
		return this.player;
	}
	
	
	/**
	 * Gets the player group
	 * @return The group instance
	 */
	public SabreGroup getGroup() {
		return this.group;
	}
	
	
	/**
	 * Gets the player ID
	 * @return The player ID
	 */
	public UUID getID() {
		return this.player.getID();
	}
	
	
	/**
	 * Gets the player name
	 * @return The player name
	 */
	public String getName() {
		return this.player.getName();
	}
	
	
	/**
	 * Gets the player rank in the group
	 * @return The player rank
	 */
	public Rank getRank() {
		return this.rank;
	}
	
	
	/**
	 * Sets the player rank in the group
	 * @param rank The new rank
	 */
	public void setRank(Rank rank) {
		Guard.ArgumentNotNull(rank, "rank");
		
		// Nothing to do
		if (this.rank == rank) {
			return;
		}
		
		// Don't allow multiple group owners
		if (rank == Rank.OWNER) {
			for(SabreMember m : group.getMembers()) {
				if (m.getRank() == Rank.OWNER) {
					throw new RuntimeException(String.format("Tried to set multiple owners for group %s", group.getFullName()));
				}
			}
		}
		
		// Don't allow demoting the owner unless the group is unlocked
		if (this.rank == Rank.OWNER && group.isLocked()) {
			throw new RuntimeException(String.format("Tried to change rank of owner for locked group %s", group.getFullName()));
		}
		
		this.rank = rank;
	}
	
	
	/**
	 * Checks if the member can kick another member
	 * @param other The member to kick
	 * @return true if he can be kicked
	 */
	public boolean canKickMember(SabreMember other) {
		Guard.ArgumentNotNull(other, "other");
		
		if (this.group.equals(other.group)) {
			int rankMe = rank.ordinal();
			int rankOther = other.rank.ordinal();
			int rankMin = Rank.OFFICER.ordinal();
			
			if (rankMe > rankOther && rankMe >= rankMin) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Gets whether the member can invite other members
	 * @return true if the member can invite other members
	 */
	public boolean canInvite() {
		if (this.rank.ordinal() >= Rank.OFFICER.ordinal()) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Gets whether the member is owner
	 * @return true if the member is owner
	 */
	public boolean isOwner() {
		return rank.equals(Rank.OWNER);
	}
	
	
	/**
	 * Gets whether the member can build blocks
	 * @return true if the member can build blocks
	 */
	public boolean canBuild() {
		if (this.rank.ordinal() >= Rank.BUILDER.ordinal()) {
			return true;
		}
		
		return false;
	}
}
