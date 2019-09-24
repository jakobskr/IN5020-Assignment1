package HelloApp;


public class UserCounterImpl extends UserCounter {
	public UserCounterImpl(String user, int count) {
		super();
		this.user_id = user;
		this.songid_play_time = count;
	}
	
	public UserCounterImpl() {
		// :)
	}
}
