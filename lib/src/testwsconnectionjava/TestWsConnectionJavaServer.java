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

import kbasereport.CreateParams;
import kbasereport.KBaseReportClient;
import kbasereport.Report;
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
    private static final String gitCommitHash = "";

    //BEGIN_CLASS_HEADER
    private final URL callbackURL;
    private final Path scratch;
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
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: run_test_ws_connection_java</p>
     * <pre>
     * This example function accepts any number of parameters and returns results in a KBaseReport
     * </pre>
     * @param   params   instance of mapping from String to unspecified object
     * @return   parameter "output" of type {@link testwsconnectionjava.ReportResults ReportResults}
     */
    @JsonServerMethod(rpc = "test_ws_connection_java.run_test_ws_connection_java", async=true)
    public ReportResults runTestWsConnectionJava(Map<String,UObject> params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ReportResults returnVal = null;
        //BEGIN run_test_ws_connection_java
        final KBaseReportClient kbr = new KBaseReportClient(callbackURL, authPart);
        kbr.setIsInsecureHttpConnectionAllowed(true);
        final String ws_name = params.get("workspace_name").asInstance();
        final String parameter_1 = params.get("parameter_1").asInstance();
        final ReportInfo report = kbr.create(new CreateParams()
                                        .withWorkspaceName(ws_name)
                                        .withReport(new Report()
                                                .withTextMessage(parameter_1)
                                                .withObjectsCreated(new java.util.ArrayList<WorkspaceObject>())));

        returnVal = new ReportResults()
                        .withReportName(report.getName())
                        .withReportRef(report.getRef());
        //END run_test_ws_connection_java
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
