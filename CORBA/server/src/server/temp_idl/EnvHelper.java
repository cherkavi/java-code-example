
/**
* EnvHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Env.idl
* ������, 14 ������ 2008 �. 19:03:35 EET
*/

abstract public class EnvHelper
{
  private static String  _id = "IDL:Env:1.0";

  public static void insert (org.omg.CORBA.Any a, Env that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Env extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (EnvHelper.id (), "Env");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Env read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_EnvStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Env value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Env narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Env)
      return (Env)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _EnvStub stub = new _EnvStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static Env unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Env)
      return (Env)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _EnvStub stub = new _EnvStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
