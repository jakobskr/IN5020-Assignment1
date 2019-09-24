package HelloApp;

public class SongProfileImpl extends SongProfile {
	
	public SongProfileImpl() {
		total_play_count = 0;
		top_three_users = new TopThreeUsersImpl();
	}
	
	public SongProfileImpl(int playCount, TopThreeUsersImpl topList) {
		total_play_count = playCount;
		top_three_users = topList;
	}
}
