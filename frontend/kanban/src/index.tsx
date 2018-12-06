/*
import * as React from "react";
import * as ReactDOM from "react-dom";
*/
/*
let loader = new KanbanLoader(live2dData);
loader.load();
*/

//import { WebSocketClient } from "./modules/testProtobuf";

//let webSocket = new WebSocketClient("ws://localhost:8090/ws");

/*
import {example} from './message/example';
let loginRequest = example.LoginRequest.create({uuid: 9});
let arr = example.LoginRequest.encode(loginRequest).finish();
console.info(arr);
*/



import { example } from './message/example';

let loginRequest = example.LoginRequest.create({uuid: 9});

show(loginRequest);
function show(arg : any) {
    //arg.test();
    //console.log(arg.constructor.name);
    /*
    let arr = arg.constructor.encode(arg).finish() as Uint8Array;
    console.log(arr);
    */
    //console.log(arg.constructor);
}
                                                                                                                                