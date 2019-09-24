package HelloApp;

public class TopThreeSongsImpl extends TopThreeSongs{
	public TopThreeSongsImpl() {
		topThreeSongs = new SongCounter[3];
	}
	
	
	public String toString() {
		String ret = ": ";
		for(int i = 0; i < 3; i ++) {
			ret += i + " " + topThreeSongs[i].song_id + "(" + topThreeSongs[i].songid_play_time + ") | "; 
		}
		return ret;
	}
	
	public void insert(SongCounter song) {
		try {for(int i = 0; i < topThreeSongs.length; i++) {
			if(topThreeSongs[i] == null) {
				topThreeSongs[i] = song;
				break;
			}
			if(topThreeSongs[i].songid_play_time < song.songid_play_time) {
				SongCounter temp = topThreeSongs[i];
				topThreeSongs[i] = song;
				song = temp;
			}
		} }
		catch (NullPointerException e) {
			System.out.println("her kommer noe");
			e.printStackTrace(System.out);
			System.exit(0);
		}
	}
	
	public boolean insertable(int val) {
		for (int i = 0; i < 3; i++) {
			if(topThreeSongs[i] == null) {
				return true;
			}
		}
		if (topThreeSongs[2].songid_play_time < val) return true;

		return false;
	}
	
}
