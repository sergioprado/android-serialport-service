package vendor.labworks.serialportmanager;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.IBinder;
import android.util.Log;
import vendor.labworks.serialportservice.*;

public class SerialPortManager {

    private static final String SERVICE_NAME = "serialport";
    private static SerialPortManager instance;
    private static ISerialPortService service;

    private SerialPortManager() {
        // the service is registered by a persistent app that only starts after
        // the user is unlocked, so the caller may need to wait for it
        IBinder binder = ServiceManager.waitForService(SERVICE_NAME);
        service = ISerialPortService.Stub.asInterface(binder);
        if (service == null) {
            Log.e("SerialPortManager", "Failed to get reference to serialport");
        }
    }

    public static SerialPortManager getInstance() {
        if(instance == null) {
            instance = new SerialPortManager();
        }
        return instance;
    }

    public void tx(byte data) throws RemoteException {
        service.tx(data);
    }

    public byte rx() throws RemoteException {
        return service.rx();
    }
}
