import * as React from "react";
import * as ReactDOM from "react-dom";

/*
let loader = new KanbanLoader(live2dData);
loader.load();
*/

import { WebSocketClient } from "./modules/testProtobuf";

let webSocket = new WebSocketClient("ws://localhost:8090/ws");

/*
import {example} from './message/example';
let loginRequest = example.LoginRequest.create({uuid: 9});
let arr = example.LoginRequest.encode(loginRequest).finish();
console.info(arr);
*/