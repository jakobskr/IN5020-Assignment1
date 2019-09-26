package server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import HelloApp.HelloPOA;
import HelloApp.SongCounterImpl;
import HelloApp.SongProfileImpl;
import HelloApp.TopThreeSongs;
import HelloApp.TopThreeSongsImpl;
import HelloApp.TopThreeUsers;
import HelloApp.TopThreeUsersImpl;
import HelloApp.UserCounterImpl;
import HelloApp.UserProfileImpl;


public class HelloServant extends HelloPOA {
	
	//if the given song id < = maxIndexOfFile1, then we know that the song info is in train_triplets1.
	//
	private String maxIndexOfFile1 = "SOMPBQG12AC3DF6169";
	private HashMap<String, SongProfileImpl> song_cache = new HashMap<String, SongProfileImpl>(); 
	private HashMap<String, UserProfileImpl> user_cache = new HashMap<String, UserProfileImpl>();
	private HashMap<String, Integer> user_storage = new HashMap<String,Integer>();
	
	public void log(String input) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String now = sdf.format(cal.getTime());
        System.out.println(now + ": "+input);
	}
			
	public void build_song_cache() {
		
		log("building song cache");
		
        try {
        	File f = new File("train_triplets_1.txt");
        	Scanner fileScanner = new Scanner(f);
        	
        	String cur_song = "";
        	int count_played = 0;
        	
        	TopThreeUsersImpl topList = new TopThreeUsersImpl();
        	
        	while(fileScanner.hasNextLine()) {
        		String[] split = fileScanner.nextLine().split("\\s+");
        		
        		if(! split[0].equalsIgnoreCase(cur_song)) {
        			if (! cur_song.equalsIgnoreCase("")) {
        				song_cache.put(cur_song, new SongProfileImpl(count_played, topList));     				
        			}
        			cur_song = split[0];
        			count_played = 0;
    				topList = new TopThreeUsersImpl();
        		}
        		int played = Integer.parseInt(split[2]);
        		count_played += played;
        		if(topList.insertable(played)) {
        			topList.insert(new UserCounterImpl (split[1],played));
        		}
        		
        		if(user_storage.containsKey(split[1])) {
        			user_storage.put(split[1], user_storage.get(split[1]) + played);
        		}
        		
        		else user_storage.put(split[1],played);
        		
        	}
        	
        	
        	
        	
        	fileScanner.close();
        	
    		System.out.println("found this i guess : " + user_storage.get("caae83a17bf6d278a6b4c2173df5d86854ad9a68"));

        	
			song_cache.put(cur_song, new SongProfileImpl(count_played, topList));     				

			
			f = new File("train_triplets_2.txt");
        	fileScanner = new Scanner(f);
        	
        	cur_song = "";
        	count_played = 0;
        	
        	topList = new TopThreeUsersImpl();
        	
        	while(fileScanner.hasNextLine()) {
        		String[] split = fileScanner.nextLine().split("\\s+");
        		
        		if(! split[0].equalsIgnoreCase(cur_song)) {
        			if (! cur_song.equalsIgnoreCase("")) {
        				song_cache.put(cur_song, new SongProfileImpl(count_played, topList));     				
        			}
        			cur_song = split[0];
        			count_played = 0;
    				topList = new TopThreeUsersImpl();
        		}
        		int played = Integer.parseInt(split[2]);
        		count_played += played;
        		if(topList.insertable(played)) {
        			topList.insert(new UserCounterImpl (split[1],played));
        		}
        		
        		if(user_storage.containsKey(split[1])) {
        			user_storage.put(split[1], user_storage.get(split[1]) + played);
        		}
        		
        		else user_storage.put(split[1],played);
        		
        	}
        	
        	fileScanner.close();
			song_cache.put(cur_song, new SongProfileImpl(count_played, topList));     
        	
			
			SongProfileImpl sp = song_cache.get("SOMPBQG12AC3DF6169");
			
			log("Created song_cache, tallied users");
        	
    		class Node implements Comparable<Node>{
    			String id;
    			Integer val;
    			public Node(String id, Integer val) {
    				this.id = id;
    				this.val = val;
    			}
    			public int compareTo(Node other) {
    				return this.val-other.val;
    			}
				
    		}
        		
        	LinkedList<Node> ls = new LinkedList<Node>();
        	for(String s : user_storage.keySet()) {
        		ls.add(new Node(s, user_storage.get(s)));        		
        	}
        	user_storage = null;
        	Node[] arr = (Node[]) ls.toArray();
        	
        	log("Sorting users");
        	
        	Arrays.sort(arr,Collections.reverseOrder());
        	
        	log("Sorted users");
        	
        	
        	
        	for(int i = 0; i < 1000; i++) {
        		UserProfileImpl temp = new UserProfileImpl(arr[i].id,arr[i].val);
        		user_cache.put(temp.user_id,temp);
        		if(i < 6) {
        			System.out.println("user "+arr[i].id+", total play time: "+arr[i].val);
        		}
        	}
        	
        	
			
			//System.out.format("SOMPBQG12AC3DF6169: count_played %d topThreeUsers: %s\n", sp.total_play_count, sp.top_three_users);
			//System.out.println("Actual count " + getTimesPlayed("SOMPBQG12AC3DF6169"));
			
			log("Done buidling cache");
			
			
        } catch (Exception e) {
        	
        }
	}
	
	@Override
	public String sayHello(String message) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String now = sdf.format(cal.getTime());

        System.out.println("Message from client: " + message);
        return "Hello from Server at " + now;

	}
	
	@Override
	public String sayHelp(String help) {
		System.out.println("recieved a call for help from client: " + help);
		return "this is a answer for help";
	}
	
	
	public int getTimesPlayed(String song) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
		System.out.printf("%s: Invocation of getTimesPlayed(%s)\n", sdf.format(cal.getTime()), song);
		
		SongProfileImpl lookup = song_cache.get(song);
		
		if (lookup != null) {
			return lookup.total_play_count;
		}
		
		
		int retVal = 0;
		
		if (song.compareTo(maxIndexOfFile1) <= 0) {
			try {
				File f = new File("train_triplets_1.txt");
				Scanner fileScanner = new Scanner(f);
				
				while(fileScanner.hasNextLine()) {
					String s = fileScanner.nextLine();
					String[] split = s.split("\\s+");
					if(split[0].equalsIgnoreCase(song)) {
						while(split[0].equalsIgnoreCase(song)) {
							retVal += Integer.parseInt(split[2]);
							split = fileScanner.nextLine().split("\\s+");
						}
						fileScanner.close();
						return retVal;						
					}	
				}
				fileScanner.close();
			} catch (NullPointerException e) {
				System.out.println("yeehaw");
				return retVal;
			}
			
			catch (Exception e) {
				//yeehaw
			}
		}
		
		else {
			try {
				File f = new File("train_triplets_2.txt");
				Scanner fileScanner = new Scanner(f);
								
				while(fileScanner.hasNextLine()) {
					String s = fileScanner.nextLine();
					String[] split = s.split("\\s+");
					if(split[0].equalsIgnoreCase(song)) {
						while(split[0].equalsIgnoreCase(song)) {
							retVal += Integer.parseInt(split[2]);
							split = fileScanner.nextLine().split("\\s+");
						}
						fileScanner.close();
						return retVal;						
					}	
				}
				fileScanner.close();
			} catch (NullPointerException e) {
				return retVal;
			}
			
			catch (Exception e) {
				//yeehaw
			}
		}
		
		return retVal;
	}
	
	
	
	@Override
	public int getTimesPlayedByUser(String user, String song) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
		System.out.printf("%s: Invocation of getTimesPlayedByUser(%s, %s)\n", sdf.format(cal.getTime()), user,  song);
		if (song.compareTo(maxIndexOfFile1) < 0) {
			//file 1
			try {
				File f = new File("train_triplets_1.txt");
				Scanner fileScanner = new Scanner(f);
				
				/*naive solution, reads and adds to file. no client sided caching	
				 * Start time before it starts filereading
				 * 
				 * 
				 * end time is when we recieved the final data from server.
				 */
				while(fileScanner.hasNext()) {
					String l = fileScanner.nextLine();
					//System.out.println("Line: " + l);
					String[] split = l.split("\\s+");
					
					if(split[0].equalsIgnoreCase(song)) {
						while(split[0].equalsIgnoreCase(song)) {
							if(split[1].equalsIgnoreCase(user)) {
								fileScanner.close();
								return Integer.parseInt(split[2]);
							}
							
							l = fileScanner.nextLine();
							//System.out.println("Line: " + l);
							split = l.split("\\s+");
						}
					}
				}
				
				fileScanner.close();
				return -1;
			}
			
			catch (Exception e) {
				//:(
			}
		}
		
		else {
			try {
				File f = new File("train_triplets_2.txt");
				Scanner fileScanner = new Scanner(f);
				
				/*naive solution, reads and adds to file. no client sided caching	
				 * Start time before it starts filereading
				 * 
				 * 
				 * end time is when we recieved the final data from server.
				 */
				while(fileScanner.hasNext()) {
					String l = fileScanner.nextLine();
					//System.out.println("Line: " + l);
					String[] split = l.split("\\s+");
					
					if(split[0].equalsIgnoreCase(song)) {
						while(split[0].equalsIgnoreCase(song)) {
							if(split[1].equalsIgnoreCase(user)) {
								fileScanner.close();
								return  Integer.parseInt(split[2]);
							}
							
							l = fileScanner.nextLine();
							//System.out.println("Line: " + l);
							split = l.split("\\s+");
						}
					}
				}
				
				fileScanner.close();
				return -1;
			}
			
			catch (Exception e) {
				//:(
			}
		}
		
		return -1;
		
	}

	@Override
	public TopThreeUsersImpl getTopThreeUsersBySong(String song) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
		System.out.printf("%s: Invocation of getTopThreeUsersBySong(%s)\n", sdf.format(cal.getTime()),  song);
		
		String filename;
		TopThreeUsersImpl topList = new TopThreeUsersImpl();
		
		SongProfileImpl lookup = song_cache.get(song);
		if (lookup != null) {
			return (TopThreeUsersImpl) lookup.top_three_users;
		}
		
		System.out.println("foer scanner");
		if (song.compareTo(maxIndexOfFile1) <= 0) {
			filename = "train_triplets_1.txt";
		}else {
			filename = "train_triplets_2.txt";
		}
		
		
		try {
			File f = new File(filename);
			Scanner fileScanner = new Scanner(f);
			System.out.println("etter scanner");

			/*naive solution, reads and adds to file. no client sided caching	
			 * Start time before it starts filereading
			 * 
			 * 
			 * end time is when we recieved the final data from server.
			 */
			while(fileScanner.hasNext()) {
				String l = fileScanner.nextLine();
				//System.out.println("Line: " + l);
				String[] split = l.split("\\s+");
				
				if(split[0].equalsIgnoreCase(song)) {
					System.out.println("fant " + song);
					while(split[0].equalsIgnoreCase(song)) {
						if(topList.insertable(Integer.parseInt(split[2]))) {
							//System.out.println("insertable " + split[2]);
							topList.insert(new UserCounterImpl(split[1],Integer.parseInt(split[2])));
						}
						
						l = fileScanner.nextLine();
						//System.out.println("Line: " + l);
						split = l.split("\\s+");
					}
					System.out.println("ferdig med aa inserte");
					System.out.println(topList);
					break;
				}
			}
			
			fileScanner.close();
		}
		
		catch (Exception e) {
			//:(
		}
		
		//we are assuming that each song has atleast three users who have listened to that song
		return topList;
	}

	@Override
	public TopThreeSongsImpl getTopThreeSongsByUser(String user) {
		// TODO Auto-generated method stub
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
		System.out.printf("%s: Invocation of getTopThreeSongsByUser(%s)\n", sdf.format(cal.getTime()),  user);
		TopThreeSongsImpl topList = new TopThreeSongsImpl();
		
		try {
			File f = new File("train_triplets_1.txt");
			Scanner fileScanner = new Scanner(f);
			
			while (fileScanner.hasNextLine()) {
				String[] split = fileScanner.nextLine().split("\\s+");
				if (split[1].equalsIgnoreCase(user)) {
					if (topList.insertable(Integer.parseInt(split[2]))) {
						topList.insert( new SongCounterImpl(split[0],Integer.parseInt(split[2])));
					}
				}
			}
			
			fileScanner.close();
			
			
			//System.out.println("done wtih file 1 :))");
			f = new File("train_triplets_2.txt");
			fileScanner = new Scanner(f);
			
			while (fileScanner.hasNextLine()) {
				String[] split = fileScanner.nextLine().split("\\s+");
				if (split[1].equalsIgnoreCase(user)) {
					if (topList.insertable(Integer.parseInt(split[2]))) {
						topList.insert( new SongCounterImpl(split[0],Integer.parseInt(split[2])));
					}
				}
			}
			
			fileScanner.close();
			

		} catch (Exception e) {
			
		}
		
		
		
		
		return topList;
	}
	
	
}
