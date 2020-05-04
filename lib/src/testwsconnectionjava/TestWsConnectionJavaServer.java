package testwsconnectionjava;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UObject;

//BEGIN_HEADER
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.net.MalformedURLException;

import workspace.WorkspaceClient;
import workspace.WorkspaceIdentity;
import kbasereport.CreateParams;
import kbasereport.KBaseReportClient;
import kbasereport.SimpleReport;
import kbasereport.ReportInfo;
import kbasereport.WorkspaceObject;
import testwsconnectionjava.ReportResults;
//END_HEADER

/**
 * <p>Original spec-file module name: test_ws_connection_java</p>
 * <pre>
 * A KBase module: test_ws_connection_java
 * </pre>
 */
public class TestWsConnectionJavaServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "0.0.1";
    private static final String gitUrl = "";
    private static final String gitCommitHash = "af546d9f9c88325a226f5a669e43d94e73e10078";

    //BEGIN_CLASS_HEADER
    private final URL callbackURL;
    private final Path scratch;
    private final URL wsURL;
    //END_CLASS_HEADER

    public TestWsConnectionJavaServer() throws Exception {
        super("test_ws_connection_java");
        //BEGIN_CONSTRUCTOR
    final String sdkURL = System.getenv("SDK_CALLBACK_URL");
    try {
        callbackURL = new URL(sdkURL);
        System.out.println("Got SDK_CALLBACK_URL " + callbackURL);
    } catch (MalformedURLException e) {
        throw new IllegalArgumentException("Invalid SDK callback url: " + sdkURL, e);
    }
    scratch = Paths.get(super.config.get("scratch"));
    wsURL = new URL(super.config.get("workspace-url"));
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: get_ws_info</p>
     * <pre>
     * Returns information about a workspace as per the get_workspace_info method.
     * </pre>
     * @param   wsid   instance of Long
     * @return   parameter "wsinfo" of unspecified object
     */
    @JsonServerMethod(rpc = "test_ws_connection_java.get_ws_info", async=true)
    public UObject getWsInfo(Long wsid, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        UObject returnVal = null;
        //BEGIN get_ws_info
	final WorkspaceClient ws = new WorkspaceClient(wsURL, authPart);
	final Object wsinfo = ws.getWorkspaceInfo(new WorkspaceIdentity().withId(wsid));
	returnVal = new UObject(wsinfo);
        //END get_ws_info
        return returnVal;
    }
    @JsonServerMethod(rpc = "test_ws_connection_java.status")
    public Map<String, Object> status() {
        Map<String, Object> returnVal = null;
        //BEGIN_STATUS
        returnVal = new LinkedHashMap<String, Object>();
        returnVal.put("state", "OK");
        returnVal.put("message", "");
        returnVal.put("version", version);
        returnVal.put("git_url", gitUrl);
        returnVal.put("git_commit_hash", gitCommitHash);
        //END_STATUS
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new TestWsConnectionJavaServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new TestWsConnectionJavaServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
