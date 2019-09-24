package client;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import HelloApp.Hello;
import HelloApp.HelloHelper;
import HelloApp.TopThreeSongsImpl;
import HelloApp.TopThreeUsers;

import HelloApp.TopThreeUsersImpl;

public class HelloClient {

	static Hello helloImpl;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		try{
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is 
			// part of the Interoperable naming Service.  
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// resolve the Object Reference in Naming
			String name = "Hello";
			helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));
			
			Calendar cal = Calendar.getInstance();
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        	String now = sdf.format(cal.getTime());

			String message = helloImpl.sayHello("Hello from Client at " + now);
			
			System.out.println("Message from Server: " + message);
			
			
			File f = new File("input.txt");
			Scanner fileScanner = new Scanner(f);
			File naive = new File("naive.txt");
			naive.createNewFile();
			FileOutputStream fos = new FileOutputStream(naive);
			
			
			
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
				
				if(split[0].equalsIgnoreCase("getTimesPlayed")) {
					long time = System.nanoTime();
					System.out.println(split[0] + " " + split[1] + " " + helloImpl.getTimesPlayed(split[1]) + " " + ((System.nanoTime() - time) / 1000000.0));
					//System.out.println(split[0] + " of " + split[1]);
				}
				
				else if (split[0].equalsIgnoreCase("getTimesPlayedByUser")) {
					long time = System.nanoTime();				
					
					//String data = String.format("getTimesUser %s played %s %d times. %d\u202Fms \n", split[1], split[2], helloImpl.getTimesPlayedByUser(split[1], split[2]),  (System.nanoTime() - time) / 1000000);
					
					//fos.write(data.getBytes());
										
				}
				
				else if (split[0].equalsIgnoreCase("getTopThreeUsersBySong")) {
					long time = System.nanoTime();				
					TopThreeUsersImpl ret =  (TopThreeUsersImpl) helloImpl.getTopThreeUsersBySong(split[1]);
					System.out.println(ret);
					
				}
				
				else if(split[0].equalsIgnoreCase("getTopThreeSongsByUser")) {
					long time = System.nanoTime();
					TopThreeSongsImpl ret = (TopThreeSongsImpl) helloImpl.getTopThreeSongsByUser(split[1]);
					System.out.println(ret);
				}
				
			}
				
			fileScanner.close();
			fos.flush();
			fos.close();
			
			/*
			 * Now we do the same as above, but we try to cache the data we receive from the server
			 * 
			 * timer starts when we open the file with the scanner,
			 * 
			 * timer ends when we are done handling the last response from the server
			 * 
			 */
			
			
			

		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}	
	}

}
