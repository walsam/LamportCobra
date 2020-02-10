package Lamport;

import CorbaApp.Lamport;
import CorbaApp.LamportHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class Client {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef =   orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            Lamport pr1 = LamportHelper.narrow(ncRef.resolve_str("Processus1"));
            Lamport pr2 = LamportHelper.narrow(ncRef.resolve_str("Processus2"));
            System.out.println("-----------------------------------");
            pr1.demandeSC();
            pr2.demandeSC();

        }
        catch (Exception e) {
            System.out.println("Hello Client exception: " + e);
            e.printStackTrace();
        }
    }
}