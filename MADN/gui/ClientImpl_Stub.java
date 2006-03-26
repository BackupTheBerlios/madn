// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package gui;

public final class ClientImpl_Stub
    extends java.rmi.server.RemoteStub
    implements gui.Client, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_decrementAttempts_0;
    private static java.lang.reflect.Method $method_existsServer_1;
    private static java.lang.reflect.Method $method_getAttempts_2;
    private static java.lang.reflect.Method $method_getColor_3;
    private static java.lang.reflect.Method $method_getDiceResult_4;
    private static java.lang.reflect.Method $method_getNickname_5;
    private static java.lang.reflect.Method $method_getServer_6;
    private static java.lang.reflect.Method $method_getStatus_7;
    private static java.lang.reflect.Method $method_hasAttemptsLeft_8;
    private static java.lang.reflect.Method $method_recieveErrorMessage_9;
    private static java.lang.reflect.Method $method_recieveMessage_10;
    private static java.lang.reflect.Method $method_recieveRadioMessage_11;
    private static java.lang.reflect.Method $method_refresh_12;
    private static java.lang.reflect.Method $method_setAttempts_13;
    private static java.lang.reflect.Method $method_setClientListener_14;
    private static java.lang.reflect.Method $method_setColor_15;
    private static java.lang.reflect.Method $method_setDiceResult_16;
    private static java.lang.reflect.Method $method_setServer_17;
    private static java.lang.reflect.Method $method_setStatus_18;
    private static java.lang.reflect.Method $method_throwTheDice_19;
    
    static {
	try {
	    $method_decrementAttempts_0 = gui.Client.class.getMethod("decrementAttempts", new java.lang.Class[] {});
	    $method_existsServer_1 = gui.Client.class.getMethod("existsServer", new java.lang.Class[] {});
	    $method_getAttempts_2 = gui.Client.class.getMethod("getAttempts", new java.lang.Class[] {});
	    $method_getColor_3 = gui.Client.class.getMethod("getColor", new java.lang.Class[] {});
	    $method_getDiceResult_4 = gui.Client.class.getMethod("getDiceResult", new java.lang.Class[] {});
	    $method_getNickname_5 = gui.Client.class.getMethod("getNickname", new java.lang.Class[] {});
	    $method_getServer_6 = gui.Client.class.getMethod("getServer", new java.lang.Class[] {});
	    $method_getStatus_7 = gui.Client.class.getMethod("getStatus", new java.lang.Class[] {});
	    $method_hasAttemptsLeft_8 = gui.Client.class.getMethod("hasAttemptsLeft", new java.lang.Class[] {});
	    $method_recieveErrorMessage_9 = gui.Client.class.getMethod("recieveErrorMessage", new java.lang.Class[] {java.lang.String.class});
	    $method_recieveMessage_10 = gui.Client.class.getMethod("recieveMessage", new java.lang.Class[] {java.lang.String.class});
	    $method_recieveRadioMessage_11 = gui.Client.class.getMethod("recieveRadioMessage", new java.lang.Class[] {java.lang.String.class});
	    $method_refresh_12 = gui.Client.class.getMethod("refresh", new java.lang.Class[] {boolean.class});
	    $method_setAttempts_13 = gui.Client.class.getMethod("setAttempts", new java.lang.Class[] {int.class});
	    $method_setClientListener_14 = gui.Client.class.getMethod("setClientListener", new java.lang.Class[] {gui.ClientListener.class});
	    $method_setColor_15 = gui.Client.class.getMethod("setColor", new java.lang.Class[] {int.class});
	    $method_setDiceResult_16 = gui.Client.class.getMethod("setDiceResult", new java.lang.Class[] {int.class});
	    $method_setServer_17 = gui.Client.class.getMethod("setServer", new java.lang.Class[] {gui.Server.class});
	    $method_setStatus_18 = gui.Client.class.getMethod("setStatus", new java.lang.Class[] {int.class});
	    $method_throwTheDice_19 = gui.Client.class.getMethod("throwTheDice", new java.lang.Class[] {});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public ClientImpl_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of decrementAttempts()
    public void decrementAttempts()
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_decrementAttempts_0, null, 5918742747605151335L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of existsServer()
    public boolean existsServer()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_existsServer_1, null, 2170980658904311152L);
	    return ((java.lang.Boolean) $result).booleanValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getAttempts()
    public int getAttempts()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getAttempts_2, null, -615863090769100455L);
	    return ((java.lang.Integer) $result).intValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getColor()
    public int getColor()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getColor_3, null, 2799746608478781808L);
	    return ((java.lang.Integer) $result).intValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getDiceResult()
    public int getDiceResult()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getDiceResult_4, null, -4492352963711334292L);
	    return ((java.lang.Integer) $result).intValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getNickname()
    public java.lang.String getNickname()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getNickname_5, null, 6879335616512200463L);
	    return ((java.lang.String) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getServer()
    public gui.Server getServer()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getServer_6, null, -5382833405163169280L);
	    return ((gui.Server) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getStatus()
    public int getStatus()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getStatus_7, null, 5311013277333051704L);
	    return ((java.lang.Integer) $result).intValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of hasAttemptsLeft()
    public boolean hasAttemptsLeft()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_hasAttemptsLeft_8, null, 7398880508018578432L);
	    return ((java.lang.Boolean) $result).booleanValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of recieveErrorMessage(String)
    public void recieveErrorMessage(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_recieveErrorMessage_9, new java.lang.Object[] {$param_String_1}, 5968531226949328872L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of recieveMessage(String)
    public void recieveMessage(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_recieveMessage_10, new java.lang.Object[] {$param_String_1}, 3943030463258928448L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of recieveRadioMessage(String)
    public void recieveRadioMessage(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_recieveRadioMessage_11, new java.lang.Object[] {$param_String_1}, 5760690271768378364L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of refresh(boolean)
    public void refresh(boolean $param_boolean_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_refresh_12, new java.lang.Object[] {new java.lang.Boolean($param_boolean_1)}, 6145905710067060550L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setAttempts(int)
    public void setAttempts(int $param_int_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setAttempts_13, new java.lang.Object[] {new java.lang.Integer($param_int_1)}, 3725936236797379388L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setClientListener(ClientListener)
    public void setClientListener(gui.ClientListener $param_ClientListener_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setClientListener_14, new java.lang.Object[] {$param_ClientListener_1}, 1919407857807121188L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setColor(int)
    public void setColor(int $param_int_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setColor_15, new java.lang.Object[] {new java.lang.Integer($param_int_1)}, 4937115091876740062L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setDiceResult(int)
    public void setDiceResult(int $param_int_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setDiceResult_16, new java.lang.Object[] {new java.lang.Integer($param_int_1)}, -2726844814753774606L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setServer(Server)
    public void setServer(gui.Server $param_Server_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setServer_17, new java.lang.Object[] {$param_Server_1}, 5553493545045261026L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setStatus(int)
    public void setStatus(int $param_int_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setStatus_18, new java.lang.Object[] {new java.lang.Integer($param_int_1)}, -6351359262559720857L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of throwTheDice()
    public void throwTheDice()
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_throwTheDice_19, null, -3673238369464274525L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
