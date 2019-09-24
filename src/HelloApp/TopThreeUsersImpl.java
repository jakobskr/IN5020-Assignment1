package HelloApp;


public class TopThreeUsersImpl extends TopThreeUsers{
	//UserCounterImpl[] topThreeUsers = new UserCounterImpl[3];
	
	public TopThreeUsersImpl() {
		super.topThreeUsers = new UserCounter[3];
	}
	
	public String toString() {
		String ret = "";
		for(int i = 0; i < 3; i ++) {
			ret += i + " " + topThreeUsers[i].user_id + "(" + topThreeUsers[i].songid_play_time + ") | "; 
		}
		return ret;
	}
	
	public void insert(UserCounter user) {
		try {for(int i = 0; i < topThreeUsers.length; i++) {
			if(topThreeUsers[i] == null) {
				topThreeUsers[i] = user;
				break;
			}
			if(topThreeUsers[i].songid_play_time < user.songid_play_time) {
				UserCounter temp = topThreeUsers[i];
				topThreeUsers[i] = user;
				user = temp;
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
			if(topThreeUsers[i] == null) {
				return true;
			}
		}
		if (topThreeUsers[2].songid_play_time < val) return true;

		return false;
	}
	
}
