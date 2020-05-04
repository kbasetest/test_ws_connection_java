/*
A KBase module: test_ws_connection_java
*/

module test_ws_connection_java {
    typedef structure {
        string report_name;
        string report_ref;
    } ReportResults;

    /*
        This example function accepts any number of parameters and returns results in a KBaseReport
    */
    funcdef run_test_ws_connection_java(mapping<string,UnspecifiedObject> params) returns (ReportResults output) authentication required;

};
