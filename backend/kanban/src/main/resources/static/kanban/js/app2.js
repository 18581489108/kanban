;

if (typeof dcodeIO === 'undefined' || !dcodeIO.ProtoBuf) {
    throw(new Error("ProtoBuf.js is not present. Please see www/index.html for manual setup instructions."));
}
// Initialize ProtoBuf.js
var ProtoBuf = dcodeIO.ProtoBuf;
var WSMessage = ProtoBuf.loadProtoFile("./protobuf/Test.proto").build("test.WSMessage");


var wsUri = "ws://localhost:8090/ws";

var webSocket = new WebSocket(wsUri);
webSocket.binaryType = "arraybuffer"; // We are talking binary

webSocket.onopen = function(evt) {
}

function sendMsg() {
    var msg = new WSMessage();
    
    msg.id = "1";
    msg.content = "test";
    msg.sender = "web";
    msg.tiem = new Date().toDateString();
    
    webSocket.send(msg.toArrayBuffer());
}

function stringToBytes(str) {
    var ch, st, re = []; 
    for (var i = 0; i < str.length; i++ ) { 
        ch = str.charCodeAt(i);  // get char  
        st = [];                 // set up "stack"  

       do {  
            st.push( ch & 0xFF );  // push byte to stack  
            ch = ch >> 8;          // shift value down by 1 byte  
        }    

        while ( ch );  
        // add stack contents to result  
        // done because chars have "wrong" endianness  
        re = re.concat( st.reverse() ); 
    }  
    // return an array of bytes  
    return re;  
}
















































