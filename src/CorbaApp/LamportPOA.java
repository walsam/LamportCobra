package CorbaApp;


/**
* CorbaApp/LamportPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Processus.idl
* Sunday, 12 January 2020 15:02:16 o'clock WEST
*/

public abstract class LamportPOA extends org.omg.PortableServer.Servant
 implements CorbaApp.LamportOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("sendRequest", new java.lang.Integer (0));
    _methods.put ("sendACK", new java.lang.Integer (1));
    _methods.put ("sendREL", new java.lang.Integer (2));
    _methods.put ("receiveMessage", new java.lang.Integer (3));
    _methods.put ("enterSC", new java.lang.Integer (4));
    _methods.put ("leftSC", new java.lang.Integer (5));
    _methods.put ("demandeSC", new java.lang.Integer (6));
    _methods.put ("shutdown", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CorbaApp/Lamport/sendRequest
       {
         int id = in.read_long ();
         int h = in.read_long ();
         this.sendRequest (id, h);
         out = $rh.createReply();
         break;
       }

       case 1:  // CorbaApp/Lamport/sendACK
       {
         int id2Send = in.read_long ();
         this.sendACK (id2Send);
         out = $rh.createReply();
         break;
       }

       case 2:  // CorbaApp/Lamport/sendREL
       {
         int id2Send = in.read_long ();
         this.sendREL (id2Send);
         out = $rh.createReply();
         break;
       }

       case 3:  // CorbaApp/Lamport/receiveMessage
       {
         int id_rec = in.read_long ();
         int type = in.read_long ();
         int h_rec = in.read_long ();
         this.receiveMessage (id_rec, type, h_rec);
         out = $rh.createReply();
         break;
       }

       case 4:  // CorbaApp/Lamport/enterSC
       {
         this.enterSC ();
         out = $rh.createReply();
         break;
       }

       case 5:  // CorbaApp/Lamport/leftSC
       {
         this.leftSC ();
         out = $rh.createReply();
         break;
       }

       case 6:  // CorbaApp/Lamport/demandeSC
       {
         this.demandeSC ();
         out = $rh.createReply();
         break;
       }

       case 7:  // CorbaApp/Lamport/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CorbaApp/Lamport:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Lamport _this() 
  {
    return LamportHelper.narrow(
    super._this_object());
  }

  public Lamport _this(org.omg.CORBA.ORB orb) 
  {
    return LamportHelper.narrow(
    super._this_object(orb));
  }


} // class LamportPOA