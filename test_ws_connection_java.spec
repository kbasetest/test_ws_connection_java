/*
A KBase module: test_ws_connection_java
*/

module test_ws_connection_java {

    /*
	Returns information about a workspace as per the get_workspace_info method.
    */
    funcdef get_ws_info(int wsid) returns (UnspecifiedObject wsinfo) authentication required;

};
