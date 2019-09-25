package HelloApp;

import java.util.LinkedList;

public class UserProfileImpl extends UserProfile implements Comparable<UserProfileImpl> {

	public LinkedList<SongCounterImpl> song_list;
	//super.top_three_songs = new TopThreeSongsImpl();
	
	public UserProfileImpl() {
		super.total_play_count = 0;
		super.top_three_songs = new TopThreeSongsImpl();
		song_list = new LinkedList<SongCounterImpl>();
	}
	
	public UserProfileImpl(String id) {
		user_id = id;
		super.total_play_count = 0;
		super.top_three_songs = new TopThreeSongsImpl();
		song_list = new LinkedList<SongCounterImpl>();
	}
	
	@Override
	public int compareTo(UserProfileImpl other) {
		return this.total_play_count - other.total_play_count;
	}
	
	
	
}
