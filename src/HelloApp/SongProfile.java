package HelloApp;


/**
* HelloApp/SongProfile.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Hello.idl
* Monday, September 23, 2019 1:50:32 PM CEST
*/

public abstract class SongProfile implements org.omg.CORBA.portable.StreamableValue
{
  public int total_play_count = (int)0;
  public HelloApp.TopThreeUsers top_three_users = null;

  private static String[] _truncatable_ids = {
    HelloApp.SongProfileHelper.id ()
  };

  public String[] _truncatable_ids() {
    return _truncatable_ids;
  }

  public void _read (org.omg.CORBA.portable.InputStream istream)
  {
    this.total_play_count = istream.read_long ();
    this.top_three_users = HelloApp.TopThreeUsersHelper.read (istream);
  }

  public void _write (org.omg.CORBA.portable.OutputStream ostream)
  {
    ostream.write_long (this.total_play_count);
    HelloApp.TopThreeUsersHelper.write (ostream, this.top_three_users);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return HelloApp.SongProfileHelper.type ();
  }
} // class SongProfile